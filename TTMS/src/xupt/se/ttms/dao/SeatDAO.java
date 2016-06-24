package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iSeatDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Seat;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public class SeatDAO implements iSeatDAO {
    @Override
    public int insert(Seat seat) {
        try {
            String sql = "insert into seat(studio_id,seat_row,seat_column,seat_status)"
                    + " values("
                    + seat.getStuId()
                    + "," + seat.getsRow()
                    + "," + seat.getScolumn()
                    + "," + seat.getSeat_status()
                    + ")";
            DBUtil db=new DBUtil();
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst != null && rst.first()) { //rst.first()移动到第一行
                seat.setSeatId(rst.getInt(1));
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
    public int update(Seat seat) {
        int rtn=0;
        try{
            String sql="update seat set "
                    + " studio_id="+seat.getStuId()
                    +","+" seat_row="+seat.getsRow()
                    +","+" seat_column="+seat.getScolumn()
                    +","+" seat_status="+seat.getSeat_status();
            sql+=" where seat_id="+seat.getSeatId();
            System.out.println(sql);
            DBUtil db=new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rtn;
    }
    @Override
    public int delete(int ID) {
        int rtn=0;
        try{
            String sql="delete from seat ";
            sql+=" where seat_id = "+ID;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn = db.execCommand(sql);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rtn;
    }

    @Override
    public List<Seat> select(String condt) {
        List<Seat> seatList=null;
        seatList=new LinkedList<Seat>();
        try{
            String sql="select seat_id,studio_id,seat_row,seat_column,seat_status from seat ";
            condt.trim(); //去掉条件两边的空格
            if(!condt.isEmpty()){
                sql+="where "+ condt;
            }
            System.out.println(sql);
            DBUtil db = new DBUtil();
            if (!db.openConnection()) {
                System.out.print("fail to connect database");
                return null;
            }
            ResultSet rst = db.execQuery(sql);
            if (rst != null) {
                while(rst.next()){
                    Seat seat=new Seat();
                    seat.setSeatId(rst.getInt("seat_id"));
                    seat.setStuId(rst.getInt("studio_id"));
                    seat.setsRow(rst.getInt("seat_row"));
                    seat.setScolumn(rst.getInt("seat_column"));
                    seat.setSeat_status(rst.getInt("seat_status"));
                    seatList.add(seat);
                }
            }
            db.close(rst);
            db.close();
        }catch(Exception e){
                e.printStackTrace();
        }finally{

        }
        return seatList;
    }

    @Override
    public Pager<Seat> selectByPage(String condt, int page, int pageSize) {
        return null;
    }

}
