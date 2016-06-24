package xupt.se.ttms.service;

import java.util.List;

import xupt.se.config.Config;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iStudioDAO;
import xupt.se.ttms.model.Pager;
import xupt.se.ttms.model.Studio;

public class StudioSrv {
	private iStudioDAO stuDAO=DAOFactory.creatStudioDAO();
	
	public int add(Studio stu){
		return stuDAO.insert(stu); 		
	}
	
	public int modify(Studio stu){
		return stuDAO.update(stu); 		
	}
	
	public int delete(int ID){
		return stuDAO.delete(ID); 		
	}
	
	public List<Studio> Fetch(String condt){
		return stuDAO.select(condt);		
	}

	public Pager<Studio> FetchByPage(String condt,int page, int pageSize){
		if(page < 1){
            page = 1;
        }
		return stuDAO.selectByPage(condt,page,pageSize);
	}


	public List<Studio> FetchAll(){
		return stuDAO.select("");		
	}
}
