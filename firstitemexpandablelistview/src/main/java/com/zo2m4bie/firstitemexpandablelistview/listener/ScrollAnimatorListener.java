package com.zo2m4bie.firstitemexpandablelistview.listener;

import android.animation.ValueAnimator;

import com.zo2m4bie.firstitemexpandablelistview.view.SelfExpandableListView;

/**
 * Created by dima on 8/11/16.
 */
public class ScrollAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    private int mOldDistance ;
    private SelfExpandableListView mSelfExpandebleListView;

    public ScrollAnimatorListener(SelfExpandableListView selfExpandebleListView){
        mOldDistance = 0;
        mSelfExpandebleListView = selfExpandebleListView;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (Integer)animation.getAnimatedValue();
        mSelfExpandebleListView.scrollBy(0, value - mOldDistance);
        mOldDistance = value;
    }
}
