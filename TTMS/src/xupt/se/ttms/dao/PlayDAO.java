package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iPlayDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Play;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yang on 16-6-14.
 */
public class PlayDAO implements iPlayDAO {
    @Override
    public int insert(Play play) {
        try{
            String sql="insert into play(play_type_id,play_lang_id,play_name,play_introduction,play_image,play_length,play_ticket_price,play_status)"
                    + "values("
                    +play.getpTypeId()
                    +","+play.getpLangId()
                    +",'"+play.getpName()+"','"
                    +play.getpIntro()+"','"
                    +play.getpImage()+"',"
                    +play.getpLength()
                    +","+play.getpTicketprice()
                    +","+play.getpStatus()+")";
            DBUtil db=new DBUtil();
            db.openConnection();
            ResultSet rst=db.getInsertObjectIDs(sql);
            if(rst!=null&&rst.first()){
                play.setpId(rst.getInt(1));
            }
            db.close(rst);
            db.close();
            return  1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(Play play) {
        int rtn=0;
        try{
            String sql="update play set "+ "play_type_id="
                    +play.getpTypeId()+","+"play_lang_id="
                    +play.getpLangId()+","+"play_name='"
                    +play.getpName()+"',"+"play_introduction='"
                    +play.getpIntro()+"',"+"play_image='"
                    +play.getpImage()+"',"+"play_length="
                    +play.getpLength()+","+"play_ticket_price="
                    +play.getpTicketprice()+","+"play_status="
                    +play.getpStatus();
            sql+=" where play_id="+play.getpId();
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
        try{
            String sql="delete from play ";
            sql+=" where play_id="+ID;
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
    public List<Play> select(String condt) {
        List<Play>  playList=null;
        playList=new LinkedList<Play>();
        try{
            String sql="select play_id,play_type_id,play_lang_id,play_name,play_introduction,play_image,play_length,play_ticket_price,play_status from play";
            condt.trim();
            if(!condt.isEmpty()){
                sql+=" where "+condt;
            }
            DBUtil db=new DBUtil();
            if(!db.openConnection()){
                System.out.println("fail to connect database");
                return  null;
            }
            ResultSet rst=db.execQuery(sql);
            if(rst !=null){
                while(rst.next()){
                    Play play=new Play();
                    play.setpId(rst.getInt("play_id"));
                    play.setpTypeId(rst.getInt("play_type_id"));
                    play.setpLangId(rst.getInt("play_lang_id"));
                    play.setpName(rst.getString("play_name"));
                    play.setpIntro(rst.getString("play_introduction"));
                    play.setpImage(rst.getString("play_image"));
                    play.setpLength(rst.getInt("play_length"));
                    play.setpTicketprice(rst.getFloat("play_ticket_price"));
                    play.setpStatus(rst.getInt("play_status"));
                    playList.add(play);
                }
            }
            db.close(rst);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{

        }
        return playList;
    }

    @Override
    public Pager<Play> selectByPage(String condt, int page, int pageSize) {
       Pager<Play> playPager=new Pager<>(pageSize,page);
        List<Play> playList=null;
        playList=new LinkedList<Play>();
        try{
            StringBuffer sql=new StringBuffer("select play_id,play_type_id,play_lang_id,play_name,play_introduction,play_image,play_length,play_ticket_price,play_status from play");
            StringBuffer countSql=new StringBuffer("select count(play_id) as count from play");
            condt.trim();
            if (!condt.isEmpty()) {
                countSql.append(" where " + condt);
                sql.append(" where " + condt);
            }
            DBUtil db=new DBUtil();
            if(!db.openConnection()){
                System.out.println("fail to connect database");
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
            System.out.println("total:"+total);
            db.close(countS);
            playPager.setTotal(total);
            sql.append(" limit "+(playPager.getCurPage()-1)*playPager.getPageSize()+","+playPager.getPageSize());
            ResultSet rst = db.execQuery(sql.toString());
            if(total!=0 && rst!=null){
                while(rst.next()){
                    Play play=new Play();
                    play.setpId(rst.getInt("play_id"));
                    play.setpTypeId(rst.getInt("play_type_id"));
                    play.setpLangId(rst.getInt("play_lang_id"));
                    play.setpName(rst.getString("play_name"));
                    play.setpIntro(rst.getString("play_introduction"));
                    play.setpImage(rst.getString("play_image"));
                    play.setpLength(rst.getInt("play_length"));
                    play.setpTicketprice(rst.getFloat("play_ticket_price"));
                    play.setpStatus(rst.getInt("play_status"));
                    playList.add(play);
                }
            }
            db.close(rst);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{

        }
        playPager.setDataList(playList);
        return playPager;
    }

    public static void main(String[] args) {
        Play play=new Play();
        play.setpId(4);
        play.setpTypeId(5);
        play.setpLangId(5);
        play.setpName("李成傻逼");
        play.setpIntro("这是一个test");
        play.setpImage("/etc/passwd");
        play.setpLength(1200);
        play.setpTicketprice(34.7f);
        play.setpStatus(1);
       //new PlayDAO(t).insert(play);
        //new PlayDAO().update(play);
        //new PlayDAO().delete(5);
        //List<Play> te=new  LinkedList<Play>();
        //te=new PlayDAO().select("play_id > 6");
        //for(Play a:te){
          //  System.out.println(a.getpName());
        //}
        Pager playq= new PlayDAO().selectByPage("play_id>1",2,3);
        for(Object s:playq.getDataList()){
            System.out.println(((Play)s).getpName());
        }
    }

}
