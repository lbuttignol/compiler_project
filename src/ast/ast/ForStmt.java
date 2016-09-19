package ir.ast;

import ir.ASTVisitor;

public class ForStmt extends Statement {
	String counterName;
	Expression initValue;
	Expression endValue;
	Statement body;

	public ForStmt(String name,Expression init, Expression end, Statement b,int line, int col){
		super(line,col);
		this.counterName = name;
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
		return "for " + this.counterName + ", " + initValue.toString() + " ," + endValue.toString() + ", "  + body.toString(); //VER
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}