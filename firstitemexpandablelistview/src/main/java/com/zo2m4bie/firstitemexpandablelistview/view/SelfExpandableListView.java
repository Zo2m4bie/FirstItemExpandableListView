package com.zo2m4bie.firstitemexpandablelistview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;

import com.zo2m4bie.firstitemexpandablelistview.R;
import com.zo2m4bie.firstitemexpandablelistview.adapter.IFirstExpandableAdapter;
import com.zo2m4bie.firstitemexpandablelistview.controller.ControllerFabric;
import com.zo2m4bie.firstitemexpandablelistview.controller.Flinger;
import com.zo2m4bie.firstitemexpandablelistview.controller.IMaxMinController;
import com.zo2m4bie.firstitemexpandablelistview.holder.ISelfExpandableHolder;
import com.zo2m4bie.firstitemexpandablelistview.listener.ScrollAnimatorListener;
import com.zo2m4bie.firstitemexpandablelistview.utils.AnimatorsUtils;

/**
 * Created by dima on 1/6/16.
 */
public class SelfExpandableListView extends AdapterView<IFirstExpandableAdapter> {

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
    private IMaxMinController mMaxMinController;

    //it's offset to current(visible) first item
    private int mOffsetFromTopItem;
    //it's offset to first top item
    private int mOffsetFromTop;

    private int mTouchStartY;
    private int mTouchStartX;

    private int mHalfScreen;

    private IFirstExpandableAdapter mAdapter;
    private ISelfExpandableHolder mCurrentChanging;

    private VelocityTracker velocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private Flinger mFlinger;

    // This animator help this view to scroll to top of bigger item
    private ValueAnimator mScrollAnimator;

    public SelfExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMinMaxConteoller(attrs);
        init();
    }

    private void initMinMaxConteoller(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SelfExpandableListView, 0, 0);
        try {
            String type = ta.getString(R.styleable.SelfExpandableListView_minMaxType);
            mMaxMinController = ControllerFabric.getMinMaxController(type);
            mMaxMinController.init(getContext(), attrs);
        } finally {
            ta.recycle();
        }
    }

    public void init(){
        mFirstVisibleItem = 0;
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mFlinger = new Flinger(getContext(), this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHalfScreen = getMeasuredHeight() / 2;
        mMaxMinController.setMaxOfMaxHeight(mHalfScreen);
    }

    @Override
    public IFirstExpandableAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(IFirstExpandableAdapter adapter) {
        mAdapter = adapter;
        removeAllViewsInLayout();
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mAdapter == null) {
            return;
        }
        int offsetFromTopVisibleItem = mOffsetFromTopItem;
        removeExtraChild();
        fillUp();
        fillListDown(offsetFromTopVisibleItem);
        positionItems();

    }

    private void scrollToItemStartIfNeeded() {
        stopAnimator();
        int firstTop = getChildAt(0).getTop();
        if(firstTop == 0){
            return;
        }
        View secondView = getChildAt(1);
        int distance = 0;
        if (secondView != null) {
            int height = secondView.getHeight();
            if (mMaxMinController.getHalfMax() < (height)) { //bottom to top
                distance = secondView.getTop();
            } else { // top to bottom
                distance = firstTop;
            }
        } else {
            distance = firstTop;
        }
        if (distance != 0) {
            startAnimator(distance);
        }
    }

    public void stopAnimator(){
        if(mScrollAnimator != null){
            AnimatorsUtils.cancelAnimator(mScrollAnimator);
            mScrollAnimator = null;
        }
    }

    public void startAnimator(int distance){
        mScrollAnimator = AnimatorsUtils.createAnimator(distance, new ScrollAnimatorListener(this));
        mScrollAnimator.start();

    }

    public void scrollToItemStartIfNeeded(int lastDiff) {
        stopAnimator();
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
            startAnimator(distance);
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
                mMaxMinController.measureToMinValue(tmpView, getWidth());

                mCurrentChanging.heightPercentage(0);
            }
            mFirstVisibleItem--;
            View newBottomChild = mAdapter.getView(mFirstVisibleItem, null, this, 100);
            addAndMeasureChild(newBottomChild, 0);
            mOffsetFromTopItem -= mMaxMinController.getFirstItemMaxValue();
        }
    }

    private void fillListDown(int mOffsetFromTopItem) {
        int childLastPosition = getChildCount() - 1;
        View lastChild = getChildAt(childLastPosition);
        int endPosition = getChildCount() + mFirstVisibleItem - 1;
        int bottom = 0;
        if(lastChild != null){
            bottom = lastChild.getBottom() + mOffsetFromTopItem;
        }
        int itemsCount = mAdapter.getCount()-1;
        while (bottom < getHeight() && endPosition < itemsCount ) {
            endPosition++;
            View newBottomChild = mAdapter.getView(endPosition, null, this, (endPosition == 0) ? 100 : 0);
            addAndMeasureChild(newBottomChild, -1);
            bottom += newBottomChild.getMeasuredHeight();
        }
    }

    private void addAndMeasureChild(View child, int position) {
        int childCount = getChildCount();
        LayoutParams params = child.getLayoutParams();
        if (params == null) { // calculate normal height
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }

        addViewInLayout(child, position, params, true);//sdd view with normal parameters
        int itemWidth = getWidth();

        if (position == 0) {
            mMaxMinController.measureFirstItem(child, itemWidth);
            mCurrentChanging = (ISelfExpandableHolder) getChildAt(1).getTag();
            if(childCount > 1){// first become second
                View childSecond = getChildAt(1);
                mMaxMinController.measureSecondItem(childSecond, itemWidth);
            }
        } else if (position == -1 && childCount == 0) {
            mMaxMinController.measureFirstItem(child, itemWidth);

        } else if (position == -1) { // add item to bottom. it means we add in bottom size
            mMaxMinController.measureToMinValue(child, getWidth());
            if (childCount == 1) {// second item
                mMaxMinController.measureSecondItem(child, getWidth());
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
        float secondItempercent = 0;
        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);

            int width = child.getMeasuredWidth();
            int height = 0;
            int left = 0;
            int topUse = top;
            if(index == 0){ //if it's first item. It allways have max height value
                height = mMaxMinController.getFirstItemMaxValue();
                if((childCount - 1) == mFirstVisibleItem && topUse < 0){ // if it's last item
                    topUse = 0;
                }
                secondItempercent = 1 - ((float)(topUse + height)) / height;
            }else if(index == 1){// second item it have floating height
                int minV = mMaxMinController.getFirstItemMinValue(child);
                int diff = mMaxMinController.getDifferentMaxMin(minV);
                height = (int) (diff * secondItempercent);

                mCurrentChanging.heightPercentage(secondItempercent);
                height += minV;
                child.measure(MeasureSpec.EXACTLY | getWidth(), MeasureSpec.EXACTLY | height);
            } else { // else items have min height
                height = mMaxMinController.getMinValue(child);
            }

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
        while(tmpView != null && (mOffsetFromTopItem + mMaxMinController.getFirstItemMaxValue()) <= 0){
            mOffsetFromTopItem += mMaxMinController.getFirstItemMaxValue();
            removeViewInLayout(firstChild);
            mMaxMinController.measureFirstItemToMax(tmpView, getWidth());
            mCurrentChanging.heightPercentage(100);
            mFirstVisibleItem++;
            childCount--;
            firstChild = tmpView;
            tmpView = getChildAt(1);
            if(tmpView != null){
                mMaxMinController.setSecondItemHeight(tmpView, getWidth());
                mCurrentChanging = (ISelfExpandableHolder) tmpView.getTag();
            }
        }

        tmpView = getChildAt(childCount - 1); // last view
        while(tmpView != null && tmpView.getTop() >= getHeight()){
            removeViewInLayout(tmpView);
            childCount--;
            tmpView = getChildAt(childCount - 1);
        }
    }

    /**
     * The method scrolls to @item
     */
    public void scrollToItem(int item){
        int childCount = mAdapter.getCount();
        if(childCount <= item){
            //No such item in list
            return;
        }
        scrollBy(0, getScrollDistanceTo(item));
    }

    /**
     * The method scrolls to @item
    */
    public void scrollToAnimated(int item){
        int childCount = mAdapter.getCount();
        if(childCount <= item){
            //No such item in list
            return;
        }
        startAnimator(getScrollDistanceTo(item));
    }

    private int getScrollDistanceTo(int item){
        int scroll = mOffsetFromTopItem + mMaxMinController.getFirstItemMaxValue();
        if((item - mFirstVisibleItem) > 1) {
            scroll += mMaxMinController.getSecondItemMaxValue();
            for (int i = (mFirstVisibleItem + 2); i < item; i++) {
                scroll += mMaxMinController.getMaxVauleFor(getChildAt(i), getWidth());
            }
        }
        return scroll;
    }

    @Override
    public void scrollBy(int x, int y) {
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
                startTouch(event);
                break;

            case MotionEvent.ACTION_MOVE:

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
                    clickChildAt((int)event.getX(), (int)event.getY());
                } else {
                    final VelocityTracker velocityTracker = this.velocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int velocityX = (int) velocityTracker.getXVelocity();
                    int velocityY = (int) velocityTracker.getYVelocity();

                    if (Math.abs(velocityY) > mMinimumVelocity) {
                        mFlinger.start(0, -mOffsetFromTop, velocityX, velocityY, 0, -mOffsetFromTop  * getHeight());
                    } else {
                        if (this.velocityTracker != null) { // If the velocity less than threshold
                            this.velocityTracker.recycle(); // recycle the tracker
                            this.velocityTracker = null;
                        }
                        scrollToItemStartIfNeeded();
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
                int firstMaxValue = mMaxMinController.getFirstItemMaxValue();
                distance = ((distance - top) > firstMaxValue) ? (firstMaxValue + top): distance;
            }else if(mFirstVisibleItem == (mAdapter.getCount() - 1)){

                int top = getChildAt(0).getTop();
                if (top == 0) {//TODO remove get child at
                    return false;
                }
                distance = (distance > top) ? top : distance;
            }
        }
        mOffsetFromTopItem -= distance;
        mOffsetFromTop -= distance;
        return true;
    }

    private void startTouch(final MotionEvent event) {
        stopAnimator();
        mFlinger.forceFinished();
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

}
