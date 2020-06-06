package wow.entity;

import org.springframework.web.multipart.MultipartFile;

public class CreateMedical {
	
	private Integer doctorID;
	private String patientPhone;
	private MedicalRecord medical;
	private MultipartFile medicalFile;
	private String doctorPrivateKey;
	public CreateMedical() {
		super();
		
	}
	public CreateMedical(Integer doctorID, String patientPhone, MedicalRecord medical, MultipartFile medicalFile,
			String doctorPrivateKey) {
		super();
		this.doctorID = doctorID;
		this.patientPhone = patientPhone;
		this.medical = medical;
		this.medicalFile = medicalFile;
		this.doctorPrivateKey = doctorPrivateKey;
	}
	public Integer getDoctorID() {
		return doctorID;
	}
	public void setDoctorID(Integer doctorID) {
		this.doctorID = doctorID;
	}
	public String getPatientPhone() {
		return patientPhone;
	}
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}
	public MedicalRecord getMedical() {
		return medical;
	}
	public void setMedical(MedicalRecord medical) {
		this.medical = medical;
	}
	public MultipartFile getMedicalFile() {
		return medicalFile;
	}
	public void setMedicalFile(MultipartFile medicalFile) {
		this.medicalFile = medicalFile;
	}
	public String getDoctorPrivateKey() {
		return doctorPrivateKey;
	}
	public void setDoctorPrivateKey(String doctorPrivateKey) {
		this.doctorPrivateKey = doctorPrivateKey;
	}

}
