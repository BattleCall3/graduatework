package wow.entity;

public class BackJSON {

	private int code;
	private Object data;
	public BackJSON() {}
	public BackJSON(int code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
