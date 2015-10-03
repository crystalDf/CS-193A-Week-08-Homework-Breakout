package com.star.breakout;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

public class BreakoutView extends View {

    public static final String MY_DATA = "my_data";

    private float mScreenWidth;
    private float mScreenHeight;

    private DrawingThread mDrawingThread;

    private Paddle mPaddle;

    private List<Brick> mBricks;
    private List<CommonBall> mCommonBalls;
    private List<BonusBall> mBonusBalls;
    private List<BoomBall> mBoomBalls;

    private Level mLevel;
    private Score mScore;
    private Life mLife;
    private Message mMessage;
    private HighestScore mHighestScore;

    private static int sTimer;

    private Context mContext;

    public BreakoutView(Context context, AttributeSet attrs) {

        super(context, attrs);

        mContext = context;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();

        initializeGame();
    }

    private void initializeGame() {
        mLevel = new Level(mScreenWidth, mScreenHeight);
        mScore = new Score(mScreenWidth, mScreenHeight);
        mLife = new Life(mScreenWidth, mScreenHeight);
        mMessage = new Message(mScreenWidth, mScreenHeight);
        mHighestScore = new HighestScore(mScreenWidth, mScreenHeight);

        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(MY_DATA, Context.MODE_PRIVATE);

        int currentLevel = sharedPreferences.getInt(Level.LEVEL, -1);
        if (currentLevel != -1) {
            float currentEnhancedProbability = sharedPreferences.getFloat(Level.ENHANCED_PROBABILITY, -1);
            boolean boomEnabled = sharedPreferences.getBoolean(Level.BOOM_ENABLED, false);

            mLevel.setCurrentLevel(currentLevel);
            mLevel.setCurrentEnhancedProbability(currentEnhancedProbability);
            mLevel.setBoomEnabled(boomEnabled);
        }

        int currentScore = sharedPreferences.getInt(Score.SCORE, -1);
        if (currentScore != -1) {
            mScore.setCurrentScore(currentScore);
        }

        int currentLife = sharedPreferences.getInt(Life.LIFE, -1);
        if (currentLife != -1) {
            mLife.setCurrentLife(currentLife);
        }

        String currentMessage = sharedPreferences.getString(Message.MESSAGE, "");
        if (!currentMessage.equals("")) {
            mMessage.setLabel(currentMessage);
        }

        int highestScore = sharedPreferences.getInt(HighestScore.HIGHEST_SCORE, -1);
        if (highestScore != -1) {
            mHighestScore.setHighestScore(highestScore);
        }

        mPaddle = Paddle.getInstance(mScreenWidth, mScreenHeight);

        mBricks = Brick.getBricks();

        if (mBricks.size() == 0) {
            Brick.setBricks(mScreenWidth, mScreenHeight, mLevel);
        }

        mCommonBalls = CommonBall.getCommonBalls();

        if (mCommonBalls.size() == 0) {
            new CommonBall(mScreenWidth, mScreenHeight);
        }

        mBonusBalls = BonusBall.getBonusBalls();

        mBoomBalls = BoomBall.getBoomBalls();

        mDrawingThread = new DrawingThread(this, DrawingThread.FPS);

        invalidate();
    }

    private void finalizeGame() {
        mBricks.clear();
        mCommonBalls.clear();
        mBonusBalls.clear();
        mBoomBalls.clear();
        mPaddle.retainOrigin();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_DATA, Context.MODE_PRIVATE);

        sharedPreferences.edit()
                .putInt(Level.LEVEL, Level.INIT_LEVEL)
                .putFloat(Level.ENHANCED_PROBABILITY, Level.INIT_ENHANCED_PROBABILITY)
                .putBoolean(Level.BOOM_ENABLED, Level.INIT_BOOM_ENABLED)
                .putInt(Score.SCORE, Score.INIT_SCORES)
                .putInt(Life.LIFE, Life.INIT_LIVES)
                .putString(Message.MESSAGE, Message.START_PROMPT)
                .commit();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(mPaddle.getRectF(), mPaddle.getPaint());

        for (Brick brick : mBricks) {
            canvas.drawRect(brick.getRectF(), brick.getPaint());
        }

        for (CommonBall commonBall : mCommonBalls) {
            canvas.drawOval(commonBall.getRectF(), commonBall.getPaint());
        }

        for (BonusBall bonusBall : mBonusBalls) {
            canvas.drawOval(bonusBall.getRectF(), bonusBall.getPaint());
        }

        for (BoomBall boomBall : mBoomBalls) {
            canvas.drawOval(boomBall.getRectF(), boomBall.getPaint());
        }

        canvas.drawText(mLevel.getLabel(), mLevel.getOffsetLeft(), mLevel.getOffsetTop(),
                mLevel.getPaint());
        canvas.drawText(mScore.getLabel(), mScore.getOffsetLeft(), mScore.getOffsetTop(),
                mScore.getPaint());
        canvas.drawText(mLife.getLabel(), mLife.getOffsetLeft(), mLife.getOffsetTop(),
                mLife.getPaint());
        canvas.drawText(mMessage.getLabel(), mMessage.getOffsetLeft(), mMessage.getOffsetTop(),
                mMessage.getPaint());
        canvas.drawText(mHighestScore.getLabel(), mHighestScore.getOffsetLeft(), mHighestScore.getOffsetTop(),
                mHighestScore.getPaint());

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_DATA, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(Level.LEVEL, mLevel.getCurrentLevel())
                .putFloat(Level.ENHANCED_PROBABILITY, mLevel.getCurrentEnhancedProbability())
                .putBoolean(Level.BOOM_ENABLED, mLevel.isBoomEnabled())
                .putInt(Score.SCORE, mScore.getCurrentScore())
                .putInt(Life.LIFE, mLife.getCurrentLife())
                .putString(Message.MESSAGE, mMessage.getLabel())
                .putInt(HighestScore.HIGHEST_SCORE, mHighestScore.getHighestScore())
                .commit();

        updateBalls();

    }

    private void updateBalls() {

        sTimer++;

        if (mLevel.isBoomEnabled()) {
            if (mLevel.getCurrentLevel() == (Level.TOTAL_LEVELS - 1)) {
                if (sTimer % BoomBall.INIT_BOOM_PERIOD == 0) {
                    new BoomBall(mScreenWidth, mScreenHeight);
                }
            } else if (mLevel.getCurrentLevel() == Level.TOTAL_LEVELS) {
                if (sTimer % BoomBall.sBoomPeriod == 0) {
                    new BoomBall(mScreenWidth, mScreenHeight);
                    BoomBall.setBoomPeriod(
                            (int) (Math.random()
                            * (BoomBall.INIT_BOOM_PERIOD / 2 - BoomBall.INIT_BOOM_PERIOD / 4)
                            + BoomBall.INIT_BOOM_PERIOD / 4));
                }
            }
        }

        for (int i = 0; i < mCommonBalls.size();) {
            mCommonBalls.get(i).move();

            mCommonBalls.get(i).checkForSideCollision();
            mCommonBalls.get(i).checkForPaddleCollision(mPaddle);

            if (!mCommonBalls.get(i).checkForBrickCollision(mBricks, mPaddle, mScore, mLevel, mMessage, mHighestScore, mDrawingThread)
                    && !mCommonBalls.get(i).checkForBottomCollision(mPaddle, mLife, mMessage, mDrawingThread)) {
                i++;
            }
        }

        for (int i = 0; i < mBonusBalls.size();) {
            mBonusBalls.get(i).move();

            if (!mBonusBalls.get(i).checkForPaddleCollision(mPaddle, mBricks)
                    && !mBonusBalls.get(i).checkForBottomCollision()) {
                i++;
            }
        }

        for (int i = 0; i < mBoomBalls.size();) {
            mBoomBalls.get(i).move();

            if (!mBoomBalls.get(i).checkForPaddleCollision(mPaddle, mLife, mMessage, mDrawingThread)
                    && !mBoomBalls.get(i).checkForBottomCollision()) {
                i++;
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        float paddleWidth = mPaddle.getRectF().width();
        float paddleLocationY = mPaddle.getRectF().top;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (y < mScreenHeight / 2) {
                    if (mDrawingThread.isRunning()) {
                        mDrawingThread.stop();
                    } else if (mLife.getCurrentLife() > 0 && mBricks.size() > 0) {
                        mMessage.setLabel(Message.EMPTY);
                        mDrawingThread.start();
                    }
                } else {
                    for (CommonBall commonBall : mCommonBalls) {
                        if (commonBall.isSticky()) {
                            commonBall.setSticky(false);
                        }
                    }
                }

                if (mMessage.getLabel().equals(Message.WIN_RESULT)
                        || mMessage.getLabel().equals(Message.LOSE_RESULT)) {
                    finalizeGame();
                    initializeGame();
                }

            case MotionEvent.ACTION_MOVE:
                if (y > mScreenHeight / 2) {
                    if (x < paddleWidth / 2) {
                        mPaddle.setLocation(0, paddleLocationY);
                    } else if (x > mScreenWidth - paddleWidth / 2) {
                        mPaddle.setLocation(mScreenWidth - paddleWidth, paddleLocationY);
                    } else {
                        mPaddle.setLocation(x - paddleWidth / 2, paddleLocationY);
                    }

                    for (CommonBall commonBall : mCommonBalls) {
                        if (commonBall.isSticky()) {
                            commonBall.setLocation(
                                    mPaddle.getRectF().left + commonBall.getOffsetByPaddleX(),
                                    mPaddle.getRectF().top + commonBall.getOffsetByPaddleY());
                        }
                    }
                }
                break;

            default:
                break;
        }

        return true;
    }

}
