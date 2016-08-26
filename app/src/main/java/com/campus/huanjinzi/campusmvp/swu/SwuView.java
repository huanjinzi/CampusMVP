package com.campus.huanjinzi.campusmvp.swu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.view.BaseView;

public class SwuView extends Fragment implements BaseView<ISwuPresenter> {

    /**fragment生命周期
     * 1.onAttach
     * 2.onCreate
     * 3.onCreateView
     * 4.onActivityCreated
     * 5.onStart
     * 6.onResume
     * 7.running
     * 8.onPause
     * 9.onStop
     * 10.onDestroyView-->onCreateView fragment从back stack中返回
     * 11.onDestroy
     * 12.onDetach
     * */
    private ISwuPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swu,container,false);
        ListView listView = (ListView) view.findViewById(R.id.listview1);
        ListViewAdapter adapter = new ListViewAdapter(getActivity());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.showFragment(position,view);
            }
        });
        return view;
    }

    @Override
    public void setPresenter(ISwuPresenter presenter) {
        this.presenter = presenter;
    }

    class ListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater = null;
        int[] id = {
                R.mipmap.ic_exit_to_app_grey600_24dp,
                R.mipmap.ic_credit_card_grey600_24dp,
                R.mipmap.ic_pageview_grey600_24dp,
                R.mipmap.ic_assignment_grey600_24dp,
                R.mipmap.ic_help_grey600_24dp};
        String[] str = {
                "账号退出",
                "网费充值",
                "成绩查询",
                "上网记录",
                "关于软件"
        };

        private ListViewAdapter(Context context) {

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

            MyViewHolder holder;
            if (convertView == null) {

                holder = new MyViewHolder();
                convertView = mInflater.inflate(R.layout.listview1_items, null);
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

    class MyViewHolder {

        public ImageView imageView_grid;
        public TextView textView_grid;

    }
}