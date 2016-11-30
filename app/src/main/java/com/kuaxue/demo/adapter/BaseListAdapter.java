package com.kuaxue.demo.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xieqq on 2015-09-30 .
 */
public abstract class BaseListAdapter<T1> extends BaseAdapter {
    protected List<T1> mList;

    protected  Context mcontext;
    public BaseListAdapter(Context context) {
        mcontext = context;
    }

    public void setmList(List<T1> mList) {
        this.mList = mList;
    }
    @Override
    public int getCount() {
        if(mList == null){
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 添加
     *
     * @param mList
     */
    public void appendData(List<T1> mList) {
        if (mList != null) {
            this.mList.addAll(mList);
        }
    }

    /**
     * 清空
     */
    public void clear() {
        if (mList != null) {
            this.mList.clear();
//            LogX.getLogger().d("after clear size:" + mList.size());
        }
    }

    /**
     * 删除一条
     *
     * @param index
     */
    public void remove(int index) {
        if (mList != null) {
            this.mList.remove(index);
        }
    }
}
