package xupt.se.web;
import com.google.common.collect.Maps;
import xupt.se.config.Config;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.ScheduleList;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang on 16-6-20.
 */
public class SchduleSl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI().toString();
        System.out.println("uri:" + uri);
        String sn = getServletName();
        System.out.println("sn:" + sn);
        String route = uri.substring(uri.lastIndexOf(getServletName()));
        String routes[] = route.split("/");
        String curRoute = "";
        //if(routes.length>3)
        //判断当前路由
        if (routes.length > 1) {
            curRoute = routes[1];

        }

        PrintWriter out = response.getWriter();
        HashMap<String, String> sp = RequestParams.getParams(request.getParameterMap());
        ScheduleSrv sches = new ScheduleSrv();
        response.setContentType("text/html;charset=utf-8");
        if (curRoute.equals("add")) {
            System.out.println("SCH ADD");
                this.addItem(sp,sches,out,"add");
        } else if (curRoute.equals("delete")) {
                this.deleteItem(sp,sches,out);
        } else if (curRoute.equals("update")) {
                this.addItem(sp,sches,out,"update");
        }
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI().toString();
        String sn = getServletName();
        String route = uri.substring(uri.lastIndexOf(getServletName()));
        String routes[] = route.split("/");
        String curRoute = "";
        if (routes.length > 1) {
            curRoute = routes[1];
        }
        System.out.println("GET Route: " + StringUtils.join(routes));
        ScheduleSrv sche = new ScheduleSrv();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        HashMap<String, String> sp = RequestParams.getFullParams(request.getParameterMap());
        if (curRoute.equals("get")) {
            this.getItem(sp,sche,out);
        } else if (curRoute.equals("")) {
            this.getPager(sp,sche,out);
        }
    }

    public void getPager(HashMap<String, String> sp, ScheduleSrv sches, PrintWriter out) {
        Pager<ScheduleList> list = null;
        int page = RequestParams.getInt(sp.get("p"), 1),
                pn = RequestParams.getInt(sp.get("pn"), Config.pageSize);

        System.out.println("P->  page:" + page + "  pN: " + pn);
        //取出数据
        list = sches.FetchByPage("", page, pn);
        //根据请求方式参数确定返回格式
        if (sp.get("method").equals("hxr")) { //动态加载请求
            if (sp.get("format").equals("html")) { //html格式特殊处理
                Map<String, Object> context = Maps.newHashMap();
                context.put("page", list);
                String template = Resource.renderTpl(
                        this.getServletContext().getRealPath("templates"), "schdule_table.html", context);
                out.print(template);
            } else {//默认json格式
                out.print(ResponseJson.toJSONString(list));
            }
        } else {//直接请求
            Map<String, Object> context = Maps.newHashMap();
            context.put("page", list);
            String template = Resource.renderTpl(
                    this.getServletContext().getRealPath("templates"), "schdule.html", context);
            out.print(template);
        }

    }

    public void getItem(HashMap<String, String> sp, ScheduleSrv sches, PrintWriter out) {
        if (sp.containsKey("schdule_id")){
            try {
                int sid = Integer.parseInt(sp.get("schdule_id"));
                List<Schedule> stu = sches.Fetch("sched_id=" + sid);
                if (stu.size() > 0) {
                    out.print(ResponseJson.toJSONString(stu.get(0)));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据不存在", ""));
                }
            } catch (NumberFormatException e) {
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                getServletContext().log("Schedule Error: " + e.getMessage());
            }
        }else{
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确！"));
        }
    }
    public void addItem(HashMap<String,String> sp,ScheduleSrv scheds,PrintWriter out,String type){

           Schedule newschedule= null;
           if (type.equals("add")){
               int  stu_id;
               int  play_id;
               float price;
               newschedule=new Schedule();
               newschedule.setScheTime(DateUtil.dtl2dt(sp.get("sched_time")));
               try{
                   stu_id=Integer.parseInt(sp.get("studio_id"));
                   play_id=Integer.parseInt(sp.get("play_id"));
                   price=Float.parseFloat(sp.get("sched_ticket_price"));
                   newschedule.setStuId(stu_id);
                   newschedule.setPlayId(play_id);
                   newschedule.setSchePrice(price);
               }catch (NumberFormatException e){
                   out.print(ResponseJson.toJSONString(ResponseJson.Error,"参数有误","请检查参数"));
                   return;
               }

               if(scheds.add(newschedule) == 0){
                   init(newschedule.getSchedId(), newschedule.getStuId(), newschedule.getSchePrice());//生成票
                   out.print(ResponseJson.toJSONString(ResponseJson.Success,"添加成功",""));
                   return;
               }else{
                   out.print(ResponseJson.toJSONString(ResponseJson.Error,"数据库错误","请检查数据库设置"));
                   return;
               }
           }else{
               int sid=0;
               try{
                   sid=Integer.parseInt(sp.get("schdule_id"));
                   newschedule=scheds.Fetch("sched_id="+sid).get(0);
                   newschedule.setSchePrice(Float.parseFloat(sp.get("sched_ticket_price")));
                   newschedule.setScheTime(DateUtil.dtl2dt(sp.get("sched_time")));
                   if(scheds.modify(newschedule)>0){
                       out.print(ResponseJson.toJSONString(ResponseJson.Success,"更新成功",""));
                       return;
                   }else{
                       out.print(ResponseJson.toJSONString(ResponseJson.Error,"数据库错误","请检查数据库设置"));
                       return;
                   }
               }catch (NumberFormatException e){
                   return;
               }
           }

    }
    public void deleteItem(HashMap<String,String> sp,ScheduleSrv scheds,PrintWriter out){
//        延时 便于前端效果
//        try{
//            Thread.sleep(2000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        if(!sp.containsKey("schdule_id")){
            out.print(ResponseJson.toJSONString(ResponseJson.Error,"参数有误",""));
        }else{
            try{
                int sid=Integer.parseInt(sp.get("schdule_id"));
                if(scheds.delete(sid)>0){
                    out.print(ResponseJson.toJSONString(ResponseJson.Success,"删除成功",""));
                }else{
                    out.print(ResponseJson.toJSONString(ResponseJson.Error,"参数有误",""));
                }
            }catch (NumberFormatException e){
                out.print(ResponseJson.toJSONString(ResponseJson.Error,"参数有误",""));
                getServletContext().log("schdule_id: "+e.getMessage());
            }
        }
    }
    public void init(int sched_id, int studio_id, float price){
        StudioSrv studiosrv=new StudioSrv();

            List<Seat> seatlist = new SeatSrv().Fetch("studio_id=" + studio_id);
            StringBuffer sql=new StringBuffer("insert ticket values");
            for(Seat seat:seatlist){
                if(seat.getSeat_status()==1){
                    sql.append("(NULL,"+seat.getSeatId()+","+sched_id+","+price+","+0+",'2016-06-23 14:10:41'),");
                }
            }
            if(','==sql.charAt(sql.length()-1)){
                sql=sql.deleteCharAt(sql.length()-1);
            }
//        System.out.println("T init -> "+sql.toString());
            DBUtil db = new DBUtil();
            db.openConnection();
        try {
            db.execCommand(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
