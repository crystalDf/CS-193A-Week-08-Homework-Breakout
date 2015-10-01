package com.star.breakout;


public class Level extends Label {

    public static final int TOTAL_LEVELS = 5;

    public static final float INIT_ENHANCED_PROBABILITY = 0.5f;

    private static int currentLevel;
    private static float currentEnhancedProbability;

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(int currentLevel) {
        Level.currentLevel = currentLevel;
    }

    public static float getCurrentEnhancedProbability() {
        return currentEnhancedProbability;
    }

    public static void setCurrentEnhancedProbability(float currentEnhancedProbability) {
        Level.currentEnhancedProbability = currentEnhancedProbability;
    }
}
