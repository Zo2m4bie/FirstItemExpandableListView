package com.zo2m4bie.firstitemexpandablelistview.utils;

import android.animation.ValueAnimator;

/**
 * Created by dima on 8/11/16.
 */
public class AnimatorsUtils {

    public static final int MAX_SCROLL_TIME = 250;
    public static final int MIN_SCROLL_TIME = 100;

    public static ValueAnimator createAnimator(int distance, ValueAnimator.AnimatorUpdateListener listener){
        ValueAnimator simplyRunnable = ValueAnimator.ofInt(0, distance);
        int distanceAbs = Math.abs(distance);

        //if distance less then MAX_SCROLL_TIME this animation will work too long so we use MIN_SCROLL_TIME
        simplyRunnable.setDuration(distanceAbs < MAX_SCROLL_TIME ? MIN_SCROLL_TIME : MAX_SCROLL_TIME);
        simplyRunnable.addUpdateListener(listener);
        return simplyRunnable;
    }

    public static void cancelAnimator(ValueAnimator animator) {
        animator.removeAllUpdateListeners();
        animator.cancel();
    }
}
