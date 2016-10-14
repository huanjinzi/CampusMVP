package com.campus.huanjinzi.campusmvp.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.data.StudentCj;
import com.campus.huanjinzi.campusmvp.data.StudentCj.DataBean.GetDataResponseBean.ReturnBean.BodyBean.ItemsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanjinzi on 2016/8/23.
 */
public class ExcelView extends View {


    private Paint mpaint;
    private int row = 0;
    private int rank = 0;
    int[] rowh = {110, 180};
    int[] rankw;
    int excel_h = 0;
    private int textsize = 40;
    private List<ItemsBean> itemsList;
    private List<ItemsBean> list;

    public void setXnm(int xnm) {
        this.xnm = xnm;
    }

    private int xnm;
    private static final String TAG = "ExcelView";

    public void setStudentCj(StudentCj studentCj) {

        list = studentCj.getData().getGetDataResponse().getReturnX().getBody().getItems();
        for (ItemsBean item : list
                ) {
            if (item.getXnm().equals(queryXnm(item.getXh(), xnm))) {
                itemsList.add(item);
            }
        }
        /**设置了数据之后刷新界面，显示新的数据*/
        requestLayout();
    }

    private String queryXnm(String student_id, int xnm) {

        student_id = student_id.substring(2, 6);
        int xn = Integer.parseInt(student_id);
        switch (xnm) {
            case 0:
                return xn + xnm + "";
            case 1:
                return xn + xnm + "";
            case 2:
                return xn + xnm + "";
            case 3:
                return xn + xnm + "";
        }
        return "";
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num = 1;

    private void init() {

        itemsList = new ArrayList();
        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setSubpixelText(true);
        mpaint.setStrokeWidth(3);
        mpaint.setTextAlign(Paint.Align.CENTER);
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
        int width = MeasureSpec.getSize(widthMeasureSpec);
        rankw = new int[]{width / 28 * 16, width / 28 * 4, width / 28 * 3, width / 28 * 4};
        if (list != null) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getExcelHeight(itemsList));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (itemsList.size() != 0) {
            mpaint.setTextSize(40);
            row = itemsList.size();
            rank = 4;//课程名称、分数、学分、选修还是必修、绩点
            String[] str = {"课程名称", "成绩", "学分", "绩点"};
            int rowh_flag = 0;//行高控制标志位

            int[] flag = new int[row];//用来记录是否应该扩展行高
            canvas.translate(25, 60);

            mpaint.setTextSize(45);
            /**表头绘制*/
            for (int m = 0; m < 4; m++) {

                mpaint.setColor(Color.GRAY);
                mpaint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(0, 0, rankw[m], rowh[0], mpaint);

                mpaint.setColor(getResources().getColor(R.color.GREEN));
                mpaint.setStyle(Paint.Style.FILL);
                canvas.drawText(str[m], rankw[m] / 2, (rowh[0] + textsize) / 2, mpaint);

                canvas.translate(rankw[m], 0);
                excel_h += rankw[m];
            }
            canvas.translate(-excel_h, rowh[0]);


            excel_h = 0;

            mpaint.setTextSize(textsize);
            /**表格绘制*/
            for (int i = 0; i < rank; i++) {
                for (int j = 0; j < row; j++) {
                    String kcmc = itemsList.get(j).getKcmc();
                    String kslx = itemsList.get(j).getKslx();
                    String cj = itemsList.get(j).getCj();
                    String jd = itemsList.get(j).getJd();
                    String xf = itemsList.get(j).getXf();
                    float cj_value = 0;
                    try {
                        cj_value = Float.parseFloat(cj);
                        if (cj_value < 60) {
                            mpaint.setColor(Color.RED);
                        } else {
                            mpaint.setColor(Color.GRAY);
                        }

                    } catch (Exception e) {
                        if (cj.toUpperCase().equals("D")) {
                            mpaint.setColor(Color.RED);
                        } else {
                            mpaint.setColor(Color.GRAY);
                        }
                    }

                    mpaint.setStyle(Paint.Style.FILL);
                    switch (i) {
                        case 0:

                            if (textsize * (kcmc.length() + 1) > rankw[0]) {
                                flag[j] = 1;
                                rowh_flag = 1;
                                canvas.drawText(kcmc.substring(0, (rankw[0]) / textsize - 1), rankw[i] / 2, rowh[rowh_flag] / 2 - 4, mpaint);
                                canvas.drawText(kcmc.substring((rankw[0]) / textsize - 1, kcmc.length()), rankw[i] / 2, rowh[rowh_flag] / 2 + textsize + 4, mpaint);
                            } else {
                                rowh_flag = 0;
                                canvas.drawText(kcmc, rankw[i] / 2, (rowh[0] + textsize) / 2, mpaint);
                            }
                            break;
                        case 1:
                            if (flag[j] == 1) {
                                rowh_flag = 1;
                            } else {
                                rowh_flag = 0;
                            }
                            canvas.drawText(cj, rankw[i] / 2, (rowh[rowh_flag] + textsize) / 2, mpaint);
                            break;
                        case 2:
                            if (flag[j] == 1) {
                                rowh_flag = 1;
                            } else {
                                rowh_flag = 0;
                            }
                            canvas.drawText(xf, rankw[i] / 2, (rowh[rowh_flag] + textsize) / 2, mpaint);
                            break;
                        case 3:
                            if (flag[j] == 1) {
                                rowh_flag = 1;
                            } else {
                                rowh_flag = 0;
                            }
                            canvas.drawText(jd, rankw[i] / 2, (rowh[rowh_flag] + textsize) / 2, mpaint);
                            break;
                        case 4:
                            if (flag[j] == 1) {
                                rowh_flag = 1;
                            } else {
                                rowh_flag = 0;
                            }
                            canvas.drawText(kslx, rankw[i] / 2, (rowh[rowh_flag] + textsize) / 2, mpaint);
                            break;
                    }

                    mpaint.setColor(Color.GRAY);
                    mpaint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(0, 0, rankw[i], rowh[rowh_flag], mpaint);
                    canvas.translate(0, rowh[rowh_flag]);
                    excel_h += rowh[rowh_flag];
                }
                canvas.translate(rankw[i], -excel_h);
                excel_h = 0;
            }
        } else {
            mpaint.setColor(Color.GRAY);
            mpaint.setTextSize(50);
            mpaint.setStyle(Paint.Style.FILL);
            canvas.drawText("成绩还没有出来...", canvas.getWidth() / 2, canvas.getHeight() / 2, mpaint);
        }
    }

    /**
     * 计算表格的高度
     *
     * @param itemsList 存有课程名称的list
     * @return 返回表格的高度
     */
    private int getExcelHeight(List<ItemsBean> itemsList) {
        int excel_h = 0;
        for (ItemsBean item : itemsList
                ) {
            String kcmc = item.getKcmc();
            /**
             * rankw[0]为课程名称列的宽度
             *
             * 如果文字的   size*文字的个数   比列宽大，说明需要换行显示*/
            if (textsize * (kcmc.length() + 1) > rankw[0]) {
                excel_h += rowh[1];
            } else {
                excel_h += rowh[0];
            }
            //加线宽
        }
        //加上表头的高度
        excel_h += 3 * rowh[0];
        return excel_h;
    }

}
