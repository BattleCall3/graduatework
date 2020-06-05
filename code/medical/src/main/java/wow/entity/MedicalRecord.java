package wow.entity;

public class MedicalRecord {
	
	private Integer patinetID;
	private String patientName;
	private String doctorName;
	private String createTime;
	private String medicalPicture;
	private String desciption;
	public MedicalRecord() {
		super();
	}
	public MedicalRecord(Integer patinetID, String patientName, String doctorName, String createTime,
			String medicalPicture, String desciption) {
		super();
		this.patinetID = patinetID;
		this.patientName = patientName;
		this.doctorName = doctorName;
		this.createTime = createTime;
		this.medicalPicture = medicalPicture;
		this.desciption = desciption;
	}
	public Integer getPatinetID() {
		return patinetID;
	}
	public void setPatinetID(Integer patinetID) {
		this.patinetID = patinetID;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMedicalPicture() {
		return medicalPicture;
	}
	public void setMedicalPicture(String medicalPicture) {
		this.medicalPicture = medicalPicture;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

}
