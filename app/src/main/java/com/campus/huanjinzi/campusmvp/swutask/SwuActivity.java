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
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.view.customview.ProgressView;

public class SwuActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private SwuPresenter presenter;
    private Intent intent;

    public RelativeLayout getTabs() {
        return tabs;
    }

    private RelativeLayout tabs;


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

        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean(SwuPresenter.HAS_LOGED,false)){
                tabs.setBackgroundColor(getResources().getColor(R.color.GREEN));
            }}
        else if(getSharedPreferences(SwuPresenter.SPREF,0).getBoolean(SwuPresenter.HAS_LOGED,false)){
            tabs.setBackgroundColor(getResources().getColor(R.color.GREEN));
        }
        ListView listView = (ListView) findViewById(R.id.swu_listview);
        final ProgressView progress = (ProgressView) findViewById(R.id.progess);
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
                presenter.logTask(v);
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
                intent = new Intent(this, LogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.TITLE), getString(R.string.exchange_user));
                bundle.putString(getString(R.string.BUTTON), getString(R.string.EXCHANGE));
                intent.putExtras(bundle);
                this.startActivity(intent);
                break;
            case R.id.current_user:
                Snackbar.make(toolbar, R.string.current_user, Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("huanjinzi", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("huanjinzi", "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("huanjinzi", "onRestart");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getBoolean(SwuPresenter.SUCCESS)) {
                    tabs.setBackgroundColor(getResources().getColor(R.color.GREEN));
                    Snackbar.make(tabs, "账号登陆成功", Snackbar.LENGTH_LONG).show();
                }
                else if(bundle.getBoolean(SwuPresenter.LOGOUT_SUCCESS)){
                    tabs.setBackgroundColor(getResources().getColor(R.color.GREY_900));
                    Snackbar.make(tabs, "账号退出成功", Snackbar.LENGTH_LONG).show();
                }
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(SwuPresenter.HAS_LOGED,getSharedPreferences(SwuPresenter.SPREF,0).getBoolean(SwuPresenter.HAS_LOGED,false));
    }
}
