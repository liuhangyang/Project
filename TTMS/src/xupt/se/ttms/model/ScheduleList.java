package xupt.se.ttms.model;

/**
 * Created by lc on 2016/6/22.
 */
public class ScheduleList {
    private Schedule sched;
    private Play play;
    private Studio stu;

    public ScheduleList(Play play, Schedule sched, Studio stu) {
        this.play = play;
        this.sched = sched;
        this.stu = stu;
    }

    public ScheduleList() {
    }

    public void setSched(Schedule sched) {
        this.sched = sched;
    }

    public void setStu(Studio stu) {
        this.stu = stu;
    }

    public void setPlay(Play play) {
        this.play = play;
    }

    public Schedule getSched() {
        return sched;
    }

    public Studio getStu() {
        return stu;
    }

    public Play getPlay() {
        return play;
    }
}
