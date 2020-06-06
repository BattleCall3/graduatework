package wow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import wow.entity.Authorize;
import wow.entity.Doctor;
import wow.entity.UserIN;
import wow.entity.UserInfo;

@Repository
public interface DoctorMapper {
	
	@Insert("insert into t_doctor(doctor_id, doctor_name, doctor_phone, doctor_password, doctor_hospital, doctor_state) values("
			+ "#{userID, jdbcType=INTEGER}, #{userName, jdbcType=VARCHAR}, #{userPhone, jdbcType=VARCHAR}, #{password, jdbcType=VARCHAR}, #{hospitalID, jdbcType=INTEGER}, 0)")
	int newDoctor(UserInfo userInfo);
	
	@Select("select count(1) from t_doctor where doctor_phone=#{phone}")
	int ifPhone(String phone);
	
	@Select("select doctor_id as doctorID, doctor_password as doctorPassword, doctor_state as doctorState "
			+ "from t_doctor where doctor_phone=#{phone}")
	Doctor doctorLogin(String phone);
	
	@Insert("insert into t_authorize(user_id, to_user_id, apply_state, apply_date, description, sign_text) "
			+ "values(#{userID.userID, jdbcType=INTEGER}, #{toUserID.userID, jdbcType=INTEGER}, 0, #{applyDate, jdbcType=TIMESTAMP}, #{description}, #{signText, jdbcType=VARCHAR})")
	int newAuthorize(Authorize authorize);
	
	@Select("select user_publickey from t_publickey where user_id=${_parameter}")
	String getPublicKey(Integer userID);
	
	@Select("select apply_id as applyID, user_id, apply_date as applyDate from t_authorize where to_user_id=${_parameter} and apply_state=1")
	@Results({
		@Result(property="userID", column="user_id", javaType=UserIN.class, 
				one=@One(select="getUserIN", fetchType=FetchType.EAGER))
	})
	List<Authorize> getAuthorizeList(Integer userID);
	
	@Select("select patient_id as userID, patient_name as userName from t_patient where patient_id=${_parameter}")
	UserIN getUserIN(Integer userID);
	
	@Select("select description from t_authorize where apply_id=${_parameter}")
	String getAuthorizeDescription(Integer applyID);
	
	
	

}
