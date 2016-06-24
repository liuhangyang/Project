package xupt.se.ttms.idao;

import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Studio;

import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public interface iEmployeeDAO {
    public int insert(Employee emp);
    public int update(Employee emp);
    public int delete(int ID);
    public List<Employee> select(String condt);
    public Pager<Employee> selectByPage(String condt, int page, int pageSize);
}
