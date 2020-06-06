package wow.entity;

public class Patient {
	
	private Integer patientID;
	private String patientName;
	private String patientPhone;
	private String patientPassword;
	private String patientPicture;
	private Integer patientState;
	public Patient() {
		super();
		
	}
	public Patient(Integer patientID, String patientName, String patientPhone, String patientPassword,
			String patientPicture, Integer patientState) {
		super();
		this.patientID = patientID;
		this.patientName = patientName;
		this.patientPhone = patientPhone;
		this.patientPassword = patientPassword;
		this.patientPicture = patientPicture;
		this.patientState = patientState;
	}
	public Integer getPatientID() {
		return patientID;
	}
	public void setPatientID(Integer patientID) {
		this.patientID = patientID;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientPhone() {
		return patientPhone;
	}
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}
	public String getPatientPassword() {
		return patientPassword;
	}
	public void setPatientPassword(String patientPassword) {
		this.patientPassword = patientPassword;
	}
	public String getPatientPicture() {
		return patientPicture;
	}
	public void setPatientPicture(String patientPicture) {
		this.patientPicture = patientPicture;
	}
	public Integer getPatientState() {
		return patientState;
	}
	public void setPatientState(Integer patientState) {
		this.patientState = patientState;
	}

}
