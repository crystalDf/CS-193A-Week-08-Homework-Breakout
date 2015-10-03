package com.star.breakout;


import android.graphics.Paint;

public class Label {

    private Paint mPaint;
    private String mLabel = "";

    private float mOffsetLeft;
    private float mOffsetTop;

    public Label() {
        mPaint = new Paint();
    }

    public Paint getPaint() {
        return mPaint;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public float getOffsetLeft() {
        return mOffsetLeft;
    }

    public void setOffsetLeft(float offsetLeft) {
        mOffsetLeft = offsetLeft;
    }

    public float getOffsetTop() {
        return mOffsetTop;
    }

    public void setOffsetTop(float offsetTop) {
        mOffsetTop = offsetTop;
    }
}
