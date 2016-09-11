package ir.ast;

import ir.ASTVisitor;
//import ir.ast.TempExpression;

public class BinOpExpr extends Expression {
	protected BinOpType operator; //operator in the expr = expr operator expr
	protected Expression lOperand; //left expression
	protected Expression rOperand; //right expression
	
	public BinOpExpr(Expression l, BinOpType op, Expression r){
		operator = op;
		lOperand = l;
		rOperand = r;
	}
	
	public BinOpType getOperator() {
		return operator;
	}

	public void setOperator(BinOpType operator) {
		this.operator = operator;
	}

	public Expression getLeftOperand() {
		return lOperand;
	}

	public void setLeftOperand(Expression lOperand) {
		this.lOperand = lOperand;
	}

	public Expression getRightOperand() {
		return rOperand;
	}

	public void setRightOperand(Expression rOperand) {
		this.rOperand = rOperand;
	}
	
	@Override
	public String toString() {
		return lOperand + " " + operator + " " + rOperand;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
