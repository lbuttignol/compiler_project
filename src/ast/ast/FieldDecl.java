package ir.ast;

import ir.ASTVisitor;
import java.util.List;

public class FieldDecl extends AST{
	private Type type;
	private List<IdDecl> names;

	public FieldDecl(Type type, List<IdDecl> names){
		this.type = type;
		this.names = names;
	}

	public Type getType(){
		return this.type;
	}

	public void setType(Type t){
		this.type = t;
	} 

	public List<IdDecl> getName(){
		return this.names;
	}

	public void setName(List<IdDecl> n){
		this.names = n;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}