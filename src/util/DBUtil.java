package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {
	public static String db_url = "jdbc:mariadb://127.0.0.1:3306/chat";
	public static String db_usr = "my";
	public static String db_pwd = "1234";
	
	public static Connection getConn() {
		Connection conn = null;
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:/comp/env/jdbc/mariadb");
			conn = ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Statement state, Connection conn) {
		try {
			if(state != null) {
				state.close();
			}
			if(conn != null) {
				conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs, Statement state, Connection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(state != null) {
				state.close();
			}
			if(conn != null) {
				conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}