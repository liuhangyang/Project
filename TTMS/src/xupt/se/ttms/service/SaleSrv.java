package xupt.se.ttms.service;

import xupt.se.ttms.dao.SaleDAO;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iSaleDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale;

import java.util.List;

/**
 * Created by yang on 16-6-21.
 */
public class SaleSrv {
    private iSaleDAO saleDao= DAOFactory.createSaleDAO();
    public  int add(Sale sale){
        return new SaleDAO().insert(sale);
    }
    public int modify(Sale sale){
        return new SaleDAO().update(sale);
    }
    public int delete(int Id){
        return  new SaleDAO().delete(Id);
    }
    public List<Sale>  Fetch(String condt){
        return new SaleDAO().select(condt);
    }
    public Pager<Sale> FetchByPage(String condt, int page, int pageSize){
        if(page < 1){
            page = 1;
        }
        return new SaleDAO().selectByPage(condt,page,pageSize);
    }

}

