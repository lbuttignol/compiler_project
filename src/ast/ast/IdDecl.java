package ir.ast;

import ir.ASTVisitor;
import java.util.List;

public class IdDecl extends Declaration {

	
	public IdDecl( int line, int col){
		super(line,col,null);
	}

	public IdDecl (String name, int line, int col){
		super(line,col,name);
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

