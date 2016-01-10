package com.zo2m4bie.firstitemexpandablelistview.controller;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zo2m4bie.firstitemexpandablelistview.R;


/**
 * Created by dima on 1/10/16.
 */
public class MaxMinController implements IMixMinController{

    private int mMaxValue, mMinValue, mDifferentMaxMin, mHalfMax;

    @Override
    public void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelfExpandebleListView, 0, 0);
        try {
            mMaxValue = (int) ta.getDimension(R.styleable.SelfExpandebleListView_itemMaxHeight, 0);
            mMinValue = (int) ta.getDimension(R.styleable.SelfExpandebleListView_itemMinHeight, 0);
            Log.d("Sizeissues", " max = " + mMaxValue + " min = " + mMinValue);
            mDifferentMaxMin = mMaxValue - mMinValue;
            mHalfMax = mMaxValue / 2;

        } finally {
            ta.recycle();
        }
    }

    @Override
    public int getHalfMax() {
        return mHalfMax;
    }

    @Override
    public int getMaxValue() {
        return mMaxValue;
    }

    @Override
    public int getMinValue(View child) {
        return mMinValue;
    }

    @Override
    public int getDifferentMaxMin(int minV) {
        return mDifferentMaxMin;
    }

    @Override
    public void measureToMinValue(View tmpView, int viewWidth) {
        tmpView.measure(View.MeasureSpec.EXACTLY | viewWidth, View.MeasureSpec.EXACTLY | mMinValue);
    }

    @Override
    public void setSecondItemHeight(int measuredHeight) {

    }

    @Override
    public int getSecondItemMinHeight() {
        return mMinValue;
    }

    @Override
    public void measureAndSaveMinValue(View childSecond, int width) {

    }
}
