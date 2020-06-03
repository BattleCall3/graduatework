func (m *MedicalChainCode) getAllHistoryRecordbyID(stub shim.ChaincodeStubInterface, args []string) peer.Response {
	if len(args) != 1 {
		return shim.Error("only one parameter required")
	}
	recordIterator, err := stub.GetHistoryForKey(args[0])
	defer recordIterator.Close()
	if err != nil {
		return shim.Error(err.Error())
	}
	var resultBuffer bytes.Buffer
	resultBuffer.WriteString("[")
	if recordIterator.HasNext() {
		record, err := recordIterator.Next()
		if err != nil {
			return shim.Error(err.Error())
		}
		resultBuffer.WriteString("{")
		resultBuffer.WriteString("\"key\":")
		resultBuffer.WriteString(record.Key)
		resultBuffer.WriteString(", \"record\":")
		resultBuffer.WriteString(string(record.Value))
		resultBuffer.WriteString("}")
		for recordIterator.HasNext() {
			record, err = recordIterator.Next()
			if err != nil {
				return shim.Error(err.Error())
			}
			resultBuffer.WriteString(", {\"key\":")
			resultBuffer.WriteString(record.Key)
			resultBuffer.WriteString(", \"record\":")
			resultBuffer.WriteString(string(record.Value))
			resultBuffer.WriteString("}")
		}
	}
	resultBuffer.WriteString("]")
	return shim.Success(resultBuffer.Bytes())
}