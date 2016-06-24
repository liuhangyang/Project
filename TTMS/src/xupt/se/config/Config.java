package xupt.se.config;

/**
 * Created by lc on 2016/5/22.
 */
public final class Config {
    //msql相关
    public final static String driver = "com.mysql.jdbc.Driver";
    public final static String url = "jdbc:mysql://localhost/ttms?useUnicode=true&characterEncoding=utf-8&useSSL=true";
    public final static String username = "root";
    public final static String password = "429256";

    public final static int pageSize = 5;

    public final static int expTime = 20 * 60 * 60; //session过期时间
    public final static String FileUploadPath = "upload/";
}
