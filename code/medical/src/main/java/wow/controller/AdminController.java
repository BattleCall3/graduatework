package wow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {

	@RequestMapping("test")
	public void test() {
		System.out.println("进入测试！");
	}

}
