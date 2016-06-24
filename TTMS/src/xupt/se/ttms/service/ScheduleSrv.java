package xupt.se.ttms.service;

import xupt.se.ttms.dao.ScheduleDAO;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iScheduleDAO;
import xupt.se.ttms.model.*;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yang on 16-6-20.
 */
public class ScheduleSrv {
    private iScheduleDAO scheduleDAO= DAOFactory.createScheduleDAO();
    public int add(Schedule sche){
        return new ScheduleDAO().insert(sche);
    }
    public  int modify(Schedule sche){
        return  new ScheduleDAO().update(sche);
    }
    public int delete(int ID){
        return  new ScheduleDAO().delete(ID);
    }
    public List<Schedule> Fetch(String condt){
        return new ScheduleDAO().select(condt);
    }
    public Pager<ScheduleList> FetchByPage(String condt, int page, int pageSize){
        if(page < 1){
            page = 1;
        }
        //return scheduleDAO.selectByPage(condt,page,pageSize);

        Pager<ScheduleList> schePager=new Pager<>(pageSize,page);
        List<ScheduleList> scheList= new LinkedList<ScheduleList>();
        try{
            StringBuffer sql=new StringBuffer("SELECT * FROM schedule left join play on schedule.play_id = play.play_id left join studio on schedule.studio_id = studio.studio_id ");
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
                    Studio stu = new Studio();
                    Play play = new Play();
                    sche.setSchedId(rst.getInt("sched_id"));
                    sche.setPlayId(rst.getInt("play_id"));
                    sche.setStuId(rst.getInt("studio_id"));
                    sche.setScheTime(rst.getString("sched_time"));
                    sche.setSchePrice(rst.getFloat("sched_ticket_price"));

                    play.setpId(rst.getInt("play_id"));
                    play.setpTypeId(rst.getInt("play_type_id"));
                    play.setpLangId(rst.getInt("play_lang_id"));
                    play.setpName(rst.getString("play_name"));
                    play.setpIntro(rst.getString("play_introduction"));
                    play.setpImage(rst.getString("play_image"));
                    play.setpLength(rst.getInt("play_length"));
                    play.setpTicketprice(rst.getFloat("play_ticket_price"));
                    play.setpStatus(rst.getInt("play_status"));

                    stu.setID(rst.getInt("studio_id"));
                    stu.setName(rst.getString("studio_name"));
                    stu.setRowCount(rst.getInt("studio_row_count"));
                    stu.setColCount(rst.getInt("studio_col_count"));
                    stu.setIntroduction(rst.getString("studio_introduction"));
                    stu.setFlag(rst.getInt("studio_flag"));

                    scheList.add(new ScheduleList(play, sche, stu));
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
}
