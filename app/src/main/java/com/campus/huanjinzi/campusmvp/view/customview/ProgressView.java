package com.campus.huanjinzi.campusmvp.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    private boolean show = true;
    private boolean flag = false;
    private boolean drawcircle = false;
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


    private int i = 0;
    private int j = 20;//点击控件初始圆的大小，默认j*j=100
    private float x = 0;
    private float y = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        /**控制是放大还是缩小，i = 40，说明已到最大，i需要减小；i = 0则相反*/
        if (i > 40) {
            flag = true;
        }
        if (i == 0) {
            flag = false;
        }

        /**缩放*/
        if (show) {

            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setColor(0xddffffff);
            if (flag) {
                mpaint.setStrokeWidth(2);
                canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 90 + i / 3, mpaint);//大圆圈缩小
                mpaint.setStrokeWidth(8);
                canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 40 + i, mpaint);//小圆圈缩小
                i -= 1;
            } else {
                mpaint.setStrokeWidth(2);
                canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 90 + i / 3, mpaint);//大圆圈放大
                mpaint.setStrokeWidth(8);
                canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 40 + i, mpaint);//小圆圈放大
                i += 1;
            }
        }
        /**椭圆缩放，上下缩放*/
        else
        {
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setColor(0xddffffff);
            if (flag) {
                mpaint.setStrokeWidth(2);
                canvas.drawOval(canvas.getWidth()/2-90-i, canvas.getHeight()/2-90-i/5, canvas.getWidth()/2+90+i,canvas.getHeight()/2+90+i/5, mpaint);//大圆圈缩小
                mpaint.setStrokeWidth(8);
                canvas.drawOval(canvas.getWidth()/2-40-i, canvas.getHeight()/2-40-i*2, canvas.getWidth()/2+40+i,canvas.getHeight()/2+40+i*2, mpaint);////小圆圈缩小
                i -= 1;
            } else {
                mpaint.setStrokeWidth(2);
                canvas.drawOval(canvas.getWidth()/2-90-i, canvas.getHeight()/2-90-i/5, canvas.getWidth()/2+90+i,canvas.getHeight()/2+90+i/5, mpaint);//大圆圈放大
                mpaint.setStrokeWidth(8);
                canvas.drawOval(canvas.getWidth()/2-40-i, canvas.getHeight()/2-40-i*2, canvas.getWidth()/2+40+i,canvas.getHeight()/2+40+i*2, mpaint);//小圆圈放大
                i += 1;
            }
        }



        mpaint.setStyle(Paint.Style.FILL);
        if (drawcircle) {
            mpaint.setColor(0x22ffffff);
            canvas.drawCircle(x, y, j * 5, mpaint);

        }
        if (j < 250) {
            j+=2;
        }

        if (drawrect) {
            mpaint.setColor(0x11ffffff);
            canvas.drawRect(0, 0, canvas.getWidth(), getHeight(), mpaint);

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
                    if (time < 300) { mOnClickListener.onClick(this); }
                    if(time > 1000){
                        if(null != mOnLongClickListener){
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
    public void setOnLongClickListener(OnLongClickListener l) {mOnLongClickListener = l;}



    private boolean clickable = true;
    public void Clickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
