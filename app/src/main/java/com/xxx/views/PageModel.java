package com.xxx.views;

import android.widget.ListView;

import com.kuaxue.database.ConfigUtil;
import com.kuaxue.demo.adapter.BaseListAdapter;
import com.kuaxue.demo.netparse.NetRestClient;
import com.kuaxue.demo.netparse.parse.BaseListParse;
import com.xxx.utils.DateUtil;
import com.xxx.utils.LogX;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

/**
 * 上下拉分页分的
 * Created by xieqq on 2015-10-04 .
 */
public class PageModel {
    private String url;
    private int nPageid = 1;    // 页码
    private int nRows = 10;     // 每页的条数
    private BaseListAdapter adapter;
    private ListView lstView;
    private PullToRefreshView pullToRefreshView;
    private BaseListParse bp = null;

    private boolean bRefesh = false; // 下拉刷新  上拉加载更多

    public boolean isbRefesh() {
        return bRefesh;
    }
    public void setnPageid(int nPageid) {
        this.nPageid = nPageid;
    }

    public void setnRows(int nRows) {
        this.nRows = nRows;
    }

    public PageModel(String strUrl, BaseListAdapter adapter, BaseListParse bp, ListView lstview, PullToRefreshView pullToRefreshView) {
        this.url = strUrl;
        this.adapter = adapter;
        this.bp = bp;
        this.lstView = lstview;
        this.pullToRefreshView = pullToRefreshView;
    }

    // 底部刷新
    public void PullFoot() {
        LogX.getLogger().d("PullFoot");

        bRefesh = false;
        // 网络处理请求
        doRequest();
    }

    // 头部刷新
    public void PullHead() {
        LogX.getLogger().d("PullHead");
        bRefesh = true; // 刷新了要清空listview
        // 网络处理请求
        doRequest();
    }

    public void clearToken() {
        NetRestClient.Instance().AddHeader("Authorization", null);
    }

    public void initToken(String token) {
        NetRestClient.Instance().AddHeader("Authorization", "Bearer " + token);
    }

    public void doRequest() {
        LogX.getLogger().d("rows = %d, page = %d", nRows, nPageid);
        RequestParams params = new RequestParams();
        params.put("rows", nRows + ""); // 每页条数
        if (isbRefesh()) {
            params.put("page", "1");    // 刷新总是第一页
//            LogX.getLogger().d("rows = %d, page = %d", nRows, 1);
        } else {
            params.put("page", nPageid + "");    // 页码
        }
        initToken(ConfigUtil.Instance().GetConfigValue(ConfigUtil.C_ACCESS_TOKEN));

        LogX.getLogger().d("url = %s, token = %s", url, ConfigUtil.Instance().GetConfigValue(ConfigUtil.C_ACCESS_TOKEN));

        NetRestClient.Instance().client.get(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                LogX.getLogger().d("onFailure net rest onfailure:%d, %s" , statusCode , throwable.getMessage());

                super.onFailure(statusCode, headers, responseBytes, throwable);
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                // {"error":"invalid_token","error_description":"Invalid access token: "}
                LogX.getLogger().d("net rest onfailure:%d, %s" , i , s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                LogX.getLogger().d("net rest onsuccess");
                try {
                    // 新解析把之前的清空下
                    bp.clear();

                    bp.parser(s);

                    if (isbRefesh()) { // 刷新把之前的清空
                        adapter.clear(); // adapter 的清空
                        if (bp.getmList() != null)
                            LogX.getLogger().d("parse size:" + bp.getmList().size());
                    }
                    adapter.appendData(bp.getmList());

                    if(lstView.getAdapter() == null || lstView.getAdapter().getCount() == 0) {
                        lstView.setAdapter(adapter); // setAdapter会使页面回到顶部，不会停留在之前的位置
                        // 调用setAdapter的话view是空的需要重新创建
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    LogX.getLogger().d("net rest onsuccess error" + e.toString());
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onFinish() {
                LogX.getLogger().d("net rest onfinish");
                if (isbRefesh()) {
                    nPageid = 2;// 成功一次后刷新第二页的
                    pullToRefreshView.onHeaderRefreshComplete(getRefreshTime(System.currentTimeMillis()));
                } else {
                    nPageid++;
                    pullToRefreshView.onFooterRefreshComplete();
                }
                LogX.getLogger().d("onFinish rows = %d, page = %d", nRows, nPageid);

                super.onFinish();
            }
        });
    }

    //获取不同的fragment下拉刷新时间  // 更新时间
    private String getRefreshTime(long ntime) {
        try {
            return "更新于:" + DateUtil.formatTime(ntime, "yyyy-MM-dd HH:mm:ss");
        } catch (NumberFormatException e) {
            return ""; // 之前没有
        } finally {
        }

    }
}
