package ir.ast;

import java.util.List;
public class ClassDecl extends AST{
	private String name;
	private List<FieldDecl> attributes;
	private List<MethodDecl> methods;

	public String getName(){
		return this.name;
	}

	public void setName(String n){
		this.name = n;
	}

	public List<FieldDecl> getAttributes(){
		return this.attributes;
	}

	public void setAttributes(List<FieldDecl> atts){
		this.attributes = atts;
	}

	public List<MethodDecl> getMethods(){
		return this.methods;
	}

	public void setMethods(List<MethodDecl> meth){
		this.methods = meth;
	}
}