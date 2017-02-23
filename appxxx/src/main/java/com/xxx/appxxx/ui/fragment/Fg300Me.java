package com.xxx.appxxx.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextSubListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.upload.ProgressRequestBody;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.upload.UploadProgressListener;
import com.xxx.appxxx.R;
import com.xxx.appxxx.api.SubjectPostApi;
import com.xxx.appxxx.api.UploadApi;
import com.xxx.appxxx.mvpsample.DWeatherActivity;
import com.xxx.appxxx.resulte.BaseResultEntity;
import com.xxx.appxxx.resulte.SubjectResulte;
import com.xxx.appxxx.resulte.UploadResulte;
import com.xxx.appxxx.uitest.Act00NavBar;
import com.xxx.appxxx.uitest.DownLaodActivity;
import com.xxx.appxxx.uitest.DrawAppBarDemoActivity;
import com.xxx.appxxx.uitest.ScrollingActivity;
import com.xxx.base.BackHandledFragment;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fg300Me#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg300Me extends BackHandledFragment implements View.OnTouchListener, View.OnClickListener, HttpOnNextListener, HttpOnNextSubListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fg300Me() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fg300Me.
     */
    // TODO: Rename and change types and number of parameters
    public static Fg300Me newInstance(String param1, String param2) {
        Fg300Me fragment = new Fg300Me();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_fg300_me;
//        return R.layout.act_001_main;               //  这个 Toolbar 上滑可隐藏
    }

    @Override
    public void initView() {
        Button button = (Button) mLayoutView.findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScrollingActivity.class));
            }
        });

        Button btn = (Button) mLayoutView.findViewById(R.id.button);
        btn.setText(mParam2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Act00NavBar.ATOB;
                onButtonPressed(uri);

                startActivity( new Intent(getActivity(), DWeatherActivity.class));

            }
        });

        Button btn3 = (Button) mLayoutView.findViewById(R.id.button_appbar);
//        btn3.setText(mParam2);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Act00NavBar.ATOB;
                onButtonPressed(uri);

                startActivity( new Intent(getActivity(), DrawAppBarDemoActivity.class));

            }
        });
        //获取控件实例
        cityET = (EditText) mLayoutView.findViewById(R.id.city);
        queryTV = (TextView) mLayoutView.findViewById(R.id.query);
        weatherTV = (TextView) mLayoutView.findViewById(R.id.weather);
        //对查询按钮侦听点击事件
        queryTV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // 查询

                weatherTV.setText("");
                String city = cityET.getText().toString();
                if(TextUtils.isEmpty(city)){
                    Toast.makeText(getActivity(), "城市不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //采用普通写法创建Observable
//                observableAsNormal(city);
                //采用lambda写法创建Observable
//            observableAsLambda(city);
                //采用普通写法创建Observable,使用map操作符转换
                observableMapAsNormal(city);
                //采用lambda写法创建Observable,使用map操作符转换
//            observableMapAsLambda(city);
            }
        });

        tvMsg = (TextView) mLayoutView.findViewById(R.id.tv_msg);
        mLayoutView.findViewById(R.id.btn_simple).setOnClickListener(this);
        mLayoutView.findViewById(R.id.btn_rx).setOnClickListener(this);
        mLayoutView.findViewById(R.id.btn_rx_mu_down).setOnClickListener(this);
        mLayoutView.findViewById(R.id.btn_rx_uploade).setOnClickListener(this);
        img=(ImageView)mLayoutView.findViewById(R.id.img);
        progressBar=(NumberProgressBar)mLayoutView.findViewById(R.id.number_progress_bar);
    }
    //    公用一个HttpManager
    private HttpManager manager;
    //    post请求接口信息
    private SubjectPostApi postEntity;
    //    上传接口信息
    private UploadApi uplaodApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        /*初始化数据*/
        manager = new HttpManager(this, getBaseActivity());

        postEntity = new SubjectPostApi();
        postEntity.setAll(true);

        /*上传接口内部接口有token验证，所以需要换成自己的接口测试，检查file文件是否手机存在*/
        uplaodApi = new UploadApi();
        File file = new File("/storage/emulated/0/Download/11.jpg");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file_name", file.getName(), new ProgressRequestBody
                (requestBody,
                        new UploadProgressListener() {
                            @Override
                            public void onProgress(long currentBytesCount, long totalBytesCount) {
                                tvMsg.setText("提示:上传中");
                                progressBar.setMax((int) totalBytesCount);
                                progressBar.setProgress((int) currentBytesCount);
                            }
                        }));
        uplaodApi.setPart(part);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container,savedInstanceState);
    }


    /**
     * 天气预报API地址
     */
    private static final String WEATHRE_API_URL="http://php.weather.sina.com.cn/xml.php?city=%s&password=DJOYnieT8234jlsK&day=0";
    private EditText cityET;     //城市
    private TextView queryTV;    //查询按钮
    private TextView weatherTV;  //天气结果

    private Subscription subscription;

    /**
     * 采用普通写法创建Observable
     * @param city
     */
    private void observableAsNormal(final String city){
        subscription = Observable.create(new Observable.OnSubscribe<Weather>() {
            @Override
            public void call(Subscriber<? super Weather> subscriber) {
                //1.如果已经取消订阅，则直接退出
                if(subscriber.isUnsubscribed()) return;
                try {
                    //2.开网络连接请求获取天气预报，返回结果是xml格式
                    String weatherXml = getWeather(city);
                    //3.解析xml格式，返回weather实例
                    Weather weather = parseWeather(weatherXml);
                    //4.发布事件通知订阅者
                    subscriber.onNext(weather);
                    //5.事件通知完成
                    subscriber.onCompleted();
                } catch(Exception e){
                    //6.出现异常，通知订阅者
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())    //让Observable运行在新线程中
                .observeOn(AndroidSchedulers.mainThread())   //让subscriber运行在主线程中
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {
                        //对应上面的第5点：subscriber.onCompleted();
                        //这里写事件发布完成后的处理逻辑

                    }

                    @Override
                    public void onError(Throwable e) {
                        //对应上面的第6点：subscriber.onError(e);
                        //这里写出现异常后的处理逻辑
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Weather weather) {
                        //对应上面的第4点：subscriber.onNext(weather);
                        //这里写获取到某一个事件通知后的处理逻辑
                        if(weather != null)
                            weatherTV.setText(weather.toString());
                    }
                });
    }

    /**
//     * 采用lambda写法创建Observable
//     * @param city
//     */
//    private void observableAsLambda(String city){
//        subscription = Observable.create(subscriber->{
//                    if(subscriber.isUnsubscribed()) return;
//                    try {
//                        String weatherXml = getWeather(city);
//                        Weather weather = parseWeather(weatherXml);
//                        subscriber.onNext(weather);
//                        subscriber.onCompleted();
//                    } catch(Exception e){
//                        subscriber.onError(e);
//                    }
//                }
//        ).subscribeOn(Schedulers.newThread())    //让Observable运行在新线程中
//                .observeOn(AndroidSchedulers.mainThread())   //让subscriber运行在主线程中
//                .subscribe(
//                        weather->{
//                            if(weather != null)
//                                weatherTV.setText(weather.toString());
//                        },
//                        e->{
//                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        });
//    }

    /**
     * 采用普通写法创建Observable,使用map操作符转换
     * @param city
     */
    private void observableMapAsNormal(final String city){
        subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                try {
                    String weatherXml = getWeather(city);
                    subscriber.onNext(weatherXml);
                    subscriber.onCompleted();
                } catch(Exception e){
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<String, Weather>() {
            @Override
            public Weather call(String weatherXml) {
                return parseWeather(weatherXml);
            }
        }).subscribeOn(Schedulers.newThread())    //让Observable运行在新线程中
                .observeOn(AndroidSchedulers.mainThread())   //让subscriber运行在主线程中
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Weather weather) {
                        if(weather != null)
                            weatherTV.setText(weather.toString());
                    }
                });
    }

//    /**
//     * 采用lambda写法创建Observable,使用map操作符转换
//     * @param city
//     */
//    private void observableMapAsLambda(String city){
//        subscription = Observable.create(subscriber->{
//                    if(subscriber.isUnsubscribed()) return;
//                    try {
//                        String weatherXml = getWeather(city);
//                        subscriber.onNext(weatherXml);
//                        subscriber.onCompleted();
//                    } catch(Exception e){
//                        subscriber.onError(e);
//                    }
//                }
//        ).map(weatherXml->parseWeather(weatherXml.toString()))
//                .subscribeOn(Schedulers.newThread())    //让Observable运行在新线程中
//                .observeOn(AndroidSchedulers.mainThread())   //让subscriber运行在主线程中
//                .subscribe(
//                        weather->{
//                            if(weather != null)
//                                weatherTV.setText(weather.toString());
//                        },
//                        e->{
//                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        });
//    }

    @Override
    public void onDestroyView() {
        //取消订阅
        if(subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
        super.onDestroyView();
    }

//    @Override
//    protected void onDestroy() {
//        //取消订阅
//        if(subscription != null && !subscription.isUnsubscribed())
//            subscription.unsubscribe();
//        super.onDestroy();
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v == weatherTV && event.getAction() == MotionEvent.ACTION_DOWN){
            //隐藏软键盘
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View focusedView = getActivity().getCurrentFocus();
            if(focusedView!=null && focusedView.getWindowToken()!=null){
                manager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            }
        }
        return true;
    }

    /**
     * 天气情况类
     */
    private class Weather{
        /**
         * 城市
         */
        String city;
        /**
         * 日期
         */
        String date;
        /**
         * 温度
         */
        String temperature;
        /**
         * 风向
         */
        String direction;
        /**
         * 风力
         */
        String power;
        /**
         * 天气状况
         */
        String status;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("城市:" + city + "\r\n");
            builder.append("日期:" + date + "\r\n");
            builder.append("天气状况:" + status + "\r\n");
            builder.append("温度:" + temperature + "\r\n");
            builder.append("风向:" + direction + "\r\n");
            builder.append("风力:" + power + "\r\n");
            return builder.toString();
        }
    }

    /**
     * 解析xml获取天气情况
     * @param weatherXml
     * @return
     */
    private Weather parseWeather(String weatherXml){
        //采用Pull方式解析xml
        StringReader reader = new StringReader(weatherXml);
        XmlPullParser xmlParser = Xml.newPullParser();
        Weather weather = null;
        try {
            xmlParser.setInput(reader);
            int eventType = xmlParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        weather = new Weather();
                        break;
                    case XmlPullParser.START_TAG:
                        String nodeName = xmlParser.getName();
                        if("city".equals(nodeName)){
                            weather.city = xmlParser.nextText();
                        } else if("savedate_weather".equals(nodeName)){
                            weather.date = xmlParser.nextText();
                        } else if("temperature1".equals(nodeName)) {
                            weather.temperature = xmlParser.nextText();
                        } else if("temperature2".equals(nodeName)){
                            weather.temperature += "-" + xmlParser.nextText();
                        } else if("direction1".equals(nodeName)){
                            weather.direction = xmlParser.nextText();
                        } else if("power1".equals(nodeName)){
                            weather.power = xmlParser.nextText();
                        } else if("status1".equals(nodeName)){
                            weather.status = xmlParser.nextText();
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
            return weather;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.close();
        }
    }

    /**
     * 获取指定城市的天气情况
     * @param city
     * @return
     * @throws
     */
    private String getWeather(String city) throws Exception{
        BufferedReader reader = null;
        HttpURLConnection connection=null;
        try {
            String urlString = String.format(WEATHRE_API_URL, URLEncoder.encode(city, "GBK"));
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            //连接
            connection.connect();

            //处理返回结果
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line="";
            while(!TextUtils.isEmpty(line = reader.readLine()))
                buffer.append(line);
            return buffer.toString();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private TextView tvMsg;
    private NumberProgressBar progressBar;
    private ImageView img;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simple:   // rxjava+retrofit的http
                onButton9Click();
                break;
            case R.id.btn_rx: // rx+retrofit绝对封装
//                simpleDo();
                manager.doHttpDeal(postEntity);
                break;
            case R.id.btn_rx_uploade:   // rx+retrofit上传
//                uploadeDo();
                manager.doHttpDeal(uplaodApi);
                break;
            case  R.id.btn_rx_mu_down:  // 多任务断点续传下载
                Intent intent=new Intent(getContext(),DownLaodActivity.class);
                startActivity(intent);
                break;
        }
    }

    //    完美封装简化版
    private void simpleDo() {
         /*初始化数据*/
        manager = new HttpManager(this, getBaseActivity());
        postEntity = new SubjectPostApi();
        postEntity.setAll(true);
        manager.doHttpDeal(postEntity);
    }


    @Override
    public void onNext(String resulte, String mothead) {
        /*post返回处理*/
        if (mothead.equals(postEntity.getMethod())) {
            BaseResultEntity<ArrayList<SubjectResulte>>   subjectResulte = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<ArrayList<SubjectResulte>>>(){});
            tvMsg.setText("post返回：\n" + subjectResulte.getData().toString());
        }

        /*上传返回处理*/
        if (mothead.equals(uplaodApi.getMethod())) {
            BaseResultEntity<UploadResulte> subjectResulte = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<UploadResulte>>(){});
            UploadResulte uploadResulte = subjectResulte.getData();
            tvMsg.setText("上传成功返回：\n" + uploadResulte.getHeadImgUrl());
            Glide.with(getBaseActivity()).load(uploadResulte.getHeadImgUrl()).skipMemoryCache(true).into(img);
        }
    }

    @Override
    public void onError(ApiException e) {
        tvMsg.setText("失败：\ncode=" + e.getCode() + "\nmsg:" + e.getDisplayMessage());
    }


    @Override
    public void onNext(Observable observable) {

    }
//    /*************************************************封装完请求*******************************************************/
//
//    //    完美封装简化版
//    private void simpleDo() {
//        SubjectPostApi postEntity = new SubjectPostApi(simpleOnNextListener,getBaseActivity());
//        postEntity.setAll(true);
//        HttpManager manager = HttpManager.getInstance();
//        manager.doHttpDeal(postEntity);
//    }
//
//    //   回调一一对应
//    HttpOnNextListener simpleOnNextListener = new HttpOnNextListener<List<SubjectResulte>>() {
//        @Override
//        public void onNext(List<SubjectResulte> subjects) {
//            tvMsg.setText("网络返回：\n" + subjects.toString());
//        }
//
//        @Override
//        public void onCacheNext(String cache) {
//            /*缓存回调*/
//            Gson gson=new Gson();
//            java.lang.reflect.Type type = new TypeToken<BaseResultEntity<List<SubjectResulte>>>() {}.getType();
//            BaseResultEntity resultEntity= gson.fromJson(cache, type);
//            tvMsg.setText("缓存返回：\n"+resultEntity.getData().toString() );
//        }
//
//        /*用户主动调用，默认是不需要覆写该方法*/
//        @Override
//        public void onError(Throwable e) {
//            super.onError(e);
//            tvMsg.setText("失败：\n" + e.toString());
//        }
//
//        /*用户主动调用，默认是不需要覆写该方法*/
//        @Override
//        public void onCancel() {
//            super.onCancel();
//            tvMsg.setText("取消請求");
//        }
//    };
//
//
//    /*********************************************文件上传***************************************************/
//
//    private void uploadeDo(){
//        File file=new File("/storage/emulated/0/Download/11.jpg");
//        RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpeg"),file);
//        MultipartBody.Part part= MultipartBody.Part.createFormData("file_name", file.getName(), new ProgressRequestBody(requestBody,
//                new UploadProgressListener() {
//                    @Override
//                    public void onProgress(long currentBytesCount, long totalBytesCount) {
//                        tvMsg.setText("提示:上传中");
//                        progressBar.setMax((int) totalBytesCount);
//                        progressBar.setProgress((int) currentBytesCount);
//                    }
//                }));
//        UploadApi uplaodApi = new UploadApi(httpOnNextListener,getBaseActivity());
//        uplaodApi.setPart(part);
//        HttpManager manager = HttpManager.getInstance();
//        manager.doHttpDeal(uplaodApi);
//    }
//
//
//    /**
//     * 上传回调
//     */
//    HttpOnNextListener httpOnNextListener=new HttpOnNextListener<UploadResulte>() {
//        @Override
//        public void onNext(UploadResulte o) {
//            tvMsg.setText("成功");
//            Glide.with(getBaseActivity()).load(o.getHeadImgUrl()).skipMemoryCache(true).into(img);
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            super.onError(e);
//            tvMsg.setText("失败："+e.toString());
//        }
//
//    };
//
//
//
//    /**********************************************************正常不封装使用**********************************/
//
    /**
     * Retrofit加入rxjava实现http请求
     */
    private void onButton9Click() {
//        String BASE_URL="http://www.izaodao.com/Api/";
//        //手动创建一个OkHttpClient并设置超时时间
//        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(5, TimeUnit.SECONDS);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(builder.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .baseUrl(BASE_URL)
//                .build();
//
////        加载框
//        final ProgressDialog pd = new ProgressDialog(getContext());
//
//        HttpPostService apiService = retrofit.create(HttpPostService.class);
//        Observable<RetrofitEntity> observable = apiService.getAllVedioBy(true);
//        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        new Subscriber<RetrofitEntity>() {
//                            @Override
//                            public void onCompleted() {
//                                if (pd != null && pd.isShowing()) {
//                                    pd.dismiss();
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                if (pd != null && pd.isShowing()) {
//                                    pd.dismiss();
//                                }
//                            }
//
//                            @Override
//                            public void onNext(RetrofitEntity retrofitEntity) {
//                                tvMsg.setText("无封装：\n" + retrofitEntity.getData().toString());
//                            }
//
//                            @Override
//                            public void onStart() {
//                                super.onStart();
//                                pd.show();
//                            }
//                        }
//
//                );
    }
}
