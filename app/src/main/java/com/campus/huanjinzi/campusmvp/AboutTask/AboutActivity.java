package com.campus.huanjinzi.campusmvp.AboutTask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campus.huanjinzi.campusmvp.InfoTask.InfoActivity;
import com.campus.huanjinzi.campusmvp.InfoTask.InfoPresenter;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuActivity;
import com.campus.huanjinzi.campusmvp.data.StudentInfo;

import static android.view.View.GONE;
import static android.view.View.generateViewId;

public class AboutActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setTitleTextAppearance(this, R.style.TitleText);
        toolbar.setTitle(getIntent().getExtras().getString("title"));
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0,android.R.anim.slide_out_right);
            }
        });

        RelativeLayout content = (RelativeLayout) findViewById(R.id.layout_content);
        content.setPadding(0,0,0,0);

        recycler = (RecyclerView) findViewById(R.id.recyclerview);

        /**image view parent*/
        RelativeLayout content1 = new RelativeLayout(this);
        content1.setId(generateViewId());
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params2.addRule(RelativeLayout.ABOVE,recycler.getId());
        content1.setLayoutParams(params2);


        /**image view*/
        ImageView imageview = new ImageView(this);
        imageview.setImageResource(R.mipmap.ac_black);
        imageview.setId(generateViewId());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);

        /**recycler view*/
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) recycler.getLayoutParams();
        params1.addRule(RelativeLayout.CENTER_VERTICAL);

        RelativeLayout author_parent = new RelativeLayout(this);
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params3.addRule(RelativeLayout.BELOW,recycler.getId());
        TextView textView = new TextView(this);
        textView.setTextSize(13);
        textView.setText("design by huanjinzi");
        //textView.setTextColor(getResources().getColor(R.color.GREEN));
        textView.setId(generateViewId());
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params4.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params4.addRule(RelativeLayout.CENTER_VERTICAL);

        author_parent.addView(textView,params4);

        content1.addView(imageview,params);
        content.addView(content1);
        content.addView(author_parent,params3);



        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new RecyclerAdapter(this);
        recycler.setAdapter(adapter);

    }

    class RecyclerAdapter extends RecyclerView.Adapter<Holder> {
        private Context context;
        private String[] title = {"获取新版本","软件协议","项目地址","作者博客"};
        private String[] content = {"","","Github","CSDN"};
        public RecyclerAdapter(Context context){this.context = context;}
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.info_listview_item,null);
            return new Holder(view);
        }
        private void onItemClilck(int position){
            Intent intent = null;
            Uri uri = null;
            switch (position)
            {
                case 0:
                    //
                    uri = Uri.parse("https://github.com/huanjinzi/CampusMVP/tree/develop");
                    intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                    break;
                case 1:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage(context.getString(R.string.about));
                    builder.setTitle("软件协议");
                    builder.create().show();
                    break;
                case 2:
                    uri = Uri.parse("https://github.com/huanjinzi/campusmvp");
                    intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                    break;
                case 3:
                    uri = Uri.parse("http://blog.csdn.net/qq_25923235");
                    intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                    break;
            }
        }
        @Override
        public void onBindViewHolder(Holder holder, final int position) {
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            v.setBackgroundColor(getResources().getColor(R.color.GREY_300));
                            break;
                        case  MotionEvent.ACTION_CANCEL:
                            v.setBackgroundColor(Color.WHITE);
                            break;
                        case  MotionEvent.ACTION_UP:
                            onItemClilck(position);
                            v.setBackgroundColor(Color.WHITE);
                            break;
                    }
                    return true;
                }
            });

            holder.getTitle().setText(title[position]);
            holder.getContent().setText(content[position]);
            if(position == title.length - 1){holder.getDivider().setVisibility(GONE);}
        }

        @Override
        public int getItemCount() {
            return title.length;
        }
    }

    class Holder extends RecyclerView.ViewHolder{

        public TextView getTitle() {
            return title;
        }

        private TextView title;

        public TextView getContent() {
            return content;
        }

        private TextView content;

        public TextView getDivider() {
            return divider;
        }

        private TextView divider;
        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            divider = (TextView) itemView.findViewById(R.id.divider);
        }
    }
}
