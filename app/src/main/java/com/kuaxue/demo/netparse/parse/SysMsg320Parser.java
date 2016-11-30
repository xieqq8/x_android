/**
 *
 * copyright(C)2014- 
 *
 */
package com.kuaxue.demo.netparse.parse;


import com.kuaxue.demo.netparse.entity.MessageAsk;
import com.xxx.utils.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统消息解析
 * @author
 * @code: create：14-11-3下午3:19
 * class:
 */
public  class SysMsg320Parser extends BaseListParse<MessageAsk> {
    public int total = 0;
    public int getTotal() {
        return total;
    }

    @Override
    public List<MessageAsk> parser(String response) {
        if(mList == null) {
            mList = new ArrayList<>();
        }
        try {
            JSONObject object = new JSONObject(response).optJSONObject("data");
            JSONArray tobj;
            if(object == null){
                tobj = new JSONObject(response).getJSONArray("data");
            }else{
                tobj = object.getJSONArray("rows");
                total = object.optInt("total");
            }
            for(int i=0;i<tobj.length();i++){
                JSONObject t = tobj.getJSONObject(i);
                MessageAsk bean = new MessageAsk();
                bean.setId(t.optString("id"));
                bean.setbViewed(t.optBoolean("viewed"));    // 是否己读
                bean.setShowTitle(t.optString("showTitle"));// 标题
                bean.setShowcontent(t.optString("showContent")); // 内容
//                bean.setCcid(t.optString("ccid"));
                bean.setRemarks(t.optString("itemid"));
                bean.setMessageTypeA(1);// 系统消息 1
                bean.setMessageTypeB(t.optString("messageType"));
                bean.setContentTime(DateUtil.parseSpaceTime(t.optLong("pushTime"), System.currentTimeMillis()));
                mList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }

}
