package xupt.se.ttms.service;

import xupt.se.ttms.dao.PlayDAO;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iPlayDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Play;


import java.util.List;

/**
 * Created by yang on 16-6-19.
 */
public class PlaySrv {
    private iPlayDAO playDAO=DAOFactory.createPlayDAO();
    public int add(Play play){
        return  new PlayDAO().insert(play);
    }
    public int modify(Play play){
        return  new PlayDAO().update(play);
    }
    public int delete(int ID){
        return new PlayDAO().delete(ID);
    }
    public List<Play> Fetch(String condt) {
        return new PlayDAO().select(condt);
    }
    public Pager<Play> FetchByPage(String condt, int p, int pn) {
        return new PlayDAO().selectByPage(condt, p, pn);
    }
}
