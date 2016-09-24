package ir.semcheck;

import ir.ast.*;

public class SymbolInfo <T extends Declaration> {
	
	private String type;
	private boolean method;
	private String name;
	private int column;
	private int line;

	public  SymbolInfo(T ast){
		this.name = ast.getName();
		this.column = ast.getColumnNumber();
		this.line  = ast.getLineNumber();
	}

	public SymbolInfo(String name, int column, int line){
		this.name = name;
		this.column = column;
		this.line = line;
	}

	public SymbolInfo(String type,String name, int column, int line){
		this.type = type;
		this.name = name;
		this.column = column;
		this.line = line;
		this.method=false;
	}

	public SymbolInfo(boolean method, String type,String name, int column, int line){
		this.type = type;
		this.name = name;
		this.column = column;
		this.line = line;
		this.method=method;
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
		this.type=type;
	}

}