package xupt.se.web;
import com.google.common.collect.Maps;
import xupt.se.config.Config;
import xupt.se.ttms.model.DataDict;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.service.DataDictSrv;
import xupt.se.ttms.service.EmployeeSrv;
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
 * Created by lc on 2016/6/20.
 */
public class employSl extends HttpServlet {
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

        EmployeeSrv employee=new EmployeeSrv();
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, String> sp = RequestParams.getFullParams(request.getParameterMap());
        if (curRoute.equals("get")) {
            this.getItem(sp,employee,out);
        } else if (curRoute.equals("")){
            this.getPager(sp,employee,out);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI().toString();
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

        EmployeeSrv employee=new EmployeeSrv();
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        HashMap<String, String> sp = RequestParams.getFullParams(req.getParameterMap());
        if (curRoute.equals("add")) {
            this.addItem(sp,employee,out,"add");
        } else if (curRoute.equals("update")) {
            this.addItem(sp,employee,out,"update");
        }else if(curRoute.equals("delete")){
            this.deltedeItem(sp,employee,out);
        }
    }

    public void getItem(HashMap<String, String> sp, EmployeeSrv empsrv, PrintWriter out) {
        if (sp.containsKey("emp_id")) {
            try {
                int emp_id = Integer.parseInt(sp.get("emp_id"));
                List<Employee> emp = empsrv.Fetch("emp_id=" + emp_id);
                if (emp.size() > 0) {
                    out.print(ResponseJson.toJSONString(emp.get(0)));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据不存在", "请检查参数是否正确"));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确!"));
        }
    }

    public void addItem(HashMap<String, String> sp, EmployeeSrv empsrv, PrintWriter out, String type) {
        if (sp.containsKey("emp_name")) {

            if (type.equals("add")) {
                Employee newemp = new Employee();
                newemp.setNo(sp.get("emp_no"));
                newemp.setName(sp.get("emp_name"));
                newemp.setEmail(sp.get("emp_email"));
                newemp.setAddr(sp.get("emp_addr"));
                newemp.setTel(sp.get("emp_tel"));
                if(sp.containsKey("emp_pass")&&sp.get("emp_pass")!=null){
                    newemp.setPassword( PasswordUtil.getMd5(sp.get("emp_pass")));
                }
                try {
                    int emp_group = Integer.parseInt(sp.get("emp_group"));
                    newemp.setGroup(emp_group);
                } catch (NumberFormatException e) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查输入!"));
                    return;
                }
                if (empsrv.add(newemp) == 0) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "添加成功", ""));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据库错误", "请检查参数"));

                }
            } else if (type.equals("update")) {
                Employee newemp = null;
                int emp_id = 0;
                try {
                    emp_id = Integer.parseInt(sp.get("emp_id"));
                    newemp = empsrv.Fetch("emp_id="+emp_id).get(0);
                } catch (NumberFormatException e) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确"));
                    return;
                }
                newemp.setName(sp.get("emp_name"));
                newemp.setEmail(sp.get("emp_email"));
                newemp.setAddr(sp.get("emp_addr"));
                newemp.setTel(sp.get("emp_tel"));
                newemp.setNo(sp.get("emp_no"));
                if(sp.containsKey("emp_pass")&&sp.get("emp_pass")!=null){
                    newemp.setPassword( PasswordUtil.getMd5(sp.get("emp_pass")));
                }
                try {
                    int emp_group = Integer.parseInt(sp.get("emp_group"));
                    newemp.setGroup(emp_group);
                } catch (NumberFormatException e) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查输入!"));
                    return;
                }

                if (empsrv.modify(newemp) > 0) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "更新成功", ""));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据库错误", "请检查数据库设置"));

                }
            }

        } else {
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确"));

        }
    }

    public void deltedeItem(HashMap<String, String> sp, EmployeeSrv empsrv, PrintWriter out) {
        if (sp.containsKey("emp_id")) {
            try {
                int emp_id = Integer.parseInt(sp.get("emp_id"));
                empsrv.delete(emp_id);
                out.print(ResponseJson.toJSONString(ResponseJson.Success, "删除成功", ""));
            } catch (NumberFormatException e) {
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查输入!"));
                return;
            }
        }else{
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查输入!"));
        }
    }

    public void getPager(HashMap<String, String> sp, EmployeeSrv empsrv, PrintWriter out) {
        Pager<Employee> list = null;
        int page = RequestParams.getInt(sp.get("p"), 1),
            pn = RequestParams.getInt(sp.get("pn"), Config.pageSize);

        System.out.println("P->  page:" + page + "  pN: " + pn);
        //取出数据
        list = empsrv.FetchByPage("", page, pn);
        DataDictSrv datasrv = new DataDictSrv();
        List<DataDict> u_group = datasrv.findByID(4); //取用户组

        if (sp.get("method").equals("hxr")) { //动态加载请求
            if (sp.get("format").equals("html")) { //html格式特殊处理
                Map<String, Object> context = Maps.newHashMap();
                context.put("page", list);
                context.put("ugroup", List2Map.putListInMap(u_group, "ug"));
                String template = Resource.renderTpl(
                    this.getServletContext().getRealPath("templates"), "employ_table.html", context);
                out.print(template);
            } else {//默认json格式
                out.print(ResponseJson.toJSONString(list));
            }
        } else {//直接请求
            Map<String, Object> context = Maps.newHashMap();
            context.put("page", list);
            context.put("ugroup", List2Map.putListInMap(u_group, "ug"));
            context.put("ug_json", ResponseJson.tojson(u_group));
            String template = Resource.renderTpl(
                this.getServletContext().getRealPath("templates"), "employee.html", context);
            out.print(template);
        }
    }
}