package xxx.com.dbutil.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * CC下载用的课程包
 * Created by kuaX on 2017/3/23.
 */
@Entity
public class CCCoursePack {
    @Id
    private String courseid;      // 课程包ID
    private String title;   // 标题
    private String name;    // 名称
    @Generated(hash = 295260871)
    public CCCoursePack(String courseid, String title, String name) {
        this.courseid = courseid;
        this.title = title;
        this.name = name;
    }
    @Generated(hash = 93754482)
    public CCCoursePack() {
    }
    public String getCourseid() {
        return this.courseid;
    }
    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
