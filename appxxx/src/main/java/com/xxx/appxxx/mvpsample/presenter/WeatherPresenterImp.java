package com.xxx.appxxx.mvpsample.presenter;

import android.util.Log;

import com.xxx.appxxx.mvpsample.bean.WeatherData;
import com.xxx.appxxx.mvpsample.model.WeatherModel;
import com.xxx.appxxx.mvpsample.model.WeatherModelImp;
import com.xxx.appxxx.mvpsample.view.WeatherView;


/**
 * Created by lidong on 2016/3/2.
 */
public class WeatherPresenterImp extends WeatherPresenter implements WeatherModelImp.WeatherOnListener{

    /**
     * WeatherModel和WeatherView都是通过接口来实现，这就Java设计原则中依赖倒置原则使用
     */
   private WeatherModel mWeatherModel;
   private WeatherView mWeatherView;

    public WeatherPresenterImp(WeatherView mWeatherView) {
        this.mWeatherModel = new WeatherModelImp(this);
        this.mWeatherView = mWeatherView;
    }

    @Override
    public void getWeatherData(String format, String city) {
        mWeatherView.showProgress();
        addSubscription(mWeatherModel.getWeatherData(format,city));
    }

    @Override
    public void onSuccess(WeatherData s) {
        mWeatherView.loadWeather(s);
        mWeatherView.hideProgress();
        Log.d("-------", "onSuccess() called with: " + "s = [" + s.toString() + "]");
    }

    @Override
    public void onFailure(Throwable e) {
        mWeatherView.hideProgress();
        Log.d("-------", "onFailure" + e.getMessage());
    }
}
