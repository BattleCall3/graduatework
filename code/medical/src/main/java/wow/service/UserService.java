package wow.service;

import wow.entity.BackJSON;
import wow.entity.UserInfo;

public interface UserService {
	
	public BackJSON userRegister(UserInfo userInfo);
	
	public BackJSON getHospitalID(String hospitalName);
	
	public BackJSON userLogin(UserInfo userInfo);

}
