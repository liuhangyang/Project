package xupt.se.ttms.service;

import xupt.se.ttms.dao.SaleDAO;
import xupt.se.ttms.dao.TicketDAO;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iSaleDAO;
import xupt.se.ttms.idao.iTicketDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.Ticket;

import java.util.List;

/**
 * Created by yang on 16-6-21.
 */
public class TicketSrv {
    private iTicketDAO ticketDAO= DAOFactory.createTicketDAO();
    public  int add(Ticket ticket){
        return new TicketDAO().insert(ticket);
    }
    public int modify(Ticket ticket){
        return new TicketDAO().update(ticket);
    }
    public int delete(int Id){
        return  new TicketDAO().delete(Id);
    }
    public List<Ticket> Fetch(String condt){
        return new TicketDAO().select(condt);
    }
    public Pager<Ticket> FetchByPage(String condt, int page, int pageSize){
        if(page < 1){
            page = 1;
        }
        return new TicketDAO().selectByPage(condt,page,pageSize);
    }
}
