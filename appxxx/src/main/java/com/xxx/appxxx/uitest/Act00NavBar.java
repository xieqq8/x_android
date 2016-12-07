package com.xxx.appxxx.uitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xxx.appxxx.R;
import com.xxx.base.BaseActivity;

public class Act00NavBar extends BaseActivity {
    private int lastSelectedPosition = 0;

    @Override
    public void initContentView() {
        setContentView(R.layout.act00_nav_bar);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp, "UI").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_find_replace_white_24dp, "网络").setActiveColor(R.color.orange))//blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_favorite_white_24dp, "进阶").setActiveColor(R.color.orange))//green))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "综合").setActiveColor(R.color.orange))//blue))
                .setFirstSelectedPosition(lastSelectedPosition )//设置默认选中
                .initialise();

        Button button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ScrollingActivity.class));
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }
}
