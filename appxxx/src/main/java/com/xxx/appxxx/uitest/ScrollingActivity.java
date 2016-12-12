package com.xxx.appxxx.uitest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xxx.appxxx.R;
import com.xxx.base.BaseApcActivity;

public class ScrollingActivity extends BaseApcActivity {

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_scrolling);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mToolbar.setLogo(R.mipmap.ic_book_white_24dp);
        mToolbar.setNavigationIcon(android.R.drawable.ic_menu_delete);
        mToolbar.setTitle("zhangphil");
        mToolbar.setSubtitle("zhangphil副标题");
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
