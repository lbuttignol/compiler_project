package ir.ast;

import ir.ASTVisitor;
//import ir.ast.TempExpression;

public class BinOpExpr extends Expression {
	private BinOpType operator; //operator in the expr = expr operator expr
	private Expression lOperand; //left expression
	private Expression rOperand; //right expression
	
	public BinOpExpr(Expression l, BinOpType op, Expression r){
		operator = op;
		lOperand = l;
		rOperand = r;
	}
	
	public BinOpExpr(BinOpExpr e, BinOpExpr t) { //TempExpression t VEEEEEEERRRRRRRR!!!!!!
		lOperand = e;
		operator = t.getOperator();
		rOperand = t.getRightOperand();
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
