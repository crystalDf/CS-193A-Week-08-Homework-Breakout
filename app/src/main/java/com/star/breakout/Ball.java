package com.star.breakout;


import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Ball {

    private RectF mRectF;
    private Paint mPaint;

    private float mRadius;

    private float mVelocityX;
    private float mVelocityY;

    private boolean mRemoved;

    public Ball() {
        mRectF = new RectF();
        mPaint = new Paint();
    }

    public RectF getRectF() {
        return mRectF;
    }

    public void setRectF(RectF rectF) {
        mRectF = rectF;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public float getVelocityX() {
        return mVelocityX;
    }

    public void setVelocityX(float velocityX) {
        mVelocityX = velocityX;
    }

    public float getVelocityY() {
        return mVelocityY;
    }

    public void setVelocityY(float velocityY) {
        mVelocityY = velocityY;
    }

    public boolean isRemoved() {
        return mRemoved;
    }

    public void setRemoved(boolean removed) {
        mRemoved = removed;
    }

    public void setSize(float width, float height) {
        mRectF.right = mRectF.left + width;
        mRectF.bottom = mRectF.top + height;
    }

    public void setLocation(float x, float y) {
        mRectF.offsetTo(x, y);
    }

    public void setVelocity(float dx, float dy) {
        mVelocityX = dx;
        mVelocityY = dy;
    }

    public void move() {
        mRectF.offset(mVelocityX, mVelocityY);
    }
}
