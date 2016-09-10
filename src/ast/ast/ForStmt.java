package ir.ast;

import ir.ASTVisitor;

public class ForStmt extends Statement {
	Expression initValue;
	Expression endValue;
	Statement body;

	public ForStmt(Expression init, Expression end, Statement b){
		this.initValue = init;
		this.endValue = end;
		this.body = b;
	}

	public void setInit(Expression i){
		this.initValue = i;
	}

	public Expression getInit(){
		return this.initValue;
	}

	public void setEnd(Expression e){
		this.endValue = e;
	}

	public Expression getEnd(){
		return this.endValue;
	}

	public void setBody(Statement b){
		this.body = b;
	}

	public Statement getBody(){
		return this.body;
	}

	@Override
	public String toString() {
		return "for" + " " + operator + " " + expr; //VER
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}