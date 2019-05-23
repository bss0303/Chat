package bean;

public class Room {
	private int rid;
	private String rname;
	private String createTime;
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public Room() {};
	public Room(int rid, String rname) {
		this.rid = rid;
		this.rname = rname;
	}
	public Room(String rname, String createTime) {
		this.rname = rname;
		this.createTime = createTime;
	}
	public Room(int rid, String rname, String createTime) {
		this.rid = rid;
		this.rname = rname;
		this.createTime = createTime;
	}
}
