package com.star.breakout;


import android.content.Context;
import android.graphics.Canvas;
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
    private List<PriceBall> mPriceBalls;
    private List<BoomBall> mBoomBalls;

    private Level mLevel;
    private Score mScore;
    private Life mLife;

    public BreakoutView(Context context, AttributeSet attrs) {

        super(context, attrs);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();

        mLevel = new Level(mScreenWidth, mScreenHeight);
        mScore = new Score(mScreenWidth, mScreenHeight);
        mLife = new Life(mScreenWidth, mScreenHeight);

        mPaddle = Paddle.getInstance(mScreenWidth, mScreenHeight);
        mBricks = Brick.initBricks(mScreenWidth, mScreenHeight, mLevel);
        mCommonBalls = CommonBall.initCommonBalls(mScreenWidth, mScreenHeight);

        mDrawingThread = new DrawingThread(this, DrawingThread.FPS);
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

        canvas.drawText(mLevel.getLabel(), mLevel.getOffsetLeft(), mLevel.getOffsetTop(),
                mLevel.getPaint());
        canvas.drawText(mScore.getLabel(), mScore.getOffsetLeft(), mScore.getOffsetTop(),
                mScore.getPaint());
        canvas.drawText(mLife.getLabel(), mLife.getOffsetLeft(), mLife.getOffsetTop(),
                mLife.getPaint());

        updateBalls();

    }

    private void updateBalls() {

        for (CommonBall commonBall : mCommonBalls) {
            commonBall.move();

            commonBall.checkForSideCollision();
            commonBall.checkForPaddleCollision(mPaddle);
            commonBall.checkForBrickCollision(mBricks, mScore);
            commonBall.checkForBottomCollision(mLife);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        float paddleWidth = mPaddle.getRectF().width();
        float paddleLocationY = mPaddle.getRectF().top;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (y < mScreenHeight / 2) {
                    if (mDrawingThread.isRunning()) {
                        mDrawingThread.stop();
                    } else {
                        mDrawingThread.start();
                    }
                } else {
                    for (CommonBall commonBall : mCommonBalls) {
                        if (commonBall.isSticky()) {
                            commonBall.setSticky(false);
                        }
                    }
                }

            case MotionEvent.ACTION_MOVE:
                if (y > mScreenHeight / 2) {
                    if (x < paddleWidth / 2) {
                        mPaddle.setLocation(0, paddleLocationY);
                    } else if (x > mScreenWidth - paddleWidth / 2) {
                        mPaddle.setLocation(mScreenWidth - paddleWidth, paddleLocationY);
                    } else {
                        mPaddle.setLocation(x - paddleWidth / 2, paddleLocationY);
                    }

                    for (CommonBall commonBall : mCommonBalls) {
                        if (commonBall.isSticky()) {
                            commonBall.setLocation(
                                    mPaddle.getRectF().left + commonBall.getOffsetByPaddleX(),
                                    mPaddle.getRectF().top + commonBall.getOffsetByPaddleY());
                        }
                    }
                }
                break;

            default:
                break;
        }

        return true;
    }

}
