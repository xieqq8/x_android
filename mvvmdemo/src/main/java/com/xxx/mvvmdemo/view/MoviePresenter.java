package com.xxx.mvvmdemo.view;

import android.view.View;
import android.widget.Toast;

import com.xxx.mvvmdemo.bean.Food;

/**
 * Created by luoxiongwen on 16/10/24.
 */

public class MoviePresenter {
    public void buyTicket(View view, Food movie) {
        Toast.makeText(view.getContext(), "buy ticket: " + movie.getDescription(), Toast.LENGTH_SHORT).show();
    }
}
