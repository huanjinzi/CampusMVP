package com.campus.huanjinzi.campusmvp.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huanjinzi on 2016/9/1.
 */
public class ProgressView extends View{

    private Paint mpaint;
    private int i = 0;
    private boolean flag = false;

    private Handler mHandler;
    private void init(){
        mpaint = new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.WHITE);
        mpaint.setStrokeWidth(4);
        mpaint.setAntiAlias(true);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                invalidate();
                mHandler.sendEmptyMessageDelayed(0,25);
            }
        };
        mHandler.sendEmptyMessageDelayed(0,500);

    }
    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(i >50)
        {
            flag = true;
        }
        if(i == 0)
        {
            flag = false;
        }

        if(flag)
        {
            mpaint.setStrokeWidth(2);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 90 + i/3, mpaint);
            mpaint.setStrokeWidth(8);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 40 + i, mpaint);
            i-=1;
        }
        else
        {
            mpaint.setStrokeWidth(2);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 90 + i/3, mpaint);
            mpaint.setStrokeWidth(8);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 40 + i, mpaint);
            i+=1;
        }
    }
}
