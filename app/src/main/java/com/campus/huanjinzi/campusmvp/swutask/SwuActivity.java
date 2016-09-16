package com.campus.huanjinzi.campusmvp.SwuTask;

import android.content.Intent;
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
    public static final String TAG = "SwuActivity";

    private boolean isFirsttimeDoLogTask = true;
    private Toolbar toolbar;
    private SwuPresenter presenter;
    private Intent intent;

    public RelativeLayout getTabs() {
        return tabs;
    }

    private RelativeLayout tabs;
    private ProgressView progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("huanjinzi", "onCreate");
        setContentView(R.layout.swu_activity);

        presenter = new SwuPresenter(SwuActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("campus");
        setSupportActionBar(toolbar);

        tabs = (RelativeLayout) findViewById(R.id.tabs);
        presenter.setView(tabs);

        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean(SwuPresenter.HAS_LOGED,false)){
                tabs.setBackgroundColor(getResources().getColor(R.color.GREEN));
            }}
        else if(getSharedPreferences(MyApp.SPREF,0).getBoolean(SwuPresenter.HAS_COUNT,false)){
            if(getSharedPreferences(MyApp.SPREF,0).getBoolean(SwuPresenter.HAS_LOGED,false)){
                Hlog.i(TAG,"---->firstlaunch()");
                presenter.firstlaunch();
            }
        }
        ListView listView = (ListView) findViewById(R.id.swu_listview);
        progress = (ProgressView) findViewById(R.id.progess);
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

                boolean isLogTaskDone  = presenter.isLogTaskDone();
                if(isLogTaskDone || isFirsttimeDoLogTask){
                    Hlog.i(TAG,"开始执行登陆操作");
                    isFirsttimeDoLogTask = false;
                    presenter.logTask();
                }
                else {
                    Hlog.i(TAG,"正在登陆，请等待这次Task完成");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.exchange_user:
                intent = new Intent(SwuActivity.this,LogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.TITLE), getString(R.string.exchange_user));
                bundle.putString(getString(R.string.BUTTON), getString(R.string.EXCHANGE));
                intent.putExtras(bundle);
                this.startActivityForResult(intent,0);
                break;
            case R.id.current_user:

                Snackbar.make(toolbar, R.string.current_user, Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getBoolean(SwuPresenter.SUCCESS)) {
                    Hlog.i(TAG,"(返回到Swuactivity)账号 登陆 成功");
                    presenter.setLogTaskDone(true);
                    tabs.setBackgroundColor(getResources().getColor(R.color.GREEN));
                    Snackbar.make(tabs, "账号登陆成功", Snackbar.LENGTH_LONG).show();
                }
                else if(bundle.getBoolean(SwuPresenter.LOGOUT_SUCCESS)){
                    Hlog.i(TAG,"(返回到Swuactivity)账号 退出 成功");

                    if(bundle.getBoolean(SwuPresenter.LOGOUT_SUCCESS_SAME)){
                        Hlog.i(TAG,"(返回到Swuactivity)账号 退出 成功(退出的是当前账号)");
                        presenter.setLogTaskDone(true);
                        tabs.setBackgroundColor(getResources().getColor(R.color.GREY_900));
                    }
                }
            }

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(SwuPresenter.HAS_LOGED,getSharedPreferences(MyApp.SPREF,0).getBoolean(SwuPresenter.HAS_LOGED,false));
    }
}
