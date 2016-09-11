package ir.ast;

import java.util.List;
public class ParamDecl extends AST{
	private Type type;
	private String name;

	public ParamDecl(){
		
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