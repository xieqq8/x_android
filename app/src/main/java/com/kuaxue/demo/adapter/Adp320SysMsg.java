package com.kuaxue.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuaxue.demo.netparse.entity.MessageAsk;
import com.xxx.fudao.R;

import java.util.ArrayList;

/**
 * Created by xieqq on 2015-09-30 .
 */
public class Adp320SysMsg extends BaseListAdapter {
//    private List<MessageAsk> mList;
    private LayoutInflater inflater;

    public Adp320SysMsg(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        mList = new ArrayList<MessageAsk>();
//        this.setmList(mList); // 设置list remove clear replace用
    }

//    @Override
//    public int getCount() {
//        return mList.size();
//    }

//    @Override
//    public Object getItem(int position) {
//        return mList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            //convertView = inflater.inflate(R.layout.item_sys_msg, null);
            convertView = inflater.inflate(R.layout.item_sys_msg, null);
            holder.iv_sysmsg_head = (ImageView)convertView.findViewById(R.id.iv_sysmsg_head);
            holder.iv_sysmsg_bread = (ImageView)convertView.findViewById(R.id.iv_sysmsg_bread);

            holder.tv_sys_title = (TextView)convertView.findViewById(R.id.tv_sys_title);
            holder.tv_date = (TextView)convertView.findViewById(R.id.tv_date);
            holder.tv_content_sys = (TextView)convertView.findViewById(R.id.tv_content_sys);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        MessageAsk msg = (MessageAsk)mList.get(position);
        holder.tv_sys_title.setText(msg.getShowTitle());
        holder.tv_date.setText(msg.getContentTime());
        holder.tv_content_sys.setText(msg.getShowcontent());
        if(msg.getbViewed()){
            holder.iv_sysmsg_bread.setVisibility(View.INVISIBLE);
            holder.tv_sys_title.setTextColor(Color.rgb(0x99, 0x99, 0x99));
            holder.tv_content_sys.setTextColor(Color.rgb(0xaa,0xaa,0xaa));
        }else{
            holder.iv_sysmsg_bread.setVisibility(View.VISIBLE);
            holder.tv_sys_title.setTextColor(Color.rgb(0x33,0x33,0x33));
            holder.tv_content_sys.setTextColor(Color.rgb(0x66, 0x66, 0x66));
        }
        if(msg.getMessageTypeB().equals("video")){
            holder.iv_sysmsg_head.setImageResource(R.drawable.photograph_2);
        } else if(msg.getMessageTypeB().equals("question")){
            holder.iv_sysmsg_head.setImageResource(R.drawable.photograph_3);
        }else{
            holder.iv_sysmsg_head.setImageResource(R.drawable.photograph_4);
        }
        return convertView;
    }

    class ViewHolder{
        // 头像
        ImageView iv_sysmsg_head;
        ImageView iv_sysmsg_bread;
        // 系统消息标题
        TextView tv_sys_title;
        // 时间
        TextView tv_date;
        // 消息内容
        TextView tv_content_sys;
    }
}
