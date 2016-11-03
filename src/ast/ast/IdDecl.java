package ir.ast;

import ir.ASTVisitor;
import java.util.List;
import java.util.LinkedList;

public class IdDecl extends Declaration {
	String type;
	private Boolean isAttribute;
	private ClassDecl classRef;

	public IdDecl( int line, int col){
		super(line,col,null);
		this.isAttribute =false;
	}

	public IdDecl (String name, int line, int col){
		super(line,col,name);
		this.isAttribute = false;
	}

	public IdDecl(String name, int line, int col, String type){
		super(line,col,name);
		this.type = type;
		this.isAttribute = false;
	}

	public Boolean isAttribute(){
		return this.isAttribute;
	}

	public void setIsAttribute(Boolean isAttribute){
		this.isAttribute = isAttribute;
	}

	public ClassDecl getClassRef(){
		return this.classRef;
	}

	public void setClassRef(ClassDecl classRef){
		this.classRef =classRef;
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

