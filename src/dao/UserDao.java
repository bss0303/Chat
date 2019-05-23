package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.User;
import util.DBUtil;

public class UserDao {
	public static final int countOfOnePage = 10;
	//增
	public boolean add(User user) {
		String sql = "insert into user(username, password, privilege) values('"+user.getUsername()+"','"+user.getPwd()+"',"+ user.getPrivilege()+");";
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
	public boolean delete(int id) {
		String sql = "delete from user where id='"+id+"'";
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
	
	//改
	public boolean update(User user) {
		String sql = "update user set username='"+user.getUsername()+"', password='"+user.getPwd()+"', privilege="+user.getPrivilege()+" where id='"+user.getId()+"';";
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
	
	//查
	public List<User> search(String username, int page){
		String sql = "select * from user where";
		if(username != "") {
			sql += " username like '%"+username+"%'";
		}
		sql += " limit " + (page-1)*countOfOnePage +","+countOfOnePage+";";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<>();
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			User us = null;
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String pwd = rs.getString(3);
				int p = rs.getInt(4);
				us = new User(id, name, pwd, p);
				list.add(us);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	public User getUserById(int id) {
		String sql = "select * from user where id='"+id+"'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		User us = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while(rs.next()) {
				String name = rs.getString(2);
				String pwd = rs.getString(3);
				int p = rs.getInt(4);
				us = new User(id, name, pwd, p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		return us;
	}
	
	public User getUserByName(String name) {
		String sql = "select * from user where username='"+name+"'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		User us = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt(1);
				String pwd = rs.getString(3);
				int p = rs.getInt(4);
				us = new User(id, name, pwd, p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		return us;
	}
	
	public List<User> list(int page){
		String sql = "select * from user limit "+ (page-1)*countOfOnePage +","+countOfOnePage+";";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<>();
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			User us = null;
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String pwd = rs.getString(3);
				int p = rs.getInt(4);
				us = new User(id, name, pwd, p);
				list.add(us);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}
	
	//获得总行数
	public int getCount() {
		String sql = "select count(*) as total from user;";
		Connection conn = DBUtil.getConn();
		Statement stmt = null;
		ResultSet rs = null;
		int ans = 0;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				ans = rs.getInt("total");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs,  stmt, conn);
		}
		return ans;
	}
	
	//获得总行数
	public int getSearchCount(String username) {
		String sql = "select count(*) as total from user where";
		if(username != "") {
			sql += " username like '%"+username+"%'";
		}
		Connection conn = DBUtil.getConn();
		Statement stmt = null;
		ResultSet rs = null;
		int ans = 0;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				ans = rs.getInt("total");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs,  stmt, conn);
		}
		return ans;
	}
}