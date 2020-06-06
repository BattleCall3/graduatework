package wow.entity;

public class UserIN {
	
	private Integer userID;
	private String userName;
	public UserIN(Integer userID, String userName) {
		super();
		this.userID = userID;
		this.userName = userName;
	}
	public UserIN(Integer userID) {
		this.userID = userID;
	}
	
	public UserIN() {
		super();
		
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
