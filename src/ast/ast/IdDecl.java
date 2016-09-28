package ir.ast;

import ir.ASTVisitor;
import java.util.List;

public class IdDecl extends Declaration {
	String type;
	
	public IdDecl( int line, int col){
		super(line,col,null);
	}

	public IdDecl (String name, int line, int col){
		super(line,col,name);
	}

	public IdDecl(String name, int line, int col, String type){
		super(line,col,name);
		this.type=type;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String type){
		this.type = type;
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

