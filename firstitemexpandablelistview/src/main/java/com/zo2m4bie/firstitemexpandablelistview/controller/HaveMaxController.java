package com.zo2m4bie.firstitemexpandablelistview.controller;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.zo2m4bie.firstitemexpandablelistview.R;


/**
 * Created by dima on 1/10/16.
 */
public class HaveMaxController implements IMixMinController{

    private int mMaxValue, mHalfMax;

    @Override
    public void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelfExpandebleListView, 0, 0);
        try {
            mMaxValue = (int) ta.getDimension(R.styleable.SelfExpandebleListView_itemMaxHeight, 0);
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
}
