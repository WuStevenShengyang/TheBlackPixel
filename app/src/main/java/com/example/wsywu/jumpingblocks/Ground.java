package com.example.wsywu.jumpingblocks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ground implements GameObject{
    private RectF groundRectangle;
    private int rectY;

    // Constructor
    public Ground(){
        rectY = 6 * Constant.SCREEN_HEIGHT / 10;
        groundRectangle = new RectF(0, rectY, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
    }

    public void draw(Canvas canvas){
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.GRAY);
        canvas.drawRect(groundRectangle, rectPaint);
    }

    public RectF getRect(){
        return groundRectangle;
    }


}
