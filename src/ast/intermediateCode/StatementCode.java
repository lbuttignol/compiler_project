package ir.intermediateCode;

import ir.ast.*;
public class StatementCode{
	
	private OperationCode operationCode;
	private AST operand1;
	private AST operand2;
	private AST operand3;
	
	public StatementCode(OperationCode opCode, AST op1, AST op2, AST op3){
		this.operationCode = opCode;
		this.operand1 = op1;
		this.operand2 = op2;
		this.operand3 = op3;
	}

	public void setOperationCode(OperationCode opCode){
		this.operationCode = opCode; 
	}

	public void setOperand1(AST op1){
		this.operand1 = op1;
	}

	public void setOperand2(AST op2){
		this.operand2 = op2;
	}

	public void setOperand3(AST op3){
		this.operand3 = op3;
	}

	public OperationCode getOperationCode(){
		return this.operationCode; 
	}

	public AST getOperand1(){
		return this.operand1;
	}

	public AST getOperand2(){
		return this.operand2;
	}

	public AST getOperand3(){
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