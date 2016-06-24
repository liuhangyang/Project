package xupt.se.web;

import com.google.common.collect.Maps;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.SeatList;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.util.RequestParams;
import xupt.se.util.Resource;
import xupt.se.util.ResponseJson;

import javax.naming.directory.SearchControls;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by lc on 2016/6/16.
 */
public class SeatSl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().toString();
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

        PrintWriter out = resp.getWriter();
        HashMap<String, String> sp = RequestParams.getParams(req.getParameterMap());
        for (Object key : sp.keySet()) {
            System.out.println(key + "：" + sp.get(key));
        }

        if (curRoute.equals("update")) {   //更新
            if (sp.containsKey("studio_id") && sp.containsKey("uplist")) {
                String seats[] = sp.get("uplist").split("\\|");

                int md[][] = new int[seats.length][3], i = 0;
                for (String s : seats) {
                    String seat[] = s.split(",");
                    if (seat.length != 3) {//参数有误
                        out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确！"));
                    } else {
                        try {
                            md[i][0] = Integer.parseInt(seat[0]);
                            md[i][1] = Integer.parseInt(seat[1]);
                            md[i][2] = Integer.parseInt(seat[2]);
                            i++;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确！"));
                            return;
                        }
                    }
                }
                System.out.println("row \tcol \tstatus");
                for (int k = 0; k < seats.length; k++) {
                    new SeatSrv().modifyseat(Integer.parseInt(sp.get("studio_id")), md[k][0], md[k][1], md[k][2]);
                }

                out.print(ResponseJson.toJSONString(ResponseJson.Success, "修改成功！", ""));
            } else {//参数有误
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确！"));
            }
        } else if (curRoute.equals("init")) {//初始化
            if (sp.containsKey("studio_id")) {
                try {
                    int stu_id = Integer.parseInt(sp.get("studio_id"));
                    Studio stu = new StudioSrv().Fetch("studio_id=" + stu_id).get(0);
                    if (stu.getFlag() == 0) {
                        stu_id = Integer.parseInt(sp.get("studio_id"));
                        int t = new SeatSrv().init(stu_id);
                        out.print(ResponseJson.toJSONString(ResponseJson.Success, "初始化成功!", ""));
                    } else {
                        out.print(ResponseJson.toJSONString(ResponseJson.Success, "初始化成功!", ""));
                    }
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "初始化失败!", ""));
                }
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        String uri = req.getRequestURI().toString();
        System.out.println("uri:" + uri);
        String sn = getServletName();
        System.out.println("sn:" + sn);
        String route = uri.substring(uri.lastIndexOf(getServletName()));
        String routes[] = route.split("/");
        String curRoute = "";
        if (routes.length > 1) {
            curRoute = routes[1];

        }
        HashMap<String, String> sp = RequestParams.getParams(req.getParameterMap());
        System.out.print("yang=get");
        if (curRoute.equals("get")) {
            int stu_id = Integer.parseInt(sp.get("studio_id"));
            Studio stu = new StudioSrv().Fetch("studio_id=" + stu_id).get(0);
            if (stu.getFlag() == 0) {
                out.print(ResponseJson.toJSONString(5, "未初始化,是否初始化", ""));

            } else {

                int row = stu.getRowCount();
                int col = stu.getColCount();
                int[][] seatlist;
                //out.print(ResponseJson.toJSONString(ResponseJson.Success, "", ""));
                seatlist = new SeatSrv().getseatarray(stu_id, row, col);

//                for (int m=0;i<row;i++){
//                    for (int k=0;k<col;k++){
//                        System.out.print(a[m][k]+"\t");
//                    }
//                    System.out.println();
//                }

                Map<String, Object> context = Maps.newHashMap();

                context.put("seLt", new SeatList(seatlist, row, col));
                String template = Resource.renderTpl(
                    this.getServletContext().getRealPath("templates"), "seat_table.html", context);

                //out.print(template);
                out.print(ResponseJson.toJSONString(ResponseJson.Success, "", template));
            }

        }

    }
}
