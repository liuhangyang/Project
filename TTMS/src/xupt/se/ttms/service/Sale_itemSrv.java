package xupt.se.ttms.service;



import xupt.se.ttms.dao.Sale_itemDAO;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iSale_itemDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.Sale_item;

import java.util.List;

/**
 * Created by yang on 16-6-21.
 */
public class Sale_itemSrv {
    private iSale_itemDAO sale_itemDAO=DAOFactory.createSaleitemDAO();
    public int add(Sale_item s_item){
        return new Sale_itemDAO().insert(s_item);
    }
    public int modify(Sale_item s_item){
        return new Sale_itemDAO().update(s_item);
    }
    public int delete(int Id){
        return  new Sale_itemDAO().delete(Id);
    }
    public List<Sale_item> Fetch(String condt){
        return  new Sale_itemDAO().select(condt);
    }
    public Pager<Sale_item> FetchByPage(String condt, int page, int pageSize){
        if(page < 1){
            page = 1;
        }
        return new Sale_itemDAO().selectByPage(condt,page,pageSize);
    }
}

