package com.campus.huanjinzi.campusmvp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.campus.huanjinzi.campusmvp.InfoTask.InfoPresenter;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuPresenter;
import com.campus.huanjinzi.campusmvp.TranscriptTask.TranscriptPresenter;
import com.campus.huanjinzi.campusmvp.data.CXParams;
import com.campus.huanjinzi.campusmvp.data.StudentCj;
import com.campus.huanjinzi.campusmvp.data.StudentInfo.DataBean.GetDataResponseBean.ReturnBean.BodyBean.ItemsBean;
import com.campus.huanjinzi.campusmvp.data.remote.StudentSourceRemote;
import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.Params;
import com.campus.huanjinzi.campusmvp.swuwifi.WifiLander;
import com.campus.huanjinzi.campusmvp.swuwifi.WifiLanderClass;
import com.campus.huanjinzi.campusmvp.swuwifi.WifiLanderDorm;
import com.campus.huanjinzi.campusmvp.utils.FileUtil;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by huanjinzi on 2016/9/4.
 */
public class TaskManager {
    private static TaskManager ourInstance = new TaskManager();

    public static TaskManager getInstance() {
        return ourInstance;
    }

    private TaskManager() {
        pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        pool.setCorePoolSize(2);
        pool.setKeepAliveTime(10, TimeUnit.SECONDS);
    }

    /**类成员*/
    private ThreadPoolExecutor pool;

    private Handler mHander;
    public void setHander(Handler mHander) {this.mHander = mHander;}


    /**
     * 登陆之前处理
     */
    private void loginTask(WifiLander lander, String username, String password, int mode) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        int result;
        try {
            result = lander.login(username, password);
            bundle.putInt(SwuPresenter.RESULT, result);
            bundle.putInt(SwuPresenter.MODE, mode);
            message.setData(bundle);
            mHander.sendMessage(message);
        } catch (Exception e) {
            Hlog.i("SWU","ex="+e.getMessage());
            bundle.putInt(SwuPresenter.RESULT, -2);
            bundle.putInt(SwuPresenter.MODE, mode);
            message.setData(bundle);
            mHander.sendMessage(message);
        }
    }
    /**
     * 账号登陆的方法
     */
    public void login(final String username, final String password, final int mode) {

        pool.submit(new Runnable() {
            @Override
            public void run() {
                WifiLander lander;
                switch (mode) {
                    //在教室里面登陆
                    case SwuPresenter.LOGIN_CLASS:
                        lander = new WifiLanderClass();
                        loginTask(lander, username, password, mode);
                        break;

                    case SwuPresenter.LOGIN_DORM:
                        lander = new WifiLanderDorm();
                        loginTask(lander, username, password, mode);
                        break;
                }
            }
        });
    }
    /**
     * 账号退出方法
     */
    public void logout(final String username, final String password, final int mode) {

        pool.submit(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                Bundle bundle = new Bundle();
                try {
                    int result = WifiLander.logout(username, password);
                    bundle.putInt(SwuPresenter.RESULT, result);
                    bundle.putInt(SwuPresenter.MODE, mode);
                    message.setData(bundle);
                    mHander.sendMessage(message);

                } catch (Exception e) {
                    bundle.putInt(SwuPresenter.MODE, mode);
                    bundle.putInt(SwuPresenter.RESULT, -1);
                    message.setData(bundle);
                    mHander.sendMessage(message);
                }
            }
        });
    }

    public void getStudentCj(final Context context,final String username, final String password){

        pool.submit(new Runnable() {
            @Override
            public void run() {
                CXParams cxparams = new CXParams();
                cxparams.setUsename(username);
                cxparams.setPassword(password);
                StudentSourceRemote remote = StudentSourceRemote.getInstance();
                StudentCj cj;
                Bundle bundle = new Bundle();
                Message message = new Message();
                try {
                    //先获取学号
                    ItemsBean info = remote.getStudentInfo(cxparams);
                    //有了学号再查询成绩
                    cj = remote.getTranscript(cxparams);

                    /**保存成绩对象*/
                    FileUtil<StudentCj> fileUtil = new FileUtil<>();
                    FileUtil<ItemsBean> fileUtil1 = new FileUtil<>();
                    fileUtil.save(context,cj,"cj");
                    fileUtil1.save(context,info,"info");

                    bundle.putSerializable(InfoPresenter.INFO,info);
                    bundle.putSerializable(TranscriptPresenter.STUDENTCJ,cj);

                    /**返回1表示获取到数据*/
                    bundle.putInt(TranscriptPresenter.RESULT,1);

                    message.setData(bundle);
                    mHander.sendMessage(message);
                } catch (Exception e) {
                    /**返回-1表示网络连接失败*/
                Hlog.i("SWU","ex-file="+e.getMessage());
                    bundle.putInt(TranscriptPresenter.RESULT,-1);

                    message.setData(bundle);
                    mHander.sendMessage(message);
                }
            }
        });
    }

    /**网络测试*/
    public void NetTest(){
        pool.submit(new Runnable() {
            @Override
            public void run() {
                Params params = Params.getInstance();
                params.setUrl("http://www.baidu.com");
                InputStream in = null;
                try
                {
                    in = HjzHttp.getInstance().get(params);
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("pass",true);
                    bundle.putInt(SwuPresenter.MODE,SwuPresenter.LOGIN_TEST);
                    message.setData(bundle);
                    mHander.sendMessage(message);
                }
                catch (Exception e) {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("pass",false);
                    bundle.putInt(SwuPresenter.MODE,SwuPresenter.LOGIN_TEST);
                    message.setData(bundle);
                   mHander.sendMessage(message);
                }
                finally {
                    if(in != null){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void shutDownPool(){ pool.shutdown();}
}
