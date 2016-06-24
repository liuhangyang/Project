package xupt.se.ttms.idao;

import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.Schedule;

import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public interface iScheduleDAO{
    public int insert(Schedule sche);
    public int update(Schedule sche);
    public int delete(int ID);
    public List<Schedule> select(String condt);
    public Pager<Schedule> selectByPage(String condt, int page, int pageSize);
}
