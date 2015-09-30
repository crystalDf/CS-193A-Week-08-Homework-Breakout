package com.star.breakout;


import android.graphics.Color;

public class CommonBall extends Ball {

    private static final int COLOR = Color.BLACK;

    private static final float RADIUS = 10;

    private static final float VELOCITY_X_MIN = 1;
    private static final float VELOCITY_X_MAX = 3;
    private static final float VELOCITY_Y_MIN = 3;
    private static final float VELOCITY_Y_MAX = 4;
    private static final float VELOCITY_MIN = 1;
    private static final float VELOCITY_MAX = 8;

    private static final int DOUBLE_VELOCITY_EVERY_N_COLLISION = 7;
    
}
