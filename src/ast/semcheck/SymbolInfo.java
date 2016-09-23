package ir.semcheck;

import ir.ast.*;

public class SymbolInfo <T extends Declaration> {
	
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

	public String getName(){
		return this.name;
	}

	public int getColumnNumber(){
		return this.column;
	}

	public int getLineNumber(){
		return this.line;
	}

}