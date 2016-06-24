package xupt.se.web;

import xupt.se.config.Config;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.EmployeeSrv;
import xupt.se.util.PasswordUtil;
import xupt.se.util.Resource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lc on 2016/6/14.
 */
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        Employee user = (Employee)session.getAttribute("user");
        if (!(user==null)){
            //out.print("   "+user);
            resp.sendRedirect("studio");
        }else {
            out.print(Resource.renderTpl(
                this.getServletContext().getRealPath("templates"),"login.html", null));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        session.setMaxInactiveInterval(Config.expTime);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        String uname = req.getParameter("username");
        System.out.println(uname);
        String upass = req.getParameter("password");
        String refurl = req.getParameter("ref");
        refurl = (refurl == null)?"studio":refurl;
        if(uname != null && !uname.isEmpty()){
            EmployeeSrv empsrv = new EmployeeSrv();
            try {
                Employee employee = empsrv.Fetch("emp_name='"+uname+"'").get(0);
                if (PasswordUtil.checkPassword(upass, employee.getPassword())){
                    session.setAttribute("user", employee);
                    out.print("{\"code\": 0,\"locurl\":\""+ refurl +"\"}");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            out.print("{\"code\": 2,\"err\":\"用户名或密码错误！\"}");
            return;
        }

        out.print("{\"code\": 2,\"err\":\"用户名或密码错误！\"}");
    }
}
