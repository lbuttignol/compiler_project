package ir.semcheck;

import ir.ast.*;
import java.util.List;
import java.util.LinkedList;

public class SymbolInfo <T extends Declaration> {
	
	private String type;
	private boolean method;
	private String name;
	private int column;
	private int line;
	private List<IdDecl> methodList;	//Se incluyen las 3 listas en symbol info 
	private List<IdDecl> attList;		//independientemente de que sean o no necesarias
	//private List<ParamDecl> paramList; 
	
	public  SymbolInfo(T ast){
		this.name 		= ast.getName();
		this.column 	= ast.getColumnNumber();
		this.line  		= ast.getLineNumber();
		this.methodList = new LinkedList<IdDecl>();
		this.attList	= new LinkedList<IdDecl>();
	}

	public SymbolInfo(String type, T ast){
		this.type 		= type;
		this.name 		= ast.getName();
		this.column 	= ast.getColumnNumber();
		this.line 		= ast.getLineNumber();
		this.method 	= false;
		this.methodList = new LinkedList<IdDecl>();
		this.attList	= new LinkedList<IdDecl>();
	}

	public SymbolInfo(boolean method, String type,T ast){
		this.type 		= type;
		this.name 		= ast.getName();
		this.column 	= ast.getColumnNumber();
		this.line 		= ast.getLineNumber();
		this.method 	= method;
		this.methodList = new LinkedList<IdDecl>();
		this.attList	= new LinkedList<IdDecl>();
	}

	public boolean isMethod(){
		return this.method;
	}

	public String getType(){
		return this.type;
	}

	public String getName(){
		return this.name;
	}

	public int getColumnNumber(){
		return this.column;
	}

	public int getLineNumber(){
		return this.line;
	}

	public void setType(String type){
		this.type = type;
	}

	public void addMethod(IdDecl meth){
		this.methodList.add(meth);
	}

	public void addAttribute(IdDecl att){
		this.attList.add(att);
	}

	public void addMethodList(List<MethodDecl> methL ){
		this.methodList = new LinkedList<IdDecl>();
		for(MethodDecl meth: methL){
			this.methodList.add(new IdDecl(meth.getName(), meth.getLineNumber(), meth.getColumnNumber()));
		}
	}

	public void addAttList(List<FieldDecl> fieldList ){
		this.attList = new LinkedList<IdDecl>();
		for(FieldDecl att: fieldList){
			List<IdDecl> ids = att.getNames();
			for(IdDecl id: ids){
				this.attList.add(id);
			}
		}
	}

	public void addParamList(List<ParamDecl> paramL ){
		this.attList = new LinkedList<IdDecl>();
		for(ParamDecl param: paramL){
			this.attList.add(new IdDecl(param.getName(), param.getLineNumber(), param.getColumnNumber()));
		}
	}

	public List<IdDecl> getAttList(){
		return this.attList;
	}

	public List<IdDecl> getMethodList(){
		return this.methodList;
	}

}