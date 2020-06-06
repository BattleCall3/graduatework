package wow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wow.entity.BackJSON;
import wow.entity.CreateMedical;
import wow.entity.IIS;
import wow.service.DoctorService;


/**
 * 医生功能模块
 * @author wow
 * @date 2020年6月6日
 */

@RestController
@RequestMapping("/doctor/")
public class DoctorController {
	
	@Autowired
	private DoctorService ds;

	/* 医生为患者建立病历 */
	@PostMapping("createMedical")
	public BackJSON createMedical(@ModelAttribute CreateMedical medical) {
		return ds.createMedical(medical);
	}
	
	/* 医生申请授权 */
	@PostMapping("applyAuthorize")	
	public BackJSON applyAuthorze(Integer doctorID, String patientPhone) {
		return ds.applyAuthority(doctorID, patientPhone);
	}
	
	/* 医生查看已获得授权列表  */
	@GetMapping("authorizedList")
	public BackJSON authorizedList(Integer doctorID) {
		return ds.authorizedList(doctorID);
	}
	
	/* 医生查看详细病历 
	 * is: Integer applyID, String doctorPrivateKey
	 * */
	@PostMapping("medicalDescription")
	public BackJSON getMedicalDescription(@ModelAttribute IIS is) {
		return ds.getMedicalDescription(is.getI1(), is.getS());
	}
	

	
	
	
}
