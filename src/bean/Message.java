package bean;

public class Message {
	private int reid;
	private int id;
	private String time;
	private String message;
	public int getReid() {
		return reid;
	}
	public void setReid(int reid) {
		this.reid = reid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Message(int id, String time, String message) {
		this.id = id;
		this.time = time;
		this.message = message;
	}
	public Message(int reid, int id, String time, String message) {
		super();
		this.reid = reid;
		this.id = id;
		this.time = time;
		this.message = message;
	}
}
