package xupt.se.util;

import com.google.gson.Gson;

import javax.swing.*;

/**
 * Created by lc on 2016/5/23.
 */
public class ResponseJson {
    /**
     * 默认字符编码
     */
    private static String encoding = "UTF-8";

    /**
     * JSONP默认的回调函数
     */
    private static String callback = "callback";

    /**
     * 状态码
     */
    public static int Error = 2;
    public static int Success = 0;

    /**
     * 把Java对象JSON序列化
     *
     * @param obj 需要JSON序列化的Java对象
     * @return JSON字符串
     */
    public static String toJSONString(int code, String msg, Object obj) {
        JsonTpl json = new JsonTpl(code, msg, obj);
        System.out.println(json);
        System.out.println("------------------------------");
        return new Gson().toJson(json);
    }

    public static String toJSONString(Object obj) {

        return toJSONString(ResponseJson.Success, "ok", obj);
    }

    public static String tojson(Object o) {
        return new Gson().toJson(o);
    }

    /**
     * code, String msg
     * 返回JSONP数据
     *
     * @param callback JSONP回调函数名称
     * @param data     JSONP数据
     */
    public static String jsonp(int code, String msg, String callback, Object data) {
        StringBuffer sb = new StringBuffer(callback);
        sb.append("(");
        sb.append(toJSONString(code, msg, data));
        sb.append(");");
        return sb.toString();
    }

    /**
     * 返回JSONP数据,使用默认回调函数
     *
     * @param data JSONP数据
     */
    public static String jsonp(Object data) {
        return jsonp(ResponseJson.Success, "ok", callback, data);
    }
}


class JsonTpl {
    private int code;
    private String msg;
    private Object data;

    public JsonTpl(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String toString() {
        return "JSON " + code + "   >> " + msg;
    }
}
