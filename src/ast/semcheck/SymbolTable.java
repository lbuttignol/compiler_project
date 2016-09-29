package ir.semcheck;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import ir.ast.*;


public class SymbolTable {
	
	ArrayList <List<SymbolInfo>> symbolTable; 
	private int top;

	public SymbolTable(){
		this.symbolTable = new ArrayList<List<SymbolInfo>>();
		this.top = -1;
	}

	public void addDeclare(SymbolInfo decl){
		if (this.contains(decl, this.top)){
			new ir.error.Error(decl.getLineNumber(),decl.getColumnNumber(),"Repeated identifier "+decl.getName());
		}else{
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

	public SymbolInfo getCurrentSymbolInfo(String id){
		List<SymbolInfo> symList;
		for(int i=top; i>=0; i--){
			symList = this.symbolTable.get(i);
			for (SymbolInfo sym: symList){
				if (sym.getName().equals(id)){
					return sym;
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

	public boolean reachable(List<IdDecl> idList){
		boolean result = true;

		if (idList.size()>0){
			IdDecl firstElem = idList.remove(0);
			SymbolInfo symb = getCurrentSymbolInfo(firstElem.getType());
			if (symb!=null){
				if (idList.size()>0){
					System.out.println("Repeated identifier ");
					List <IdDecl> attList = symb.getAttList();	
					List <IdDecl> methodList = symb.getMethodList();
					IdDecl lastElem = idList.get(0);
					result = result && 
							(((attList!=null)?contains(attList,lastElem):false)||((methodList!=null)?contains(methodList,lastElem):false));
					if (result){
						SymbolInfo symbNavigated = getCurrentSymbolInfo(lastElem.getName());
						//Chequear si se setea si es method o no en Builder
						if (symbNavigated.isMethod()){
							//Check params
						}
					}
				}
			}else{
				return false;
			}
		}
		return result;
	}


	private boolean contains(List<IdDecl> ids, IdDecl id){
		for (IdDecl idC : ids){
			if (idC.getName().equals(id.getName())){
				return true;
			}
		}
		return false;
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