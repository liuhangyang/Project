package xupt.se.ttms.idao;

import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.Ticket;

import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public interface iTicketDAO {
    public int insert(Ticket tick);
    public int update(Ticket tick);
    public int delete(int ID);
    public List<Ticket> select(String condt);
    public Pager<Ticket> selectByPage(String condt, int page, int pageSize);
}
