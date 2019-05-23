package bean;

public class User {
	private int id;
	private String username;
	private String pwd;
	private int privilege;//?
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	
	public User() {};
	public User(String username, String pwd, int privilege) {
		this.username = username;
		this.pwd = pwd;
		this.privilege = privilege;
	}
	public User(int id, String username, String pwd, int privilege) {
		this.id = id;
		this.username = username;
		this.pwd = pwd;
		this.privilege = privilege;
	}
}
