package com.star.breakout;


import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Ball {

    public static final float VELOCITY_RATIO = 0.0025f;

    private RectF mRectF;
    private Paint mPaint;

    private float mVelocityX;
    private float mVelocityY;

    public Ball() {
        mRectF = new RectF();
        mPaint = new Paint();
    }

    public RectF getRectF() {
        return mRectF;
    }

    public Paint getPaint() {
        return mPaint;
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
