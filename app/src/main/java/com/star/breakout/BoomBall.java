package com.star.breakout;


import android.graphics.Color;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class BoomBall extends Ball {

    private static final int COLOR = Color.BLACK;

    private static final float DIAMETER_RATIO = 0.08f;

    private static final float VELOCITY_X_RATIO = 0 * VELOCITY_RATIO;
    private static final float VELOCITY_Y_MIN_RATIO = 3 * VELOCITY_RATIO;
    private static final float VELOCITY_Y_MAX_RATIO = 4 * VELOCITY_RATIO;

    public static final int INIT_BOOM_PERIOD = 60 * DrawingThread.FPS;
    public static int sBoomPeriod = INIT_BOOM_PERIOD / 2;

    private static List<BoomBall> sBoomBalls = new ArrayList<>();

    private float mScreenWidth;
    private float mScreenHeight;

    public BoomBall(float screenWidth, float screenHeight) {
        super();

        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;

        setSize(screenWidth * DIAMETER_RATIO, screenWidth * DIAMETER_RATIO);
        setLocation((float) (Math.random() * (screenWidth - screenWidth * DIAMETER_RATIO)), 0);
        getPaint().setColor(COLOR);

        float dx = screenWidth * VELOCITY_X_RATIO;

        float dy = (float) (Math.random()
                * (screenWidth * VELOCITY_Y_MAX_RATIO - screenWidth * VELOCITY_Y_MIN_RATIO)
                + screenWidth * VELOCITY_Y_MIN_RATIO);

        setVelocity(dx, dy);

        sBoomBalls.add(this);
    }

    public static List<BoomBall> getBoomBalls() {
        return sBoomBalls;
    }

    public static void setBoomPeriod(int boomPeriod) {
        sBoomPeriod = boomPeriod;
    }

    public boolean checkForPaddleCollision(Paddle paddle, Life life, Message message, DrawingThread drawingThread) {

        if (RectF.intersects(getRectF(), paddle.getRectF()) && getVelocityY() > 0 ) {

            CommonBall.getCommonBalls().clear();
            BonusBall.getBonusBalls().clear();
            sBoomBalls.clear();

            life.setCurrentLife(life.getCurrentLife() - 1);

            if (life.getCurrentLife() > Life.END_LIVES) {
                new CommonBall(mScreenWidth, mScreenHeight);
                message.setLabel(Message.START_PROMPT);
            } else {
                message.setLabel(Message.LOSE_RESULT);
            }

            paddle.retainOrigin();

            drawingThread.stop();

            return true;
        }

        return false;
    }

    public boolean checkForBottomCollision() {

        if (getRectF().bottom > mScreenHeight) {
            return sBoomBalls.remove(this);
        }

        return false;
    }
}
