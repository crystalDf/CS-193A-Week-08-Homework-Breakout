package com.star.breakout;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class Brick {

    private static final int[] COLORS = {
            Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE};
    private static final int[] MASK_COLORS = {Color.LTGRAY, Color.GRAY, Color.DKGRAY};

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;

    private static final float SPACING = 4;

    private static final float WIDTH =
            (BreakoutFragment.SCREEN_WIDTH - (COLUMNS + 1) * SPACING) / COLUMNS;
    private static final float HEIGHT = 8;

    private static final float OFFSET_LEFT =
            (BreakoutFragment.SCREEN_WIDTH - COLUMNS * WIDTH
                    - (COLUMNS - 1) * SPACING) / 2;
    private static final float OFFSET_TOP = 70;

    private static int sAmount;

    private RectF mRectF;
    private Paint mPaint;

    private float mWidth;
    private float mHeight;

    private Point mPoint;


}
