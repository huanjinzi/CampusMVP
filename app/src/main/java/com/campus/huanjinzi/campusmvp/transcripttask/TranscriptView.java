package com.campus.huanjinzi.campusmvp.TranscriptTask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.data.StudentCj;
import com.campus.huanjinzi.campusmvp.view.customview.ExcelView;

/**
 * Created by huanjinzi on 2016/8/27.
 */
public class TranscriptView extends Fragment {

    private TranscriptPresenter presenter;
    private ExcelView excel;
    private int xnm;

    public void setXnm(int xnm) {this.xnm = xnm;}
    public void setPresenter(TranscriptPresenter presenter){this.presenter = presenter;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transcript_view,container,false);
        excel = (ExcelView) view.findViewById(R.id.excel);
        excel.setXnm(xnm);
        if(presenter.getCj() != null){
            excel.setStudentCj(presenter.getCj());}
        return view;
    }
    public void update(StudentCj studentCj){
        excel.setStudentCj(studentCj);

    }

}
