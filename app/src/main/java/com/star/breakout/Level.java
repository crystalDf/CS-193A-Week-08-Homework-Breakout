package com.star.breakout;


import android.graphics.Color;

public class Level extends Label {

    public static final int INIT_LEVEL = 1;
    public static final int TOTAL_LEVELS = 5;

    public static final float INIT_ENHANCED_PROBABILITY = 0.5f;

    private static final float OFFSET_LEFT_RATIO = 0.05f;
    private static final float OFFSET_TOP_RATIO = 0.05f;

    private static final int COLOR = Color.BLACK;
    private static final int TEXT_SIZE = 50;

    private static final String LEVEL = "Level: ";

    private int mCurrentLevel;
    private float mCurrentEnhancedProbability;

    public Level(float screenWidth, float screenHeight) {
        super();

        setOffsetLeft(screenWidth * OFFSET_LEFT_RATIO);
        setOffsetTop(screenHeight * OFFSET_TOP_RATIO);

        getPaint().setColor(COLOR);
        getPaint().setTextSize(TEXT_SIZE);

        setCurrentLevel(INIT_LEVEL);

        setLabel(LEVEL + mCurrentLevel);
    }

    public int getCurrentLevel() {
        return mCurrentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        mCurrentLevel = currentLevel;
        if (currentLevel == 1) {
            setCurrentEnhancedProbability(0);
        } else {
            setCurrentEnhancedProbability(INIT_ENHANCED_PROBABILITY);
        }

        setLabel(LEVEL + mCurrentLevel);
    }

    public float getCurrentEnhancedProbability() {
        return mCurrentEnhancedProbability;
    }

    public void setCurrentEnhancedProbability(float currentEnhancedProbability) {
        mCurrentEnhancedProbability = currentEnhancedProbability;
    }
}
