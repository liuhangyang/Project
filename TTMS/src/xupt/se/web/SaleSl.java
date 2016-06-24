package xupt.se.web;
import com.google.common.collect.Maps;
import xupt.se.config.Config;
import xupt.se.ttms.model.*;
import xupt.se.ttms.service.Sale_playlistSrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.TicketSrv;
import xupt.se.util.RequestParams;
import xupt.se.util.Resource;
import xupt.se.util.ResponseJson;
import xupt.se.util.StringUtils;

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
 * Created by yang on 16-6-21.
 */
public class SaleSl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI().toString();
        String sn = getServletName();
        String route = uri.substring(uri.lastIndexOf(getServletName()));
        String routes[] = route.split("/");
        String curRoute = "";
        //if(routes.length>3)
        //判断当前路由
        if (routes.length > 1) {
            curRoute = routes[1];
        }
        System.out.println("GET Route: " + StringUtils.join(routes));
        Sale_playlistSrv saleSrv = new Sale_playlistSrv();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        HashMap<String, String> sp = RequestParams.getFullParams(request.getParameterMap());
        if (curRoute.equals("")) {
            this.getPager(sp, saleSrv, out);

        }

    }

    @Override
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
        HashMap<String, String> sp = RequestParams.getParams((request.getParameterMap()));
        if (curRoute.equals("sticket")) {
            if (sp.containsKey("sched_id") && sp.containsKey("salelist")) {
                String sales[] = sp.get("salelist").split("\\|");

                int md[][] = new int[sales.length][2], i = 0;
                for (String s : sales) {
                    String sale[] = s.split(",");
                    if (sale.length != 2) {
                        out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确!"));
                    } else {
                        try {
                            md[i][0] = Integer.parseInt(sale[0]);
                            md[i][1] = Integer.parseInt(sale[1]);
                            i++;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确!"));
                            return;
                        }
                    }
                }
                ScheduleSrv schesrv = new ScheduleSrv();
                SeatSrv seatsrv = new SeatSrv();
                TicketSrv ticketsrv=new TicketSrv();
                Ticket ticket;
                for (int k = 0; k < sales.length; k++) {
                    Schedule sched = schesrv.Fetch("sched_id=" + sp.get("sched_id")).get(0);
                    List<Seat> seatlist = seatsrv.Fetch("studio_id=" + sched.getStuId());
                    i=0;
                    for (Object s : seatlist) {
                        Seat seat = (Seat) s;
                        if (seat.getStuId() == sched.getStuId() && seat.getsRow() == md[i][0] && seat.getScolumn() == md[i][1]) {
                            seat.setSeat_status(3);
                            seatsrv.modify(seat);
                            ticket=ticketsrv.Fetch("seat_id="+seat.getSeatId()).get(0);
                            ticket.setTicket_status(2);
                            ticketsrv.modify(ticket);
                            i++;
                            if (i >= md.length) break;
                        }
                    }
                }

                out.print(ResponseJson.toJSONString(ResponseJson.Success, "售票成功", ""));
                //添加销售记录
                //

            }
        }
    }

    public void getPager(HashMap<String, String> sp, Sale_playlistSrv saleplay, PrintWriter out) {
        Pager<Sale_playlist> list = null;
        int page = RequestParams.getInt(sp.get("p"), 1),
            pn = RequestParams.getInt(sp.get("pn"), Config.pageSize);

        System.out.println("P->  page:" + page + "  pN: " + pn);
        //取出数据
        list = saleplay.SelectByPage("", page, pn);
        if (sp.get("method").equals("hxr")) { //动态加载请求
            if (sp.get("format").equals("html")) { //html格式特殊处理
                Map<String, Object> context = Maps.newHashMap();
                context.put("page", list);
                String template = Resource.renderTpl(
                    this.getServletContext().getRealPath("templates"), "sale_table.html", context);
                out.print(template);
            } else {//默认json格式
                out.print(ResponseJson.toJSONString(list));
            }
        } else {
            Map<String, Object> context = Maps.newHashMap();
            context.put("page", list);
            String template = Resource.renderTpl(
                this.getServletContext().getRealPath("templates"), "sale.html", context);
            out.print(template);
        }
    }
}