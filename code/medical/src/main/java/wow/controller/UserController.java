package wow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wow.entity.BackJSON;
import wow.entity.UserInfo;
import wow.service.UserService;


/**
 * 通用用户功能模块
 * @author wow
 * @date 2020年6月6日
 */

@RestController
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService us;
	
	/* 新用户注册 */
	@PostMapping("register")
//	public BackJSON register(@RequestBody UserInfo userInfo) {
	public BackJSON register(@ModelAttribute UserInfo userInfo) {
		return us.userRegister(userInfo);
	}
	
	/* 根据名称获取医疗机构ID */
	@GetMapping("getHospitalID")
	public BackJSON getHospitalID(String hospitalName) {
		return us.getHospitalID(hospitalName);
	}
	
	/* 用户登录 */
	@PostMapping("login")
	public BackJSON login(@ModelAttribute UserInfo userInfo) {
		return us.userLogin(userInfo);
	}

}
