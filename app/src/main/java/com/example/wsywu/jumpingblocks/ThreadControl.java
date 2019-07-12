package com.example.wsywu.jumpingblocks;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ThreadControl extends Thread {
    public static final int MAX_FPS = 150;
    private SurfaceHolder mSurfaceHolder;
    private GameControl mGameControl;
    private boolean running;
    private static Canvas sCanvas;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ThreadControl(SurfaceHolder holder, GameControl gameControl) {
        super();
        mSurfaceHolder = holder;
        mGameControl = gameControl;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long targetTime = 1000 / MAX_FPS;

        while (running) {
            startTime = System.nanoTime();
            sCanvas = null;

            sCanvas = this.mSurfaceHolder.lockCanvas();
            synchronized (mSurfaceHolder) {
                this.mGameControl.update();
                this.mGameControl.draw(sCanvas);
                mSurfaceHolder.unlockCanvasAndPost(sCanvas);
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
