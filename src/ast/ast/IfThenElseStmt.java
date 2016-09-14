package ir.ast;

import ir.ASTVisitor;

public class IfThenElseStmt extends IfThenStmt {
	private Statement elseBlock;

	public IfThenElseStmt(Expression cond, Statement ifBl, Statement elseBl){
		super(cond, ifBl);
		this.elseBlock = elseBl;
	}

	public Statement getElseBlock() {
		return this.elseBlock;
	}

	public void setElseBlock(Statement elseBlock) {
		this.elseBlock = elseBlock;
	}
	
	@Override
	public String toString(){
		return "if " + this.getCondition().toString() + '\n' + this.getIfBlock().toString() + '\n' + "else \n" + elseBlock.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
