package ir.semcheck;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import ir.*;


public class SymbolTable {
	
	ArrayList <List<SymbolInfo>> symbolTable; 
	private int top;

	public SymbolTable(){
		this.symbolTable = new ArrayList<List<SymbolInfo>>();
		this.top = -1;
	}

	public void addDeclare(SymbolInfo decl){
		if (this.contains(decl, this.top)){
			//System.out.println("Decl - ERROR");
			new ir.error.Error(decl.getLineNumber(),decl.getColumnNumber(),"Repeated identifier "+decl.getName());
		}else{
			//System.out.println("Decl - OK");
			this.symbolTable.get(this.top).add(decl);
		}
	}

	public void addDeclareList(List<SymbolInfo> declList){
		Iterator<SymbolInfo> i = declList.iterator();
		while (i.hasNext()){
			this.addDeclare(i.next());
		}
	}

	public String getCurrentType(String id){
		List<SymbolInfo> symList;
		for(int i=top; i>=0; i--){
			symList = this.symbolTable.get(i);
			for (SymbolInfo sym: symList){
				if (sym.getName().equals(id)){
					return sym.getType();
				}
			}
		}
		return null;
	}


	public void newLevel(){
		List<SymbolInfo> SymbolInfoList = new LinkedList<SymbolInfo>();
		this.symbolTable.add(SymbolInfoList);
		this.top++;
	}

	public void closeLevel(){
		this.symbolTable.remove(top);
		this.top--;

	}

	public boolean reachable(SymbolInfo sym){
		boolean result = false;
		for(int i=top; i>=0; i--){
			result= result || this.contains(sym ,i);
		}
		return result;

	}

	private boolean contains(SymbolInfo sym, int i){
		List<SymbolInfo> symList = this.symbolTable.get(i);
		Iterator<SymbolInfo> itSym = symList.iterator();
		while (itSym.hasNext()){
			if (itSym.next().getName().equals(sym.getName())){
				return true;
			}
		}
		return false;
	}

	public String getMethodType(){
		List<SymbolInfo> symList;
		for(int i=top; i>=0; i--){
			symList = this.symbolTable.get(i);
			for (SymbolInfo sym: symList){
				if (sym.isMethod()){
					return sym.getType();
				}
			}
		}
		return null;
	}

}