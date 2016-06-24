package xupt.se.util;

import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.lib.filter.Filter;

/**
 * Created by lc on 2016/6/5.
 */
public class JinjavaFilters {
}
class MathOPFilter implements Filter {

    public String getName() { return "fmath"; }


    public Object filter(Object var,
                         JinjavaInterpreter interpreter,
                         String... args) {
        int num = ((Number)var).intValue();
        int num1 = 0;
        try{
            num1 = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){}

        if (args[0].equals("+")){
            num += num1;
        }else if (args[0].equals("-")){
            num -= num1;
        }
        return num;
    }
}
