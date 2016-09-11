package ir.ast;

import ir.ASTVisitor;
import java.util.ArrayList;

public class WhileStmt extends Statement {
	private Expression condition;
	private Statement body;

	public WhileStmt(Expression cond, Statement b){
		this.condition = cond; 
		this.body = b;
	} 

	public void setCondition(Expression cond){
		this.condition = cond;
	}

	public Expression getCondition(){
		return this.condition;
	}

	public void setBody(Statement b){
		this.body = b;
	}

	public Statement getBody(){
		return this.body;
	}

	@Override
	public String toString(){
		return "while" + condition + '\n' + body.toString(); 
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}