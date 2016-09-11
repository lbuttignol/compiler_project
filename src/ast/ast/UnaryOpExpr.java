package ir.ast;

import ir.ASTVisitor;

public abstract class UnaryOpExpr <T extends Expression> extends Expression {
	protected BinOpType operator; 
	protected T operand;

	
	public UnaryOpExpr(BinOpType operator, T operand ){
		this.operator = operator;
		this.operand  = operand;
	}
	
	/*
	public BinOpExpr(T e, TempT t) {
		lOperand = e;
		operator = t.getOperator();
		rOperand = t.getRightOperand();
	}
	*/

	public BinOpType getOperator() {
		return operator;
	}

	public void setOperator(BinOpType operator) {
		this.operator = operator;
	}

	public T getOperand() {
		return operand;
	}

	public void setLeftOperand(T operand) {
		this.operand = operand;
	}

	
	@Override
	public String toString() {
		return operand + " " + operator ;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
