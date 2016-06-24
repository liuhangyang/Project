package test;

import xupt.se.ttms.model.DataDict;
import xupt.se.ttms.service.DataDictSrv;
import xupt.se.web.Dict;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by lc on 2016/6/19.
 */
public class DictTest {
    public static void main(String[] args) {

        List<DataDict> dl = null;
        DataDictSrv sr = new DataDictSrv();

        dl = sr.findByID( 1);
        //System.out.println(sr.hasChildren(5));
        for (DataDict d : dl) {
            System.out.println(d);
        }

    }
}
