package com.kuaxue.demo.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaxue.demo.adapter.Adp111List;
import com.xxx.fudao.R;
import com.xxx.xbase.BaseActivityWithSlideBack;

import java.util.ArrayList;

/**
 * ListView展示
 */
public class Act111ListViewCheck extends BaseActivityWithSlideBack {

    private ListView dataListView = null;
    private Adp111List adp111List = null;

    int nn = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_111_list);

        dataListView = (ListView) findViewById(R.id.lv_list);
//
        adp111List = new Adp111List(mContext);
        ArrayList mList = new ArrayList<String>();
//
        for(int i = 0; i < 6; i++,nn++){
            mList.add(String.valueOf(nn));
        }

        adp111List.appendData(mList);
//
        dataListView.setAdapter(adp111List);
        AddListItemClick();
    }
    private int location;
    private void AddListItemClick(){
        // 如果item里有button则根加这个属性android:descendantFocusability="blocksDescendants"，要不item点击不响应

//        beforeDescendants：viewgroup会优先其子类控件而获取到焦点
//        afterDescendants：viewgroup只有当其子类控件不需要获取焦点时才获取焦点
//        blocksDescendants：viewgroup会覆盖子类控件而直接获得焦点

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv=(TextView)view.findViewById(R.id.tv_num);
                Toast.makeText(getBaseContext(), "您点击了第" + tv.getText()+"行", Toast.LENGTH_SHORT).show();
            }
        });

        dataListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                location = position;
                final View nowView = (View) view.findViewById(R.id.ib_edit);
                Toast.makeText(getBaseContext(), "location=" + location, Toast.LENGTH_SHORT).show();
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Toast.makeText(getBaseContext(), "编辑", Toast.LENGTH_LONG).show();
                        } else if (which == 1) {
                            Toast.makeText(getBaseContext(), "删除", Toast.LENGTH_LONG).show();
                        }
                    }
                };
//                dataListView.getChildAt(location).setBackgroundResource(
//                        R.drawable.item_frame);
//                Message message = new Message();
//                message.arg1 = 4;
//                message.arg2 = location;
//                message.what = 1;
//                mHandler.sendMessageDelayed(message, 500);
//                String[] Menu = { "编辑", "删除" };
//
//                new AlertDialog.Builder(MainActivity.this).setItems(Menu,
//                        listener).show();
                return false;

            }
        });
    }


}
