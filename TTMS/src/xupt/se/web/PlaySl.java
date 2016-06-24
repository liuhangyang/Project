package xupt.se.web;

import com.google.common.collect.Maps;
import xupt.se.config.Config;
import xupt.se.ttms.model.DataDict;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Play;
import xupt.se.ttms.service.DataDictSrv;
import xupt.se.ttms.service.PlaySrv;
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
public class PlaySl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI().toString();
        String sn = getServletName();
        String route = uri.substring(uri.lastIndexOf(getServletName()));
        String routes[] = route.split("/");
        String curRoute = "";
        if (routes.length > 1) {
            curRoute = routes[1];
        }
        HashMap<String, String> sp = RequestParams.getParams(request.getParameterMap());
        PlaySrv plays = new PlaySrv();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (curRoute.equals("add")) {  //添加
            this.addItem(sp, plays, out, "add");
        } else if (curRoute.equals("delete")) {//删除
            this.deleteItem(sp, plays, out);
        } else if (curRoute.equals("update")) { //更新
            this.addItem(sp, plays, out, "update");
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
        System.out.println("GET Route: " + StringUtils.join(routes));
        PlaySrv plays = new PlaySrv();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        HashMap<String, String> sp = RequestParams.getFullParams(request.getParameterMap());
        if (curRoute.equals("get")) {
            this.getItem(sp, plays, out);
        } else if (curRoute.equals("")) {
            this.getPager(sp, plays, out);
        } else if (curRoute.equals("serch")) {
        }
        out.close();
    }

    public void getPager(HashMap<String, String> sp, PlaySrv play, PrintWriter out) {
        Pager<Play> list = null;
        List<DataDict> play_type, play_lang;
        DataDictSrv datasrv = new DataDictSrv();
        play_type = datasrv.findByID(2);   //剧目类型
        play_lang = datasrv.findByID(3);
        int page = RequestParams.getInt(sp.get("p"), 1);
        int pn = RequestParams.getInt(sp.get("pn"), Config.pageSize);

        list = play.FetchByPage("", page, pn);
        System.out.println("P-> page:" + page + "pN:" + pn + "Total: " + list.getTotal());
        System.out.println("PlaySL-> play_type " + play_type.size());
        //根据请求方式参数确定返回格式
        if (sp.get("method").equals("hxr")) {
            if (sp.get("format").equals("html")) {
                Map<String, Object> context = Maps.newHashMap();
                context.put("page", list);
                context.put("ptmap", List2Map.putListInMap(play_type, "pt"));
                context.put("plmap", List2Map.putListInMap(play_lang, "pl"));
                String template = Resource.renderTpl(
                    this.getServletContext().getRealPath("templates"), "play_table.html", context);
                out.print(template);
            } else {//默认json格式
                out.print(ResponseJson.toJSONString(list));
            }
        } else { //直接请求,当在地址栏里输入的是uri时.
            Map<String, Object> context = Maps.newHashMap();
            context.put("page", list);
            context.put("ptmap", List2Map.putListInMap(play_type, "pt"));
            context.put("plmap", List2Map.putListInMap(play_lang, "pl"));

            context.put("play_type", ResponseJson.tojson(play_type));
            context.put("play_lang", ResponseJson.tojson(play_lang));

            String template = Resource.renderTpl(
                this.getServletContext().getRealPath("templates"), "play.html", context);
            out.print(template);
        }
    }

    public void getItem(HashMap<String, String> sp, PlaySrv plays, PrintWriter out) {
        if (sp.containsKey("play_id")) {
            System.out.println("Play/get-> "+ sp.get("play_id"));
            try {
                int pid = Integer.parseInt(sp.get("play_id"));
                List<Play> play = plays.Fetch("play_id=" + pid);
                if (play.size() > 0) {
                    out.print(ResponseJson.toJSONString(play.get(0)));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据不存在", ""));
                }
            } catch (NumberFormatException e) {
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数错误", ""));
            }

        } else {
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数是否正确！"));
        }
    }

    public void addItem(HashMap<String, String> sp, PlaySrv plays, PrintWriter out, String type) {
        int pid = 0;

        Play newplay = null;

        if (type.equals("add")) {
            newplay = new Play();
        }else{
            try {
                pid = Integer.parseInt(sp.get("play_id"));
            } catch (Exception e) {
                return;
            }
            newplay = plays.Fetch("play_id="+pid).get(0);
            }
            newplay.setpName(sp.get("play_name"));
            newplay.setpIntro(sp.get("play_introduction"));
            newplay.setpImage(sp.get("play_image"));
            try {
                newplay.setpTypeId(Integer.parseInt(sp.get("play_type")));
                newplay.setpLangId(Integer.parseInt(sp.get("play_lang")));
                newplay.setpLength(Integer.parseInt(sp.get("play_length")));
                newplay.setpTicketprice(Float.parseFloat(sp.get("play_ticket_price")));
                //newplay.setpStatus(Integer.parseInt(sp.get("play_status")));

            } catch (Exception e) {
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", "请检查参数！"));
                return;
            }
            if (type.equals("add")) {
                if (plays.add(newplay) > 0) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "添加成功", ""));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据库错误", "请检查参数是否正确"));

                }
            } else if (type.equals("update")) {

                if (plays.modify(newplay) > 0) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "更新成功", ""));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据库错误", "请检查数据库设置"));
                }
            } else {
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据库错误", "请检查数据库设置"));

            }

    }

    public void deleteItem(HashMap<String, String> sp, PlaySrv plays, PrintWriter out) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!sp.containsKey("play_id")) {
            out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
        } else {
            try {
                int pid = Integer.parseInt(sp.get("play_id"));
                if (plays.delete(pid) > 0) {
                    out.print(ResponseJson.toJSONString(ResponseJson.Success, "删除成功", ""));
                } else {
                    out.print(ResponseJson.toJSONString(ResponseJson.Error, "数据不存在", ""));

                }
            } catch (Exception e) {
                out.print(ResponseJson.toJSONString(ResponseJson.Error, "参数有误", ""));
                getServletContext().log("play Error:" + e.getMessage());
            }
        }

    }

    public void search(HashMap<String, String> sp, PlaySrv plays, PrintWriter out) {

    }

}
