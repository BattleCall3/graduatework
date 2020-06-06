package wow.entity;

public class MedicalRecord {
	
	private Integer patientID;
	private String patientName;
	private String doctorName;
	private String createTime;
	private String medicalPicture;
	private String description;
	public MedicalRecord() {
		super();
	}
	
	public String getPatientName() {
		return patientName;
	}

	public Integer getPatientID() {
		return patientID;
	}

	public void setPatientID(Integer patientID) {
		this.patientID = patientID;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MedicalRecord(Integer patientID, String patientName, String doctorName, String createTime,
			String medicalPicture, String description) {
		super();
		this.patientID = patientID;
		this.patientName = patientName;
		this.doctorName = doctorName;
		this.createTime = createTime;
		this.medicalPicture = medicalPicture;
		this.description = description;
	}

}
