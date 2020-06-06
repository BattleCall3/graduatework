package wow.service;

import wow.entity.BackJSON;
import wow.entity.CreateMedical;

public interface DoctorService {
	
	public BackJSON createMedical(CreateMedical medical);
	
	public BackJSON applyAuthority(Integer doctorID, String patientPhone);
	
	public BackJSON authorizedList(Integer doctorID);
	
	public BackJSON getMedicalDescription(Integer applyID, String doctorPrivateKey);
	

}
