package xupt.se.ttms.idao;

import xupt.se.ttms.dao.SaleDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Sale;

import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public interface iSaleDAO {
    public int insert(Sale sae);
    public int update(Sale sae);
    public int delete(int ID);
    public List<Sale> select(String condt);
    public Pager<Sale> selectByPage(String condt, int page, int pageSize);
}
