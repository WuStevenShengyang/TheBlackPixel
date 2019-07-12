package com.example.wsywu.jumpingblocks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Obstacle implements GameObject{
    private RectF obstacle;

    public Obstacle(int top, int bottom, int left, int right){
        obstacle = new RectF(left, top, right, bottom);
    }

    public RectF getRect(){
        return obstacle;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawRect(obstacle, paint);
    }
}
