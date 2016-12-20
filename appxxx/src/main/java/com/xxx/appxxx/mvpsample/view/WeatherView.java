package com.xxx.appxxx.mvpsample.view;


import com.xxx.appxxx.mvpsample.bean.WeatherData;

/**
 * Created by lidong on 2016/3/2.
 */
public interface WeatherView {

    void showProgress();
    void hideProgress();
    void loadWeather(WeatherData weatherData);
}
