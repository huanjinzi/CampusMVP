package com.campus.huanjinzi.campusmvp.SwuTask;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.campus.huanjinzi.campusmvp.LogTask.LogActivity;
import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.utils.Hlog;
import com.campus.huanjinzi.campusmvp.view.customview.ProgressView;

public class SwuActivity extends AppCompatActivity {

    public static final String GREEN ="com.campus.huanjinzi.campusmvp.LOGIN_SUCCESS";
    public static final String WATER ="com.campus.huanjinzi.campusmvp.LOGOUT_SUCCESS";

    //private Toolbar toolbar;
    private SwuPresenter presenter;


    private ProgressView progress;
    private RelativeLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swu_activity);
        //Hlog.i("SWU", this.toString()+getTaskId());

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("campus");
        //setSupportActionBar(toolbar);
        ListView listView = (ListView) findViewById(R.id.swu_listview);
        tab = (RelativeLayout)findViewById(R.id.tabs);
        progress = (ProgressView) findViewById(R.id.progess);
        progress.Clickable(true);
        presenter = new SwuPresenter(SwuActivity.this);
        presenter.setView(progress);

        ListViewAdapter adapter = new ListViewAdapter(SwuActivity.this);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.showFragment(position);
            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SwuFlags.WATER)
                {
                    SwuFlags.WATER = true;
                    refreshState();
                    presenter.logTask();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        /*IntentFilter filter = new IntentFilter(LOGIN_SUCCESS);
        filter.addAction(LOGOUT_SUCCESS);
        filter.addAction(LOGTASK_FAILURE);
        filter.addAction(LOGTASK_DONE);
        receiver = new BroadcastReceiverHelper();
        registerReceiver(receiver,filter);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        Hlog.i("SWU","onPause()");
        //取消注册
        /*unregisterReceiver(receiver);*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Hlog.i("SWU","onRestart()");
        /**设置登陆状态*/
        refreshState();

    }
    public void refreshState()
    {
        if(SwuFlags.GREEN)
        {
            tab.setBackgroundColor(getResources().getColor(R.color.GREEN));
        }else
        {
            tab.setBackgroundColor(getResources().getColor(R.color.GREY_900));
        }
        if(SwuFlags.WATER)
        {
            progress.setDrawsin(true);
        }
        else
        {
            progress.setDrawsin(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Hlog.i("SWU","onStrat()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Hlog.i("SWU","onStop()");
    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(receiver);
        super.onDestroy();
        TaskManager.getInstance().shutDownPool();
        Hlog.i("SWU","onDestroy");
        //关闭线程池
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
