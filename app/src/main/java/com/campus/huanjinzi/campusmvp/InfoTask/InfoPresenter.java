package com.campus.huanjinzi.campusmvp.InfoTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuPresenter;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.TranscriptTask.TranscriptPresenter;
import com.campus.huanjinzi.campusmvp.data.StudentCj;
import com.campus.huanjinzi.campusmvp.data.StudentInfo.DataBean.GetDataResponseBean.ReturnBean.BodyBean.ItemsBean;
import com.campus.huanjinzi.campusmvp.utils.FileUtil;

/**
 * Created by huanjinzi on 2016/9/28.
 */

public class InfoPresenter {

    public static final String INFO = "InfoPresenter.info";
    private Context context;
    private Handler mHandler;
    private SharedPreferences sp;

    public InfoPresenter(final Context context){
        this.context = context;
        sp = context.getSharedPreferences(MyApp.SPREF, 0);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                switch (bundle.getInt("result")){
                    case -1:
                        //Snackbar.make((WindowS)context.getSystemService(Context.WINDOW_SERVICE)., "成绩获取失败", Snackbar.LENGTH_LONG).show();
                        break;
                    case 1:
                        ItemsBean info = (ItemsBean) bundle.getSerializable(InfoPresenter.INFO);
                        setData(info);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(TranscriptPresenter.HAS_TRANSCRIPT, true);
                        editor.putBoolean(SwuPresenter.EX_COUNT,false);
                        editor.commit();
                        break;
                }
            }
        };
    }

    public void doTask() {


        if(sp.getBoolean(SwuPresenter.EX_COUNT,false)){

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(SwuPresenter.EX_COUNT,false);
            editor.commit();

            String username = sp.getString(SwuPresenter.USERNAME, "");
            String password = sp.getString(SwuPresenter.PASSWORD, "");
            TaskManager task = TaskManager.getInstance();
            task.setHander(mHandler);
            task.getStudentCj(context,username, password);
        }
        else {

            if (sp.getBoolean(TranscriptPresenter.HAS_TRANSCRIPT, false) ) {
                FileUtil<ItemsBean> fileUtil = new FileUtil<>();
                ItemsBean info = (ItemsBean) fileUtil.get(context,"info");
                setData(info);

            } else {
                String username = sp.getString(SwuPresenter.USERNAME, "");
                String password = sp.getString(SwuPresenter.PASSWORD, "");
                TaskManager task = TaskManager.getInstance();
                task.setHander(mHandler);
                task.getStudentCj(context,username, password);
            }

        }


    }
    /***/
    private void setData(ItemsBean result){

        InfoActivity activity = (InfoActivity) context;
        RecyclerView view = activity.getRecycler();
        InfoActivity.RecyclerAdapter adapter = activity.getAdapter();
        adapter.setData(result);
        view.invalidate();
    }

}
