package com.kuaxue.demo.netparse.parse;

import java.util.List;

/**
 *
 * Created by xieqq on 2015-10-13 .
 */
public abstract class BaseListParse<T> {
    protected List<T> mList = null;

    public abstract List<T> parser(String var1);

    public List<T> getmList() {
        return mList;
    }
    /**
     * 清空
     */
    public void clear() {
        if (mList != null) {
            this.mList.clear();
        }
    }
}
