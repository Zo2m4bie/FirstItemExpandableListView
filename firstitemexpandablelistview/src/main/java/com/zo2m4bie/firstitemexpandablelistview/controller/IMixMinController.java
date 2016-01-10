package com.zo2m4bie.firstitemexpandablelistview.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dima on 1/10/16.
 */
public interface IMixMinController {

    void init(Context context, AttributeSet attrs);

    int getHalfMax();

    int getMaxValue();

    int getMinValue(View child);

    int getDifferentMaxMin(int minV);

    void measureToMinValue(View tmpView, int viewWidth);

    void setSecondItemHeight(int measuredHeight);

    int getSecondItemMinHeight();

    void measureAndSaveMinValue(View childSecond, int width);
}
