package xupt.se.util;

import xupt.se.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lc on 2016/5/24.
 */
public class RequestParams {

    public static HashMap<String, String> getParams(Map<String, String[]> params){
        HashMap<String, String> sp = new HashMap<String, String>();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (values.length > 0) {
                sp.put(key, values[0]);
            }
        }
        return sp;
    }

    public static HashMap<String, String> getFullParams(Map<String, String[]> params){
        HashMap<String, String> sp = new HashMap<String, String>();
        //填充默认数据
        sp.put("format","json");
        sp.put("method","default");
        sp.put("p", "1");
        sp.put("pn", String.valueOf(Config.pageSize));

        //sp.put("","");sp.put("","");sp.put("","");sp.put("","");

        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (values.length > 0 && !values[0].equals("")) {//只接受第一个参数，参数不能为空
                sp.put(key, values[0]);
            }
        }
        return sp;
    }

    /**
     * 解析字符串为数字，如果出错返回默认值
     * @param s 字符串
     * @param defaultValue 默认值
     * @return
     */
    public static int getInt(String s, int defaultValue){
        int p = defaultValue;
        try {
            p = Integer.parseInt(s);
        } catch (NumberFormatException e) {}
        return p;
    }

}
