package com.campus.huanjinzi.campusmvp.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.campus.huanjinzi.campusmvp.utils.Hlog;

/**
 * Created by huanjinzi on 2016/8/22.
 */
public class HjzTextView extends View {

    private Paint mPaint;
    private Handler mHandler;
    private float degree = 0;
    private boolean isFirst = true;

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    private String mText;
    public HjzTextView(Context context) {
        super(context);
    }

    public HjzTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                invalidate();
                //16ms刷新一次
                sendEmptyMessageDelayed(0,40);
            }
        };

        //500ms之后启动
        //mHandler.sendEmptyMessageDelayed(0,500);

}


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //Hlog.log(canvas.getHeight()/2+"--"+canvas.getWidth()/2);
        if(degree == 360)
        {degree =0;}
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(degree);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);

            canvas.drawCircle(0, 0, 380, mPaint);
            canvas.drawCircle(0, 0, 360, mPaint);

        /*for (int i = 0;i < 360; i+=10)
        {
            canvas.drawLine(365,0,375,0,mPaint);
            canvas.rotate(10);
        }*/
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(48);
        canvas.drawText(mText, -260, 0, mPaint);

        degree+=0.25;
    }
}
