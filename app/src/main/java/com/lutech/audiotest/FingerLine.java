package com.lutech.audiotest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class FingerLine extends FrameLayout {
    private final Paint mPaint;
    private float startXA;
    private float startXB;
    private float startXC;
    private float startYA;
    private float startYB;
    private float startYC;
    private float endXA;
    private float endXB;
    private float endXC;
    private float endYA;
    private float endYB;
    private float endYC;

    public FingerLine(Context context) {
        this(context, null);
    }

    public FingerLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawLine(startXA, startYA, endXA, endYA, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(startXB, startYB, endXB, endYB, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(startXC, startYC, endXC, endYC, mPaint);


        
    }
//
//    @Override
//    public boolean onTouchEvent(@NonNull MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = event.getX();
//                startY = event.getY();
//                // Set the end to prevent initial jump (like on the demo recording)
//                endX = event.getX();
//                endY = event.getY();
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                endX = event.getX();
//                endY = event.getY();
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                endX = event.getX();
//                endY = event.getY();
//                invalidate();
//                break;
//        }
//        return true;
//    }


    public void setStartXA(float startXA) {
        this.startXA = startXA;
    }

    public void setStartXB(float startXB) {
        this.startXB = startXB;
    }

    public void setStartXC(float startXC) {
        this.startXC = startXC;
    }

    public void setStartYA(float startYA) {
        this.startYA = startYA;
    }

    public void setStartYB(float startYB) {
        this.startYB = startYB;
    }

    public void setStartYC(float startYC) {
        this.startYC = startYC;
    }

    public void setEndXA(float endXA) {
        this.endXA = endXA;
    }

    public void setEndXB(float endXB) {
        this.endXB = endXB;
    }

    public void setEndXC(float endXC) {
        this.endXC = endXC;
    }

    public void setEndYA(float endYA) {
        this.endYA = endYA;
    }

    public void setEndYB(float endYB) {
        this.endYB = endYB;
    }

    public void setEndYC(float endYC) {
        this.endYC = endYC;
    }
}