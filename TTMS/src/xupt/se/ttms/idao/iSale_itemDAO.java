package xupt.se.ttms.idao;

import xupt.se.ttms.model.Pager;

import xupt.se.ttms.model.Sale_item;

import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public interface iSale_itemDAO {
    public int insert(Sale_item item);
    public int update(Sale_item item);
    public int delete(int ID);
    public List<Sale_item> select(String condt);
    public Pager<Sale_item> selectByPage(String condt, int page, int pageSize);
}
