package com.star.breakout;


import android.graphics.Color;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class CommonBall extends Ball {

    private static final int COLOR = Color.BLACK;

    private static final float DIAMETER_RATIO = 0.06f;

    private static final float VELOCITY_X_MIN_RATIO = 0.005f;
    private static final float VELOCITY_X_MAX_RATIO = 0.015f;
    private static final float VELOCITY_Y_MIN_RATIO = 0.015f;
    private static final float VELOCITY_Y_MAX_RATIO = 0.020f;
    private static final float VELOCITY_MIN_RATIO = 0.005f;
    private static final float VELOCITY_MAX_RATIO = 0.040f;

    private static final int DOUBLE_VELOCITY_EVERY_N_COLLISION = 7;
    private static int sCollisionTimes;

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

        setLaserCapable(true);

        sCommonBalls.add(this);

        if (sCommonBalls.size() == 1) {
            sCollisionTimes = 0;
        }
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

    public static List<CommonBall> getCommonBalls() {
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

            float centerX = (getRectF().left + getRectF().right) / 2;
            float centerY = (getRectF().top + getRectF().bottom) / 2;

            if (centerX < paddle.getRectF().left) {
                setVelocityX(-Math.abs(getVelocityY()));
                if (centerY < paddle.getRectF().top) {
                    setVelocityY(-Math.abs(getVelocityX()));
                } else {
                    setVelocityY(Math.abs(getVelocityX()));
                }
            } else if (centerX > paddle.getRectF().right) {
                setVelocityX(Math.abs(getVelocityY()));
                if (centerY < paddle.getRectF().top) {
                    setVelocityY(-Math.abs(getVelocityX()));
                } else {
                    setVelocityY(Math.abs(getVelocityX()));
                }
            } else if (centerY < paddle.getRectF().top) {
                setVelocityY(-Math.abs(getVelocityY()));
            }

            if (centerY < paddle.getRectF().top) {

                sCollisionTimes++;

                if (sCollisionTimes == DOUBLE_VELOCITY_EVERY_N_COLLISION) {
                    for (CommonBall commonBall : sCommonBalls) {
                        commonBall.multiplyVelocityByFactor(2);
                    }
                    sCollisionTimes = 0;
                }

                if (paddle.isSticky()) {
                    mOffsetByPaddleX = getRectF().left - paddle.getRectF().left;
                    mOffsetByPaddleY = getRectF().top - paddle.getRectF().top;

                    setSticky(true);
                }
            }

        }
    }

    public void checkForBrickCollision(List<Brick> bricks, Score score, Level level, Message message, DrawingThread drawingThread) {
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

                    score.setCurrentScore(score.getCurrentScore()
                            + score.calculateScore(bricks.get(i)));

                    level.determineEnhancedBall(bricks.get(i));

                    bricks.remove(bricks.get(i));
                    break;
                } else {

                    score.setCurrentScore(score.getCurrentScore()
                            + score.calculateScore(bricks.get(i)));

                    level.determineEnhancedBall(bricks.get(i));

                    bricks.remove(bricks.get(i));
                }
            }
        }

        if (bricks.size() == 0) {

            sCommonBalls.clear();

            if (level.getCurrentLevel() == Level.TOTAL_LEVELS) {
                message.setLabel(Message.WIN_RESULT);
            } else {
                level.setCurrentLevel(level.getCurrentLevel() + 1);
                Brick.initBricks(mScreenWidth, mScreenHeight, level);
                new CommonBall(mScreenWidth, mScreenHeight);
                message.setLabel(Message.START_PROMPT);
            }

            drawingThread.stop();
        }
    }

    public void checkForBottomCollision(Life life, Message message, DrawingThread drawingThread) {
        if (getRectF().bottom > mScreenHeight) {
            sCommonBalls.remove(this);
            life.setCurrentLife(life.getCurrentLife() - 1);

            if (life.getCurrentLife() > 0) {
                new CommonBall(mScreenWidth, mScreenHeight);
                message.setLabel(Message.START_PROMPT);
            } else {
                message.setLabel(Message.LOSE_RESULT);
            }

            drawingThread.stop();

        }
    }

    public void multiplyVelocityByFactor(float factor) {

        float newFactor = 1;
        float velocityXYMax = Math.max(getVelocityX(), getVelocityY());
        float velocityXYMin = Math.min(getVelocityX(), getVelocityY());

        if (factor > 1) {
            newFactor = velocityXYMax * factor <=
                    mScreenWidth * VELOCITY_MAX_RATIO ? factor
                    : mScreenWidth * VELOCITY_MAX_RATIO / velocityXYMax;
        } else if (factor < 1) {
            newFactor = velocityXYMin * factor >=
                    mScreenWidth * VELOCITY_MIN_RATIO ? factor
                    : mScreenWidth * VELOCITY_MIN_RATIO / velocityXYMin;
        }

        setVelocity(getVelocityX() * newFactor, getVelocityY() * newFactor);
    }


}
