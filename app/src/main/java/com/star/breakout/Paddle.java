package com.star.breakout;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Paddle {

    private static final int COLOR = Color.BLACK;

    private static final float WIDTH_RATIO = 0.25f;
    private static final float HEIGHT_RATIO = 0.02f;
    private static final float OFFSET_BOTTOM_RATIO = 0.125f;

    private RectF mRectF;
    private Paint mPaint;

    private static Paddle sPaddle;

    private boolean mSticky;

    private Paddle(float screenWidth, float screenHeight) {

        float width = screenWidth * WIDTH_RATIO;
        float height = screenHeight * HEIGHT_RATIO;

        float offsetBottom = screenHeight * OFFSET_BOTTOM_RATIO;

        mRectF = new RectF();
        mPaint = new Paint();

        setSize(width, height);
        setLocation((screenWidth - mRectF.width()) / 2, screenHeight - offsetBottom);
        mPaint.setColor(COLOR);
    }

    public static Paddle getInstance(float screenWidth, float screenHeight) {
        if (sPaddle == null) {
            synchronized (Paddle.class) {
                if (sPaddle == null) {
                    sPaddle = new Paddle(screenWidth, screenHeight);
                }
            }
        }

        return sPaddle;
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

    public boolean isSticky() {
        return mSticky;
    }

    public void setSticky(boolean sticky) {
        mSticky = sticky;
    }

    public void setSize(float width, float height) {
        mRectF.right = mRectF.left + width;
        mRectF.bottom = mRectF.top + height;
    }

    public void setLocation(float x, float y) {
        mRectF.offsetTo(x, y);
    }

    public void multiplySizeByFactor(float factor) {
        setSize(getRectF().width() * factor, getRectF().height() * factor);
    }
}
