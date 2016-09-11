package ir.ast;

import ir.ASTVisitor;

public class ContinueStmt extends Statement {

	public ContinueStmt(){

	}
	
	public String toString() {
		return "continue";
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}