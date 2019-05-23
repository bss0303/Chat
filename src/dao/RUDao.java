package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBUtil;

public class RUDao {
	//增
	public boolean add(int id, int rid) {
		String sql = "insert into ru(id, rid) values("+id+","+rid+");";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		
		try {
			state = conn.createStatement();
			if(state.executeUpdate(sql) > 0)
				f = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(state, conn);
		}
		return f;
	}
	
	//删
	public boolean delete(int id, int rid) {
		String sql = "delete from ru where id="+id+" and rid="+rid;
		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		
		try {
			state = conn.createStatement();
			if(state.executeUpdate(sql) > 0)
				f = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(state, conn);
		}
		return f;
	}
}
