package com.star.breakout;


import android.graphics.Color;

public class Message extends Label {

    private static final float OFFSET_LEFT_RATIO = 0.35f;
    private static final float OFFSET_TOP_RATIO = 0.4f;

    private static final int COLOR = Color.BLACK;
    private static final int TEXT_SIZE = 50;

    public static final String START_PROMPT = "Click to start";
    public static final String EMPTY = "";
    public static final String WIN_RESULT = "You win!";
    public static final String LOSE_RESULT = "You lose...";

    public static final String MESSAGE = "Message: ";

    public Message(float screenWidth, float screenHeight) {
        super();

        setOffsetLeft(screenWidth * OFFSET_LEFT_RATIO);
        setOffsetTop(screenHeight * OFFSET_TOP_RATIO);

        getPaint().setColor(COLOR);
        getPaint().setTextSize(TEXT_SIZE);

        setLabel(START_PROMPT);
    }
}
