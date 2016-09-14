package ir.ast;

import ir.ASTVisitor;

public class IfThenStmt extends Statement {
	private Expression condition;
	private Statement ifBlock;

	public IfThenStmt(Expression cond, Statement ifBl) {
		this.condition = cond;
		this.ifBlock = ifBl;
	}

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Statement getIfBlock() {
		return ifBlock;
	}

	public void setIfBlock(Statement ifBlock) {
		this.ifBlock = ifBlock;
	}

	@Override
	public String toString(){
		return "if " + condition.toString() + '\n' + ifBlock.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}