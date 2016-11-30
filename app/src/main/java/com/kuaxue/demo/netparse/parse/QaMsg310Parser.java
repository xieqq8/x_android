/**
 * copyright(C)2014-
 */
package com.kuaxue.demo.netparse.parse;


import com.kuaxue.demo.netparse.entity.MessageAsk;
import com.xxx.utils.DateUtil;
import com.xxx.utils.LogX;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 答疑消息处理
 * @author
 * @code:
 * class:
 */
public class QaMsg310Parser extends BaseListParse<MessageAsk> {
    public int total = 0;

    public int getTotal() {
        return total;
    }

    @Override
    public List<MessageAsk> parser(String response) {

        if (mList == null)
            mList = new ArrayList<MessageAsk>();

        try {
            JSONObject object = new JSONObject(response).optJSONObject("data");
            JSONArray tobj;
            if (object == null) {
                tobj = new JSONObject(response).getJSONArray("data");
            } else {
                tobj = object.getJSONArray("rows");
                total = object.optInt("total");
            }
            for (int i = 0; i < tobj.length(); i++) {
                JSONObject t = tobj.getJSONObject(i);
                MessageAsk bean = new MessageAsk();
                bean.setId(t.optString("id"));  // question里面
                bean.setMessageTypeA(2);    // 答疑消息 2
                bean.setQuestionNumber(t.optString("questionNumber"));
                bean.setbViewed(t.optBoolean("viewed"));

                JSONObject qainfo = t.getJSONObject("qaAdditionalInfo");
                JSONObject grade = qainfo.getJSONObject("grade");
                JSONObject subject = qainfo.getJSONObject("subject");
                if (grade != null && subject != null) {
                    bean.setUsername(grade.optString("name") + subject.optString("name"));
                }
                bean.setImageUrl(qainfo.optString("questionPhoto"));
                JSONObject answer = t.getJSONObject("answer");
                bean.setShowcontent(answer.optString("content"));
                bean.setContentTime(answer.optString("answerTime"));
                bean.setContentTime(DateUtil.parseSpaceTime(answer.optLong("answerTime"), System.currentTimeMillis()));
                mList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogX.getLogger().d("parse mlist size:" + mList.size());
        return mList;
    }
}
