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
 * Created by huanjinzi on 2016/8/23.
 */
public class ExcelView extends View {
    private Paint mpaint;
    private Adapter adapter;
    private int rowh = 0;
    private int rankw = 0;
    private int row = 0;
    private int rank = 0;
    private Handler mHandler;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num = 1;

    private void init(){
        mpaint = new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(2);
        mpaint.setTextSize(60);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                invalidate();
                //16ms刷新一次
                sendEmptyMessageDelayed(0,300);
            }
        };
        //500ms之后启动
        mHandler.sendEmptyMessageDelayed(0,500);

        /*mHandler  = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            }
        });

        thread.start();*/
    }
    public ExcelView(Context context) {
        super(context);
        init();
    }

    public ExcelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        super.addOnLayoutChangeListener(listener);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(num == 15){num =1;}
        mpaint.setColor(Color.BLACK);
        canvas.translate(5,5);
        row = 10;
        rank = 8;
        rowh = 110;
        rankw = 120;
        for (int i = 0; i < rank; i ++)
        {
            for (int j = 0; j < row; j ++)
            {
                if((i *row +j)%num == 0 )
                {
                    mpaint.setColor(Color.GREEN);
                    mpaint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(0,0,rankw,rowh,mpaint);

                    mpaint.setColor(Color.BLACK);
                    mpaint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(0,0,rankw,rowh,mpaint);
                }
                else
                {
                    mpaint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(0,0,rankw,rowh,mpaint);
                }

                mpaint.setStyle(Paint.Style.FILL);
                canvas.drawText(i*row+j+"",rankw/2-30,rowh/2 + 20,mpaint);
                canvas.translate(0,rowh);
            }
                canvas.translate(rankw,-rowh*row);
        }
        canvas.translate(-rank * rankw,rowh*row +100);
        canvas.translate(canvas.getWidth()/2,0);
        canvas.drawText("fragment:"+num,rankw/2-30,rowh/2 + 20,mpaint);

        num++;

    }

    /*设置adapter*/
    public void setAdapter(Adapter adapter)
    {
        this.adapter = adapter;
    }
    /*对外提供的接口，对数据进行适配*/
    interface Adapter
    {

    }
}
