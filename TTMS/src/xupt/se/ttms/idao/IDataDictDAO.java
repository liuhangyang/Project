package xupt.se.ttms.idao;

/**
 * Created by lc on 2016/6/17.
 */

import java.util.List;

import xupt.se.ttms.model.DataDict;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Studio;

public interface IDataDictDAO {
    public int insert(DataDict ddict);

    public int update(DataDict ddict);

    public int delete(int ID);

    public int deleteChilds(int pid);

    public List<DataDict> select(String condt);

    public List<DataDict> findByID(int id);

    public void findAllSonByID(List<DataDict> list, int id);

    public boolean hasChildren(int id);
}
