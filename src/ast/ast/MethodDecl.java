package ir.ast;

import java.util.LinkedList;
import java.util.List;

public class MethodDecl extends AST{
	private Type type;
	private String name;
	private List<ParamDecl> params;
	private List<Statement> body;

	public MethodDecl( Type type,String name,List<ParamDecl> params, 
					   List<Statement> body){
		this.type      = type;
		this.name      = name;
		this.params    = params;
		this.body      = body;

	}

	public MethodDecl( Type type,String name, 
					   List<Statement> body){
		this.type      = type;
		this.name      = name;
		this.params    =  new LinkedList<ParamDecl>();
		this.body      = body;

	}

	public String getName(){
		return this.name;
	}

	public void setName(String n){
		this.name = n;
	}

	public Type getReturnType(){
		return this.type;
	}

	public void setType(Type t){
		this.type = t;
	}

	public List<ParamDecl> getParams(){
		return this.params;
	}

	public void setParams(List<ParamDecl> p){
		this.params = p;
	}

	public List<Statement> getBody(){
		return this.body;
	}

	public void setBody(List<Statement> b){
		this.body = b;
	}
}