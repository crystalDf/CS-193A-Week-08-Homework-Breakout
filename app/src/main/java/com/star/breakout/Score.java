package com.star.breakout;


import android.graphics.Color;

public class Score extends Label {

    public static final int INIT_SCORES = 0;

    private static final float OFFSET_LEFT_RATIO = 0.35f;
    private static final float OFFSET_TOP_RATIO = 0.05f;

    private static final int COLOR = Color.BLACK;
    private static final int TEXT_SIZE = 50;

    public static final String SCORE = "Score: ";

    private int mCurrentScore;

    public Score(float screenWidth, float screenHeight) {
        super();

        setOffsetLeft(screenWidth * OFFSET_LEFT_RATIO);
        setOffsetTop(screenHeight * OFFSET_TOP_RATIO);

        getPaint().setColor(COLOR);
        getPaint().setTextSize(TEXT_SIZE);

        setCurrentScore(INIT_SCORES);

        setLabel(SCORE + mCurrentScore);

    }

    public int getCurrentScore() {
        return mCurrentScore;
    }

    public void setCurrentScore(int currentScore) {
        mCurrentScore = currentScore;
        setLabel(SCORE + mCurrentScore);
    }

    public int calculateScore(Brick brick) {
        for (int i = 0; i < Brick.COLORS.length; i++) {
            if (brick.getPaint().getColor() == Brick.COLORS[i]) {
                return Brick.COLORS.length - i;
            }
        }

        return 0;
    }

}
