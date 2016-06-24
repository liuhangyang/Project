package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iScheduleDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.Schedule;
import xupt.se.util.DBUtil;

import java.util.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public class ScheduleDAO implements iScheduleDAO {
    @Override
    public int insert(Schedule sche) {
        try{
            String sql="insert into schedule(studio_id,play_id,sched_time,sched_ticket_price)"
                    + "values("
                    +sche.getStuId()
                    +","+sche.getPlayId()
                    +",'"+sche.getScheTime()
                    +"',"
                    +sche.getSchePrice()+")";
            DBUtil db=new DBUtil();
            db.openConnection();
            ResultSet rst=db.getInsertObjectIDs(sql);
            if(rst!=null && rst.first()){
                sche.setSchedId(rst.getInt(1));
            }
            db.close(rst);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Schedule sche) {
      int rtn=0;
        try{
            String sql="update schedule set " + " studio_id="
                    +sche.getStuId()+","+" play_id="
                    +sche.getPlayId()+","+" sched_time= '"+sche.getScheTime()+"'"
                    +","+" sched_ticket_price="+ sche.getSchePrice();
            sql+=" where sched_id= "+sche.getSchedId();
            System.out.println("update: "+sql);
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
            String sql="delete from schedule ";
            sql+=" where sched_id="+ID;
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
    public List<Schedule> select(String condt) {
        List<Schedule> schList=null;
        schList=new LinkedList<Schedule>();
        try{
            String sql="select sched_id,studio_id,play_id,sched_time,sched_ticket_price from schedule ";
            System.out.println(sql);
            condt.trim(); //去掉空格两边的空格
            if(!condt.isEmpty()){
                sql+=" where " + condt;
            }
           // System.out.println(sql);
            DBUtil db=new DBUtil();
            if(!db.openConnection()){
                System.out.println("fail to connect database");
                return  null;
            }
            ResultSet rst=db.execQuery(sql);
            if(rst!=null){
                while(rst.next()){
                    Schedule sche=new Schedule();
                    sche.setSchedId(rst.getInt("sched_id"));
                    sche.setStuId(rst.getInt("studio_id"));
                    sche.setPlayId(rst.getInt("play_id"));
                    sche.setScheTime(rst.getString("sched_time"));
                    sche.setSchePrice(rst.getFloat("sched_ticket_price"));
                    schList.add(sche);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return schList;
    }

    @Override
    public Pager<Schedule> selectByPage(String condt, int page, int pageSize) {
        Pager<Schedule> schePager=new Pager<>(pageSize,page);
        List<Schedule> scheList=null;
        scheList=new LinkedList<Schedule>();
        try{
            StringBuffer sql=new StringBuffer("select sched_id,studio_id,play_id,sched_time,sched_ticket_price from schedule ");
            StringBuffer countSql=new StringBuffer("select count(sched_id) as count from schedule ");

            condt.trim();
            if(!condt.isEmpty()){
                countSql.append(" where "+condt);
                sql.append(" where "+ condt);
            }

            DBUtil db=new DBUtil();
            if(!db.openConnection()){
                System.out.println("fail to connect database");
                return  null;
            }
            System.out.println("SQL->"+sql);
            //查询总记录数
            ResultSet countS=db.execQuery(countSql.toString());
            int total=0;
            if(countS!=null){
                countS.next();
                total=countS.getInt("count");
            }
            db.close(countS);
            schePager.setTotal(total);
            sql.append(" limit "+(schePager.getCurPage()-1)*schePager.getPageSize()+","+ schePager.getPageSize());
            ResultSet rst=db.execQuery(sql.toString());
            if(total != 0 && rst !=null) {
                while (rst.next()) {
                    Schedule sche = new Schedule();
                    sche.setSchedId(rst.getInt("sched_id"));
                    sche.setPlayId(rst.getInt("play_id"));
                    sche.setStuId(rst.getInt("studio_id"));
                    sche.setScheTime(rst.getString("sched_time"));
                    sche.setSchePrice(rst.getFloat("sched_ticket_price"));
                    scheList.add(sche);
                }
            }
            db.close(rst);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        schePager.setDataList(scheList);
        return schePager;
    }

    public static void main(String[] args) {
        Schedule sche=new Schedule();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now=new Date();
        String value=sdf.format(now);
        sche.setSchedId(1);
        sche.setStuId(16);
        sche.setPlayId(4);
        sche.setScheTime(value);
        sche.setSchePrice(33.8f);
     //  new ScheduleDAO().insert(sche);
        //new ScheduleDAO().update(sche);
       // new ScheduleDAO().delete(2);
       /* List<Schedule> ts=new LinkedList<Schedule>();
        ts=new ScheduleDAO().select("sched_id > 4");
        for(Schedule a:ts){
            System.out.println(a.getScheTime());
        }*/
        new ScheduleDAO().selectByPage("sched_id >1",2,3);
    }
}
