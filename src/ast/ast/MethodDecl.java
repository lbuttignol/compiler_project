package ir.ast;

import java.util.LinkedList;
import java.util.List;

public class MethodDecl extends AST{
	private String type;
	private String name;
	private List<ParamDecl> params;
	private BodyDecl body;

	public MethodDecl( String type,String name,List<ParamDecl> params, 
					   BodyDecl body, int line, int col){
		super(line,col);
		this.type      = type;
		this.name      = name;
		this.params    = params;
		this.body      = body;

	}

	public MethodDecl( String type,String name, 
					   BodyDecl body, int line, int col){
		super(line,col);
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

	public String getReturnType(){
		return this.type;
	}

	public void setType(String t){
		this.type = t;
	}

	public List<ParamDecl> getParams(){
		return this.params;
	}

	public void setParams(List<ParamDecl> p){
		this.params = p;
	}

	public BodyDecl getBody(){
		return this.body;
	}

	public void setBody(BodyDecl b){
		this.body = b;
	}
}