package ir.intermediateCode;

public class StatementCode{
	
	private OperationCode operationCode;
	private String operand1;
	private String operand2;
	private String operand3;
	
	public StatementCode(OperationCode opCode,String op1,String op2, String op3){
		this.operationCode = opCode;
		this.operand1 = op1;
		this.operand2 = op2;
		this.operand3 = op3;
	}

	public void setOperationCode(OperationCode opCode){
		this.operationCode = opCode; 
	}

	public void setOperand1(String op1){
		this.operand1 = op1;
	}

	public void setOperand2(String op2){
		this.operand2 = op2;
	}

	public void setOperand3(String op3){
		this.operand3 = op3;
	}

	public OperationCode getOperationCode(){
		return this.operationCode; 
	}

	public String getOperand1(){
		return this.operand1;
	}

	public String getOperand2(){
		return this.operand2;
	}

	public String getOperand3(){
		return this.operand3;
	}
}