package com.campus.huanjinzi.campusmvp.TranscriptTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.campus.huanjinzi.campusmvp.LogTask.LogActivity;
import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuPresenter;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.data.StudentCj;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by huanjinzi on 2016/8/27.
 */
public class TranscriptPresenter {
    public static final String STUDENTCJ = "studentcj";
    public static final String RESULT = "result";
    public static final String HAS_TRANSCRIPT = "has_transcript";
    private static final String TAG = "TranscriptPresenter";

    private Context context;
    private Handler mHandler;
    private SharedPreferences sp;
    private TranscriptActivity activity;

    private StudentCj cj;
    public StudentCj getCj() {return cj;}

    public TranscriptPresenter(final Context context) {
        this.context = context;
        activity = (TranscriptActivity) context;
        sp = context.getSharedPreferences(MyApp.SPREF, 0);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                switch (bundle.getInt(RESULT)) {
                    case 1:
                        StudentCj cj = (StudentCj) bundle.getSerializable(STUDENTCJ);
                        updateData(cj);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(HAS_TRANSCRIPT, true);
                        editor.commit();
                        /**保存成绩对象*/
                        try {
                            FileOutputStream os = new FileOutputStream(context.getCacheDir().getPath() + "\\cj.ser");
                            ObjectOutputStream oos = new ObjectOutputStream(os);
                            oos.writeObject(cj);
                            Hlog.i(TAG, "保存成绩对象到：" + context.getCacheDir().getPath() + "cj.ser");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case -1:
                        //Snackbar.make(view.getExcel(), "成绩获取失败", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        };
    }

    public void doTask() {

        if (sp.getBoolean(SwuPresenter.HAS_COUNT, false)) {

            if (sp.getBoolean(HAS_TRANSCRIPT, false)) {
                try {
                    FileInputStream is = new FileInputStream(context.getCacheDir().getPath() + "\\cj.ser");
                    ObjectInputStream ois = new ObjectInputStream(is);
                    cj = (StudentCj) ois.readObject();
                    //updateData(cj);
                    Hlog.i(TAG, "读取成绩对象：" + context.getCacheDir().getPath() + "cj.ser");
                    Hlog.i(TAG, cj.getData().getGetDataResponse().getReturnX().getBody().getItems().get(0).getKcmc());

                } catch (Exception e) {
                    Hlog.i(TAG,e.getMessage());
                }

            } else {
                String username = sp.getString(SwuPresenter.USERNAME, "");
                String password = sp.getString(SwuPresenter.PASSWORD, "");
                TaskManager task = TaskManager.getInstance();
                task.setHander(mHandler);
                task.getStudentCj(username, password);
            }
        }
        else
        {

            Intent intent = new Intent(context, LogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(context.getString(R.string.MODE), SwuPresenter.isSwuWifi(context));
            bundle.putString(context.getString(R.string.TITLE), "登陆");
            bundle.putString(context.getString(R.string.BUTTON), "登陆");
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

    }

    private void updateData(StudentCj cj){
        FragmentManager fm  = activity.getSupportFragmentManager();
        List<Fragment> list =fm.getFragments();

        if(list != null){
            Log.i(TAG, "list.size = " + list.size());
        for (Fragment fragment : list
                ) {
            TranscriptView tr = (TranscriptView) fragment;
            //通知所有Fragment更新数据
            tr.update(cj);
        }
        }
        else {
            Log.i(TAG, "list = null");
        }
    }
}
