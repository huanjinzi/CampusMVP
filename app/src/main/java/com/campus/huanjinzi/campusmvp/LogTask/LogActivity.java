package com.campus.huanjinzi.campusmvp.LogTask;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuPresenter;

/**
 * A login screen that offers login via email/password.
 */
public class LogActivity extends AppCompatActivity {

    private String title;
    private String button;
    private int mode;
    private AutoCompleteTextView username;
    private EditText password;
    private String s_username;
    private String s_password;
    private LogPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        presenter = new LogPresenter(LogActivity.this);
        SharedPreferences sp = getSharedPreferences(MyApp.SPREF,0);

        mode = getIntent().getExtras().getInt(getString(R.string.MODE));
        title = getIntent().getExtras().getString(getString(R.string.TITLE));
        button = getIntent().getExtras().getString(getString(R.string.BUTTON));
        setContentView(R.layout.log_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        if (sp.getBoolean(SwuPresenter.HAS_COUNT,false)){
            username.setText(sp.getString(SwuPresenter.USERNAME,""));
            password.setText(sp.getString(SwuPresenter.PASSWORD,""));
        }

        final Button mbutton = (Button) findViewById(R.id.email_sign_in_button);
        mbutton.setText(button);

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    presenter.logTask(v,s_username,s_password,mode);
                }
            }
        });

    }
  private boolean check(){
      if(username.getText().toString().trim().length() == 0)
      {
          username.requestFocus();
          return false;
      }
      if(password.getText().toString().trim().length() == 0)
      {
          password.requestFocus();
          return false;
      }
      s_username  = username.getText().toString().trim();
      s_password = password.getText().toString().trim();
      return true;
  }

}

