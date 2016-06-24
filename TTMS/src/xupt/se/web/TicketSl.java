package xupt.se.web;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.TicketSrv;
import xupt.se.util.RequestParams;
import xupt.se.util.Resource;
import xupt.se.util.ResponseJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by yang on 16-6-21.
 */
public class TicketSl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String template = Resource.renderTpl(
            this.getServletContext().getRealPath("templates"), "re_ticket.html", null);
        out.print(template);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        HashMap<String, String> sp = RequestParams.getParams(request.getParameterMap());
        TicketSrv ticketSrv = new TicketSrv();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (curRoute.equals("rticket")){
            this.rItem(sp, ticketSrv, out);
        }

    }

    public void rItem(HashMap<String, String> sp, TicketSrv ticketsrv, PrintWriter out) {
        if (sp.containsKey("ticket_id")) {
            SeatSrv seatsrv = new SeatSrv();
            Seat seat = new Seat();
            Ticket ticket = null;
            try {
                ticket = ticketsrv.Fetch("ticket_id=" + sp.get("ticket_id")).get(0);
                if(ticket.getTicket_status() == 2){

                    ticket.setTicket_status(0);
                    ticketsrv.modify(ticket);
                    seat = seatsrv.Fetch("seat_id=" + ticket.getSeat_id()).get(0);
                    seat.setSeat_status(1);
                    seatsrv.modify(seat);
                    out.println(ResponseJson.toJSONString(ResponseJson.Success,"退票成功",""));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            out.println(ResponseJson.toJSONString(ResponseJson.Error,"票不存在",""));
        }
    }
}

