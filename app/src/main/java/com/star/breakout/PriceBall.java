package com.star.breakout;


import android.graphics.Color;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class PriceBall extends Ball {

    public static final int[] COLORS = {
            Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA,
            Color.LTGRAY, Color.DKGRAY};

    private static final float DIAMETER_RATIO = 0.04f;

    private static final float VELOCITY_X_RATIO = 0;
    private static final float VELOCITY_Y_MIN_RATIO = 0.015f;
    private static final float VELOCITY_Y_MAX_RATIO = 0.020f;

    private static List<PriceBall> sPriceBalls = new ArrayList<>();

    private float mScreenWidth;
    private float mScreenHeight;

    public PriceBall(float screenWidth, float screenHeight, float locationX, float locationY) {
        super();

        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;

        setSize(screenWidth * DIAMETER_RATIO, screenWidth * DIAMETER_RATIO);
        setLocation(locationX - screenWidth * DIAMETER_RATIO / 2,
                locationY - screenWidth * DIAMETER_RATIO / 2);
        getPaint().setColor(COLORS[((int) (Math.random() * COLORS.length))]);

        float dx = screenWidth * VELOCITY_X_RATIO;

        float dy = (float) (Math.random()
                * (screenWidth * VELOCITY_Y_MAX_RATIO - screenWidth * VELOCITY_Y_MIN_RATIO)
                + screenWidth * VELOCITY_Y_MIN_RATIO);

        setVelocity(dx, dy);

        sPriceBalls.add(this);
    }

    public static List<PriceBall> getPriceBalls() {
        return sPriceBalls;
    }

    public void checkForPaddleCollision(Paddle paddle, List<Brick> bricks) {
        if (RectF.intersects(getRectF(), paddle.getRectF()) && getVelocityY() > 0 ) {
            removeLastEnhancedCapability(paddle);

            paddle.getPaint().setColor(getPaint().getColor());

            addEnhancedCapability(paddle, bricks);

            sPriceBalls.remove(this);
        }

    }

    public void checkForBottomCollision() {
        if (getRectF().bottom > mScreenHeight) {
            sPriceBalls.remove(this);
        }
    }

    private void removeLastEnhancedCapability(Paddle paddle) {

        switch (paddle.getPaint().getColor()) {
            case Color.RED:
                paddle.multiplySizeByFactor(0.5f);
                break;

            case Color.YELLOW:
                paddle.multiplySizeByFactor(2);
                break;

            case Color.GREEN:
                for (CommonBall commonBall : CommonBall.getCommonBalls()) {
                    commonBall.multiplyVelocityByFactor(0.5f);
                }
                break;

            case Color.CYAN:
                for (CommonBall commonBall : CommonBall.getCommonBalls()) {
                    commonBall.multiplyVelocityByFactor(2);
                }
                break;

            case Color.BLUE:
                break;

            case Color.MAGENTA:
                break;

            case Color.LTGRAY:
                for (CommonBall commonBall : CommonBall.getCommonBalls()) {
                    commonBall.setLaserCapable(false);
                }
                break;

            case Color.DKGRAY:
                paddle.setSticky(false);
                break;

            default:
                break;

        }
    }

    private void addEnhancedCapability(Paddle paddle, List<Brick> bricks) {

        switch (paddle.getPaint().getColor()) {
            case Color.RED:
                paddle.multiplySizeByFactor(2);
                break;

            case Color.YELLOW:
                paddle.multiplySizeByFactor(0.5f);
                break;

            case Color.GREEN:
                for (CommonBall commonBall : CommonBall.getCommonBalls()) {
                    commonBall.multiplyVelocityByFactor(2);
                }
                break;

            case Color.CYAN:
                for (CommonBall commonBall : CommonBall.getCommonBalls()) {
                    commonBall.multiplyVelocityByFactor(0.5f);
                }
                break;

            case Color.BLUE:
                bricks.clear();
                break;

            case Color.MAGENTA:
                new CommonBall(mScreenWidth, mScreenHeight);
                break;

            case Color.LTGRAY:
                for (CommonBall commonBall : CommonBall.getCommonBalls()) {
                    commonBall.setLaserCapable(true);
                }
                break;

            case Color.DKGRAY:
                paddle.setSticky(true);
                break;

            default:
                break;

        }
    }
}
