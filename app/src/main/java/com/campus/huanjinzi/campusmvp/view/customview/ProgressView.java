package com.campus.huanjinzi.campusmvp.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by huanjinzi on 2016/9/1.
 */
public class ProgressView extends View {

    private Paint mpaint;
    private boolean flag = false;
    private boolean drawcircle = false;

    private boolean drawsin = false;
    public void setDrawsin(boolean drawsin) {this.drawsin = drawsin;}

    private boolean drawrect = false;
    private OnClickListener mOnClickListener;
    private OnLongClickListener mOnLongClickListener;

    private Handler mHandler;

    private void init() {

        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                invalidate();
                mHandler.sendEmptyMessageDelayed(0, 25);
            }
        };
        mHandler.sendEmptyMessageDelayed(0, 400);
    }

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private float i = 0;
    private int j = 20;//点击控件初始圆的大小，默认j*j=100
    private int k = 0;

    private int sin_a = 40;
    private int base_line = 150;
    private boolean base_line_flag = true;
    private float x = 0;
    private float y = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        float Height = canvas.getHeight();
        float Width = canvas.getWidth();
        /**控制是放大还是缩小，i = 40，说明已到最大，i需要减小；i = 0则相反*/
        if (i > 40) {
            flag = true;
        }
        if (i == 0) {
            flag = false;
        }
        /**缩放*/
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(0xddffffff);
        if (flag) {
            mpaint.setStrokeWidth(2);
            canvas.drawCircle(Width / 2, Height / 2, 90 + i / 6, mpaint);//大圆圈缩小
            mpaint.setStrokeWidth(8);
            canvas.drawCircle(Width / 2, Height / 2, 40 + i / 2, mpaint);//小圆圈缩小
            i -= 0.5;
        } else {
            mpaint.setStrokeWidth(2);
            canvas.drawCircle(Width / 2, Height / 2, 90 + i / 6, mpaint);//大圆圈放大
            mpaint.setStrokeWidth(8);
            canvas.drawCircle(Width / 2, Height / 2, 40 + i / 2, mpaint);//小圆圈放大
            i += 0.5;
        }

        mpaint.setStyle(Paint.Style.FILL);
        /**单击时的效果*/
        if (drawcircle) {
            mpaint.setColor(0x22ffffff);
            canvas.drawCircle(x, y, j * 5, mpaint);
        }
        if (j < 250) {
            j += 2;
        }

        if (drawrect) {
            mpaint.setColor(0x11ffffff);
            canvas.drawRect(0, 0, Width, getHeight(), mpaint);
        }
        /**正在登陆的波浪效果*/
        mpaint.setColor(0x22ffffff);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setStrokeWidth(2);
        drawSin(canvas, Width, Height);
    }

    private void drawSin(Canvas canvas, float Width, float Height) {
        if (drawsin) {

            if (k <= -Width) {
                k = 0;
            }
            Path path = new Path();
            path.moveTo(k, Height - base_line);

            /**整体偏移k个位移*/
            path.quadTo(Width / 4 + k, Height - base_line - sin_a, Width / 2 + k, Height - base_line);
            path.quadTo(Width / 4 * 3 + k, Height - base_line + sin_a, Width + k, Height - base_line);

            path.quadTo(Width / 4 * 5 + k, Height - base_line - sin_a, Width / 2 * 3 + k, Height - base_line);
            path.quadTo(Width / 4 * 7 + k, Height - base_line + sin_a, Width * 2 + k, Height - base_line);

            path.lineTo(Width*2,Height);
            path.lineTo(0,Height);
            path.close();
            canvas.drawPath(path, mpaint);
            k -= 6;

            if(base_line_flag){base_line+=2;}
            else {base_line-=2;}
            if(base_line <150){ base_line_flag = true;}
            if(base_line > Height){base_line_flag = false;}
        }
    }
    private long time = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (clickable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    time = System.currentTimeMillis();
                    j = 10;
                    x = event.getX();
                    y = event.getY();
                    drawcircle = true;
                    drawrect = true;
                    break;
                case MotionEvent.ACTION_UP:
                    time = System.currentTimeMillis() - time;
                    if (time < 300) {
                        mOnClickListener.onClick(this);
                    }
                    if (time > 1000) {
                        if (null != mOnLongClickListener) {
                            mOnLongClickListener.onLongClick(this);
                        }
                    }
                    drawcircle = false;
                    drawrect = false;
                    break;
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }
    @Override
    public void setOnClickListener(OnClickListener l) {
        mOnClickListener = l;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        mOnLongClickListener = l;
    }


    private boolean clickable = false;

    public void Clickable(boolean clickable) {
        this.clickable = clickable;
    }

}
