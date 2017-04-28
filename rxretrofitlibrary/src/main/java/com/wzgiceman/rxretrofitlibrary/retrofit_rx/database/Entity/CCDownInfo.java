package com.wzgiceman.rxretrofitlibrary.retrofit_rx.database.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * CC下载单个课程用
 * Created by kuaX on 2017/3/23.
 */
@Entity
public class CCDownInfo {
    @Id
    private String ccid;            // videoid ccid
    private String courseName;      // 课程名称
    private String titvideotitle="";// 视频名称
    private String courseId;
    private int progress;           //进度
    private String progressText;    //进度 0M/0M
    private int status;		        //状态 是否在下载

    private boolean bCheck =false;  // 是否选中

    @Generated(hash = 1472405314)
    public CCDownInfo(String ccid, String courseName, String titvideotitle,
            String courseId, int progress, String progressText, int status,
            boolean bCheck) {
        this.ccid = ccid;
        this.courseName = courseName;
        this.titvideotitle = titvideotitle;
        this.courseId = courseId;
        this.progress = progress;
        this.progressText = progressText;
        this.status = status;
        this.bCheck = bCheck;
    }
    @Generated(hash = 389268847)
    public CCDownInfo() {
    }
    public String getCcid() {
        return this.ccid;
    }
    public void setCcid(String ccid) {
        this.ccid = ccid;
    }
    public String getCourseName() {
        return this.courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getTitvideotitle() {
        return this.titvideotitle;
    }
    public void setTitvideotitle(String titvideotitle) {
        this.titvideotitle = titvideotitle;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getCourseId() {
        return this.courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public int getProgress() {
        return this.progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
    public String getProgressText() {
        return this.progressText;
    }
    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }
    public boolean getBCheck() {
        return this.bCheck;
    }
    public void setBCheck(boolean bCheck) {
        this.bCheck = bCheck;
    }
   
}
