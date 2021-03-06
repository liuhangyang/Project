package xupt.se.ttms.dao;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import xupt.se.ttms.idao.iStudioDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Studio;
import xupt.se.util.DBUtil;

public class StudioDAO implements iStudioDAO {
    public int insert(Studio stu) {
        try {
            String sql = "insert into studio(studio_name, studio_row_count, studio_col_count, studio_introduction,studio_flag )"
                             + " values('"
                             + stu.getName()
                             + "', "
                             + stu.getRowCount()
                             + ", " + stu.getColCount()
                             + ", '" + stu.getIntroduction()
                             +"',"+stu.getFlag()
                             + ")";
            DBUtil db = new DBUtil();
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst != null && rst.first()) {
                stu.setID(rst.getInt(1));
            }
            db.close(rst);
            db.close();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public int update(Studio stu) {
        int rtn = 0;
        try {
            String sql = "update studio set " + " studio_name ='"
                             + stu.getName() + "', " + " studio_row_count = "
                             + stu.getRowCount() + ", " + " studio_col_count = "
                             + stu.getColCount() + ", " + " studio_introduction = '"
                             + stu.getIntroduction() + "', " + "studio_flag="
                             +stu.getFlag();

            sql += " where studio_id = " + stu.getID();
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn = db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }


    public int delete(int ID) {
        int rtn = 0;
        try {
            String sql = "delete from  studio ";
            sql += " where studio_id = " + ID;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn = db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }
    public List<Studio> select(String condt) {
        List<Studio> stuList = null;
        stuList = new LinkedList<Studio>();
        try {
            String sql = "select studio_id, studio_name, studio_row_count, studio_col_count, studio_introduction,studio_flag from studio ";
            condt.trim(); //去掉空格两边的空格
            if (!condt.isEmpty())
                sql += " where " + condt;

            DBUtil db = new DBUtil();
            if (!db.openConnection()) {
                System.out.print("fail to connect database");
                return null;
            }
            ResultSet rst = db.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    Studio stu = new Studio();
                    stu.setID(rst.getInt("studio_id"));
                    stu.setName(rst.getString("studio_name"));
                    stu.setRowCount(rst.getInt("studio_row_count"));
                    stu.setColCount(rst.getInt("studio_col_count"));
                    stu.setIntroduction(rst.getString("studio_introduction"));
                    stu.setFlag(rst.getInt("studio_flag"));
                    stuList.add(stu);
                }
            }
            db.close(rst);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return stuList;
    }


    /**
     * @param condt 条件
     * @return
     */
    public Pager<Studio> selectByPage(String condt, int page, int pageSize) {
        Pager<Studio> stuPager = new Pager<Studio>(pageSize, page);
        List<Studio> stuList = null;
        stuList = new LinkedList<Studio>();
        try {
            StringBuffer sql = new StringBuffer("select studio_id, studio_name, studio_row_count, studio_col_count, studio_introduction from studio ");
            StringBuffer countSql = new StringBuffer("select  count(studio_id) as count from studio ");

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
            //查询总记录数
            ResultSet countS = db.execQuery(countSql.toString());
            int total = 0;
            if (countS != null) {
                countS.next();
                total = countS.getInt("count");
            }
            db.close(countS);
            stuPager.setTotal(total);
            sql.append(" limit " + (stuPager.getCurPage() - 1) * stuPager.getPageSize() + "," + stuPager.getPageSize());


            System.out.println("SQL-> " + sql);
            ResultSet rst = db.execQuery(sql.toString());
            if (total != 0 && rst != null) {
                while (rst.next()) {
                    Studio stu = new Studio();
                    stu.setID(rst.getInt("studio_id"));
                    stu.setName(rst.getString("studio_name"));
                    stu.setRowCount(rst.getInt("studio_row_count"));
                    stu.setColCount(rst.getInt("studio_col_count"));
                    stu.setIntroduction(rst.getString("studio_introduction"));
                    stuList.add(stu);
                }
            }
            db.close(rst);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        }
        stuPager.setDataList(stuList);
        return stuPager;
    }

    public static void main(String[] args) {

    }
}