package com.example.wsywu.jumpingblocks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

public class ObstacleManager {
    private ArrayList<Obstacle> mObstacles;
    private Ground mGround;
    private int speed;
    private float totalTime = 2000.0f;
    private long startTime = System.currentTimeMillis();
    private int removed;

    public ObstacleManager(Ground ground) {
        Log.w("my", "aclled");
        mObstacles = new ArrayList<>();
        mGround = ground;
        fillList();
    }

    public void fillList() {
        int startX = 4 * Constant.SCREEN_WIDTH / 5;
        while (startX < Constant.SCREEN_WIDTH) {
            int obsHeight = (int) (Math.random() * 200 + 50);
            int top = (int)mGround.getRect().top - obsHeight;
            int bottom = (int)mGround.getRect().top;
            int left = startX;
            int right = left + 75;

            Obstacle obs = new Obstacle(top, bottom, left, right);
            mObstacles.add(obs);
            startX += 600;
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle obs : mObstacles) {
            obs.draw(canvas);
        }
    }

    private void move() {
        totalTime -= 0.5;
        if (totalTime < 1000.f) {
            totalTime = 1000.f;
        }

        float speed = Constant.SCREEN_WIDTH / totalTime;
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        for (int i = 0; i < mObstacles.size(); i++) {
            mObstacles.get(i).getRect().right -= speed * elapsedTime;
            mObstacles.get(i).getRect().left -= speed * elapsedTime;
        }
    }

    public void update() {
        move();

        if (mObstacles.get(0).getRect().right < 0) {
            mObstacles.remove(0);
            removed++;
            int obsHeight = (int) (Math.random() * 200 + 50);
            int top = (int)mGround.getRect().top - obsHeight;
            int bottom = (int)mGround.getRect().top;
            int left = Constant.SCREEN_WIDTH;
            int right = left + 75;
            mObstacles.add(new Obstacle(top, bottom, left, right));
        }

    }

    public boolean collide(RectF rect) {
        for (Obstacle obs : mObstacles) {
            if (RectF.intersects(obs.getRect(), rect)) {
                return true;
            }
        }

        return false;
    }

    public int getScore(RectF rect) {
        int score = 0;
        for (Obstacle obs : mObstacles) {
            if (rect.right > obs.getRect().right) {
                score++;
            }
        }

        int result = score + removed;
        return result;
    }


}
