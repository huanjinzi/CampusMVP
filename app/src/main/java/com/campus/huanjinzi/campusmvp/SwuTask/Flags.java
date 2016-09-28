package com.campus.huanjinzi.campusmvp.SwuTask;

/**
 * Created by huanjinzi on 2016/9/17.
 */
public class Flags {
    private static Flags ourInstance = new Flags();

    public boolean isHAS_LOGED() {
        return HAS_LOGED;
    }

    public void setHAS_LOGED(boolean HAS_LOGED) {
        this.HAS_LOGED = HAS_LOGED;
    }

    private boolean HAS_LOGED = false;
    public static Flags getInstance() {
        return ourInstance;
    }

    private Flags() {
    }
}
