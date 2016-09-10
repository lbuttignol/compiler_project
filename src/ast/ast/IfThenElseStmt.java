package ir.ast;

import ir.ASTVisitor;

public class IfThenElseStmt extends IfThenStmt {
	private Statement elseBlock;
	

	public IfThenElseStmt(Expression cond, Statement ifBl, Statement elseBl) {
		this.condition = cond;
		this.ifBlock = ifBl;
		this.elseBlock = elseBl;
	}

	public Statement getElseBlock() {
		return elseBlock;
	}

	public void setElseBlock(Statement elseBlock) {
		this.elseBlock = elseBlock;
	}
	
	@Override
	public String toString(){
		return "if " + condition + '\n' + ifBlock.toString() + '\n' + "else \n" + elseBlock.toString();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
