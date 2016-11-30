/**
 *
 * copyright(C)2014-
 *
 */
package com.kuaxue.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.fudao.R;
import com.kuaxue.demo.netparse.entity.MessageAsk;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 *
 */
public class Adp310QaMsg extends BaseListAdapter implements View.OnClickListener{
    private LayoutInflater inflater;
    private DisplayImageOptions options;

    public Adp310QaMsg(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        mList = new ArrayList<MessageAsk>();
//        setmList(mList);

        options = new DisplayImageOptions.Builder().
                bitmapConfig(Bitmap.Config.RGB_565)
//                .showImageOnLoading(R.drawable.default_avatar_s) // 设置图片下载期间显示的图片
//                .showStubImage(R.drawable.default_avatar_s) //加载开始默认的图片
                .showImageForEmptyUri(R.drawable.default_avatar_s)//url爲空會显示该图片，自己放在drawable里面的
                .showImageOnFail(R.drawable.default_avatar_s)//加载图片出现问题，会显示该图片
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(5))       //图片圆角显示，值为整数
                .build();
    }

//    @Override
//    public int getCount() {
//        if(mList == null){
//            return 0;
//        }
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
    public void onClick(View v) {
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_qa_msg, null);
            holder.iv_qamsg_head = (ImageView)convertView.findViewById(R.id.iv_qamsg_head);
            holder.iv_qamsg_bread = (ImageView)convertView.findViewById(R.id.iv_qamsg_bread);

            holder.tv_teacher_name = (TextView)convertView.findViewById(R.id.tv_teacher_name);
            holder.tv_date_qa = (TextView)convertView.findViewById(R.id.tv_date_qa);
            holder.tv_content_qa = (TextView)convertView.findViewById(R.id.tv_content_qa);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        MessageAsk msg = (MessageAsk)mList.get(position);
        // 防止图片显示错乱闪烁 http://blog.csdn.net/shineflowers/article/details/41744477
//        首先给ImageView设置一个Tag，这个Tag中设置的是图片的url，然后在加载的时候取得这个url和要加载那position中的url对比，如果不相同就加载，相同就是复用以前的就不加载了。
//        holder.iv_qamsg_head.setTag(msg.getImageUrl());
//        if (holder.iv_qamsg_head.getTag()!=null && holder.iv_qamsg_head.getTag().equals(msg.getImageUrl())) {
        ImageLoader.getInstance().displayImage(msg.getImageUrl(), holder.iv_qamsg_head, options);
//          }
        holder.tv_teacher_name.setText(msg.getUsername());
        holder.tv_date_qa.setText(msg.getContentTime());
        holder.tv_content_qa.setText(msg.getShowcontent());
        if(msg.getbViewed()){
            holder.iv_qamsg_bread.setVisibility(View.GONE);
            holder.tv_teacher_name.setTextColor(Color.rgb(0x99, 0x99, 0x99));
            holder.tv_content_qa.setTextColor(Color.rgb(0xaa, 0xaa, 0xaa));
        }else{
            holder.iv_qamsg_bread.setVisibility(View.VISIBLE);
            holder.tv_teacher_name.setTextColor(Color.rgb(0x33, 0x33, 0x33));
            holder.tv_content_qa.setTextColor(Color.rgb(0x66, 0x66, 0x66));
        }
        return convertView;
    }

    class ViewHolder{
        // 头像
        ImageView iv_qamsg_head;
        ImageView iv_qamsg_bread;
        // 老师名字
        TextView tv_teacher_name;
        // 时间
        TextView tv_date_qa;
        // 消息内容
        TextView tv_content_qa;
    }
}