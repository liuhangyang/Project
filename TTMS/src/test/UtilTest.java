package test;

import xupt.se.util.PasswordUtil;
import xupt.se.util.RequestParams;

/**
 * Created by lc on 2016/5/31.
 */
public class UtilTest {

    public static void  main(String args[]) {
        //String s="102";
        //System.out.println(RequestParams.getInt(s,2));
        //ea8a706c4c34a168
        //EA8A706C4C34A168
        //827ccb0eea8a706c4c34a16891f84e7b


       String a = PasswordUtil.getMd5("123456");
        System.out.println(a);
//        String a1 =  PasswordUtil.getMd5_16("12345");
//        System.out.println(a+ "  "+a.equalsIgnoreCase("EA8A706C4C34A168"));
//        System.out.println(a1+"  "+a1.equalsIgnoreCase("EA8A706C4C34A168"));
//        System.out.println("MD5: "+ a+"\n"+ PasswordUtil.checkPassword("12345",a));




    }




}
