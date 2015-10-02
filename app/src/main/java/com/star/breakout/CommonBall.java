package com.star.breakout;


import android.graphics.Color;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class CommonBall extends Ball {

    private static final int COLOR = Color.BLACK;

    private static final float DIAMETER_RATIO = 0.08f;

    private static final float VELOCITY_X_MIN_RATIO = 0.005f;
    private static final float VELOCITY_X_MAX_RATIO = 0.015f;
    private static final float VELOCITY_Y_MIN_RATIO = 0.015f;
    private static final float VELOCITY_Y_MAX_RATIO = 0.020f;
    private static final float VELOCITY_MIN_RATIO = 0.005f;
    private static final float VELOCITY_MAX_RATIO = 0.040f;

    private static final int DOUBLE_VELOCITY_EVERY_N_COLLISION = 7;
    private int collisionTimes;

    private static List<CommonBall> sCommonBalls = new ArrayList<>();

    private boolean mLaserCapable;
    private boolean mSticky;

    private float mScreenWidth;
    private float mScreenHeight;

    private float mLastVelocityX;
    private float mLastVelocityY;

    private float mOffsetByPaddleX;
    private float mOffsetByPaddleY;

    public CommonBall(float screenWidth, float screenHeight) {
        super();

        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;

        setSize(screenWidth * DIAMETER_RATIO, screenWidth * DIAMETER_RATIO);
        setLocation((screenWidth - screenWidth * DIAMETER_RATIO) / 2,
                (screenHeight - screenWidth * DIAMETER_RATIO) / 2);
        getPaint().setColor(COLOR);

        float dx = (float) (Math.random()
                        * (screenWidth * VELOCITY_X_MAX_RATIO - screenWidth * VELOCITY_X_MIN_RATIO)
                        + screenWidth * VELOCITY_X_MIN_RATIO);

        float dy = (float) (Math.random()
                        * (screenWidth * VELOCITY_Y_MAX_RATIO - screenWidth * VELOCITY_Y_MIN_RATIO)
                        + screenWidth * VELOCITY_Y_MIN_RATIO);

        if (Math.random() < 0.5) {
            setVelocity(dx, dy);
        } else {
            setVelocity(-dx, dy);
        }

        sCommonBalls.add(this);
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

        if (sticky) {
            mLastVelocityX = getVelocityX();
            mLastVelocityY = getVelocityY();
            setVelocity(0, 0);
        } else {
            setVelocity(mLastVelocityX, mLastVelocityY);
        }
    }

    public float getOffsetByPaddleX() {
        return mOffsetByPaddleX;
    }

    public float getOffsetByPaddleY() {
        return mOffsetByPaddleY;
    }

    public static List<CommonBall> initCommonBalls(float screenWidth, float screenHeight) {
        new CommonBall(screenWidth, screenHeight);
        return sCommonBalls;
    }

    public void checkForSideCollision() {
        if (getRectF().left < 0) {
            setVelocityX(Math.abs(getVelocityX()));
        }

        if (getRectF().right > mScreenWidth) {
            setVelocityX(-Math.abs(getVelocityX()));
        }

        if (getRectF().top < 0) {
            setVelocityY(Math.abs(getVelocityY()));
        }
    }

    public void checkForPaddleCollision(Paddle paddle) {
        if (RectF.intersects(getRectF(), paddle.getRectF()) && getVelocityY() > 0 ) {

            setVelocityY(-Math.abs(getVelocityY()));

            if (paddle.isSticky()) {
                setSticky(true);
                mOffsetByPaddleX = getRectF().left - paddle.getRectF().left;
                mOffsetByPaddleY = getRectF().top - paddle.getRectF().top;
            }
        }
    }

    public void checkForBrickCollision(List<Brick> bricks, Score score) {
        for (int i = bricks.size() - 1; i >= 0; i--) {
            if (RectF.intersects(getRectF(), bricks.get(i).getRectF())) {

                if (!isLaserCapable()) {
                    float centerX = (getRectF().left + getRectF().right) / 2;

                    if (centerX < bricks.get(i).getRectF().left) {
                        setVelocityX(-Math.abs(getVelocityX()));
                    } else if (centerX > bricks.get(i).getRectF().right) {
                        setVelocityX(Math.abs(getVelocityX()));
                    }

                    setVelocityY(-getVelocityY());

                    score.setCurrentScore(score.getCurrentScore() + 1);

                    bricks.remove(bricks.get(i));
                    break;
                } else {

                    score.setCurrentScore(score.getCurrentScore() + 1);

                    bricks.remove(bricks.get(i));
                }
            }
        }
    }

    public void checkForBottomCollision(Life life) {
        if (getRectF().bottom > mScreenHeight) {
            sCommonBalls.remove(this);
            life.setCurrentLife(life.getCurrentLife() - 1);
        }
    }
}
