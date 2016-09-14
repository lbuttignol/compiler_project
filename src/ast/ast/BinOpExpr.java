package ir.ast;

import ir.ASTVisitor;
//import ir.ast.TempExpression;


public abstract class BinOpExpr  extends Expression {
	protected BinOpType operator; //operator in the expr = expr operator expr
	protected Expression lOperand; //left expression
	protected Expression rOperand; //right expression
	
	public BinOpExpr(Expression l, BinOpType op, Expression r){
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

	public Expression getLeftOperand() {
		return this.lOperand;
	}

	public void setLeftOperand(Expression lOp) {
		this.lOperand = lOp;
	}

	public Expression getRightOperand() {
		return this.rOperand;
	}

	public void setRightOperand(Expression rOp) {
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
