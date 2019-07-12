package com.example.wsywu.jumpingblocks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class Player implements GameObject{
    private RectF playerRect;
    private int gravity;
    private int initVelocity;
    private int upwardMove;
    private int playerHeight;
    private int maxHeight;
    private Matrix mat;

    public Player(Ground ground){
        playerHeight = 75;
        playerRect = new RectF(Constant.SCREEN_WIDTH / 10, ground.getRect().top - playerHeight, Constant.SCREEN_WIDTH / 10 + playerHeight, ground.getRect().top);
        gravity = 4000;
        upwardMove = 20;
        maxHeight = 300;
        initVelocity = 1300;
        mat = new Matrix();
        mat.setScale(playerHeight, playerHeight);
    }

    public void draw(Canvas canvas){
        Paint playerPaint = new Paint();
        playerPaint.setColor(Color.BLACK);

        //mat.setRotate(10, playerRect.centerX(), playerRect.centerY());
        //mat.mapRect(playerRect);
        canvas.drawRect(playerRect, playerPaint);

    }

    public void jump(long startTime, float startPos){
        double elapsedTime = (System.nanoTime() - startTime) / 1000000000.0;
        playerRect.bottom = startPos - (int) (initVelocity * elapsedTime - 0.5 * gravity * elapsedTime * elapsedTime);
        playerRect.top = playerRect.bottom - playerHeight;
    }

    public RectF getRect(){
        return playerRect;
    }

    public void setRect(RectF rect){
        playerRect.bottom = rect.top;
        playerRect.top = playerRect.bottom - playerHeight;
    }

    public int getMaxHeight() {return maxHeight;}
}
