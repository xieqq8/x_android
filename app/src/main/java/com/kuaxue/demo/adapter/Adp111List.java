package com.kuaxue.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.fudao.R;

import java.util.ArrayList;

/**
 * Created by xieqq on 2016-06-29 .
 */
public class Adp111List extends BaseListAdapter {
//
    private LayoutInflater inflater;

    public Adp111List(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);

        mList = new ArrayList<String>();
    }


    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_111, null);
            holder.ib_edit = (Button)convertView.findViewById(R.id.ib_edit);
            holder.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            holder.tv_numx = (TextView)convertView.findViewById(R.id.tv_numx);
            holder.tv_numy = (TextView)convertView.findViewById(R.id.tv_numy);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        final String xx = (String) mList.get(position);
        holder.ib_edit.setText(xx);
        holder.tv_num.setText(xx);
        holder.ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "button_" + xx, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    class ViewHolder{

        Button ib_edit;

        TextView tv_num;

        TextView tv_numx;

        TextView tv_numy;
    }
}
