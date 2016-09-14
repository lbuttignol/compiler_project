package ir.ast;

import ir.ASTVisitor;

public class ReturnVoidStmt extends AbstractReturn {

	public ReturnVoidStmt() {}

	@Override
	public String toString() {
		return "return";
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}