package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iTicketDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Ticket;
import xupt.se.util.DBUtil;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public class TicketDAO implements iTicketDAO{
    @Override
    public int insert(Ticket tick) {
       try{
           String sql="insert into ticket(seat_id,sched_id,ticket_price,ticket_status,ticket_locked_time)"
                   +" values("
                   +tick.getSeat_id()
                   +","+tick.getSched_id()
                   +","+tick.getTicket_price()
                   +","+tick.getTicket_status()
                   +",'"+tick.getTicket_times()+"')";
           System.out.println(sql);
           DBUtil db=new DBUtil();
           db.openConnection();
           ResultSet rst = db.getInsertObjectIDs(sql);
           if(rst !=null && rst.first()){
               tick.setTicket_id(rst.getInt(1));
           }
           db.close(rst);
           db.close();
           return 1;
       }catch (Exception e){
           e.printStackTrace();
       }
        return 0;
    }

    @Override
    public int update(Ticket tick) {
        int rtn=0;
        try{
            String sql="update ticket set "+" seat_id="+tick.getSeat_id()
                    +","+" sched_id="+tick.getSched_id()
                    +","+" ticket_price="+tick.getTicket_price()
                    +","+" ticket_status="+tick.getTicket_status()
                    +","+" ticket_locked_time='"+tick.getTicket_times()+"'";
            sql+=" where ticket_id= "+tick.getTicket_id();
//            System.out.println("Usql"+sql);
            DBUtil db=new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rtn;
    }



    @Override
    public int delete(int ID) {
        int rtn=0;
        try{
            String sql="delete from ticket ";
            sql+=" where ticket_id="+ID;
            DBUtil db=new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rtn;
    }

    @Override
    public List<Ticket> select(String condt) {
        List<Ticket> ticketList=null;
        ticketList=new LinkedList<Ticket>();
        try{
            String sql="select ticket_id,seat_id,sched_id,ticket_price,ticket_status,ticket_locked_time from ticket";
            condt.trim();
            if(!condt.isEmpty()){
                sql+=" where "+condt;
            }
//            System.out.println(sql);
            DBUtil db=new DBUtil();
            if(!db.openConnection()){
                System.out.println("fail to connect database");
                return  null;
            }
            ResultSet rst=db.execQuery(sql);
            if(rst!=null){
                while(rst.next()){
                    Ticket tick=new Ticket();
                    tick.setTicket_id(rst.getInt("ticket_id"));
                    tick.setSeat_id(rst.getInt("seat_id"));
                    tick.setSched_id(rst.getInt("sched_id"));
                    tick.setTicket_price(rst.getFloat("ticket_price"));
                    tick.setTicket_status(rst.getInt("ticket_status"));
                    tick.setTicket_times(rst.getString("ticket_locked_time"));
                    ticketList.add(tick);
                }
            }
            db.close(rst);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{

        }
        return ticketList;
    }

    @Override
    public Pager<Ticket> selectByPage(String condt, int page, int pageSize){

        return null;
    }
}
