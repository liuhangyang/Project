package xupt.se.ttms.service;

/**
 * Created by lc on 2016/6/22.
 */

import sun.management.counter.perf.PerfLongArrayCounter;
import xupt.se.ttms.model.*;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yang on 16-6-22.
 */
public class Sale_playlistSrv {

    public Pager<Sale_playlist> SelectByPage(String condt,int page,int pageSize){
        Pager<Sale_playlist> salePager=new Pager<>(pageSize,page);
        List<Sale_playlist>  saleList=null;
//        List<Studio> stuList=new LinkedList<>();
//        List<Schedule> scheList=new  LinkedList<>();

        List<Studio> stuList = null;
        List<Schedule> scheList =null;



        saleList=new LinkedList<Sale_playlist>();
        int studio_id=0,play_id=0;
        try{
            StringBuffer play_name=new StringBuffer("select play_name from play where play_id=");
            StringBuffer sql_playid=new StringBuffer("select distinct play_id from schedule ");
            StringBuffer sql_id=new StringBuffer("select studio_id from schedule where play_id=");
            StringBuffer sql_stu=new StringBuffer("select * from studio where studio_id=");
            StringBuffer schdule=new StringBuffer("select * from schedule where play_id=");
            DBUtil db = new DBUtil();
            if (!db.openConnection()) {
                System.out.print("fail to connect database");
                return null;
            }
            //查询记录总数
            ResultSet countPlays=db.execQuery("select  count(distinct(play_id)) as count from schedule");
            int total=0;
            if(countPlays!=null){
                countPlays.next();
                total=countPlays.getInt("count");
            }
            db.close(countPlays);
            salePager.setTotal(total);
            sql_playid.append(" limit "+(salePager.getCurPage()-1)*salePager.getPageSize()+","+salePager.getPageSize());

            ResultSet rst=db.execQuery(sql_playid.toString());

            if(rst!=null){
                //rst.next();
                //System.out.print(rst.getInt("play_id"));

                while(rst.next()){
                    stuList = new LinkedList<>();
                    scheList = new LinkedList<>();
                    System.out.println("yang");
                    play_id=rst.getInt("play_id");
                    System.out.println("play: "+play_id);
                    // System.out.println(play_name.toString());
                    String s=play_name.toString();
                    //play_name.append(play_id);
                    s=s+play_id;
                    ResultSet p_name=db.execQuery(s);
                    p_name.next();
                    String pname=p_name.getString("play_name");
                    Sale_playlist salePlay=new Sale_playlist();
                    //sql_id.append(play_id);
                    String s2=sql_id.toString();
                    s2=s2+play_id;
                    ResultSet stu_rst=db.execQuery(s2);
                    ResultSet rst_stu=null;
                    if(stu_rst!=null){
                        while(stu_rst.next()){
                            studio_id=stu_rst.getInt("studio_id");
                            System.out.println(studio_id);
                            String  s1=sql_stu.toString();
                            s1=s1+studio_id;
                            //sql_stu.append(studio_id);
                            rst_stu=db.execQuery(s1);
                            rst_stu.next();
                            Studio stu=new Studio();
                            stu.setID(rst_stu.getInt("studio_id"));
                            stu.setName(rst_stu.getString("studio_name"));
                            stu.setRowCount(rst_stu.getInt("studio_row_count"));
                            stu.setColCount(rst_stu.getInt("studio_col_count"));
                            stu.setIntroduction(rst_stu.getString("studio_introduction"));
                            stuList.add(stu);
                        }
                    }
                    String s3=schdule.toString();
                    s3=s3+play_id;
                    ResultSet scherst=db.execQuery(s3);
                    if(scherst!=null){
                        while(scherst.next()){
                            Schedule sche=new Schedule();
                            sche.setSchedId(scherst.getInt("sched_id"));
                            sche.setStuId(scherst.getInt("studio_id"));
                            sche.setPlayId(scherst.getInt("play_id"));
                            sche.setScheTime(scherst.getString("sched_time"));
                            sche.setSchePrice(scherst.getFloat("sched_ticket_price"));
                            scheList.add(sche);
                        }
                    }
                    salePlay.setStulist(stuList);
                    salePlay.setSchedlist(scheList);
                    salePlay.setPlay_name(pname);
                    saleList.add(salePlay);
                }


            }
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        salePager.setDataList(saleList);
        return salePager;

    }

    public static void main(String[] args) {
        new Sale_playlistSrv(). SelectByPage("",1,10);
    }
}
