package test;

import xupt.se.ttms.model.DataDict;
import xupt.se.util.DateUtil;
import xupt.se.util.StringUtils;

import java.util.HashMap;

/**
 * Created by lc on 2016/6/16.
 */
public class StrTest {

    public static void main(String[] args) {
//        String s = "5,9,2|5,10,2|6,7,2|8,9,2|";
//
//        String ss[] = s.split("\\|");
//
//
//        // System.out.println(StringUtils.join(ss, "     "));
//
//
//        int i = 0;
//        for (String d : ss) {
//            System.out.println(d);
//            if (ss.length != ++i)
//                System.out.println("->>");
//        }
//System.out.println(StringUtils.str2HexStr("文字"));

//
//        HashMap<String,String> ss = new HashMap<String,String>(); //随机性验证
//        for (int i=0;i<100;i++) ss.put(StringUtils.getRandFilaName(), "1");
//        System.out.println(ss.size());

//        String s = "wegferhtyj.jpg";
//        int f = s.lastIndexOf('.');
//        System.out.print(f+"   "+s.substring(f));


        System.out.println(DateUtil.dtl2dt("2016-06-02T20:00"));
    }
}
