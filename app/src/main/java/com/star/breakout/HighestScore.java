package com.star.breakout;


import android.graphics.Color;

public class HighestScore extends Label {

    public static final int INIT_SCORES = 0;

    private static final float OFFSET_LEFT_RATIO = 0.35f;
    private static final float OFFSET_TOP_RATIO = 0.1f;

    private static final int COLOR = Color.BLACK;
    private static final int TEXT_SIZE = 50;

    public static final String HIGHEST_SCORE = "Highest Score: ";

    private int mHighestScore;

    public HighestScore(float screenWidth, float screenHeight) {
        super();

        setOffsetLeft(screenWidth * OFFSET_LEFT_RATIO);
        setOffsetTop(screenHeight * OFFSET_TOP_RATIO);

        getPaint().setColor(COLOR);
        getPaint().setTextSize(TEXT_SIZE);

        setHighestScore(INIT_SCORES);

        setLabel(HIGHEST_SCORE + mHighestScore);

    }

    public int getHighestScore() {
        return mHighestScore;
    }

    public void setHighestScore(int highestScore) {
        mHighestScore = highestScore;
        setLabel(HIGHEST_SCORE + mHighestScore);
    }
}
