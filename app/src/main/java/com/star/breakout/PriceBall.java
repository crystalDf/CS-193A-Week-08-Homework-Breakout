package com.star.breakout;


import android.graphics.Color;

import java.util.ArrayList;

public class PriceBall extends Ball {

    private static final int[] COLORS = {
            Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA,
            Color.LTGRAY, Color.DKGRAY};

    private static final float RADIUS = 6;

    private static final float VELOCITY_X = 0;
    private static final float VELOCITY_Y_MIN = 3;
    private static final float VELOCITY_Y_MAX = 4;

    private static ArrayList<PriceBall> sPriceBalls;
}
