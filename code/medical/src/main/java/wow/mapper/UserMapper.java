package wow.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

	@Select("select hospital_id from t_hospital where hospital_name=#{hospitalName}")
	Integer getHospitalID(String hospitalName);
	
	@Select("select user_count from t_usercount")
	int getUserCount();
	
	@Update("update t_usercount set user_count=user_count+1")
	int updateUserCount();
	
	@Insert("insert into t_publickey values(#{param1}, #{param2})")
	int newUserPublicKey(Integer userID, String userPublicKey);
	
}
