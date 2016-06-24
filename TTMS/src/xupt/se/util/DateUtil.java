package xupt.se.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by lc on 2016/6/22.
 */
public class DateUtil {


    public static String dtl2dt(String dateStr) {
        String ds = dateStr.replace('T',' ');
        return ds;
    }

    public static String dt2dtl(String dateStr) {
        String ds = dateStr.replace(' ','T');
        return ds;
    }
}
