package com.campus.huanjinzi.campusmvp.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.campus.huanjinzi.campusmvp.data.cjbean.CJBean;

/**
 * Created by huanjinzi on 2016/8/23.
 */
public class ExcelView extends View {
    private Paint mpaint;
    private Adapter adapter;
    private int row = 0;
    private int rank = 0;
    private Handler mHandler;
    private int textsize = 40;

    public void setCj(CJBean cj) {
        this.cj = cj;
    }

    private CJBean cj;

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
        mpaint.setStrokeWidth(4);
        mpaint.setTextSize(textsize);
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
        //mHandler.sendEmptyMessageDelayed(0,500);

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

        int width = canvas.getWidth()-5;
        mpaint.setColor(Color.BLACK);
        row = cj.getTotalResult();
        rank = 5;//课程名称、分数、学分、选修还是必修、绩点
        int rowh_flag = 0;//行高控制标志位
        int excel_h = 0;
        int[] rowh = {110,180};
        int[] rankw = {width/28*12,width/28*3,width/28*3,width/28*3,width/28*7};
        int[] flag = new int[row];//用来记录是否应该扩展行高
        canvas.translate(5,5);

        /**表头绘制*/
        for (int i = 0; i < rank; i ++)
        {
            for (int j = 0; j < row; j ++)
            {
                String kcmc = cj.getItems().get(j).getKcmc();
                String kcxzmc = cj.getItems().get(j).getKcxzmc();

                mpaint.setStyle(Paint.Style.FILL);
                switch (i){
                    case 0:

                        if(textsize*(kcmc.length()+1) > rankw[0])
                        {
                            flag[j] = 1;
                            rowh_flag = 1;
                            canvas.drawText(kcmc.substring(0,(rankw[0])/textsize -1),16,rowh[rowh_flag]/2,mpaint);
                            canvas.drawText(kcmc.substring((rankw[0])/textsize - 1,kcmc.length()),16,rowh[rowh_flag]/2+textsize,mpaint);
                        }
                        else
                        {
                            rowh_flag = 0;
                            canvas.drawText(kcmc,16,(rowh[0]+textsize)/2,mpaint);
                        }
                        break;
                    case 1:
                        if(flag[j] == 1){rowh_flag = 1;}else {rowh_flag = 0;}
                        canvas.drawText(cj.getItems().get(j).getCj(),16,(rowh[rowh_flag]+textsize)/2,mpaint);
                        break;
                    case 2:
                        if(flag[j] == 1){rowh_flag = 1;}else {rowh_flag = 0;}
                        canvas.drawText(cj.getItems().get(j).getXf(),16,(rowh[rowh_flag]+textsize)/2,mpaint);
                        break;
                    case 3:
                        if(flag[j] == 1){rowh_flag = 1;}else {rowh_flag = 0;}
                        canvas.drawText(cj.getItems().get(j).getJd(),16,(rowh[rowh_flag]+textsize)/2,mpaint);
                        break;
                    case 4:
                        if(flag[j] == 1){rowh_flag = 1;}else {rowh_flag = 0;}
                        canvas.drawText(cj.getItems().get(j).getKcxzmc(),16,(rowh[rowh_flag]+textsize)/2,mpaint);
                        break;
                }
                mpaint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(0,0,rankw[i],rowh[rowh_flag],mpaint);
                canvas.translate(0,rowh[rowh_flag]);
                excel_h+=rowh[rowh_flag];
            }
                canvas.translate(rankw[i],-excel_h);
                excel_h = 0;
        }
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
