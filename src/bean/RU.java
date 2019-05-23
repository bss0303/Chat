package bean;

public class RU {
	private int id;
	private int rid;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	
	public RU() {}
	public RU(int id, int rid) {
		this.id = id;
		this.rid = rid;
	}
}
