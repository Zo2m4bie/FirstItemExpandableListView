package com.zo2m4bie.firstitemexpandablelistview.controller;

import android.content.Context;
import android.widget.Scroller;

import com.zo2m4bie.firstitemexpandablelistview.view.SelfExpandableListView;

/**
 * Created by dima on 8/11/16.
 * From
 * http://stackoverflow.com/a/6219382/842697
 */
public class Flinger implements Runnable {

    private SelfExpandableListView mSelfExpandebleListView;
    private final Scroller mScroller;

    private int mLastX = 0;
    private int mLastY = 0;
    private int mLastDiff = 0;

    public Flinger(Context context, SelfExpandableListView selfExpandebleListView) {
        mScroller = new Scroller(context);
        mSelfExpandebleListView = selfExpandebleListView;
    }

    public void start(int initX, int initY, int initialVelocityX, int initialVelocityY, int maxX, int maxY) {
        mScroller.fling(initX, initY, initialVelocityX, initialVelocityY, 0, maxX, 0, maxY);

        mLastX = initX;
        mLastY = initY;
        mSelfExpandebleListView.post(this);
    }

    public void run() {
        if (mScroller.isFinished()) {
            mSelfExpandebleListView.scrollToItemStartIfNeeded(mLastDiff);
            return;
        }

        boolean more = mScroller.computeScrollOffset();
        int x = mScroller.getCurrX();
        int y = mScroller.getCurrY();
        int diffX = mLastX - x;
        int diffY = mLastY - y;
        if (diffX != 0 || diffY != 0) {
            mSelfExpandebleListView.scrollBy(diffX, diffY);
            mLastX = x;
            mLastY = y;
            mLastDiff = diffY;
        }

        if (more) {
            mSelfExpandebleListView.post(this);
        } else {
            mSelfExpandebleListView.scrollToItemStartIfNeeded(mLastDiff);
        }
    }

    public boolean isFinished() {
        return mScroller.isFinished();
    }

    public void forceFinished() {
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
    }
}