package com.zo2m4bie.firstitemexpandablelistview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Scroller;

import com.zo2m4bie.firstitemexpandablelistview.R;
import com.zo2m4bie.firstitemexpandablelistview.adapter.IMyAdapter;
import com.zo2m4bie.firstitemexpandablelistview.controller.HaveMaxController;
import com.zo2m4bie.firstitemexpandablelistview.controller.IMixMinController;
import com.zo2m4bie.firstitemexpandablelistview.controller.MaxMinController;
import com.zo2m4bie.firstitemexpandablelistview.holder.ISelfExpandableHolder;

/**
 * Created by dima on 1/6/16.
 */
public class SelfExpandebleListView extends AdapterView<IMyAdapter> {

    public static final String HAVE_MAX = "HAVE_MAX";
    public static final String HAVE_MAX_MIN = "HAVE_MAX_MIN";

    /** Distance to drag before we intercept touch events */
    private static final int TOUCH_SCROLL_THRESHOLD = 10;

    /** User is not touching the list */
    private static final int TOUCH_STATE_RESTING = 0;

    /** User is touching the list and right now it's still a "click" */
    private static final int TOUCH_STATE_CLICK = 1;

    /** User is scrolling the list */
    private static final int TOUCH_STATE_SCROLL = 2;

    /** Current touch state */
    private int mTouchState = TOUCH_STATE_RESTING;

    /** Represents an invalid child index */
    private static final int INVALID_INDEX = -1;

    private int mFirstVisibleItem;
    private IMixMinController mMixMinController;

    //it's offset to current(visible) first item
    private int mOffsetFromTopItem;
    //it's offset to first top item
    private int mOffsetFromTop;

    private int mTouchStartY;
    private int mTouchStartX;

    private int mHalfScreen;

    private IMyAdapter mAdapter;
    private ISelfExpandableHolder mCurrentChanging;

    private VelocityTracker velocityTracker;
    private int minimumVelocity;
    private int maximumVelocity;
    private Flinger flinger;

    private SimplyRunnable mSimplyRunnable;

    public SelfExpandebleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMinMaxConteoller(attrs);
        init(attrs);
    }

    private void initMinMaxConteoller(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SelfExpandebleListView, 0, 0);
        try {
            String type = ta.getString(R.styleable.SelfExpandebleListView_minMaxType);
            if(HAVE_MAX_MIN.equals(type)){
                mMixMinController = new MaxMinController();
            } else if(HAVE_MAX.equals(type)){
                mMixMinController = new HaveMaxController();
            } else {
                throw new IllegalArgumentException("You didn't set up max min type or did it wrong. minMaxType has to be HAVE_MAX or HAVE_MAX_MIN");
            }


        } finally {
            ta.recycle();
        }
    }

    public void init(AttributeSet attrs){
        mFirstVisibleItem = 0;

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.minimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.maximumVelocity = configuration.getScaledMaximumFlingVelocity();
        flinger = new Flinger(getContext());

        mMixMinController.init(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHalfScreen = heightMeasureSpec / 2;
    }

    @Override
    public IMyAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(IMyAdapter adapter) {
        mAdapter = adapter;
        removeAllViewsInLayout();
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("MotionEvent", "draw start");
        if (mAdapter == null) {
            return;
        }
        removeExtraChild();
        fillUp();
        fillListDown();
        positionItems();

    }

    private void scroolToItemStartIfNeeded() {
        removeCallbacks(mSimplyRunnable);
        int firstTop = getChildAt(0).getTop();
        if(firstTop == 0){
            return;
        }
        View secondView = getChildAt(1);
        int distance = 0;
        if (secondView != null) {
            int height = secondView.getHeight();
            if (mMixMinController.getHalfMax() < (height)) { //bottom to top
                distance = secondView.getTop();
            } else { // top to bottom
                distance = firstTop;
            }
        } else {
            distance = firstTop;
        }
        if (distance != 0) {
            mSimplyRunnable = new SimplyRunnable(distance);
            post(mSimplyRunnable);
        }
    }

    private void scroolToItemStartIfNeeded(int lastDiff) {
        removeCallbacks(mSimplyRunnable);
        int firstTop = getChildAt(0).getTop();
        if(firstTop == 0){
            return;
        }
        View secondView = getChildAt(1);
        int distance = 0;
        if (secondView != null) {
            if (lastDiff > 0) { //bottom to top
                distance = secondView.getTop();
            } else { // top to bottom
                distance = firstTop;
            }
        }
        if (distance != 0) {
            mSimplyRunnable = new SimplyRunnable(distance);
            post(mSimplyRunnable);
        }
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }

    private void fillUp() {

        View firstChild = getChildAt(0);

        while( mFirstVisibleItem > 0 && firstChild != null && (mOffsetFromTopItem)  > 0) {
            View tmpView = getChildAt(1);
            if(tmpView != null) {
                mMixMinController.measureToMinValue(tmpView, getWidth());
                mCurrentChanging.heightPercentage(0);
            }
            mFirstVisibleItem--;
            View newBottomChild = mAdapter.getView(mFirstVisibleItem, null, this, 100);
            addAndMeasureChild(newBottomChild, 0);
            mOffsetFromTopItem -= mMixMinController.getMaxValue();
        }
    }

    private void fillListDown() {

        int childLastPosition = getChildCount() - 1;
        View lastChild = getChildAt(childLastPosition);
        int endPosition = getChildCount() + mFirstVisibleItem - 1;
        int bottom = 0;
        if(lastChild != null){
            bottom = lastChild.getBottom() + mOffsetFromTopItem;
        }
        int itemsCount = mAdapter.getCount()-1;
        while (bottom < getHeight() && endPosition < itemsCount ) {

            Log.d("tag", "fillDown");
            endPosition++;
            View newBottomChild = mAdapter.getView(endPosition, null, this, (endPosition == 0) ? 100 : 0);
            addAndMeasureChild(newBottomChild, -1);
            bottom += newBottomChild.getMeasuredHeight();
        }
    }

    private void addAndMeasureChild(View child, int position) {
        Log.d("tag", "addAndMeasureChild");
        int childCount = getChildCount();
        LayoutParams params = child.getLayoutParams();
        if (params == null) { // calculate normal height
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }

        addViewInLayout(child, position, params, true);//sdd view with normal parameters
        int itemWidth = getWidth();

        if (position == 0) {
            child.measure(MeasureSpec.EXACTLY | itemWidth, MeasureSpec.EXACTLY | mMixMinController.getMaxValue());
            mCurrentChanging = (ISelfExpandableHolder) getChildAt(1).getTag();
            if(childCount > 1){
                View childSecond = getChildAt(1);
                mMixMinController.measureAndSaveMinValue(childSecond, getWidth());
            }
        } else if (position == -1 && childCount == 0) {
            child.measure(MeasureSpec.EXACTLY | itemWidth, MeasureSpec.EXACTLY | mMixMinController.getMaxValue());

        } else if (position == -1) { // add item to bottom. it means we add in bottom size
            mMixMinController.measureToMinValue(child, getWidth());
            if (childCount == 1) {
                mMixMinController.measureAndSaveMinValue(child, getWidth());
                mCurrentChanging = (ISelfExpandableHolder) child.getTag();
            }
        }
    }

    /**
     * Positions the children at the "correct" positions
     */
    private void positionItems() {
        int childCount = getChildCount();
        if(childCount == 0){
            return;
        }
        int top = mOffsetFromTopItem;
        Log.d("TEST", "positionItems = " + mOffsetFromTopItem);

        Log.d("tag", "positionItems");
        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);

            int width = child.getMeasuredWidth();
            int height = 0;//mMaxValue;//child.getMeasuredHeight();
            int left = 0;//(getWidth() - width) / 2;
            int topUse = top;
            if(index == 0){ //if it's first item. It allways have max height value
                height = mMixMinController.getMaxValue();
                if((childCount - 1) == mFirstVisibleItem && topUse < 0){
                    topUse = 0;
                }
            }else if(index == 1){// second item it have floating height
                int minV = mMixMinController.getMinValue(child);
                int diff = mMixMinController.getDifferentMaxMin(minV);
                height = (int) (((float)diff)/mMixMinController.getMaxValue() * mOffsetFromTopItem * -1);
                mCurrentChanging.heightPercentage((height * 100) / diff);
                height += minV;
                child.measure(MeasureSpec.EXACTLY | getWidth(), MeasureSpec.EXACTLY | height);
            } else { // else items have min height
                height = mMixMinController.getMinValue(child);//mMinValue;
            }

            Log.d("heightissues",  index + " measuredheight = " + child.getMeasuredHeight() + " position height = " + height + " mOffsetFromTopItem = " + mOffsetFromTopItem + " mFirstVisibleItem = " + mFirstVisibleItem);
            child.layout(left, topUse, left + width, topUse + height);
            top += height;
        }
    }

    /**
     * Remove extra child frpm top
     */
    private void removeExtraChild(){
        View firstChild = getChildAt(0);
        int childCount = getChildCount();

        View tmpView = getChildAt(1); // second view
        while(tmpView != null && firstChild.getBottom() <= 0){
            Log.d("TEST", "RemoveTopItem = " + firstChild.getBottom());
            mOffsetFromTopItem += firstChild.getHeight();
            removeViewInLayout(firstChild);
            tmpView.measure(MeasureSpec.EXACTLY | getWidth(), MeasureSpec.EXACTLY | mMixMinController.getMaxValue());
            mCurrentChanging.heightPercentage(100);
            mFirstVisibleItem++;
            childCount--;
            firstChild = tmpView;
            Log.d("TEST", "RemoveTopItem = " + mOffsetFromTopItem);
            tmpView = getChildAt(1);
            if(tmpView != null){
                mMixMinController.setSecondItemHeight(tmpView.getMeasuredHeight());
                mCurrentChanging = (ISelfExpandableHolder) tmpView.getTag();
            }
        }

        tmpView = getChildAt(childCount - 1); // last view
        while(tmpView != null && tmpView.getTop() >= getHeight()){
            Log.d("TEST", "RemoveBottomItem = " + tmpView.getTop() + " getHeight() = " + getHeight());
            removeViewInLayout(tmpView);
            childCount--;
            tmpView = getChildAt(childCount - 1);
        }


    }

    @Override
    public void scrollBy(int x, int y) {
        Log.d("Fliper", "ScrollBy x = " + x + " y = " + y);
        if(changeTopPosition(y)){
            requestLayout();
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (getChildCount() == 0) {
            return false;
        }
        if (velocityTracker == null) { // If we do not have velocity tracker
            velocityTracker = VelocityTracker.obtain(); // then get one
        }
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("MotionEvent", "Down");
                startTouch(event);
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("MotionEvent", "Move");

                if (mTouchState == TOUCH_STATE_CLICK) {
                    startScrollIfNeeded(event);
                }
                if (mTouchState == TOUCH_STATE_SCROLL) {
                    int distance = (int) (mTouchStartY - event.getY());
                    if(changeTopPosition(distance)){
                        mTouchStartY = (int) event.getY();
                        requestLayout();
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mTouchState == TOUCH_STATE_CLICK) {
                    Log.d("MotionEvent", "mTouchState == TOUCH_STATE_CLICK");
                    clickChildAt((int)event.getX(), (int)event.getY());
                } else {
                    final VelocityTracker velocityTracker = this.velocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
                    int velocityX = (int) velocityTracker.getXVelocity();
                    int velocityY = (int) velocityTracker.getYVelocity();
                    Log.d("Fliper", "velocityX " + velocityX + " velocityY = " + velocityY);

                    if (Math.abs(velocityY) > minimumVelocity) {
                        flinger.start(0, -mOffsetFromTop, velocityX, velocityY, 0, -mOffsetFromTop  * getHeight());
                    } else {
                        if (this.velocityTracker != null) { // If the velocity less than threshold
                            this.velocityTracker.recycle(); // recycle the tracker
                            this.velocityTracker = null;
                        }
                        scroolToItemStartIfNeeded();
                    }
                }
                endTouch();
                break;

            default:
                endTouch();
                break;

        }
        return true;
    }

    private void endTouch() {
        mTouchState = TOUCH_STATE_RESTING;
    }

    /**
     * Calls the item click listener for the child with at the specified
     * coordinates
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    private void clickChildAt(final int x, final int y) {
        final int index = getContainingChildIndex(x, y);
        if (index != INVALID_INDEX) {
            final View itemView = getChildAt(index);
            final int position = mFirstVisibleItem + index;
            final long id = mAdapter.getItemId(position);
            performItemClick(itemView, position, id);
        }
    }
    private int getContainingChildIndex(final int x, final int y) {
        Rect mRect = new Rect();
        for (int index = 0; index < getChildCount(); index++) {
            getChildAt(index).getHitRect(mRect);
            if (mRect.contains(x, y)) {
                return index;
            }
        }
        return INVALID_INDEX;
    }
    /**
     * Move top position
     */
    private boolean changeTopPosition(int distance){
        if (distance < 0) { //top to bottom
            if (mFirstVisibleItem == 0) {

                if (mOffsetFromTopItem == 0) {
                    return false;
                }
                distance = (distance < mOffsetFromTopItem) ? mOffsetFromTopItem : distance;
            }
        } else if (distance > 0) { // bottom to top
            if (mFirstVisibleItem == (mAdapter.getCount() - 2)) {//if wee see only last item(it's end of list)


                int top = getChildAt(0).getTop();
                Log.d("TEST", "changeTopPosition = " + top + " distance = " + distance);
                distance = ((distance - top) > mMixMinController.getMaxValue()) ? (mMixMinController.getMaxValue() + top): distance;
            }else if(mFirstVisibleItem == (mAdapter.getCount() - 1)){

                int top = getChildAt(0).getTop();
                if (top == 0) {//TODO remove get child at
                    return false;
                }
                Log.d("TEST", "changeTopPosition = " + top + " distance = " + distance);
                distance = (distance > top) ? top : distance;
            }
        }
        mOffsetFromTopItem -= distance;
        mOffsetFromTop -= distance;
        return true;
    }

    private void startTouch(final MotionEvent event) {

        removeCallbacks(mSimplyRunnable);
        flinger.forceFinished();
        // save the start place
        mTouchStartX = (int)event.getX();
        mTouchStartY = (int)event.getY();

        // we don't know if it's a click or a scroll yet, but until we know
        // assume it's a click
        mTouchState = TOUCH_STATE_CLICK;
    }

    private boolean startScrollIfNeeded(final MotionEvent event) {
        final int xPos = (int)event.getX();
        final int yPos = (int)event.getY();
        if (xPos < mTouchStartX - TOUCH_SCROLL_THRESHOLD
                || xPos > mTouchStartX + TOUCH_SCROLL_THRESHOLD
                || yPos < mTouchStartY - TOUCH_SCROLL_THRESHOLD
                || yPos > mTouchStartY + TOUCH_SCROLL_THRESHOLD) {
            mTouchState = TOUCH_STATE_SCROLL;
            return true;
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(event);
                return false;

            case MotionEvent.ACTION_MOVE:
                return startScrollIfNeeded(event);

            default:
                endTouch();
                return false;
        }
    }

    class SimplyRunnable implements Runnable {

        public static final int PART_OF_SCREEN_FOR_MOVE = 10;

        int mScrollDistance=0;
        int mPart = 1;
        int mPartAbs = 1;

        public SimplyRunnable(int scrollDistance){
            mScrollDistance = scrollDistance;
            mPart = mMixMinController.getMaxValue() / ((mScrollDistance > 0) ? PART_OF_SCREEN_FOR_MOVE : -PART_OF_SCREEN_FOR_MOVE);
            mPartAbs = Math.abs(mPart);
        }

        @Override
        public void run() {
            if(mScrollDistance != 0){
                if(Math.abs(mScrollDistance) < mPartAbs){
                    mPart = mScrollDistance;
                }
                mScrollDistance -= mPart;
                scrollBy(0, mPart);
                // the list is not at rest, so schedule a new frame
                postDelayed(this, 1);
            }
        }
    }

    // http://stackoverflow.com/a/6219382/842697
    private class Flinger implements Runnable {
        private final Scroller scroller;

        private int lastX = 0;
        private int lastY = 0;
        private int lastDiff = 0;

        Flinger(Context context) {
            scroller = new Scroller(context);
        }

        void start(int initX, int initY, int initialVelocityX, int initialVelocityY, int maxX, int maxY) {
            scroller.fling(initX, initY, initialVelocityX, initialVelocityY, 0, maxX, 0, maxY);

            lastX = initX;
            lastY = initY;
            post(this);
        }

        public void run() {
            if (scroller.isFinished()) {
                scroolToItemStartIfNeeded(lastDiff);
                return;
            }

            boolean more = scroller.computeScrollOffset();
            int x = scroller.getCurrX();
            int y = scroller.getCurrY();
            int diffX = lastX - x;
            int diffY = lastY - y;
            if (diffX != 0 || diffY != 0) {
                Log.d("Fliper", "Y = " + y + " lastX = " + lastY + " diffY = " + diffY + " moffset " + mOffsetFromTop);
                scrollBy(diffX, diffY);
                lastX = x;
                lastY = y;
                lastDiff = diffY;
            }

            if (more) {
                post(this);
            } else {
                scroolToItemStartIfNeeded(lastDiff);
            }
        }

        boolean isFinished() {
            return scroller.isFinished();
        }

        void forceFinished() {
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
        }
    }
}
