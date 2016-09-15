package ir.ast;

import ir.ASTVisitor;
import java.util.List;

public class FieldDecl extends AST{
	private String type;
	private List<IdDecl> names;

	public FieldDecl(String type, List<IdDecl> names, int line, int col){
		super(line,col);
		this.type = type;
		this.names = names;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String t){
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