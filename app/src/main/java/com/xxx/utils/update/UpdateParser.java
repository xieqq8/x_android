package com.xxx.utils.update;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本信息检测
 */
public class UpdateParser {

    /**
     *
     {
         "status": 200,
         "message": "查询成功",
         "data": {
             "id": 7,
             "versionName": "1.3.8",
             "versionCode": 22,
             "apkFileURL": "http://oss.kuaxue.com/app/2015-11-06/admin/42117ad7-7a08-4548-80c4-57d8e226697f.apk",
             "forceUpdate": true,
             "notes": "更新: 增加了老师自动抢答功能, 修正了一些bug并优化了程序性能."
         }
     }
     * @param response
     * @return
     * @throws JSONException
     */
    public UpdateData parse(String response) throws JSONException {

        UpdateData updateData = new UpdateData();
        try {
            JSONObject rootObject = new JSONObject(response);
            JSONObject object = rootObject.optJSONObject("data");
            if (object!=null){
                updateData.setId(object.optInt("id"));
                updateData.setSize(object.optInt("size"));
                updateData.setVersionCode(object.optInt("versionCode"));
                updateData.setApkFileURL(object.optString("apkFileURL"));
                updateData.setAppname(object.optString("appName"));
                updateData.setConfigType(object.optString("configType"));
                updateData.setNotes(object.optString("notes"));
                updateData.setPid(object.optString("pid"));
                updateData.setVersionName(object.optString("versionName"));
                updateData.setInactive(object.optBoolean("inactive"));
                updateData.setVisibility(object.optBoolean("visibility"));
                updateData.setForceUpdate(object.optBoolean("forceUpdate"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return updateData;
    }
}
