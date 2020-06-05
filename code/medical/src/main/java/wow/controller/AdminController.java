package wow.controller;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.hyperledger.fabric.gateway.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import wow.entity.BackJSON;
import wow.entity.MedicalRecord;
import wow.service.AdminService;
import wow.util.Value;

/**
 * 管理员功能实现
 * @author wow
 * @date 2020年6月5日
 */

@RestController
@RequestMapping("/admin/")
public class AdminController {

	@Autowired
	private AdminService as;
	
	/* 第一次测试 */
	@RequestMapping("test")
	public void test() {
		System.out.println("进入测试！");
	}
	/* 查询区块链测试 */
	@RequestMapping("testQueryFabric")
	public void testQueryFabric(HttpServletResponse response) {
		Contract contract = Value.getContract();
		try {
			byte[] queryResult = contract.evaluateTransaction("testGet","firstKey");
			String queryStr = new String(queryResult, StandardCharsets.UTF_8);
			System.out.println("===>>>value："+queryStr);
			PrintWriter out = response.getWriter();
			out.write("{\"firstValue\":\""+queryStr+"\"}");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* 数据存储上链测试 */
	@RequestMapping("testInputFabric")
	public BackJSON testInputFabric() {
		Contract contract = Value.getContract();
		BackJSON result = new BackJSON(0);
		try {
			byte[] queryResult = contract.createTransaction("uploadMedicalRecord").submit("patientID0", "patientName0", "doctorName0", "createTime0", "medicalPicture0", "description0");
			String queryStr = new String(queryResult, StandardCharsets.UTF_8);
			System.out.println("create result:"+queryStr);
			if(queryStr.equals("success")) {
				result.setCode(200);
			}
			
			queryResult = contract.evaluateTransaction("getRecordbyID", "patientID0");
			queryStr = new String(queryResult, StandardCharsets.UTF_8);
			JSONObject jsonResult = JSONObject.parseObject(queryStr);
			MedicalRecord medicalRecord = new MedicalRecord();
			medicalRecord.setPatinetID(01);
			medicalRecord.setPatientName(jsonResult.getString("patientname"));
			medicalRecord.setDoctorName(jsonResult.getString("doctorname"));
			medicalRecord.setCreateTime(jsonResult.getString("createtime"));
			medicalRecord.setMedicalPicture(jsonResult.getString("medicalpicture"));
			medicalRecord.setDesciption(jsonResult.getString("description"));
			result.setData(medicalRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/* 获取用户总数 */
	@GetMapping("getUserCount")
	public BackJSON getUserCount() {
		return as.getUserCount();
	}

}











