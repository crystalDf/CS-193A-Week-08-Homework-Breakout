package com.star.breakout;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class CommonBall extends Ball {

    private static final int COLOR = Color.BLACK;

    private static final float DIAMETER_RATIO = 0.1f;

    private static final float VELOCITY_X_MIN_RATIO = 0.01f;
    private static final float VELOCITY_X_MAX_RATIO = 0.03f;
    private static final float VELOCITY_Y_MIN_RATIO = 0.03f;
    private static final float VELOCITY_Y_MAX_RATIO = 0.04f;
    private static final float VELOCITY_MIN_RATIO = 0.01f;
    private static final float VELOCITY_MAX_RATIO = 0.08f;

    private static final int DOUBLE_VELOCITY_EVERY_N_COLLISION = 7;

    private static List<CommonBall> sCommonBalls = new ArrayList<>();

    private boolean mLaserCapable;
    private boolean mSticky;

    public CommonBall(float screenWidth, float screenHeight) {
        super();

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
    }

    public static List<CommonBall> initCommonBalls(float screenWidth, float screenHeight) {
        new CommonBall(screenWidth, screenHeight);
        return sCommonBalls;
    }

    public void checkForCollision() {

    }
}
