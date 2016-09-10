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
		return condition;
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
		return "if " + condition + '\n' + ifBlock.toString();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}