package com.star.breakout;


import android.graphics.Color;

public class BoomBall extends Ball {

    private static final int COLOR = Color.BLACK;
    private static final float RADIUS = 16;
    private static final float VELOCITY_X = 0;
    private static final float VELOCITY_Y_MIN = 3;
    private static final float VELOCITY_Y_MAX = 4;

    private static final float INIT_PERIOD = 60 * 1000 / PAUSE_TIME;
}
