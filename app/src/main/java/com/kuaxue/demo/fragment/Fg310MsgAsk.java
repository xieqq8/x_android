package com.kuaxue.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kuaxue.database.ConfigUtil;
import com.kuaxue.demo.netparse.HttpUrlConstant;
import com.kuaxue.demo.netparse.parse.QaMsg310Parser;
import com.kuaxue.demo.adapter.Adp310QaMsg;
import com.xxx.fudao.R;
import com.xxx.views.PageModel;
import com.xxx.views.PullToRefreshView;
import com.xxx.xbase.BaseFragment;


/**
 * 答疑消息
 */
public class Fg310MsgAsk extends BaseFragment implements
        PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private final String strRefreshTag = "ask_msg_refresh_time";

    private PullToRefreshView pullToRefreshView = null;
    private ListView lstview = null;

    private Adp310QaMsg adp310QaMsg = null;
    private PageModel pageQa = null;
    private QaMsg310Parser qaMsg310Parser = null;
    /**
     * onCreate是指创建该fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        logx.d("onCreate ");
        Bundle args = getArguments();
        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView是创建该fragment对应的视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        logx.d("onCreateView ");
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_msg_ask, null);
        }
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.gv_pullToRefresh);
        //下拉刷新、底部加载
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener((PullToRefreshView.OnFooterRefreshListener) this);
        pullToRefreshView.setLastUpdated(ConfigUtil.Instance().getRefreshTime(strRefreshTag));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        logx.d("onViewCreated list ");
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        lstview = (ListView) view.findViewById(R.id.listView);

        // item点击
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // 第一个参数就是我们的图片加载对象ImageLoader,
        // 第二个是控制是否在滑动过程中暂停加载图片，如果需要暂停传true就行了，
        // 第三个参数控制猛的滑动界面的时候图片是否加载
//        lstview.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));


        if (adp310QaMsg == null) {
            adp310QaMsg = new Adp310QaMsg(mContext);
        }
        if(qaMsg310Parser == null){
            qaMsg310Parser = new QaMsg310Parser() ;
        }
        // 请求地址  adapter json解析 listview 上下拉控件
        pageQa = new PageModel(HttpUrlConstant.ASK_MSG, adp310QaMsg, qaMsg310Parser,lstview, pullToRefreshView);

        // 初始化刷新 相当于顶部下拉刷新
        pullToRefreshView.headerRefreshing();

    }

    @Override
    public void onDestroyView() {
//        logx.d("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
//        logx.d("onDestroy");
        super.onDestroy();
    }

    //上拉加载方法
    @Override
    public void onFooterRefresh(PullToRefreshView view) {

        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageQa.PullFoot();
            }
        }, 300);
    }

    //下拉刷新方法
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                pageQa.PullHead();
            }
        }, 300);
    }
}
