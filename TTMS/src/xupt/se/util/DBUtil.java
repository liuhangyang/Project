package xupt.se.util;

import xupt.se.config.Config;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties; 

public class DBUtil {
	private Connection conn=null;
	private String dbDriver;	//定义驱动  
    private String dbURL;		//定义URL  
    private String userName;	//定义用户名  
    private String password;	//定义密码	
    
    //从配置文件取数据库链接参数
	//web 程序中，将它改为从配置类里读取
    private void loadConnProperties(){
        this.dbDriver = Config.driver;
		this.dbURL = Config.url;
        this.userName = Config.username;
        this.password = Config.password;
     
    }
    
	public boolean openConnection(){
		try {  
			loadConnProperties();
            Class.forName(dbDriver);  
            this.conn = DriverManager.getConnection(dbURL,userName,password);
            return true;
        } catch(ClassNotFoundException classnotfoundexception) {  
              classnotfoundexception.printStackTrace();  
            System.err.println("db: " + classnotfoundexception.getMessage());  
        } catch(SQLException sqlexception) {  
            System.err.println("db.getconn(): " + sqlexception.getMessage());  
        }
		return	false;
	}
	
	
	protected void finalize() throws Exception{
		try {
		if(null!=conn)
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
     }
	
	// 查询并得到结果集
	public ResultSet execQuery(String sql) throws Exception {
		ResultSet rstSet = null;
		try {
			if (null == conn)
				throw new Exception("Database not connected!");
			Statement stmt = conn.createStatement();
			rstSet = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rstSet;
	}

	// 插入一条新纪录，并获取标识列的值
	public ResultSet getInsertObjectIDs(String insertSql) throws Exception{
		ResultSet rst = null;
		try {
			if(null==conn)
				throw new Exception("Database not connected!");
			
			Statement stmt = conn.createStatement();
			
			stmt.executeUpdate(insertSql, Statement.RETURN_GENERATED_KEYS);
			rst = stmt.getGeneratedKeys();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rst;
	}

	// 插入、更新、删除
	public int execCommand(String sql) throws Exception{
		int flag = 0;
		try {
			if(null==conn)
				throw new Exception("Database not connected!");
			
			Statement stmt = conn.createStatement();
			flag = stmt.executeUpdate(sql);
			
			stmt.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 释放资源
	public void close(ResultSet rst) throws Exception {
		try {
			Statement stmt = rst.getStatement();
			rst.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	// 释放资源
	public void close(Statement stmt) throws Exception {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 释放资源
	public void close() throws SQLException, Exception{
		if(null!=conn){
			conn.close();
			conn=null;
		}
	}

}
