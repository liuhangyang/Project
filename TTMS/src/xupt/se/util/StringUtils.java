package xupt.se.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lc on 2016/6/1.
 */
public class StringUtils {
    /**
     * 序列化字符串数组
     * @param arg 字符串数组
     * @param splitChar 分隔符
     * @return
     */
    public static String join(String[] arg, String splitChar){
        StringBuffer str = new StringBuffer();
        for (String s:arg) {
            str.append(s);
            str.append(splitChar);
        }
        return str.toString();
    }
    public static String join(String[] arg ){
        return StringUtils.join(arg,",");
    }

    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer();
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            sb.append("0x");
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    public static String getRandFilaName(){
        //时间 +4位随机数
        StringBuffer sb = new StringBuffer();
        int rand = (int)(Math.random()*9000+1000);
        sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        sb.append(rand);
        return sb.toString();
    }
}
