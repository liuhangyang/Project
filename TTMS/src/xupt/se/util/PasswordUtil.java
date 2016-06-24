package xupt.se.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lc on 2016/6/3.
 */
public class PasswordUtil {
    /**
     * md5加密
     * @param str
     * @return 32位md5小写
     */
    public static String getMd5(String str) {
        if (str == null || str.isEmpty()) return null;
        MessageDigest md5 = null;
        byte md5byte[] = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5byte = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!md5byte.equals(null)){
            StringBuffer sb = new StringBuffer();
            for (byte b: md5byte) {
                sb.append(Integer.toHexString(b&0xff));
            }
            return sb.toString();
        }
        return null;
    }
    public static String getMd5_16(String str) {
        return PasswordUtil.getMd5(str).substring(7,23);
    }


    public static boolean checkPassword(String pwdStr, String md5Pwd) {
        String pwd = null;

        pwd = PasswordUtil.getMd5(pwdStr);

        if (pwd.equals(md5Pwd)) {
            return true;
        }
        return false;
    }
}
