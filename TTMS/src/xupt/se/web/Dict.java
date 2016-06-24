package xupt.se.web;

import xupt.se.ttms.model.DataDict;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.DataDictSrv;
import xupt.se.util.RequestParams;
import xupt.se.util.Resource;
import xupt.se.util.ResponseJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by lc on 2016/6/19.
 */
public class Dict extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        Employee user = (Employee)session.getAttribute("user");
        PrintWriter out = resp.getWriter();
       // if(user.getGroup()==16) {

            HashMap<String, String> sp = RequestParams.getParams(req.getParameterMap());


            //
            //out.print(Resource.getHtmlstr(this.getServletContext().getRealPath("templates/dict.html")));
            out.print(Resource.renderTpl(
                    this.getServletContext().getRealPath("templates"), "dict.html", null));
       // }else{
            //System.out.println("权限不足");
           // out.print(ResponseJson.toJSONString(ResponseJson.Error, "权限不足", ""));
        //}


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        Employee user = (Employee) session.getAttribute("user");
        PrintWriter out = resp.getWriter();
       // if (user.getGroup() == 16) {
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


            HashMap<String, String> sp = RequestParams.getParams(req.getParameterMap());
            StringBuffer sb = new StringBuffer("Pra: ");
            for (String k : sp.keySet()) {
                sb.append(k + "=" + sp.get(k));
            }
            System.out.println("Pra -> " + sb.toString());
            if (curRoute.equals("add")) {//
                if (sp.containsKey("name") && sp.containsKey("pid")) {
                    DataDictSrv sr = new DataDictSrv();
                    DataDict dd = new DataDict();
                    try {
                        int pid = Integer.parseInt(sp.get("pid"));
                        dd.setSuperId(pid);
                        dd.setValue(sp.get("name"));
                        sr.add(dd);
                    } catch (NumberFormatException e) {
                        System.out.println("pid Error!");
                        return;
                    }
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "ok", ""));

                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                }

            } else if (curRoute.equals("getNodes")) {

                int pid = RequestParams.getInt(sp.get("id"), 1);
                List<DataDict> dl = null;
                DataDictSrv sr = new DataDictSrv();

                dl = sr.findByID(pid);
                StringBuffer ds = new StringBuffer("[");
                int i = 0;
                for (DataDict d : dl) {
                    ds.append(d.toNodes(sr.hasChildren(d.getId())));
                    if (dl.size() != ++i)
                        ds.append(",");
                }
                ds.append("]");
                out.print(ds.toString());


            } else if (curRoute.equals("edit")) {
                if (sp.containsKey("name") && sp.containsKey("id")) {
                    DataDictSrv sr = new DataDictSrv();
                    try {
                        int id = Integer.parseInt(sp.get("id"));
                        DataDict dd = sr.Fetch("dict_id=" + id).get(0);       //new DataDict();
                        dd.setValue(sp.get("name"));
                        sr.modify(dd);
                    } catch (NumberFormatException e) {
                        System.out.println("id Error!");
                        return;
                    }
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "ok", ""));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                }
            } else if (curRoute.equals("sort")) {
                if (sp.containsKey("sorts")) {
                    String ss = sp.get("sorts");
                    if (ss == null) {

                        return;
                    }
                    String sar[] = ss.split("\\|");
                    DataDictSrv sr = new DataDictSrv();
                    for (String k : sar) {
                        String nv[] = k.split(",");
                        if (nv.length != 2) {

                            return;
                        }
                        int index = 0;
                        try {

                            index = Integer.parseInt(nv[1]);
                        } catch (NumberFormatException e) {
                        }
                        DataDict dd = sr.Fetch("dict_id=" + nv[0]).get(0);
                        dd.setIndex(index);
                        sr.modify(dd);
                    }

                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "ok", ""));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                }
            } else if (curRoute.equals("delete")) {
                if (sp.containsKey("id")) {
                    try {
                        DataDictSrv sr = new DataDictSrv();
                        int id = Integer.parseInt(sp.get("id"));
                        sr.delete(id);
                        out.print(ResponseJson.toJSONString(ResponseJson.Success, "ok", ""));
                    } catch (NumberFormatException e) {

                    }
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                }

            } else if (curRoute.equals("deletechild")) {
                if (sp.containsKey("pid")) {
                    try {
                        DataDictSrv sr = new DataDictSrv();
                        int pid = Integer.parseInt(sp.get("pid"));
                        sr.delAllChilds(pid);
                        out.print(ResponseJson.toJSONString(ResponseJson.Success, "ok", ""));
                    } catch (NumberFormatException e) {

                    }
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                }
            }

        //}else{
          //  out.print(ResponseJson.toJSONString(ResponseJson.Error, "权限不足", ""));
        //}
    }
}
