package test;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by lc on 2016/6/21.
 */
public class MapsTest {

    public static void main(String[] args) {
        Map<String, Object> context = Maps.newHashMap();
        context.put("11", "list");
        context.put("12", "lhgfjh");
        context.put("13", "ljty");
System.out.println(context.get("12"));

    }
}
