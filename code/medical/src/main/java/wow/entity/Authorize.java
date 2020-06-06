package wow.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Authorize {
	
	private Integer applyID;
	private UserIN userID;
	private UserIN toUserID;
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
	private Timestamp applyDate;
	private String description;
	private MedicalRecord record;
	private String signText;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSignText() {
		return signText;
	}
	public void setSignText(String signText) {
		this.signText = signText;
	}
	public MedicalRecord getRecord() {
		return record;
	}
	public void setRecord(MedicalRecord record) {
		this.record = record;
	}
	public Integer getApplyID() {
		return applyID;
	}
	public void setApplyID(Integer applyID) {
		this.applyID = applyID;
	}
	public UserIN getUserID() {
		return userID;
	}
	public void setUserID(UserIN userID) {
		this.userID = userID;
	}
	public UserIN getToUserID() {
		return toUserID;
	}
	public void setToUserID(UserIN toUserID) {
		this.toUserID = toUserID;
	}
	public Timestamp getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}
	public Authorize() {
		super();
		
	}
	public Authorize(Integer applyID, UserIN userID, UserIN toUserID, Timestamp applyDate, String description,
			MedicalRecord record, String signText) {
		super();
		this.applyID = applyID;
		this.userID = userID;
		this.toUserID = toUserID;
		this.applyDate = applyDate;
		this.description = description;
		this.record = record;
		this.signText = signText;
	}

}
