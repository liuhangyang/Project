package xupt.se.ttms.model;


import java.sql.Date;

/**
 * Created by yang on 16-6-14.
 */
public class Schedule{
    private int schedId;//演出计划id
    private int stuId; //演出厅id
    private int playId; //剧目id
    private String scheTime; //演出时间
    private float schePrice; //票价

    public int getSchedId() {
        return schedId;
    }

    public void setSchedId(int schedId) {
        this.schedId = schedId;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }

    public String getScheTime() {
        return scheTime;
    }

    public String getShortScheTime() {
        return scheTime.substring(0,scheTime.lastIndexOf(':'));
    }

    public void setScheTime(String scheTime) {
        this.scheTime = scheTime;
    }

    public float getSchePrice() {
        return schePrice;
    }

    public void setSchePrice(float schePrice) {
        this.schePrice = schePrice;
    }
}
