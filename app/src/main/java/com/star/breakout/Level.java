package com.star.breakout;


import android.graphics.Color;

public class Level extends Label {

    public static final int INIT_LEVEL = 1;
    public static final int TOTAL_LEVELS = 5;

    public static final float INIT_ENHANCED_PROBABILITY = 0;
    public static final float STANDARD_ENHANCED_PROBABILITY = 0.2f;

    public static final boolean INIT_BOOM_ENABLED = false;
    public static final boolean STANDARD_BOOM_ENABLED = true;

    private static final float OFFSET_LEFT_RATIO = 0.05f;
    private static final float OFFSET_TOP_RATIO = 0.05f;

    private static final int COLOR = Color.BLACK;
    private static final int TEXT_SIZE = 50;

    public static final String LEVEL = "Level: ";
    public static final String ENHANCED_PROBABILITY = "Enhanced probability: ";
    public static final String BOOM_ENABLED = "Boom enabled: ";

    private int mCurrentLevel;
    private float mCurrentEnhancedProbability;
    private boolean mBoomEnabled;

    private float mScreenWidth;
    private float mScreenHeight;

    public Level(float screenWidth, float screenHeight) {
        super();

        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;

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
        if (currentLevel == INIT_LEVEL) {
            setCurrentEnhancedProbability(INIT_ENHANCED_PROBABILITY);
        } else {
            setCurrentEnhancedProbability(STANDARD_ENHANCED_PROBABILITY);
        }

        if (currentLevel < (TOTAL_LEVELS - 1)) {
            setBoomEnabled(Level.INIT_BOOM_ENABLED);
        } else {
            setBoomEnabled(Level.STANDARD_BOOM_ENABLED);
        }

        setLabel(LEVEL + mCurrentLevel);
    }

    public float getCurrentEnhancedProbability() {
        return mCurrentEnhancedProbability;
    }

    public void setCurrentEnhancedProbability(float currentEnhancedProbability) {
        mCurrentEnhancedProbability = currentEnhancedProbability;
    }

    public boolean isBoomEnabled() {
        return mBoomEnabled;
    }

    public void setBoomEnabled(boolean boomEnabled) {
        mBoomEnabled = boomEnabled;
    }

    public void determineEnhancedBall(Brick brick) {
        for (int i = 0; i < Brick.COLORS.length; i++) {
            if (brick.getPaint().getColor() == Brick.COLORS[i]) {
                if (Math.random() < getCurrentEnhancedProbability()) {
                    new BonusBall(mScreenWidth, mScreenHeight,
                            (brick.getRectF().left + brick.getRectF().right) / 2,
                            (brick.getRectF().top + brick.getRectF().bottom) / 2);
                    break;
                }
            }
        }

    }
}
