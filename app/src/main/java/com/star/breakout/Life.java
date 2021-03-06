package com.star.breakout;


import android.graphics.Color;

public class Life extends Label {

    public static final int INIT_LIVES = 30;
    public static final int END_LIVES = 0;

    private static final float OFFSET_LEFT_RATIO = 0.65f;
    private static final float OFFSET_TOP_RATIO = 0.05f;

    private static final int COLOR = Color.BLACK;
    private static final int TEXT_SIZE = 50;

    public static final String LIFE = "Life: ";

    private int mCurrentLife;

    public Life(float screenWidth, float screenHeight) {
        super();

        setOffsetLeft(screenWidth * OFFSET_LEFT_RATIO);
        setOffsetTop(screenHeight * OFFSET_TOP_RATIO);

        getPaint().setColor(COLOR);
        getPaint().setTextSize(TEXT_SIZE);

        setCurrentLife(INIT_LIVES);

        setLabel(LIFE + mCurrentLife);
    }

    public int getCurrentLife() {
        return mCurrentLife;
    }

    public void setCurrentLife(int currentLife) {
        mCurrentLife = currentLife;
        setLabel(LIFE + mCurrentLife);
    }
}
