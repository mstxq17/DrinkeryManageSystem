package com.xq17.mwq.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
	
	// 定义链接信息
	private static final String DRIVERCLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String URL = "jdbc:sqlserver://192.168.0.23:1433;DatabaseName=db_DrinkeryManage";
	private static final String NAME = "sa";
	private static final String PASS = "sa";
	
	//解决多线程并发问题
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	//静态方法加载数据库驱动
	static {
		try {
			Class.forName(DRIVERCLASS).newInstance();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//创建数据库链接方法getConnection
	public static Connection getConnection() {
		Connection conn = threadLocal.get();
		if(conn == null) {
			try {
				conn = DriverManager.getConnection(URL, NAME, PASS);
				threadLocal.set(conn);// 将链接添加入线程,隔离
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		 return conn;
	}
	
	//关闭数据库链接方法closeConnection
	public static Boolean closeConnection() {
		Boolean isClosed = true;
		Connection conn = threadLocal.get();
		threadLocal.set(null);
		if(conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
				isClosed = false;
				e.printStackTrace();
			}
		}
		return isClosed;
	}
}
