package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iSale_itemDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Sale_item;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by yang on 16-6-14.
 */
public class Sale_itemDAO implements iSale_itemDAO {
    @Override
    public int insert(Sale_item item) {
        try{
            String sql="insert into sale_item(ticket_id,sale_id,sale_item_price)"
                    +" values("
                    +item.gettId()
                    +","+item.getSaleId()
                    +","+item.getSaleItemprice()
                    +")";

            System.out.println(sql);
            DBUtil db = new DBUtil();
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst != null && rst.first()) {
                item.setsItemid(rst.getInt(1));
            }
            db.close(rst);
            db.close();
            return 1;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Sale_item item) {
        int rtn=0;
        try{
            String sql="update sale_item set "+"ticket_id="
                    +item.gettId()+","+"sale_ID="
                    +item.getSaleId()+","+"sale_item_price="
                    +item.getSaleItemprice();
            sql+=" where sale_item_id= "+item.getsItemid();
            DBUtil db= new DBUtil();
            db.openConnection();
            rtn = db.execCommand(sql);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rtn;
    }

    @Override
    public int delete(int ID) {
        int rtn=0;
        try{
            String sql="delete from sale_item ";
            sql+=" where sale_item_id="+ID;
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
    public List<Sale_item> select(String condt) {
        List<Sale_item> sitemList=new LinkedList<Sale_item>();
        try{
            String sql="select sale_item_id,ticket_id,sale_ID,sale_item_price from sale_item ";
            condt.trim();
            if(!condt.isEmpty()) {
                sql += " where " + condt;
            }
            DBUtil db = new DBUtil();
            if (!db.openConnection()) {
                System.out.print("fail to connect database");
                return null;
            }
            ResultSet rst = db.execQuery(sql);
            if(rst!=null){
                while(rst.next()){
                    Sale_item item=new Sale_item();
                    item.setsItemid(rst.getInt("sale_item_id"));
                    item.settId(rst.getInt("ticket_id"));
                    item.setSaleId(rst.getInt("sale_ID"));
                    item.setSaleItemprice(rst.getLong("sale_item_price"));
                    sitemList.add(item);
                }
            }
            db.close(rst);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return sitemList;
    }

    @Override
    public Pager<Sale_item> selectByPage(String condt, int page, int pageSize) {
        Pager<Sale_item> itemPager=new Pager<>(pageSize,page);
        List<Sale_item> itemList=null;
        itemList=new LinkedList<Sale_item>();
        try{
            StringBuffer sql=new StringBuffer("select sale_item_id,ticket_id,sale_ID,sale_item_price from sale_item ");
            StringBuffer countSql=new StringBuffer("select count(sale_item_id) as count from sale_item");
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
            itemPager.setTotal(total);
            sql.append(" limit "+(itemPager.getCurPage()-1) * itemPager.getPageSize()+","+itemPager.getPageSize());
            ResultSet rst = db.execQuery(sql.toString());
            if(total!=0&&rst!=null){
                while(rst.next()){
                    Sale_item item=new Sale_item();
                    item.setsItemid(rst.getInt("sale_item_id"));
                    item.settId(rst.getInt("ticket_id"));
                    item.setSaleId(rst.getInt("sale_ID"));
                    item.setSaleItemprice(rst.getLong("sale_item_price"));
                    itemList.add(item);
                }
            }
            db.close(rst);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        itemPager.setDataList(itemList);

        return itemPager;
    }

    public static void main(String[] args) {
        Sale_item sitem=new Sale_item();
        //sitem.setsItemid(1);
        sitem.settId(7);
        sitem.setSaleId(6);
        sitem.setSaleItemprice(2388);
        //new Sale_itemDAO().insert(sitem);
        //new Sale_itemDAO().update(sitem);
       //new Sale_itemDAO().select("sale_item_id >3");
       //new  Sale_itemDAO().selectByPage("sale_item_id >3",2,3);
    }
}
