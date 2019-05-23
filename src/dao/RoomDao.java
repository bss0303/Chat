package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Room;
import util.DBUtil;

public class RoomDao {
	public static final int countOfOnePage = 10;
	
	//增
	public boolean add(Room room) {
		String sql = "insert into room(rname, createTime) values('"+room.getRname()+"','"+room.getCreateTime()+"');";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		
		try {
			state = conn.createStatement();
			if(state.executeUpdate(sql) > 0)
				f = true;
			String sql0 = "create table r"+getRoomByName(room.getRname()).getRid() + "(reid int primary key auto_increment, id int not null, time varchar(20), message varchar(500), foreign key(id) references user(id) on delete cascade);";
			state.executeUpdate(sql0);//创建房间的时候，创建对应的聊天记录
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(state, conn);
		}
		return f;
	}
	
	//删
	public boolean delete(int id) {
		String sql = "delete from room where rid='"+id+"'";
		String sql0 = "drop table r"+id;
		Connection conn = DBUtil.getConn();
		Statement state = null;
		boolean f = false;
		
		try {
			state = conn.createStatement();
			if(state.executeUpdate(sql) > 0)
				f = true;
			state.executeUpdate(sql0);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(state, conn);
		}
		return f;
	}
	
	//改
	public boolean update(Room room) {
		String sql = "update room set rname='"+room.getRname()+"', createTime='"+room.getCreateTime()+"' where rid='"+room.getRid()+"';";
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
	public List<Room> search(String rname, int page){
		String sql = "select * from room where";
		if(rname != "") {
			sql += " rname like '%"+rname+"%'";
		}
		sql += " limit " + (page-1)*countOfOnePage +","+countOfOnePage+";";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		List<Room> list = new ArrayList<>();
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			Room rm = null;
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String cT = rs.getString(3);
				rm = new Room(id, name, cT);
				list.add(rm);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		
		return list;
	}
	
	public Room getRoomById(int id) {
		String sql = "select * from room where rid='"+id+"'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		Room rm = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			while(rs.next()) {
				String name = rs.getString(2);
				String cT = rs.getString(3);
				rm = new Room(id, name, cT);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		return rm;
	}
	
	public Room getRoomByName(String name) {
		String sql = "select * from room where rname='"+name+"'";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		Room rm = null;
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			rs.last();//获得最后一个'rname'的房间
			int id = rs.getInt(1);
			String cT = rs.getString(3);
			rm = new Room(id, name, cT);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		return rm;
	}
	
	public List<Room> getRoomsByUserId(int id){
		String sql = "select room.rid,rname from ru,room where ru.id="+id+" and room.rid=ru.rid;";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		List<Room> list = new ArrayList<>();
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			Room rm = null;
			while(rs.next()) {
				int rid = rs.getInt(1);
				String rname = rs.getString(2);
				rm = new Room(rid, rname);
				list.add(rm);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, state, conn);
		}
		return list;
	}
	
	public List<Room> list(int page){
		String sql = "select * from room limit "+ (page-1)*countOfOnePage +","+countOfOnePage+";";
		Connection conn = DBUtil.getConn();
		Statement state = null;
		ResultSet rs = null;
		List<Room> list = new ArrayList<>();
		
		try {
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			Room rm = null;
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String cT = rs.getString(3);
				rm = new Room(id, name, cT);
				list.add(rm);
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
		String sql = "select count(*) as total from room;";
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
	public int getSearchCount(String rname) {
		String sql = "select count(*) as total from room where";
		if(rname != "") {
			sql += " rname like '%"+rname+"%'";
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
