package com.star.breakout;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

public class BreakoutView extends View {

    private float mScreenWidth;
    private float mScreenHeight;

    private DrawingThread mDrawingThread;

    private Paddle mPaddle;
    private List<Brick> mBricks;
    private List<CommonBall> mCommonBalls;

    public BreakoutView(Context context, AttributeSet attrs) {

        super(context, attrs);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();

        mPaddle = Paddle.getInstance(mScreenWidth, mScreenHeight);
        mBricks = Brick.initBricks(mScreenWidth, mScreenHeight);
        mCommonBalls = CommonBall.initCommonBalls(mScreenWidth, mScreenHeight);

        mDrawingThread = new DrawingThread(this, 50);
        mDrawingThread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(mPaddle.getRectF(), mPaddle.getPaint());

        for (Brick brick : mBricks) {
            canvas.drawRect(brick.getRectF(), brick.getPaint());
        }

        for (CommonBall commonBall : mCommonBalls) {
            canvas.drawOval(commonBall.getRectF(), commonBall.getPaint());
        }

        updateBalls();

    }

    private void updateBalls() {

        for (CommonBall commonBall : mCommonBalls) {
            commonBall.move();

            if (commonBall.getRectF().left < 0 || commonBall.getRectF().right > getWidth()) {
                commonBall.setVelocityX(-commonBall.getVelocityX());
            }

            if (commonBall.getRectF().top < 0 || commonBall.getRectF().bottom > getHeight()) {
                commonBall.setVelocityY(-commonBall.getVelocityY());
            }

            if (RectF.intersects(commonBall.getRectF(), mPaddle.getRectF()) &&
                    commonBall.getVelocityY() > 0 ) {
                commonBall.setVelocityY(-commonBall.getVelocityY());
            }

            for (int i = mBricks.size() - 1; i >= 0; i--) {
                if (RectF.intersects(commonBall.getRectF(), mBricks.get(i).getRectF())) {
                    commonBall.setVelocityY(-commonBall.getVelocityY());
                    mBricks.remove(mBricks.get(i));
                    break;
                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();

        float paddleWidth = mPaddle.getRectF().width();
        float paddleLocationY = mPaddle.getRectF().top;

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                if (x < paddleWidth / 2) {
                    mPaddle.setLocation(0, paddleLocationY);
                } else if (x > mScreenWidth - paddleWidth / 2) {
                    mPaddle.setLocation(mScreenWidth - paddleWidth, paddleLocationY);
                } else {
                    mPaddle.setLocation(x - paddleWidth / 2, paddleLocationY);
                }
                break;

            default:
                break;
        }

        return true;
    }


}
