package com.star.breakout;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class Brick {

    public static final int[] COLORS = {
            Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE};
    private static final int[] MASK_COLORS = {Color.LTGRAY, Color.GRAY, Color.DKGRAY};

    private static final float OFFSET_TOP_RATIO = 0.125f;
    private static final float SPACING_RATIO = 0.02f;
    private static final float HEIGHT_RATIO = 0.01f;

    public static final int ROWS = 10;
    public static final int COLUMNS = 10;

    private RectF mRectF;
    private Paint mPaint;

    private static List<Brick> sBricks = new ArrayList<>();

    private Brick() {
        mRectF = new RectF();
        mPaint = new Paint();
    }

    public RectF getRectF() {
        return mRectF;
    }

    public void setRectF(RectF rectF) {
        mRectF = rectF;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public void setSize(float width, float height) {
        mRectF.right = mRectF.left + width;
        mRectF.bottom = mRectF.top + height;
    }

    public void setLocation(float x, float y) {
        mRectF.offsetTo(x, y);
    }

    public static List<Brick> initBricks(float screenWidth, float screenHeight, Level level) {

        float spacing = screenWidth * SPACING_RATIO;

        float width = (screenWidth - (COLUMNS + 1) * spacing) / COLUMNS;
        float height = screenHeight * HEIGHT_RATIO;

        float offsetLeft = (screenWidth - COLUMNS * width - (COLUMNS - 1) * spacing) / 2;
        float offsetTop = screenHeight * OFFSET_TOP_RATIO;

        initBaseBricks(width, height, spacing, offsetLeft, offsetTop);

        int currentLevel = level.getCurrentLevel();

        switch (currentLevel) {
            case 3:
                initMaskBricks(width, height, spacing, offsetLeft, offsetTop,
                        ROWS - 2, MASK_COLORS.length - 2);
                break;

            case 4:
                initMaskBricks(width, height, spacing, offsetLeft, offsetTop,
                        ROWS - 2, MASK_COLORS.length);
                break;

            case 5:
                initMaskBricks(width, height, spacing, offsetLeft, offsetTop,
                        0, MASK_COLORS.length);
                break;

            default:
                break;
        }

        return sBricks;
    }

    private static void initBaseBricks(float width, float height, float spacing,
                                       float offsetLeft, float offsetTop) {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Brick brick = new Brick();

                brick.setSize(width, height);
                brick.setLocation(offsetLeft + (width + spacing) * j,
                        offsetTop + (height + spacing) * i);
                brick.mPaint.setColor(COLORS[i / (ROWS / COLORS.length)]);

                sBricks.add(brick);
            }
        }
    }

    private static void initMaskBricks(float width, float height, float spacing,
                                       float offsetLeft, float offsetTop,
                                       int iStart, int kEnd) {

        for (int i = iStart; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                for (int k = 0; k < kEnd; k++) {
                    Brick brick = new Brick();

                    brick.setSize(width, height);
                    brick.setLocation(offsetLeft + (width + spacing) * j,
                            offsetTop + (height + spacing) * i);
                    brick.mPaint.setColor(MASK_COLORS[k]);

                    sBricks.add(brick);
                }
            }
        }
    }

}
