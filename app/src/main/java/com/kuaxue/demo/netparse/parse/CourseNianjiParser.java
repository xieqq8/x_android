package com.kuaxue.demo.netparse.parse;

import com.kuaxue.demo.netparse.entity.NianJi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程年级解析
 */
public class CourseNianjiParser {
    private List<NianJi> mList = null;

    private String strResult;

    public List<NianJi> parse(String response)  {
        if(mList == null)
            mList = new ArrayList<NianJi>();

        try {
            JSONObject jo = new JSONObject(response);
            strResult = jo.optString("result");
            JSONArray js = jo.optJSONArray("nianjisList");
            for (int i = 0; i < js.length(); i++) {
                JSONObject t = js.getJSONObject(i);

                NianJi bean = new NianJi();
                bean.setId(t.optString("id"));
                bean.setName(t.optString("name"));
                mList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mList;
    }

    /**
     * 添加“全部”字段
     *
     */
    public void addTotalP() {
        if(mList == null)
            mList = new ArrayList<NianJi>();

        NianJi nianJi = new NianJi();
        nianJi.id = "0";
        nianJi.name = "全部";

        mList.add(6, nianJi);
    }

    public List<NianJi> getmList() {
        return mList;
    }

    public String getStrResult() {
        return strResult;
    }

}
