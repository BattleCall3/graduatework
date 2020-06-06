package wow.entity;

public class Doctor {
	
	private Integer doctorID;
	private String doctorName;
	private String doctorPhone;
	private String doctorPassword;
	private String doctorPicture;
	private Hospital doctorHospital;
	private Integer doctorState;
	public Doctor() {
		super();
		
	}
	public Doctor(Integer doctorID, String doctorName, String doctorPhone, String doctorPassword, String doctorPicture,
			Hospital doctorHospital, Integer doctorState) {
		super();
		this.doctorID = doctorID;
		this.doctorName = doctorName;
		this.doctorPhone = doctorPhone;
		this.doctorPassword = doctorPassword;
		this.doctorPicture = doctorPicture;
		this.doctorHospital = doctorHospital;
		this.doctorState = doctorState;
	}
	public Integer getDoctorID() {
		return doctorID;
	}
	public void setDoctorID(Integer doctorID) {
		this.doctorID = doctorID;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorPhone() {
		return doctorPhone;
	}
	public void setDoctorPhone(String doctorPhone) {
		this.doctorPhone = doctorPhone;
	}
	public String getDoctorPassword() {
		return doctorPassword;
	}
	public void setDoctorPassword(String doctorPassword) {
		this.doctorPassword = doctorPassword;
	}
	public String getDoctorPicture() {
		return doctorPicture;
	}
	public void setDoctorPicture(String doctorPicture) {
		this.doctorPicture = doctorPicture;
	}
	public Hospital getDoctorHospital() {
		return doctorHospital;
	}
	public void setDoctorHospital(Hospital doctorHospital) {
		this.doctorHospital = doctorHospital;
	}
	public Integer getDoctorState() {
		return doctorState;
	}
	public void setDoctorState(Integer doctorState) {
		this.doctorState = doctorState;
	}

}
