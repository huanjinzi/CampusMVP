package com.campus.huanjinzi.campusmvp.utils;

import android.content.Context;
import android.util.Log;

import com.campus.huanjinzi.campusmvp.data.StudentCj;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by huanjinzi on 2016/10/11.
 */

public class FileUtil<T> {

    public void save(Context context, T object,String name) {
        try {

            FileOutputStream os = new FileOutputStream(context.getCacheDir().getPath() + "\\"+name+".ser");
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(object);

        } catch (IOException e) {
            Hlog.i("FileUtil",e.getMessage());
        }
    }

    public Object get(Context context,String name) {
        Object object = null;
        try {
            FileInputStream is = new FileInputStream(context.getCacheDir().getPath() + "\\"+name+".ser");
            ObjectInputStream ois = new ObjectInputStream(is);
            object =  ois.readObject();

        } catch (Exception e) {
            Hlog.i("FileUtil",e.getMessage());
        }
        return object;
    }
}
