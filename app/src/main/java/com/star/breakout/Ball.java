package com.star.breakout;


import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class Ball {

    protected static final float PAUSE_TIME = 20;

    private static int sAmount;

    private RectF mRectF;
    private Paint mPaint;

    private float mRadius;

    private Point mPoint;

    private float mVelocityX;
    private float mVelocityY;

    private boolean mLaserCapable;
    private boolean mSticky;

    private boolean mRemoved;

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

    public Point getPoint() {
        return mPoint;
    }

    public void setPoint(Point point) {
        mPoint = point;
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

    public boolean isLaserCapable() {
        return mLaserCapable;
    }

    public void setLaserCapable(boolean laserCapable) {
        mLaserCapable = laserCapable;
    }

    public boolean isSticky() {
        return mSticky;
    }

    public void setSticky(boolean sticky) {
        mSticky = sticky;
    }

    public boolean isRemoved() {
        return mRemoved;
    }

    public void setRemoved(boolean removed) {
        mRemoved = removed;
    }
}
