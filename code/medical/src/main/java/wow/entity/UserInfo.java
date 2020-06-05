package wow.entity;

public class UserInfo {
	
	private Integer userType;
	private String userName;
	private String userPhone;
	private String password;
	private Integer hospitalID;
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getHospitalID() {
		return hospitalID;
	}
	public void setHospitalID(Integer hospitalID) {
		this.hospitalID = hospitalID;
	}
	public UserInfo(Integer userType, String userName, String userPhone, String password, Integer hospitalID) {
		super();
		this.userType = userType;
		this.userName = userName;
		this.userPhone = userPhone;
		this.password = password;
		this.hospitalID = hospitalID;
	}
	public UserInfo() {
		super();
	}
	

}
