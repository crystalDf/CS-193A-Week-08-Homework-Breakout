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
    private List<BonusBall> mBonusBalls;
    private List<BoomBall> mBoomBalls;

    private Level mLevel;
    private Score mScore;
    private Life mLife;
    private Message mMessage;

    public BreakoutView(Context context, AttributeSet attrs) {

        super(context, attrs);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();

        mLevel = new Level(mScreenWidth, mScreenHeight);
        mScore = new Score(mScreenWidth, mScreenHeight);
        mLife = new Life(mScreenWidth, mScreenHeight);
        mMessage = new Message(mScreenWidth, mScreenHeight);

        mPaddle = Paddle.getInstance(mScreenWidth, mScreenHeight);

        mBricks = Brick.getBricks();

        if (mBricks.size() == 0) {
            Brick.setBricks(mScreenWidth, mScreenHeight, mLevel);
        }

        mCommonBalls = CommonBall.getCommonBalls();

        if (mCommonBalls.size() == 0) {
            new CommonBall(mScreenWidth, mScreenHeight);
        }

        mBonusBalls = BonusBall.getBonusBalls();

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

        for (BonusBall bonusBall : mBonusBalls) {
            canvas.drawOval(bonusBall.getRectF(), bonusBall.getPaint());
        }

        canvas.drawText(mLevel.getLabel(), mLevel.getOffsetLeft(), mLevel.getOffsetTop(),
                mLevel.getPaint());
        canvas.drawText(mScore.getLabel(), mScore.getOffsetLeft(), mScore.getOffsetTop(),
                mScore.getPaint());
        canvas.drawText(mLife.getLabel(), mLife.getOffsetLeft(), mLife.getOffsetTop(),
                mLife.getPaint());
        canvas.drawText(mMessage.getLabel(), mMessage.getOffsetLeft(), mMessage.getOffsetTop(),
                mMessage.getPaint());

        updateBalls();

    }

    private void updateBalls() {

        for (int i = 0; i < mCommonBalls.size();) {
            mCommonBalls.get(i).move();

            mCommonBalls.get(i).checkForSideCollision();
            mCommonBalls.get(i).checkForPaddleCollision(mPaddle);

            if (!mCommonBalls.get(i).checkForBrickCollision(mBricks, mPaddle, mScore, mLevel, mMessage, mDrawingThread)
                    && !mCommonBalls.get(i).checkForBottomCollision(mPaddle, mLife, mMessage, mDrawingThread)) {
                i++;
            }
        }

        for (int i = 0; i < mBonusBalls.size();) {
            mBonusBalls.get(i).move();

            if (!mBonusBalls.get(i).checkForPaddleCollision(mPaddle, mBricks)
                    && !mBonusBalls.get(i).checkForBottomCollision()) {
                i++;
            }
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
                    } else if (mLife.getCurrentLife() > 0 && mBricks.size() > 0) {
                        mMessage.setLabel(Message.EMPTY);
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
