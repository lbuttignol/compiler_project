package ir.ast;

import ir.ASTVisitor;

public class ReturnStmt extends AbstractReturn {
	private Expression expression; // the return expression
	
	public ReturnStmt(Expression e,int line, int col){
		super(line,col);
		this.expression = e;
	}
	
	public ReturnStmt(int line, int col){
		super(line,col);
		this.expression = null;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return "return " + expression;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
