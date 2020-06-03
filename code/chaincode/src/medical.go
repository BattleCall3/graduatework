package main

import(
	"fmt"
	"encoding/json"
	
	"github.com/hyperledger/fabric-chaincode-go/shim"
	"github.com/hyperledger/fabric-protos-go/peer"
)

type MedicalChainCode struct {
}

type MedicalRecord struct {
	PatientName    string `json:"patientname"`
	DoctorName     string `json:"doctorname"`
	CreateTime 	   string `json:"createtime"`
	MedicalPicture string `json:"medicalpicture"`
	Description    string `json:"description"`
}

func (m *MedicalChainCode) Init(stub shim.ChaincodeStubInterface) peer.Response {
	err := stub.PutState("firstKey", []byte("FirstValue"))
	if err != nil {
		return shim.Error(err.Error())
	}
	fmt.Println("=========== success init ===============")
	return shim.Success(nil)
}

func (m *MedicalChainCode) Invoke(stub shim.ChaincodeStubInterface) peer.Response {
//	fmt.Println("=========== in invoke ==============")
	mFun, args := stub.GetFunctionAndParameters()
	switch {
		case "testInput" == mFun :
			return m.testInput(stub, args)
		case "testGet" == mFun :
			return m.testGet(stub, args[0])
		case "uploadMedicalRecord" == mFun :
			return m.uploadMedicalRecord(stub, args)
		case "getRecordbyID" == mFun :
			return m.getRecordbyID(stub, args)
		case "getAllHistoryRecordbyID" == mFun :
			return m.getAllHistoryRecordbyID(stub, args)
		default :
			return shim.Error("no fun invoke")
	}
//	return shim.Success([]byte("success invoke"))
}

func (m *MedicalChainCode) testInput(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	err := stub.PutState(args[0], []byte(args[1]))
	if err != nil {
		return shim.Error(err.Error())
	}
	return shim.Success([]byte("test input success"))
}

func (m *MedicalChainCode) testGet(stub shim.ChaincodeStubInterface, arg string) peer.Response {
	value, err := stub.GetState(arg)
	if(err != nil) {
		return shim.Error(err.Error())
	}
	return shim.Success(value)
}

func (m *MedicalChainCode) uploadMedicalRecord(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	var newMedicalRecord = MedicalRecord {
		PatientName:    args[1],
		DoctorName:     args[2],
		CreateTime:     args[3],
		MedicalPicture: args[4],
		Description:    args[5],
	}
//	fmt.Printf("%s-%s-%s\n", args[0], newMedicalRecord.PatientName, newMedicalRecord.DoctorName)
	medicalRecordBytes, _ := json.Marshal(newMedicalRecord)
//	fmt.Println("=====>", medicalRecordBytes)
	err := stub.PutState(args[0], medicalRecordBytes)
	if err != nil {
		return shim.Error(err.Error())
	}
//	fmt.Println("=========== uploadMedicalRecord success ==============")
	return shim.Success([]byte("success"))
}

func (m *MedicalChainCode) getRecordbyID(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 1 {
		return shim.Error("only one parameter required")
	}
//	fmt.Printf("id: %s\n", args[0])
	medicalRecordBytes, err := stub.GetState(args[0])
//	fmt.Println("=====>", medicalRecordBytes)
	if err != nil {
		fmt.Println("=========== error... ==============")
		return shim.Error(err.Error())
	}
	tRecord := new(MedicalRecord)
	json.Unmarshal(medicalRecordBytes, tRecord)
//	fmt.Printf("%s-%s\n", tRecord.PatientName, tRecord.DoctorName)
	return shim.Success(medicalRecordBytes)
}

func (m *MedicalChainCode) getAllHistoryRecordbyID(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 1 {
		return shim.Error("only one parameter required")
	}
//	fmt.Printf("id: %s\n", args[0])
	recordIterator, err := stub.GetHistoryForKey(args[0])
	recordList := []MedicalRecord{}
	defer recordIterator.Close()
	if err != nil {
		return shim.Error(err.Error())
	}
	for recordIterator.HasNext() {
		recordResponse, err := recordIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		tRecord := new(MedicalRecord)
		json.Unmarshal(recordResponse.Value, tRecord)
		recordList = append(recordList, *tRecord)
	}
//	fmt.Println(recordList)
	listBytes, _ := json.Marshal(recordList)
	return shim.Success(listBytes)
}



func main() {
	err := shim.Start(new(MedicalChainCode))
	if err != nil {
		fmt.Printf("error start chaincode : %s", err)
	}
}

