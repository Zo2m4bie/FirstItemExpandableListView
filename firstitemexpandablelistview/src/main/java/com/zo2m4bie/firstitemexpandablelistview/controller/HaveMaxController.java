package com.zo2m4bie.firstitemexpandablelistview.controller;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.zo2m4bie.firstitemexpandablelistview.R;


/**
 * Created by dima on 1/10/16.
 */
public class HaveMaxController implements IMaxMinController {

    private int mMaxValue, mHalfMax;
    private int mSecondItemHeight;

    @Override
    public void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelfExpandableListView, 0, 0);
        try {
            mMaxValue = (int) ta.getDimension(R.styleable.SelfExpandableListView_itemMaxHeight, 0);
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
    /**
     * Fix me it wrong
     */
    public int getMinValue(View child) {
        return child.getMeasuredHeight();
    }

    @Override
    public int getDifferentMaxMin(int minV) {
        return mMaxValue - minV;
    }

    @Override
    public void measureToMinValue(View tmpView, int viewWidth) {
        tmpView.measure(View.MeasureSpec.EXACTLY | viewWidth, View.MeasureSpec.UNSPECIFIED);
    }

    @Override
    public void setSecondItemHeight(View view, int width) {
        mSecondItemHeight = view.getMeasuredHeight();
    }

    @Override
    public void setFirstItemHeight(View view) {

    }

    @Override
    public int getSecondItemMinHeight() {
        return mSecondItemHeight;
    }

    @Override
    public void measureSecondItem(View tmpView, int width) {
        tmpView.measure(View.MeasureSpec.EXACTLY | width, View.MeasureSpec.UNSPECIFIED);
        mSecondItemHeight = tmpView.getMeasuredHeight();
    }

    @Override
    public void measureFirstItemToMax(View tmpView, int width) {
        tmpView.measure(View.MeasureSpec.EXACTLY | width, View.MeasureSpec.EXACTLY | mMaxValue);
    }

    @Override
    public void measureFirstItem(View tmpView, int width) {
        tmpView.measure(View.MeasureSpec.EXACTLY | width, View.MeasureSpec.EXACTLY | mMaxValue);
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


}
