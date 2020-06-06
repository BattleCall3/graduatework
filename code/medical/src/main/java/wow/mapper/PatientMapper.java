package wow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import wow.entity.Authorize;
import wow.entity.Patient;
import wow.entity.UserIN;
import wow.entity.UserInfo;

@Repository
public interface PatientMapper {

	@Insert("insert into t_patient(patient_id, patient_name, patient_phone, patient_password, patient_state) values("
			+ "#{userID, jdbcType=INTEGER}, #{userName, jdbcType=VARCHAR}, #{userPhone, jdbcType=VARCHAR}, #{password, jdbcType=VARCHAR}, 0)")
	int newPatient(UserInfo userInfo);
	
	@Select("select count(1) from t_patient where patient_phone=#{phone}")
	int ifPhone(String phone);
	
	@Select("select patient_id as patientID, patient_password as patientPassword, patient_state as patientState "
			+ "from t_patient where patient_phone=#{phone}")
	Patient patientLogin(String phone);
	
	@Select("select p.patient_id as patientID, k.user_publickey as patientPublicKey from t_patient p, t_publickey k where p.patient_phone=#{phone} and k.user_id=p.patient_id")
	Map<String, Object> getPatientIDAndPublicKey(String phone);
	
	@Select("select apply_id as applyID, user_id, apply_date as applyDate, description, sign_text as signText from t_authorize where to_user_id=${_parameter} and apply_state=0")
	@Results({
		@Result(property="userID", column="user_id", javaType=UserIN.class, 
				one=@One(select="getUserIN", fetchType=FetchType.EAGER))
	})
	List<Authorize> getNeedUploadMedicalList(int userID);
	
	@Select("select doctor_id as userID, doctor_name as userName from t_doctor where doctor_id=${_parameter}")
	UserIN getUserIN(int userID);
	
	@Select("select description from t_authorize where apply_id=${_parameter}")
	String getMedicalDescription(int applyID);
	
	@Select("select user_publickey from t_publickey where uesr_id=${_parameter}")
	String getPublicKey(int patientID);
	
	@Delete("delete from t_authorize where apply_id=${_parameter}")
	int delNeedUploadMedical(int applyID);
	
	@Select("select patient_id from t_patient where patient_phone=#{phone}")
	Integer getPatientID(String phone);
	
	@Select("select apply_id as applyID, to_user_id, apply_date as applyDate, description from t_authorize where user_id=${_parameter} and apply_state=0")
	@Results({
		@Result(property="toUserID", column="to_user_id", javaType=UserIN.class, 
				one=@One(select="getUserIN", fetchType=FetchType.EAGER))
	})
	List<Authorize> getNeedAuthorizeList(int userID);
	
	@Select("select a.to_user_id, a.description, k.user_publickey from t_authorize a, t_publickey k where a.apply_id=${_parameter} and k.user_id=a.to_user_id")
	Map<String, Object> getAuthDescription(int applyID);
	
	@Update("update t_authorize set description=#{param2}, apply_state=1, apply_date=now() where apply_id=#{param1}")
	int confirmAuthorize(Integer applyID, String description);
	
	@Update("update t_authorize set apply_state=2, apply_date=now() where apply_id=${_parameter}")
	int rejectAuthorize(int applyID);
	
	
	
	
	
}
