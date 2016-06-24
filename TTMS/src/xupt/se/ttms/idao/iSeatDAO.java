package xupt.se.ttms.idao;

import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.Seat;

import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public interface iSeatDAO {
    public int insert(Seat seat);
    public int update(Seat seat);
    public int delete(int ID);
    public List<Seat> select(String condt);
    public Pager<Seat> selectByPage(String condt, int page, int pageSize);
}
