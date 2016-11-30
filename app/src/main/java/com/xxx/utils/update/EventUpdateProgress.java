package com.xxx.utils.update;

/**
 * Created by xieqq on 2015-10-23 .
 */
public class EventUpdateProgress {

    private String mMsg;
    private int nProgress = 0;
    public EventUpdateProgress(String msg, int nProgress) {
        mMsg = msg;
        this.nProgress = nProgress;
    }

    public int getnProgress() {
        return nProgress;
    }

    public String getmMsg() {
        return mMsg;
    }
}
