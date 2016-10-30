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
	private List<SymbolInfo> methodList;	//Se incluyen las 3 listas en symbol info 
	private List<IdDecl> attList;		//independientemente de que sean o no necesarias
	private Integer index;                  //Se utiliza cuando se desea encapsular un SymbolInfo
	private List<IdDecl> arrayList;
	private Declaration reference;
	//private List<ParamDecl> paramList; 
	
	public  SymbolInfo(T ast){
		this.name 		= ast.getName();
		//if (this.name.equalsIgnoreCase("val"))
			//System.out.println("akataval");
		this.column 	= ast.getColumnNumber();
		this.line  		= ast.getLineNumber();
		this.methodList = new LinkedList<SymbolInfo>();
		this.attList	= new LinkedList<IdDecl>();
		this.arrayList  = new LinkedList<IdDecl>();
		this.method 	= false;
		this.index		= null;
		this.reference  = ast;

	}

	public SymbolInfo(String type, T ast){
		this.type 		= type;
		this.name 		= ast.getName();
		this.column 	= ast.getColumnNumber();
		this.line 		= ast.getLineNumber();
		this.method 	= false;
		this.methodList = new LinkedList<SymbolInfo>();
		this.attList	= new LinkedList<IdDecl>();
		this.arrayList  = new LinkedList<IdDecl>();
		this.index		= null;
		this.reference  = ast;

	}
	public SymbolInfo(String type, T ast,int index){
		this.type 		= type;
		this.name 		= ast.getName();
		this.column 	= ast.getColumnNumber();
		this.line 		= ast.getLineNumber();
		this.method 	= false;
		this.methodList = new LinkedList<SymbolInfo>();
		this.attList	= new LinkedList<IdDecl>();
		this.arrayList  = new LinkedList<IdDecl>();
		this.index		= index;
		this.reference  = ast;

	}

	public SymbolInfo(boolean method,T ast){
		this.type 		= "UNDEFINED";
		this.name 		= ast.getName();
		this.column 	= ast.getColumnNumber();
		this.line 		= ast.getLineNumber();
		this.method 	= method;
		this.methodList = new LinkedList<SymbolInfo>();
		this.attList	= new LinkedList<IdDecl>();
		this.arrayList  = new LinkedList<IdDecl>();
		this.index		= null;
		this.reference  = ast;

	}
	public SymbolInfo(boolean method, String type,T ast){
		this.type 		= type;
		this.name 		= ast.getName();
		this.column 	= ast.getColumnNumber();
		this.line 		= ast.getLineNumber();
		this.method 	= method;
		this.methodList = new LinkedList<SymbolInfo>();
		this.attList	= new LinkedList<IdDecl>();
		this.arrayList  = new LinkedList<IdDecl>();
		this.index		= null;
		this.reference  = ast;


	}

	public boolean isArray(){
		return (this.index!=null);
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

	public void addMethod(MethodDecl meth){
		SymbolInfo symAux=new SymbolInfo(true,meth.getType(),meth);
		symAux.addParamList(meth.getParams());
		this.methodList.add(symAux);
	}

	public void addAttribute(IdDecl att){
		this.attList.add(att);
	}

	public void addMethodList(List<MethodDecl> methL ){
		this.methodList = new LinkedList<SymbolInfo>();
		for(MethodDecl meth: methL){
			this.addMethod(meth);
		}
	}

	public void addAttList(List<FieldDecl> fieldList ){
		this.attList = new LinkedList<IdDecl>();
		for(FieldDecl att: fieldList){
			List<IdDecl> ids = att.getNames();
			for(IdDecl id: ids){
				if (id instanceof ArrayIdDecl){
					this.arrayList.add(id);
				}else{
					this.attList.add(id);
				}
			}
		}
	}

	public void addParamList(List<ParamDecl> paramL ){
		this.attList = new LinkedList<IdDecl>();
		for(ParamDecl param: paramL){
			this.attList.add(new IdDecl(param.getName(), param.getLineNumber(), param.getColumnNumber(), param.getType()));
		}
	}

	public List<IdDecl> getAttList(){
		return this.attList;
	}

	public List<SymbolInfo> getMethodList(){
		return this.methodList;
	}

	public List<IdDecl> getArrayList(){
		return this.arrayList;
	}

	public void setArrayList(List<IdDecl> arrayList){
		this.arrayList=arrayList;
	}

	public void setIndex(Integer index){
		this.index=index;
	}

	public void setReference (Declaration idDecl){
		this.reference = idDecl;
	}

	public Declaration getReference(){
		return this.reference;
	}

}