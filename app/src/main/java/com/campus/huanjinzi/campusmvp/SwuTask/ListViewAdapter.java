package com.campus.huanjinzi.campusmvp.SwuTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.campus.huanjinzi.campusmvp.R;

/**
 * Created by huanjinzi on 2016/9/2.
 */
public class ListViewAdapter extends BaseAdapter{
    private int[] id = {
            R.mipmap.ic_exit_to_app_grey600_24dp,
            R.mipmap.ic_credit_card_grey600_24dp,
            R.mipmap.ic_pageview_grey600_24dp,
            R.mipmap.ic_assignment_grey600_24dp,
            R.mipmap.ic_help_grey600_24dp};
    private String[] str = {
            "账号退出",
            "网费充值",
            "成绩查询",
            "上网记录",
            "关于软件"
    };
    private LayoutInflater mInflater = null;
    public ListViewAdapter(Context context) {

        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            /*运用缓存，提高listview的性能*/
        MyViewHolder holder;
        if (convertView == null) {

            holder = new MyViewHolder();
            convertView = mInflater.inflate(R.layout.swu_listview_item, null);
            holder.imageView_grid = (ImageView) convertView.findViewById(R.id.image_grid);
            holder.textView_grid = (TextView) convertView.findViewById(R.id.text_grid);
            convertView.setTag(holder);

        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        holder.imageView_grid.setImageResource(id[position]);
        holder.textView_grid.setText(str[position]);

        return convertView;
    }
}

/**list的item项*/
class MyViewHolder {

    public ImageView imageView_grid;
    public TextView textView_grid;

}
