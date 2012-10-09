package com.fordays.fdpay.blackscreen;

import java.sql.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.*;
public class ConAccess {
    
    
    private Connection connection;
    private Statement statement; 
    private ResultSet resultSet; 
    private ResultSetMetaData rsMetaData;

    /** Creates a new instance of ConMysql */
    
    
    public ConAccess() { 
        //使用getConnection()方法建立连接ODBC的对象
        //注意数据库的位置是放在当前工程目录下的
        //String url="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=D:\\文件夹\\test.mdb";   
   // 	String url="jdbc:access:/D:/test.mdb";   
    	String url = "jdbc:rmi://192.168.1.89:1234/jdbc:access:/D:/test.mdb";
        connectToMysql(url);
    }
    
    private void connectToMysql(String url){
        //加载驱动程序以连接数据库            
       
				try {
					try {
						Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("创建实例失败");
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("非法的Access");
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					System.out.println("加载jdbc驱动程序失败!");
					e1.printStackTrace();
					
				}
				 try {
					connection = DriverManager.getConnection(
					            url,"","");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("链接access数据库失败");
				}
			
           
           
            
        
       
        try{
        	System.out.println("链接access数据成功");
            statement = connection.createStatement(); 
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void query(String sql){
        try{                   
            resultSet = statement.executeQuery( sql ); 
        }catch ( SQLException sqlex ) { 
            sqlex.printStackTrace(); 
       }

    }
    
    public boolean exesql(String sql){
        try{                   
            boolean res = statement.execute(sql); 
            return res;
        }catch ( SQLException sqlex ) { 
            sqlex.printStackTrace(); 
            return false;
       }        
    }
    
    public boolean fetch_next(){
        try{
            //定位到达第一条记录
            boolean moreRecords = resultSet.next(); 
            //如果没有记录，则提示一条消息
            if ( ! moreRecords ) { 
                return false; 
            } 
        }catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        return true;
    }
    
    public String get(String name){
        try{
            
            return resultSet.getString(name);
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
    
    
    public void shutDownRes(){
        try{
            resultSet.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void shutDownCon(){
        try{
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
                   
        }
    }
    
    public static void main(String[] args){
    	ConAccess con= new ConAccess();
    	con.shutDownCon();
    	//添加
//    	String sql="insert into userInfo(name,email) values('5646546','zwwlmzy@qq.com');";
//    	System.out.println(con.exesql(sql));
    	//查询
//    	String sql ="select * from userInfo";
//    	con.query(sql);
//    	con.fetch_next();
//    	System.out.println(con.get("name"));
    	//修改
//    	String sql ="update userInfo set name='张大哥哈哈' where id=1  ";
//    	System.out.println(con.exesql(sql));
    	
    }
    
}
