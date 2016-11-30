package com.kuaxue.demo.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kuaxue.database.ConfigUtil;
import com.kuaxue.database.dao.DaoMaster;
import com.kuaxue.database.dao.DaoSession;
import com.kuaxue.database.dao.MsgInfoDao;
import com.kuaxue.demo.netparse.HttpUrlConstant;
import com.kuaxue.demo.netparse.parse.SysMsg320Parser;
import com.kuaxue.demo.adapter.Adp320SysMsg;
import com.xxx.fudao.R;
import com.xxx.views.PageModel;
import com.xxx.views.PullToRefreshView;
import com.xxx.xbase.BaseFragment;


/**
 * 系统消息
 */
public class Fg320MsgSys extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private final String strRefreshTag = "sys_msg_refresh_time";

    private RelativeLayout rl_course_no_data;

    //下拉刷新、底部加载
    private PullToRefreshView pullToRefreshView;
    private ListView lstView = null;

    // 分页处理
    private PageModel pageSys = null;
    private Adp320SysMsg adp320SysMsg = null;
    private SysMsg320Parser sysMsg320Parser = null;
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
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_msg_sys, null);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//		logx.d("onViewCreated list ");
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        lstView = (ListView) view.findViewById(R.id.listView);
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.gv_pullToRefresh);
        //下拉刷新、底部加载
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setLastUpdated(ConfigUtil.Instance().getRefreshTime(strRefreshTag));

        // 本地DB消息读取
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, ConfigUtil.DB_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        msginfoDao = daoSession.getMsgInfoDao();


        if (adp320SysMsg == null) {
            adp320SysMsg = new Adp320SysMsg(mContext);
        }
        if(sysMsg320Parser == null){
            sysMsg320Parser = new SysMsg320Parser();
        }
        // 系統消息
        pageSys = new PageModel(HttpUrlConstant.SYS_MSG, adp320SysMsg, sysMsg320Parser,lstView, pullToRefreshView);
        // 初始刷新
        pullToRefreshView.headerRefreshing();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageSys.PullFoot();
            }
        }, 300);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                //下拉刷新
                pageSys.PullHead();
            }
        }, 300);
    }

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;
    private MsgInfoDao msginfoDao;
}
