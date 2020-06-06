package wow.service;

import wow.entity.BackJSON;

public interface PatientService {
	
	public BackJSON needConfirmMedical(Integer patientID, String patientPrivateKey);
	
	public BackJSON uploadMedical(Integer applyID, Integer patientID, String patientPrvateKey);
	
	public BackJSON inquireMedical(Integer type, Integer patientID, String patientPrivateKey);
	
	public BackJSON authorizeList(Integer patientID, String patientPrivateKey);
	
	public BackJSON dealAuthorize(Integer applyID, String patientPrivateKey, Integer dealType);
	

}
