package ir.ast;

import java.util.List;
public class FieldDecl extends AST{
	private Type type;
	private String name;

	public FieldDecl(){
		
	}

	public Type getType(){
		return this.type;
	}

	public void setType(Type t){
		this.type = t;
	} 

	public String getName(){
		return this.name;
	}

	public void setName(String n){
		this.name = n;
	}
}