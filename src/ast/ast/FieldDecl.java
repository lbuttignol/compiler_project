package ir.ast;

import ir.ASTVisitor;
import java.util.List;

public class FieldDecl extends AST{
	protected String type;
	private List<IdDecl> names;
	private Boolean isAttribute;
	
	public FieldDecl(String type, List<IdDecl> names, int line, int col){
		super(line,col);
		this.type = type;
		this.names = names;
		this.isAttribute = false;
		for (IdDecl name : names){
			name.setType(type.toUpperCase());
		}
	}

	public Boolean isAttribute(){
		return this.isAttribute;
	}

	public void setIsAttribute(Boolean isAttribute){
		this.isAttribute = isAttribute;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String t){
		this.type = t;
	} 

	public List<IdDecl> getNames(){
		return this.names;
	}

	public void setNames(List<IdDecl> n){
		this.names = n;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}