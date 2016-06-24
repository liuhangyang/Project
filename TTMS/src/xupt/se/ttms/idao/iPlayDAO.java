package xupt.se.ttms.idao;

import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Play;

import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public interface iPlayDAO {
    public int insert(Play play);
    public int update(Play play);
    public int delete(int ID);
    public List<Play> select(String condt);
    public Pager<Play> selectByPage(String condt, int page, int pageSize);
}
