package xupt.se.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;

import xupt.se.config.Config;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.util.RequestParams;
import xupt.se.util.Resource;
import xupt.se.util.ResponseJson;
import xupt.se.util.StringUtils;

/**
 * Created by lc on 2016/5/22.
 */
public class StudioSl extends HttpServlet {
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
        StudioSrv stus = new StudioSrv();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        if (curRoute.equals("add")) {//添加
            this.addItem(sp, stus, out, "add");
        } else if (curRoute.equals("delete")) {//删除
            this.deleteItem(sp, stus, out);
        } else if (curRoute.equals("update")) {//更新
            this.addItem(sp, stus, out, "update");
        }
        out.close();
    }

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
        System.out.println("GET Route: "+ StringUtils.join(routes));
        StudioSrv stus = new StudioSrv();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        HashMap<String, String> sp = RequestParams.getFullParams(request.getParameterMap());
        if(curRoute.equals("get")){
            this.getItem(sp, stus, out);
        }else if (curRoute.equals("")){
            this.getPager(sp, stus, out);
        }

    }

    public void getPager(HashMap<String, String> sp, StudioSrv stus, PrintWriter out){
        Pager<Studio> list = null;
        int page = RequestParams.getInt(sp.get("p"), 1),
            pn = RequestParams.getInt(sp.get("pn"), Config.pageSize);

        System.out.println("P->  page:" + page + "  pN: " + pn);
        //取出数据
        list = stus.FetchByPage("", page, pn);
        //根据请求方式参数确定返回格式
        if (sp.get("method").equals("hxr")) { //动态加载请求
            if (sp.get("format").equals("html")) { //html格式特殊处理
                Map<String, Object> context = Maps.newHashMap();
                context.put("page", list);
                String template = Resource.renderTpl(
                    this.getServletContext().getRealPath("templates"), "studio_table.html", context);
                out.print(template);
            } else {//默认json格式
                out.print(ResponseJson.toJSONString(list));
            }
        } else {//直接请求
            Map<String, Object> context = Maps.newHashMap();
            context.put("page", list);
            String template = Resource.renderTpl(
                this.getServletContext().getRealPath("templates"), "studio.html", context);
            out.print(template);
        }
    }
    public  void getItem(HashMap<String, String> sp, StudioSrv stus, PrintWriter out){
        if(sp.containsKey("studio_id")){
            try {
                int sid = Integer.parseInt(sp.get("studio_id"));
                List<Studio> stu = stus.Fetch("studio_id="+sid);
                if (stu.size() > 0) {
                    out.print(ResponseJson.toJSONString(stu.get(0)));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据不存在", ""));
                }
            } catch (NumberFormatException e) {
                //记录错误
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                getServletContext().log("Studio Error: " + e.getMessage());
            }
        }else{
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确！"));
        }
    }
    public  void addItem(HashMap<String, String> sp, StudioSrv stus, PrintWriter out, String type){
        if (sp.containsKey("studio_name") && sp.containsKey("studio_row")
                && sp.containsKey("studio_col")) {
            if (type.equals("add")) {//添加
                Studio newStudio = new Studio();
                newStudio.setName(sp.get("studio_name"));
                try {//解析行、列数
                    int col = Integer.parseInt(sp.get("studio_col"));
                    int row = Integer.parseInt(sp.get("studio_row"));
                    newStudio.setColCount(col);
                    newStudio.setRowCount(row);
                } catch (NumberFormatException e) {//参数有误，返回错误信息
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查行数、列数是否正确！"));
                    return;
                }
                newStudio.setIntroduction(sp.get("introduction"));
                if (stus.add(newStudio) > 0){
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "添加成功！", ""));
                }else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据库错误", "请检数据库设置！"));
                }
            } else if (type.equals("update")){//更新
                Studio newStudio = null;
                int sid = 0;
                try {
                    sid = Integer.parseInt(sp.get("studio_id"));
                    newStudio = stus.Fetch("studio_id="+sid).get(0);
                }catch (Exception e){
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确！"));
                    return;
                }
                newStudio.setIntroduction(sp.get("introduction"));
                if (stus.modify(newStudio) > 0){
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "更新成功！", ""));
                }else{
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据库错误", "请检数据库设置！"));
                }

            }

        } else {
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确！"));
        }
    }

    public void deleteItem(HashMap<String, String> sp, StudioSrv stus, PrintWriter out){
        //
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!sp.containsKey("studio_id")) {
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
        } else {
            try {
                int sid = Integer.parseInt(sp.get("studio_id"));
                if (stus.delete(sid) > 0) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "删除成功", ""));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据不存在", ""));
                }
            } catch (NumberFormatException e) {
                //记录错误
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                getServletContext().log("Studio Error: " + e.getMessage());
            }

        }
    }

}
