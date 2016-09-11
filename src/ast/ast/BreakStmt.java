package ir.ast;

import ir.ASTVisitor;

public class BreakStmt extends Statement {

	public BreakStmt (){
		
	}

	public String toString() {
		return "break";
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}