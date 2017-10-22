package com.music163.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlConnect {
	//MYSQL数据库连接
	private static final String DBDRIVER = "com.mysql.jdbc.Driver" ;			
    private static final String DBURL = "jdbc:mysql://localhost:3306/db_music";
    private static final String DBUSER = "www" ;								
    private static final String DBPASSWORD = "www";							
	public static Connection getConnection(){
		Connection conn = null;													
		try {
			Class.forName(DBDRIVER);											
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);		
		} catch (ClassNotFoundException e) {									
			e.printStackTrace();										
		} catch (SQLException e) {												
			e.printStackTrace();
		}
		return conn;
	}
	public static void close(Connection conn) {
		if(conn != null) {				
			try {
				conn.close();			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(PreparedStatement pstmt) {
		if(pstmt != null) {				
			try {
				pstmt.close();			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs) {
		if(rs != null) {				
			try {
				rs.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
