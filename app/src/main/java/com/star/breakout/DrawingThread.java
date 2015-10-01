package com.star.breakout;


import android.os.Handler;
import android.os.Looper;
import android.view.View;

public class DrawingThread {

    protected static final float PAUSE_TIME = 20;

    private View mView;
    private int mFps;
    private Thread mThread;
    private Handler mHandler;
    private volatile boolean isRunning = false;

    public DrawingThread(View view, int fps) {
        if (view == null || fps <= 0) {
            throw new IllegalArgumentException();
        }

        this.mView = view;
        this.mFps = fps;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public boolean isRunning() {
        return mThread != null;
    }

    public void start() {
        if (mThread == null) {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    isRunning = true;
                    while (isRunning) {
                        try {
                            Thread.sleep(1000 / mFps);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            isRunning = false;
                        }

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mView.invalidate();
                            }
                        });
                    }
                }
            });
            mThread.start();
        }
    }

    public void stop() {
        if (mThread != null) {
            isRunning = false;
            try {
                mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mThread = null;
        }
    }
}
