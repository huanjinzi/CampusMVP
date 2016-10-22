package com.campus.huanjinzi.campusmvp.TranscriptTask;

import android.content.Context;
import android.content.Intent;

import com.campus.huanjinzi.campusmvp.InfoTask.InfoPresenter;
import com.campus.huanjinzi.campusmvp.data.StudentInfo.DataBean.GetDataResponseBean.ReturnBean.BodyBean.ItemsBean;
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
import com.campus.huanjinzi.campusmvp.data.StudentInfo;
import com.campus.huanjinzi.campusmvp.utils.FileUtil;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import static com.campus.huanjinzi.campusmvp.LogConstants.EX_COUNT;
import static com.campus.huanjinzi.campusmvp.LogConstants.PASSWORD;
import static com.campus.huanjinzi.campusmvp.LogConstants.USERNAME;

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
                        //ItemsBean info = (ItemsBean) bundle.getSerializable(InfoPresenter.INFO);
                        updateData(cj);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(HAS_TRANSCRIPT, true);
                        editor.commit();
                        break;
                    case -1:
                        //Snackbar.make(null, "成绩获取失败", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        };
    }

    public void doTask() {


        if(sp.getBoolean(EX_COUNT,false))
        {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(EX_COUNT,false);
            editor.commit();

            String username = sp.getString(USERNAME, "");
            String password = sp.getString(PASSWORD, "");
            TaskManager task = TaskManager.getInstance();
            task.setHander(mHandler);
            task.getStudentCj(context,username, password);
        }
        else
        {
            if (sp.getBoolean(HAS_TRANSCRIPT, false)) {
                FileUtil<StudentCj> fileUtil = new FileUtil<>();
                cj = (StudentCj) fileUtil.get(context,"cj");
            }
            else {
                String username = sp.getString(USERNAME, "");
                String password = sp.getString(PASSWORD, "");
                TaskManager task = TaskManager.getInstance();
                task.setHander(mHandler);
                task.getStudentCj(context,username, password);
            }
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
