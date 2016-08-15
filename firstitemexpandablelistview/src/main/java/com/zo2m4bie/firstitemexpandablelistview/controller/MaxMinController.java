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
public class MaxMinController implements IMaxMinController {

    private int mMaxValue, mMinValue, mDifferentMaxMin, mHalfMax;

    @Override
    public void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelfExpandableListView, 0, 0);
        try {
            mMaxValue = (int) ta.getDimension(R.styleable.SelfExpandableListView_itemMaxHeight, 0);
            mMinValue = (int) ta.getDimension(R.styleable.SelfExpandableListView_itemMinHeight, 0);
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
    public void setSecondItemHeight(View view, int width) {

    }

    @Override
    public void setFirstItemHeight(View view) {

    }

    @Override
    public int getSecondItemMinHeight() {
        return mMinValue;
    }

    @Override
    public void measureSecondItem(View tmpView, int width) {

    }

    @Override
    public void measureFirstItemToMax(View tmpView, int width) {
        tmpView.measure(View.MeasureSpec.EXACTLY | width, View.MeasureSpec.EXACTLY | mMaxValue);
    }

    @Override
    public void measureFirstItem(View child, int itemWidth) {
        child.measure(View.MeasureSpec.EXACTLY | itemWidth, View.MeasureSpec.EXACTLY | mMaxValue);
    }

    @Override
    public int getFirstItemMaxValue() {
        return mMaxValue;
    }

    @Override
    public float getSecondItemMaxValue() {
        return mMaxValue;
    }

    @Override
    public int getMaxVauleFor(View childAt, int itemWidth) {
        return mMaxValue;
    }

    @Override
    public void setMaxOfMaxHeight(int halfScreen) {
        if(mMaxValue > halfScreen){
            mMaxValue = halfScreen;
        }
    }

    @Override
    public int getFirstItemMinValue(View child) {
        return mMinValue;
    }
}
