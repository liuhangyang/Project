package ystruct.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by yang on 15-12-27.
 */


public class IsSameFile {
    public static String getFileMD5(File file){
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest =MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
  public static  String[] changStringToByte(String s){
      String[] md5= new String[16];
      int count=8;
      int begin=0;
      for(int i=0;i<16;i++){
          if(i==15){
              count =s.length();
          }
          md5[i]=s.substring(begin,count);
          begin=count;
          count=count+8;
      }
     /* for(int i=0;i<8;i++){
        System.out.println(md5[i]);
      }*/
      return md5;
  }

   /* public static void main(String[] args) {
        File f =new File("1221");
        String s = getFileMD5(f);
        System.out.println(s);
    }*/

}
