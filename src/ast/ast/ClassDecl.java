package ir.ast;

import java.util.List;
public class ClassDecl extends AST{
	private String name;
	private List<FieldDecl> attributes;
	private List<MethodDecl> methods;

	public ClassDecl (String name,List<FieldDecl> attributes, List<MethodDecl> methods, int line, int col){
		super(line,col);
		this.name       = name;
		this.attributes = attributes;
		this.methods    = methods;
	}

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