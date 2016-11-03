package com.campus.huanjinzi.campusmvp.AboutTask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campus.huanjinzi.campusmvp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.View.GONE;
import static android.view.View.generateViewId;

public class AboutActivity extends AppCompatActivity {
    
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.layout_content)
    RelativeLayout content;
    
    @InjectView(R.id.recyclerview)
    RecyclerView recycler;

    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ButterKnife.inject(this);
        setToolbar();
        initView();
    }

    private void setToolbar() {

        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setTitleTextAppearance(this, R.style.TitleText);
        toolbar.setTitle(getIntent().getExtras().getString(getString(R.string.TITLE)));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0,android.R.anim.slide_out_right);
            }
        });
    }
    
    private void initView()
    {
        content.setPadding(0,0,0,0);

        /**image view parent*/
        RelativeLayout image_parent = new RelativeLayout(this);
        image_parent.setId(generateViewId());
        RelativeLayout.LayoutParams image_parent_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        image_parent_params.addRule(RelativeLayout.ABOVE,recycler.getId());
        image_parent.setLayoutParams(image_parent_params);


        /**image view*/
        ImageView image = new ImageView(this);
        image.setImageResource(R.mipmap.ac_black);
        image.setId(generateViewId());
        
        RelativeLayout.LayoutParams image_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        image_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        image_params.addRule(RelativeLayout.CENTER_VERTICAL);

        /**recycler view*/
        RelativeLayout.LayoutParams recycler_params = (RelativeLayout.LayoutParams) recycler.getLayoutParams();
        recycler_params.addRule(RelativeLayout.CENTER_VERTICAL);

        RelativeLayout author_parent = new RelativeLayout(this);
        RelativeLayout.LayoutParams author_parent_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        author_parent_params.addRule(RelativeLayout.BELOW,recycler.getId());

        TextView author = new TextView(this);

        author.setTextSize(getResources().getInteger(R.integer.text_size_13));
        author.setText(getResources().getString(R.string.design_by));

        author.setId(generateViewId());
        RelativeLayout.LayoutParams author_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        author_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        author_params.addRule(RelativeLayout.CENTER_VERTICAL);

        author_parent.addView(author,author_params);

        image_parent.addView(image,image_params);
        content.addView(image_parent);
        content.addView(author_parent,author_parent_params);



        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new RecyclerAdapter(this);
        recycler.setAdapter(adapter);
    }
    
    class RecyclerAdapter extends RecyclerView.Adapter<Holder> {
        private Context context;
        private String[] title = getResources().getStringArray(R.array.title);
        private String[] content = getResources().getStringArray(R.array.content);

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage(context.getString(R.string.about));
                    builder.setTitle(title[0]);
                    builder.setNegativeButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                    break;
                case 1:
                    uri = Uri.parse(getResources().getString(R.string.csdn_url));
                    intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                    break;
                case 2:
                    uri = Uri.parse(getResources().getString(R.string.github_url));
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
