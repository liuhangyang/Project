package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iEmployeeDAO;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Studio;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public class EmployeeDAO implements iEmployeeDAO {
    @Override
    public int insert(Employee emp) {
        try{
            String sql="insert into employee(emp_no,emp_name,emp_tel_num,emp_addr,emp_email,emp_passwd,emp_group)"
                    + " values('"
                    +emp.getNo()
                    +"','"+emp.getName()
                    +"','"+emp.getTel()
                    +"','"+emp.getAddr()
                    +"','"+emp.getEmail()
                    +"','"+emp.getPassword()
                    +"',"+emp.getGroup()
                    +")";
            System.out.println(sql);
            DBUtil db = new DBUtil();
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst != null && rst.first()) {
                emp.setId(rst.getInt(1));
            }
            db.close(rst);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  0;
    }

    @Override
    public int update(Employee emp) {
        int rtn=0;
        try{
            String sql="update employee set "+ "emp_no='"
                    +emp.getNo()+"',"+" emp_name='"
                    +emp.getName()+"',"+" emp_tel_num='"
                    +emp.getTel()+"',"+" emp_addr='"
                    +emp.getAddr()+"',"+" emp_passwd='"
                    +emp.getPassword()+"',"+" emp_email='"
                    +emp.getEmail()+"',"+"emp_group="
                    +emp.getGroup();
            sql +=" where emp_id =" + emp.getId();
            System.out.println(sql);
            DBUtil db=new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rtn;
    }

    @Override
    public int delete(int ID) {
        int rtn=0;
        try {
            String sql = "delete from  employee ";
            sql += " where emp_id = " + ID;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn = db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    @Override
    public List<Employee> select(String condt) {
       List<Employee> empList=null;
        empList=new LinkedList<Employee>();
        try{
            String sql = "select emp_id,emp_no,emp_name,emp_tel_num,emp_addr,emp_addr,emp_email,emp_passwd,emp_group from employee";
            condt.isEmpty();
            if(!condt.isEmpty()) {
                sql += " where " + condt;
            }
                DBUtil db = new DBUtil();
                if (!db.openConnection()) {
                    System.out.println("fail to connect database");
                    return null;
                }
                ResultSet rst = db.execQuery(sql);
                if (rst != null) {
                    while (rst.next()) {
                        Employee emp = new Employee();
                        emp.setId(rst.getInt("emp_id"));
                        emp.setNo(rst.getString("emp_no"));
                        emp.setName(rst.getString("emp_name"));
                        emp.setTel(rst.getString("emp_tel_num"));
                        emp.setAddr(rst.getString("emp_addr"));
                        emp.setEmail(rst.getString("emp_email"));
                        emp.setPassword(rst.getString("emp_passwd"));
                        emp.setGroup(rst.getInt("emp_group"));
                        empList.add(emp);
                    }
                }
                db.close(rst);
                db.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{

        }
        return empList;
    }

    @Override
    public Pager<Employee> selectByPage(String condt, int page, int pageSize) {
        Pager<Employee> empPager=new Pager<>(pageSize,page);
        List<Employee> empList=null;
        empList=new LinkedList<Employee>();
        try{
            StringBuffer sql=new StringBuffer("select emp_id,emp_no,emp_name,emp_tel_num,emp_addr,emp_addr,emp_email,emp_passwd,emp_group from employee ");
            StringBuffer countSql=new StringBuffer("select count(emp_id) as count from employee ");
            condt.trim();
            if(!condt.isEmpty()){
                countSql.append(" where "+condt);
                sql.append(" where "+condt);
            }
            DBUtil db = new DBUtil();
            if (!db.openConnection()) {
                System.out.print("fail to connect database");
                return null;
            }
            System.out.println("SQL-> " + sql);
            //查询总记录数
            ResultSet countS = db.execQuery(countSql.toString());
            int total = 0;
            if (countS != null) {
                countS.next();
                total = countS.getInt("count");
            }
            db.close(countS);
            empPager.setTotal(total);
            sql.append(" limit "+(empPager.getCurPage()-1)*empPager.getPageSize()+","+empPager.getPageSize());
            ResultSet rst = db.execQuery(sql.toString());
            if(total !=0 && rst!=null){
                while(rst.next()){
                    Employee emp = new Employee();
                    emp.setId(rst.getInt("emp_id"));
                    emp.setNo(rst.getString("emp_no"));
                    emp.setName(rst.getString("emp_name"));
                    emp.setTel(rst.getString("emp_tel_num"));
                    emp.setAddr(rst.getString("emp_addr"));
                    emp.setEmail(rst.getString("emp_email"));
                    emp.setPassword(rst.getString("emp_passwd"));
                    emp.setGroup(rst.getInt("emp_group"));
                    empList.add(emp);
                }
            }
            db.close(rst);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{

        }
        empPager.setDataList(empList);
        return empPager;
    }

    public static void main(String[] args) {
        Employee emp =new Employee();
        emp.setId(1);
        emp.setNo("yh");
        emp.setName("yang");
        emp.setTel("1234");
        emp.setAddr("cdcdd");
        emp.setEmail("1198513dddddddddddddd009");
        emp.setPassword("429ccccc245");
        emp.setGroup(1);
        //new EmployeeDAO().insert(emp);
        new EmployeeDAO().selectByPage(" emp_id >4",2,3);
    }
}
