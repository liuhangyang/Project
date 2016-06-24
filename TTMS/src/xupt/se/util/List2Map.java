package xupt.se.util;

import com.google.common.collect.Maps;
import xupt.se.ttms.model.DataDict;

import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2016/6/21.
 */
public class List2Map {

    public static Map<String, Object> putListInMap(List<DataDict> list, String keyPrefix) {
        Map<String, Object> map =  Maps.newHashMap();
        if(keyPrefix == null) keyPrefix = "";
        for (DataDict d:list){
            map.put(keyPrefix+d.getId(), d.getValue());
        }

        return map;
    }
}
