package xupt.se.ttms.model;

/**
 * Created by lc on 2016/6/22.
 */

import java.util.List;


public class Sale_playlist {
    private String play_name;
    List<Studio> stulist;
    List<Schedule> schedlist;

    public String getPlay_name() {
        return play_name;
    }

    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    public List<Studio> getStulist() {
        return stulist;
    }

    public void setStulist(List<Studio> stulist) {
        this.stulist = stulist;
    }

    public List<Schedule> getSchedlist() {
        return schedlist;
    }

    public void setSchedlist(List<Schedule> schedlist) {
        this.schedlist = schedlist;
    }
}

