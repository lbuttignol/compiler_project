package ir.ast;

import ir.ASTVisitor;


public abstract class UnaryOpExpr extends Expression {
	protected UnaryOpType operator; 
	protected Expression operand;

	
	public UnaryOpExpr(UnaryOpType operator, Expression operand , int line, int col){
		super(line,col);
		this.operator = operator;
		this.operand  = operand;
	}
	

	public UnaryOpType getOperator() {
		return operator;
	}

	public void setOperator(UnaryOpType operator) {
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
