package wow.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Hospital {
	
	private Integer hospitalID;
	private String hospitalName;
	private Integer hospitalState;
	private String businessLicense;
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date inDate;
	public Hospital() {
		super();
		
	}
	public Hospital(Integer hospitalID, String hospitalName, Integer hospitalState, String businessLicense,
			Date inDate) {
		super();
		this.hospitalID = hospitalID;
		this.hospitalName = hospitalName;
		this.hospitalState = hospitalState;
		this.businessLicense = businessLicense;
		this.inDate = inDate;
	}
	public Integer getHospitalID() {
		return hospitalID;
	}
	public void setHospitalID(Integer hospitalID) {
		this.hospitalID = hospitalID;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public Integer getHospitalState() {
		return hospitalState;
	}
	public void setHospitalState(Integer hospitalState) {
		this.hospitalState = hospitalState;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

}
