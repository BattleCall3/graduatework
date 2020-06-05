package wow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import wow.entity.BackJSON;
import wow.mapper.AdminMapper;

@Service
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper am;
	
	@Override
	@Transactional(readOnly=true)
	public BackJSON getUserCount() {
		BackJSON json = new BackJSON(200);
		String count = "{\"count\":"+am.getUserCount()+"}";
		json.setData(JSONObject.parse(count));
		return json;
	}

}
