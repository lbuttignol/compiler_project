package ir.ast;

import ir.ASTVisitor;
//import ir.ast.TempExpression;


public abstract class BinOpExpr <T extends Expression> extends Expression {
	protected BinOpType operator; //operator in the expr = expr operator expr
	protected T lOperand; //left expression
	protected T rOperand; //right expression
	
	public BinOpExpr(T l, BinOpType op, T r){
		this.operator = op;
		this.lOperand = l;
		this.rOperand = r;
	}
	
	public BinOpType getOperator() {
		return this.operator;
	}

	public void setOperator(BinOpType op) {
		this.operator = op;
	}

	public T getLeftOperand() {
		return this.lOperand;
	}

	public void setLeftOperand(T lOp) {
		this.lOperand = lOp;
	}

	public T getRightOperand() {
		return this.rOperand;
	}

	public void setRightOperand(T rOp) {
		this.rOperand = rOp;
	}
	
	@Override
	public String toString() {
		return lOperand.toString() + " " + operator.toString() + " " + rOperand.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
