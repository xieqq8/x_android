package com.xxx.mvvmdemo.view;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.xxx.mvvmdemo.R;
import com.xxx.mvvmdemo.bean.Food;
import com.xxx.mvvmdemo.model.ListModelImpl;
import com.xxx.mvvmdemo.widget.RecyclerViewDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.security.AccessController.getContext;

public class ListActivity extends AppCompatActivity {
//    private ListView lv;
    private RecyclerView lv;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            MyBaseAdapter<Food> adapter = new MyBaseAdapter<>(ListActivity.this, R.layout.listview_item, foods, com.xxx.mvvmdemo.BR.food);
//            lv.setAdapter(adapter);

            mAdapter = new DataBindingUseAdapter(R.layout.listview_item, foods);
            lv.setAdapter(mAdapter);

            }
    };
    private List<Food> foods = new ArrayList<>();
    DataBindingUseAdapter mAdapter;

    ListModelImpl mListModelImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lv = ((RecyclerView) findViewById(R.id.lv));


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv.setLayoutManager(layoutManager);

        lv.addItemDecoration(new RecyclerViewDivider(
                this, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(this, R.color.gray_l)));
//        MyBaseAdapter<Food> adapter = new MyBaseAdapter<>(ListActivity.this, R.layout.listview_item, foods, com.xxx.mvvmdemo.BR.food);
//        lv.setAdapter(adapter);



        initData();
//        mListModelImpl = new ListModelImpl();

    }

//    private void initData() { // 这个应该写到model层
//        mListModelImpl.initData(foods);
//    }

    public void initData() { // 这个应该写到model层
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("http://www.tngou.net/api/food/list?id=1").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    parseJson(response.body().string());
                }
            }
        });
    }
    private void parseJson(String jsonStr) {
//        foods = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(jsonStr);
            JSONArray tngou = jo.getJSONArray("tngou");
            for (int i = 0; i < tngou.length(); i++) {
                JSONObject item = tngou.getJSONObject(i);
                String description = item.getString("description");
                String img = "http://tnfs.tngou.net/image"+item.getString("img");
                String keywords = "【关键词】 "+item.getString("keywords");
                String summary = item.getString("summary");
                foods.add(new Food(description, img, keywords, summary));
            }

            mHandler.sendEmptyMessage(0); // 只有这样才会刷新  Android中相关的view和控件不是线程安全的，我们必须单独做处理。可以用Handler的使用。

//            mAdapter = new DataBindingUseAdapter(R.layout.listview_item, foods);
//            lv.setAdapter(mAdapter);

//            MyBaseAdapter<Food> adapter = new MyBaseAdapter<>(ListActivity.this, R.layout.listview_item, foods, com.xxx.mvvmdemo.BR.food);
//            lv.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
