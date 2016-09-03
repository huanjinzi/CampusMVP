package com.campus.huanjinzi.campusmvp.TranscriptTask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.view.customview.ExcelView;

import java.io.InputStream;

/**
 * Created by huanjinzi on 2016/8/27.
 */
public class TranscriptView extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transcript_view,container,false);
        InputStream in = getResources().openRawResource(R.raw.cjbean);
        StringBuilder sb = null;
        ExcelView excel = (ExcelView) view.findViewById(R.id.excel);
        return view;
    }
}
