package ir.ast;

import ir.ASTVisitor;

public abstract class UnaryOpExpr extends Expression {
	protected BinOpType operator; 
	protected Expression operand;

	
	public UnaryOpExpr(BinOpType operator, Expression operand ){
		this.operator = operator;
		this.operand  = operand;
	}
	
	/*
	public BinOpExpr(Expression e, TempExpression t) {
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

	public Expression getOperand() {
		return operand;
	}

	public void setLeftOperand(Expression operand) {
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
