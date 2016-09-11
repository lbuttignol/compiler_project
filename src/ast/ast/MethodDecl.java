package ir.ast;

import java.util.List;
public class MethodDecl extends AST{
	private String name;
	private Type retType;
	private List<ParamDecl> params;
	private List<FieldDecl> variables;
	private List<Statement> body;

	public MethodDecl(){

	}

	public String getName(){
		return this.name;
	}

	public void setName(String n){
		this.name = n;
	}

	public Type getReturnType(){
		return this.retType;
	}

	public void setType(Type t){
		this.retType = t;
	}

	public List<ParamDecl> getParams(){
		return this.params;
	}

	public void setParams(List<ParamDecl> p){
		this.params = p;
	}

	public List<FieldDecl> getVariables(){
		return this.variables;
	}

	public void setVariables(List<FieldDecl> v){
		this.variables = v;
	}

	public List<Statement> getBody(){
		return this.body;
	}

	public void setBody(List<Statement> b){
		this.body = b;
	}
}