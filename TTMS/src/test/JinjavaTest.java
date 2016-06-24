package test;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.lib.filter.Filter;

import java.util.Map;

/**
 * Created by lc on 2016/6/5.
 */
public class JinjavaTest {
    public static void main(String[] args){
        Map<String, Object> context = Maps.newHashMap();
        Jinjava jin = new Jinjava();
        jin.getGlobalContext().registerFilter(new ConcatFilter());

        context.put("str", 1);
        String resstr = jin.render("abcd {{ 5|fmath('+', 10)  }}",context);
        System.out.println(resstr);
       // AddFilter
    }
}

class ConcatFilter implements Filter {

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