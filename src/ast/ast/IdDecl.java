package ir.ast;

import ir.ASTVisitor;
import java.util.List;

public class IdDecl extends AST {

	protected String name;
	
	public IdDecl (String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}

