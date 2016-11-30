package com.kuaxue.demo.netparse.entity;

import android.content.Context;

import com.kuaxue.database.dao.CourseInfo;
import com.kuaxue.database.dao.CourseInfoDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CourseNianJiSubjects {
	public String kemusCount;
	public String operation;
	public String result;
	public int nianjiId;
	public List<CourseInfo> kemusList;
	private Context mContext = null;

	public CourseNianJiSubjects(Context mcontext) {
		mContext = mcontext;
		kemusList = new ArrayList<CourseInfo>();

	}

	public void parseJson(String strJson) {
		JSONObject jsonObject;

		try {
			jsonObject = new JSONObject(strJson);

			result = jsonObject.getString("result");
			kemusCount = jsonObject.getString("kemusCount");

			nianjiId = jsonObject.getInt("kemusCount");

			JSONArray resultLst = jsonObject.getJSONArray("kemusList");
			for (int i = 0; i < resultLst.length(); i++) {
				JSONObject item = resultLst.getJSONObject(i);

				CourseInfo tempCourse = new CourseInfo();
				tempCourse.setClsID(nianjiId);
				tempCourse.setKemuID(Integer.valueOf(item.getString("id")));
				tempCourse.setKemuName(item.getString("name"));
				kemusList.add(tempCourse);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 默认添加所有科目
	 * @param courseInfoDao
	 */
	public void addTotalKemu(CourseInfoDao courseInfoDao) {
		// "语文", "数学","英语" ,
		// "物理", "化学", "政治", "历史","地理" ,"生物"
		CourseInfo mkemu1 = new CourseInfo();
		mkemu1.setKemuID(0);
		mkemu1.setKemuName("语文");
		mkemu1.setClsID(0);

		CourseInfo mkemu2 = new CourseInfo();
		mkemu2.setKemuID(0);
		mkemu2.setKemuName("数学");
		mkemu2.setClsID(0);

		CourseInfo mkemu3 = new CourseInfo();
		mkemu3.setKemuID(0);
		mkemu3.setKemuName("英语");
		mkemu3.setClsID(0);

		CourseInfo mkemu4 = new CourseInfo();
		mkemu4.setKemuID(0);
		mkemu4.setKemuName("物理");
		mkemu4.setClsID(0);

		CourseInfo mkemu5 = new CourseInfo();
		mkemu5.setKemuID(0);
		mkemu5.setKemuName("化学");
		mkemu5.setClsID(0);

		CourseInfo mkemu6 = new CourseInfo();
		mkemu6.setKemuID(0);
		mkemu6.setKemuName("政治");
		mkemu6.setClsID(0);

		CourseInfo mkemu7 = new CourseInfo();
		mkemu7.setKemuID(0);
		mkemu7.setKemuName("历史");
		mkemu7.setClsID(0);

		CourseInfo mkemu8 = new CourseInfo();
		mkemu8.setKemuID(0);
		mkemu8.setKemuName("地理");
		mkemu8.setClsID(0);

		CourseInfo mkemu9 = new CourseInfo();
		mkemu9.setKemuID(0);
		mkemu9.setKemuName("生物");
		mkemu9.setClsID(0);

		kemusList.add(mkemu1);
		kemusList.add(mkemu2);
		kemusList.add(mkemu3);
		kemusList.add(mkemu4);
		kemusList.add(mkemu5);
		kemusList.add(mkemu6);
		kemusList.add(mkemu7);
		kemusList.add(mkemu8);
		kemusList.add(mkemu9);

		courseInfoDao.insertOrReplace(mkemu1);
		courseInfoDao.insertOrReplace(mkemu2);
		courseInfoDao.insertOrReplace(mkemu3);
		courseInfoDao.insertOrReplace(mkemu4);
		courseInfoDao.insertOrReplace(mkemu5);
		courseInfoDao.insertOrReplace(mkemu6);
		courseInfoDao.insertOrReplace(mkemu7);
		courseInfoDao.insertOrReplace(mkemu8);
		courseInfoDao.insertOrReplace(mkemu9);
	}
}
