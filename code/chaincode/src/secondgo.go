package main

import(
	"fmt"
	
	"github.com/hyperledger/fabric-chaincode-go/shim"
	"github.com/hyperledger/fabric-protos-go/peer"
)

type MedicalChainCode struct {
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
	fmt.Println("=========== in invoke ==============")
	mFun, args := stub.GetFunctionAndParameters()
	switch {
		case "testInput" == mFun :
			return m.testInput(stub, args)
		case "testGet" == mFun :
			return m.testGet(stub, args[0])
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

func main() {
	err := shim.Start(new(MedicalChainCode))
	if err != nil {
		fmt.Printf("error start chaincode : %s", err)
	}
}

