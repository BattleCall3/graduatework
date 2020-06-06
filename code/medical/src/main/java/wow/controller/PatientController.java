package wow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wow.entity.BackJSON;
import wow.entity.IIS;
import wow.service.PatientService;

/**
 * 患者功能模块
 * @author wow
 * @date 2020年6月6日
 */

@RestController
@RequestMapping("/patient/")
public class PatientController {
	
	@Autowired
	private PatientService ps;
	
	/* 确认要提交到区块链上的病历 
	 * is: patientID, patientPrivateKey
	 *  */
	@PostMapping("needConfirmMedical")
	public BackJSON needConfirmMedical(@ModelAttribute IIS is) {
		return ps.needConfirmMedical(is.getI1(), is.getS());
	}
	
	/* 上传病历  
	 * iis: Integer applyID, Integer patientID, String patientPrvateKey
	 * */
	@PostMapping("uploadMedical")
	public BackJSON uploadMedical(@ModelAttribute IIS iis) {
		return ps.uploadMedical(iis.getI1(), iis.getI2(), iis.getS());
	}
	
	/* 查询病历  
	 * iis ：Integer type, Integer patientID, String patientPrivateKey
	 * */
	@PostMapping("inquireMedical")
	public BackJSON inquireMedical(@ModelAttribute IIS iis) {
		return ps.inquireMedical(iis.getI1(), iis.getI2(), iis.getS());
	}
	
	/* 查看待授权列表  
	 * is： Integer patientID, String patientPrivateKey
	 * */
	@PostMapping("authorizeList")
	public BackJSON authorizeList(@ModelAttribute IIS is) {
		return ps.authorizeList(is.getI1(), is.getS());
	}
	
	/* 处理授权  
	 * iis: Integer applyID, String patientPrivateKey, Integer dealType
	 * */
	@PostMapping("dealAuthorize")
	public BackJSON dealAuthorize(@ModelAttribute IIS iis) {
		return ps.dealAuthorize(iis.getI1(), iis.getS(), iis.getI2());
	}
	
	
	
	
}
