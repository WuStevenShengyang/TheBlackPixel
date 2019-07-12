package com.example.wsywu.jumpingblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameControl extends SurfaceView implements SurfaceHolder.Callback {
    private int highScore;
    private int score;

    private ThreadControl mThreadControl;
    private Ground mGround;
    private Player mPlayer;
    private ObstacleManager mManager;

    private Rect r = new Rect();

    private boolean gameOver = false;
    private boolean firstJump = false;
    private boolean secondJump = false;
    private float startPos;

    private long startTime;

    public GameControl(Context context, int highScore) {
        super(context);
        getHolder().addCallback(this);

        mThreadControl = new ThreadControl(getHolder(), this);
        mGround = new Ground();
        mPlayer = new Player(mGround);
        mManager = new ObstacleManager(mGround);

        this.highScore = highScore;

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThreadControl = new ThreadControl(getHolder(), this);
        mThreadControl.setRunning(true);
        mThreadControl.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                mThreadControl.setRunning(false);
                mThreadControl.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver) {
                    if (firstJump) {
                        firstJump = false;
                        secondJump = true;
                        startTime = System.nanoTime();
                        startPos = mPlayer.getRect().bottom;
                    } else if (secondJump) {
                    } else {
                        firstJump = true;
                        startTime = System.nanoTime();
                    }
                } else {
                    gameOver = false;
                    mThreadControl = new ThreadControl(getHolder(), this);
                    mGround = new Ground();
                    mPlayer = new Player(mGround);
                    mManager = new ObstacleManager(mGround);
                }
        }

        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        mGround.draw(canvas);
        mManager.draw(canvas);
        mPlayer.draw(canvas);

        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        highScoreText(canvas, scorePaint, "High Score: " + String.valueOf(highScore));
        scoreText(canvas, scorePaint, "Score: " + String.valueOf(score));


        if (gameOver) {
            Paint p = new Paint();
            p.setColor(Color.RED);
            gameOverText(canvas, p, "Game Over");
        }
    }

    public void update() {

        if (!gameOver) {
            mManager.update();
            if (firstJump) {
                mPlayer.jump(startTime, mGround.getRect().top);
                if (mPlayer.getRect().bottom > mGround.getRect().top) {
                    firstJump = false;
                }
            } else if (secondJump) {
                mPlayer.jump(startTime, startPos);
                if (mPlayer.getRect().bottom > mGround.getRect().top) {
                    secondJump = false;
                }
            }

            if (mPlayer.getRect().bottom > mGround.getRect().top) {
                mPlayer.setRect(mGround.getRect());
            }
        }

        if (mManager.collide(mPlayer.getRect())) {
            gameOver = true;
        }

        score = mManager.getScore(mPlayer.getRect());
        if (score > highScore) {
            highScore = score;
        }
    }

    private void gameOverText(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getTextBounds(text, 0, text.length(), r);
        paint.setTextSize(150);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    private void highScoreText(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(r);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getTextBounds(text, 0, text.length(), r);
        paint.setTextSize(100);
        float x = Constant.SCREEN_WIDTH / 2f;
        float y = 9 * Constant.SCREEN_HEIGHT / 10f;
        canvas.drawText(text, x, y, paint);
    }

    private void scoreText(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(r);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getTextBounds(text, 0, text.length(), r);
        paint.setTextSize(70);
        float x = Constant.SCREEN_WIDTH / 2f;
        float y = Constant.SCREEN_HEIGHT / 8.5f;
        canvas.drawText(text, x, y, paint);
    }

    public int getHighScore() {
        return highScore;
    }
}
