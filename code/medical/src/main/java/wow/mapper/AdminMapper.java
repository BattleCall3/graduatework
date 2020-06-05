package wow.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

	@Select("select user_count from t_usercount")
	Integer getUserCount();
	
}
