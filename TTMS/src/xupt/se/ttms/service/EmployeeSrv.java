package xupt.se.ttms.service;

import xupt.se.ttms.dao.EmployeeDAO;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iEmployeeDAO;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Pager;

import java.util.List;

/**
 * Created by yang on 16-6-20.
 */
public class EmployeeSrv {
    private iEmployeeDAO employDAO= DAOFactory.creatEmployeeDAO();
    public int add(Employee employ){
        return  new EmployeeDAO().insert(employ);
    }
    public int modify(Employee employ){
        return  new EmployeeDAO().update(employ);
    }
    public int delete(int ID){
        return new EmployeeDAO().delete(ID);
    }
    public List<Employee> Fetch(String condt){
        return new EmployeeDAO().select(condt);
    }
    public Pager<Employee> FetchByPage(String condt, int page, int pageSize){
        if(page < 1){
            page = 1;
        }
        return new EmployeeDAO().selectByPage(condt,page,pageSize);
    }


}
