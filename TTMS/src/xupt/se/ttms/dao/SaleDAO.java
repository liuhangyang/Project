package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iSaleDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
/**
 * Created by yang on 16-6-14.
 */
public class SaleDAO implements iSaleDAO {
    @Override
    public int insert(Sale sae) {
        try {
            String sql = "insert into sale(emp_id,sale_time,sale_payment,sale_change,sale_type,sale_status)"
                    + " values("
                    + sae.getEmpId()
                    + ",'" + sae.getdTime()
                    + "'," + sae.getpMent()
                    + "," + sae.getsChange()
                    + "," + sae.getsType()
                    + "," + sae.getStatus() + ")";
            System.out.println(sql);
            DBUtil db = new DBUtil();
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst != null && rst.first()) {
                sae.setsId(rst.getInt(1));
            }
            db.close(rst);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }


    @Override
    public int update(Sale sae){
        int rtn=0;
        try{
            String sql="update sale set "+ "emp_id="
                    +sae.getEmpId()+","+"sale_time='"
                    +sae.getdTime()+"',"+"sale_payment="
                    +sae.getpMent()+","+"sale_change="
                    +sae.getsChange()+","+"sale_type="
                    +sae.getsType()+","+"sale_status="
                    +sae.getStatus();
            sql+=" where sale_ID = "+sae.getsId();
            DBUtil db=new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rtn;
    }

    @Override
    public int delete(int ID) {
        int rtn = 0;
        try {
            String sql = "delete from  sale ";
            sql += " where sale_ID = " + ID;
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
    public List<Sale> select(String condt) {
        List<Sale> saleList=null;
        saleList=new LinkedList<Sale>();
        try{
            String sql="select sale_ID,emp_id,sale_time,sale_payment,sale_change,sale_type,sale_status from sale ";
            condt.trim();
            if (!condt.isEmpty())
                sql += " where " + condt;

            DBUtil db = new DBUtil();
            if (!db.openConnection()) {
                System.out.print("fail to connect database");
                return null;
            }
            ResultSet rst = db.execQuery(sql);
            if(rst!=null){
                while(rst.next()){
                    Sale sae=new Sale();
                    sae.setsId(rst.getInt("sale_ID"));
                    sae.setEmpId(rst.getInt("emp_id"));
                    sae.setdTime(rst.getString("sale_time"));
                    sae.setpMent(rst.getFloat("sale_payment"));
                    sae.setsChange(rst.getFloat("sale_change"));
                    sae.setsType(rst.getInt("sale_type"));
                    sae.setStatus(rst.getInt("sale_type"));
                    saleList.add(sae);
                }
            }
            db.close(rst);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return saleList;
    }

    @Override
    public Pager<Sale> selectByPage(String condt, int page, int pageSize) {
       Pager<Sale> salepager=new Pager<>(pageSize,page);
        List<Sale> saleList=null;
        saleList=new LinkedList<Sale>();
        try{
            StringBuffer sql=new StringBuffer("select sale_ID,emp_id,sale_time,sale_payment,sale_change,sale_type,sale_status from sale");
            StringBuffer countSql=new StringBuffer("select count(sale_ID) as count from sale");
            condt.trim();
            if (!condt.isEmpty()) {
                countSql.append(" where " + condt);
                sql.append(" where " + condt);
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
            salepager.setTotal(total);
            sql.append(" limit "+ (salepager.getCurPage()-1)*salepager.getPageSize()+","+salepager.getPageSize());
            ResultSet rst = db.execQuery(sql.toString());
            if(total !=0 && rst !=null){
                while(rst.next()){
                    Sale sae=new Sale();
                    sae.setsId(rst.getInt("sale_ID"));
                    sae.setEmpId(rst.getInt("emp_id"));
                    sae.setdTime(rst.getString("sale_time"));
                    sae.setpMent(rst.getFloat("sale_payment"));
                    sae.setsChange(rst.getFloat("sale_change"));
                    sae.setsType(rst.getInt("sale_type"));
                    sae.setStatus(rst.getInt("sale_status"));
                    saleList.add(sae);
                }
            }
            db.close(rst);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        salepager.setDataList(saleList);
        return salepager;

    }

    public static void main(String[] args) {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now=new Date();
        String value=sdf.format(now);
        Sale sae=new Sale();
        sae.setsId(1);
        sae.setEmpId(1);
        sae.setdTime(value);
        sae.setpMent(100.3f);
        sae.setsChange(1204.2f);
        sae.setsType(1);
        sae.setStatus(0);
        //new SaleDAO().insert(sae);
         //new SaleDAO().select("sale_ID=1");
        new SaleDAO().selectByPage("sale_ID >2 ",2,3);
    }
}
