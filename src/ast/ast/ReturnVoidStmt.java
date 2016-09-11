package ir.ast;

import ir.ASTVisitor;

public class ReturnVoidStmt extends Statement {
	private Expression expression;

	public ReturnVoidStmt() {
		this.expression = null;
	}

	@Override
	public String toString() {
		return "return";
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}