package ir.ast;

import ir.ASTVisitor;

public class ContinueStmt extends Statement {

	public ContinueStmt(int line, int col){
		super(line,col);
	}
	
	public String toString() {
		return "continue";
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}