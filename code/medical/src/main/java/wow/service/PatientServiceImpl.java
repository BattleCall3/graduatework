package wow.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import wow.entity.Authorize;
import wow.entity.BackJSON;
import wow.entity.MedicalRecord;
import wow.mapper.DoctorMapper;
import wow.mapper.PatientMapper;
import wow.util.Value;


@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientMapper pm;
	
	@Autowired
	private DoctorMapper dm;
	
	@Override
	@Transactional
	public BackJSON needConfirmMedical(Integer patientID, String patientPrivateKey) {
		BackJSON json = new BackJSON(200);
		Map<String, Object> data = new HashMap<>();
		List<Authorize> authorizeList = pm.getNeedUploadMedicalList(patientID);
		for(Authorize auth : authorizeList) {
			// 获取病历
			String medicalDescription = auth.getDescription();
			// 验证医生签名
			String doctorPublicKey = dm.getPublicKey(auth.getUserID().getUserID());
			boolean verifyResult = Value.RSACheckSign(medicalDescription, auth.getSignText(), doctorPublicKey);
			if(verifyResult) {
				auth.setSignText(null);
				// 患者私钥解密
				MedicalRecord record = JSONObject.parseObject(medicalDescription, MedicalRecord.class);
//				System.out.println(patientPrivateKey);
				String DescryptMedicalDescription = Value.RSADecrypt(record.getDescription(), patientPrivateKey);
				record.setDescription(DescryptMedicalDescription);
				String picturePath = Value.getMedicalPicturePath();
				record.setMedicalPicture(picturePath+record.getMedicalPicture());
				auth.setRecord(record);
				auth.setDescription(null);
			} else {
				//验证签名失败
				auth.setRecord(null);
			}
		}
		data.put("list", authorizeList);
		json.setData(data);
		return json;
	}

	@Override
	@Transactional
	public BackJSON uploadMedical(Integer applyID, Integer patientID, String patientPrvateKey) {
		BackJSON json = new BackJSON(200);
		Map<String, Object> data = new HashMap<>();
		data.put("result", 3);
		String medicalDescription = pm.getMedicalDescription(applyID);
		if(medicalDescription!=null) {
			MedicalRecord record = JSONObject.parseObject(medicalDescription, MedicalRecord.class);
			// 加密病历描述
			Contract contract = Value.getContract();
			try {
				byte[] queryResult = contract.createTransaction("uploadMedicalRecord")
						.submit(String.valueOf(patientID), record.getPatientName(), record.getDoctorName(), record.getCreateTime(), record.getMedicalPicture(), record.getDescription());
				String queryStr = new String(queryResult, StandardCharsets.UTF_8);
				if(queryStr.equals("success")) {
					data.replace("result", 0);
					pm.delNeedUploadMedical(applyID);
				} else {
					data.replace("result", 2);
				}
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		json.setData(data);
		return json;
	}

	@Override
	public BackJSON inquireMedical(Integer type, Integer patientID, String patientPrivateKey) {
		BackJSON json = new BackJSON(200);
		Map<String, Object> data = new HashMap<>();
		if(1==type) {
			Contract contract = Value.getContract();
			try {
				byte[] queryResult = contract.evaluateTransaction("getRecordbyID", String.valueOf(patientID));
				JSONObject jsonResult = JSONObject.parseObject(new String(queryResult, StandardCharsets.UTF_8));
				MedicalRecord medicalRecord = new MedicalRecord();
				medicalRecord.setPatientName(jsonResult.getString("patientname"));
				medicalRecord.setDoctorName(jsonResult.getString("doctorname"));
				medicalRecord.setCreateTime(jsonResult.getString("createtime"));
				medicalRecord.setMedicalPicture(Value.getMedicalPicturePath()+jsonResult.getString("medicalpicture"));
				medicalRecord.setDescription(Value.RSADecrypt(jsonResult.getString("description"),patientPrivateKey));
				data.put("result", 0);
				data.put("record", medicalRecord);
			} catch (ContractException e) {
				data.put("result", 2);
				e.printStackTrace();
			}
		} else {
			// TODO：历史病历
		}
		json.setData(data);
		return json;
	}

	@Override
	@Transactional(readOnly=true)
	public BackJSON authorizeList(Integer patientID, String patientPrivateKey) {
		BackJSON json = new BackJSON(200);
		JSONObject data = new JSONObject();
		List<Authorize> authList = pm.getNeedAuthorizeList(patientID);
		for(Authorize auth : authList) {
			String description = auth.getDescription();
			MedicalRecord record = JSONObject.parseObject(description, MedicalRecord.class);
			record.setMedicalPicture(Value.getMedicalPicturePath()+record.getMedicalPicture());
			record.setDescription(Value.RSADecrypt(record.getDescription(), patientPrivateKey));
			auth.setRecord(record);
			auth.setDescription(null);
		}
		data.put("list", authList);
		json.setData(data);
		return json;
	}

	@Override
	@Transactional
	public BackJSON dealAuthorize(Integer applyID, String patientPrivateKey, Integer dealType) {
		BackJSON json = new BackJSON(200);
		JSONObject data = new JSONObject();
		data.put("result", 1);
		if(1==dealType) {
			// 同意
			Map<String, Object> dkMap = pm.getAuthDescription(applyID);
			MedicalRecord record = JSONObject.parseObject((String)dkMap.get("description"), MedicalRecord.class);
			// 患者私钥解密后，医生公钥加密
			String decryptDescription = Value.RSADecrypt(record.getDescription(), patientPrivateKey);
			record.setDescription(Value.RSAEncrypt(decryptDescription, (String)dkMap.get("user_publickey")));
			String description = JSONObject.toJSONString(record);
			if(1==pm.confirmAuthorize(applyID, description)) {
				data.replace("result", 0);
			}
		} else if(2==dealType) {
			// 拒绝
			if(1==pm.rejectAuthorize(applyID)) {
				data.replace("result", 0);
			}
		} else if(3==dealType) {
			// 删除
			if(1==pm.delNeedUploadMedical(applyID)) {
				data.replace("result", 0);
			}
		} else {
			// 未知处理
			data.replace("result", 2);
		}
		json.setData(data);
		return json;
	}
	
	
	

}
