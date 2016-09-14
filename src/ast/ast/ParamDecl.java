package ir.ast;

import java.util.List;
public class ParamDecl extends AST{
	private String type;
	private String name;

	public ParamDecl(String type, String name){
		this.type = type;
		this.name = name;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String t){
		this.type = t;
	} 

	public String getName(){
		return this.name;
	}

	public void setName(String n){
		this.name = n;
	}
}