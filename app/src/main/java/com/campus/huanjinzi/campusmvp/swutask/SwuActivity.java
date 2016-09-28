package com.campus.huanjinzi.campusmvp.SwuTask;

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
import com.campus.huanjinzi.campusmvp.utils.Hlog;
import com.campus.huanjinzi.campusmvp.view.customview.ProgressView;

public class SwuActivity extends AppCompatActivity {

    public static final String LOGIN_SUCCESS ="com.campus.huanjinzi.campusmvp.LOGIN_SUCCESS";
    public static final String LOGOUT_SUCCESS ="com.campus.huanjinzi.campusmvp.LOGOUT_SUCCESS";
    public static final String LOGTASK_FAILURE ="com.campus.huanjinzi.campusmvp.LOGTASK_FAILURE";
    public static final String LOGTASK_DONE ="com.campus.huanjinzi.campusmvp.LOGTASK_DONE";


    private Toolbar toolbar;
    private SwuPresenter presenter;
    private Intent intent;
    private BroadcastReceiver receiver;

    public ProgressView getProgress() {return progress;}

    private ProgressView progress;

    private boolean TASK_DONE = true;
    public boolean isTaskDone() {return TASK_DONE;}
    public void setTaskDone(boolean TASK_DONE) {this.TASK_DONE = TASK_DONE;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swu_activity);




        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("campus");
        //setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.swu_listview);
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
                if(isTaskDone())
                {
                    setTaskDone(false);
                    progress.setDrawsin(true);
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

        int id = item.getItemId();
        switch (id) {
            case R.id.exchange_user:
                intent = new Intent(SwuActivity.this, LogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.TITLE), getString(R.string.exchange_user));
                bundle.putString(getString(R.string.BUTTON), getString(R.string.EXCHANGE));
                intent.putExtras(bundle);
                this.startActivityForResult(intent, 0);
                break;
            case R.id.current_user:

                Snackbar.make(toolbar, R.string.current_user, Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter = new IntentFilter(LOGIN_SUCCESS);
        filter.addAction(LOGOUT_SUCCESS);
        filter.addAction(LOGTASK_FAILURE);
        filter.addAction(LOGTASK_DONE);
        receiver = new BroadcastReceiverHelper();
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //取消注册
        /*unregisterReceiver(receiver);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
