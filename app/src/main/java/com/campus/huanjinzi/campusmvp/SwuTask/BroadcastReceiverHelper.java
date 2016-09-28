package com.campus.huanjinzi.campusmvp.SwuTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;

import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.utils.Hlog;
import com.campus.huanjinzi.campusmvp.view.customview.ProgressView;

/**
 * Created by huanjinzi on 2016/9/26.
 */

public class BroadcastReceiverHelper extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SwuActivity activity = (SwuActivity) context;
        RelativeLayout tab = (RelativeLayout) activity.findViewById(R.id.tabs);

        if(intent.getAction().equals(SwuActivity.LOGTASK_DONE))
        {
            activity.setTaskDone(true);
            activity.getProgress().setDrawsin(false);
        }
        else
        {
            switch (intent.getAction())
            {
                case SwuActivity.LOGIN_SUCCESS:
                    tab.setBackgroundColor(context.getResources().getColor(R.color.GREEN));
                    break;
                case SwuActivity.LOGOUT_SUCCESS:
                    tab.setBackgroundColor(context.getResources().getColor(R.color.GREY_900));
                    break;
                case SwuActivity.LOGTASK_FAILURE:
                    break;
            }
        }
        Hlog.i("BroadcastReceiverHelper", context.toString() + intent.getAction());
    }
}
