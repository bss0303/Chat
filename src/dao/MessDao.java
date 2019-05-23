package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Message;
import util.DBUtil;

public class MessDao {
	//房间增加聊天记录
	public boolean add(Message mess) {
		String table = "r"+mess.getReid();
		String sql = "insert into "+table+"(id, time, message) values('"+mess.getId()+"','"+mess.getTime()+"','"+mess.getMessage()+"');";
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
	
	public List<Message> getAll(int rid, int lastreid){
		String sql = "select * from r"+rid+" where reid>"+lastreid;
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		List<Message> list = new ArrayList<>();
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			Message me = null;
			while(rs.next()) {
				int id = rs.getInt(2);
				String time = rs.getString(3);
				String message = rs.getString(4);
				me = new Message(id, time, message);
				list.add(me);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	//获得总行数
	public int getCount(int rid) {
		String sql = "select count(*) as total from r"+rid+";";
		Connection conn = DBUtil.getConn();
		Statement stmt = null;
		ResultSet rs = null;
		int ans = 0;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.last();
			ans = rs.getInt(1);
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs,  stmt, conn);
		}
		return ans;
	}
}
