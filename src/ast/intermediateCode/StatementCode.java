package ir.intermediateCode;

import ir.ast.*;
public class StatementCode{
	
	private OperationCode operationCode;
	private Operand operand1;
	private Operand operand2;
	private Operand operand3;
	
	public StatementCode(OperationCode opCode, Operand op1, Operand op2, Operand op3){
		this.operationCode = opCode;
		this.operand1 = op1;
		this.operand2 = op2;
		this.operand3 = op3;
	}

	public void setOperationCode(OperationCode opCode){
		this.operationCode = opCode; 
	}

	public void setOperand1(Operand op1){
		this.operand1 = op1;
	}

	public void setOperand2(Operand op2){
		this.operand2 = op2;
	}

	public void setOperand3(Operand op3){
		this.operand3 = op3;
	}

	public OperationCode getOperationCode(){
		return this.operationCode; 
	}

	public Operand getOperand1(){
		return this.operand1;
	}

	public Operand getOperand2(){
		return this.operand2;
	}

	public Operand getOperand3(){
		return this.operand3;
	}

	@Override
	public String toString(){
		return 	this.operationCode.toString()+' '+
				this.operand1 +' '+
				this.operand2+' '+
				this.operand3; 
	}
}