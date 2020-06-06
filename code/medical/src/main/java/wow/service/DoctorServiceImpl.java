package wow.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import wow.entity.Authorize;
import wow.entity.BackJSON;
import wow.entity.CreateMedical;
import wow.entity.MedicalRecord;
import wow.entity.UserIN;
import wow.mapper.DoctorMapper;
import wow.mapper.PatientMapper;
import wow.util.Value;


@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private PatientMapper pm;
	
	@Autowired
	private DoctorMapper dm;
	
	@Override
	@Transactional
	public BackJSON createMedical(CreateMedical cmedical) {
		BackJSON json = new BackJSON(200);
		Map<String, Object> data = new HashMap<>();
		data.put("result", 1);
		// 封装权限信息
		Authorize authorize = new Authorize();
		long nowTime = System.currentTimeMillis();
		authorize.setApplyDate(new Timestamp(nowTime));
		Integer doctorID = cmedical.getDoctorID();
		authorize.setUserID(new UserIN(doctorID));
		Map<String, Object> ikMap = pm.getPatientIDAndPublicKey(cmedical.getPatientPhone());
		Integer patientID = ((Number)ikMap.get("patientID")).intValue();
		authorize.setToUserID(new UserIN(patientID));
		String patientPublicKey = (String)ikMap.get("patientPublicKey");
		// 处理病历图片
		MultipartFile medicalFile = cmedical.getMedicalFile();
		// 文件名字 patientID+timestamp+filename
		String fileName = patientID+nowTime+medicalFile.getOriginalFilename();
		String filePath = Value.getMedicalPicturePath();
		File file = new File(filePath+fileName);
		try {
			medicalFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			System.out.println("文件传输错误 file："+file.getName());
			e.printStackTrace();
		}
		MedicalRecord record = cmedical.getMedical();
		record.setMedicalPicture(fileName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		record.setCreateTime(sdf.format(new Date()));
		// 患者公钥加密
		String encryptDescription = Value.RSAEncrypt(record.getDescription(), patientPublicKey);
		record.setDescription(encryptDescription);
		// 授权信息描述，这里指病情信息
		String description = JSONObject.toJSONString(record);
//		System.out.println(description);
		// 医生签名
		String signDescription = Value.RSASign(description, cmedical.getDoctorPrivateKey());
		authorize.setDescription(description);
		authorize.setSignText(signDescription);
		if(1==dm.newAuthorize(authorize)) {
			data.replace("result", 0);
		}
		json.setData(data);
		return json;
	}

	@Override
	@Transactional
	public BackJSON applyAuthority(Integer doctorID, String patientPhone) {
		BackJSON json = new BackJSON(200);
		Map<String, Object> data = new HashMap<>();
		Integer patientID = pm.getPatientID(patientPhone);
		if(null==patientID) {
			//患者不存在
			data.put("result", 1);
		} else {
			Contract contract = Value.getContract();
			try {
				byte[] queryResult = contract.evaluateTransaction("getRecordbyID", String.valueOf(patientID));
				if(0==queryResult.length) {
					// 患者不存在病历数据
					data.put("result", 2);
				} else {
					JSONObject jsonResult = JSONObject.parseObject(new String(queryResult, StandardCharsets.UTF_8));
					MedicalRecord medicalRecord = new MedicalRecord();
					medicalRecord.setPatientName(jsonResult.getString("patientname"));
					medicalRecord.setDoctorName(jsonResult.getString("doctorname"));
					medicalRecord.setCreateTime(jsonResult.getString("createtime"));
					medicalRecord.setMedicalPicture(jsonResult.getString("medicalpicture"));
					medicalRecord.setDescription(jsonResult.getString("description"));
					// 封装要存入数据库的数据
					Authorize authorize = new Authorize();
					authorize.setUserID(new UserIN(patientID));
					authorize.setToUserID(new UserIN(doctorID));
					authorize.setApplyDate(new Timestamp(System.currentTimeMillis()));
					authorize.setDescription(JSONObject.toJSONString(medicalRecord));
					if(1==dm.newAuthorize(authorize)) {
						data.put("result", 0);
					}
				}
			} catch (ContractException e) {
				e.printStackTrace();
			}
		}
		json.setData(data);
		return json;
	}

	@Override
	@Transactional(readOnly=true)
	public BackJSON authorizedList(Integer doctorID) {
		BackJSON json = new BackJSON(200);
		JSONObject data = new JSONObject();
		List<Authorize> authList = dm.getAuthorizeList(doctorID);
		data.put("list", authList);
		json.setData(data);
		return json;
	}

	@Override
	@Transactional(readOnly=true)
	public BackJSON getMedicalDescription(Integer applyID, String doctorPrivateKey) {
		BackJSON json = new BackJSON(200);
		JSONObject data = new JSONObject();
		String desciption = dm.getAuthorizeDescription(applyID);
		if(desciption!=null) {
			MedicalRecord record = JSONObject.parseObject(desciption, MedicalRecord.class);
			String picturePath = Value.getMedicalPicturePath();
			record.setMedicalPicture(picturePath+record.getMedicalPicture());
			String descriptDescription = record.getDescription();
			record.setDescription(Value.RSADecrypt(descriptDescription, doctorPrivateKey));
			data.put("record", record);
		} else {
			data.put("record", null);
		}
		json.setData(data);
		return json;
	}
	
	
	

}
