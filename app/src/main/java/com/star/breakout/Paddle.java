package com.star.breakout;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class Paddle {

    private static final int COLOR = Color.BLACK;

    private static final float WIDTH = 60;
    private static final float HEIGHT = 10;
    private static final float OFFSET_BOTTOM = 30;

    private RectF mRectF;
    private Paint mPaint;

    private float mWidth;
    private float mHeight;

    private Point mPoint;

    private static Paddle sPaddle;

    private Paddle() {

    }

    private static Paddle getInstance() {
        if (sPaddle == null) {
            synchronized (Paddle.class) {
                if (sPaddle == null) {
                    sPaddle = new Paddle();
                }
            }
        }

        return sPaddle;
    }
}
