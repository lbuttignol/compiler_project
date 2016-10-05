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


	public boolean reachable(SymbolInfo sym, boolean isMethod){
		boolean result = false;
		for(int i=top; i>=0; i--){
			result= result || this.contains(sym ,i,isMethod);
		}
		return result;

	}

	public void reachable(List<IdDecl> idList,boolean isMethod,boolean isArray){
		boolean result = true;
		if (idList.size()>0){
			IdDecl firstElem = idList.remove(0);
			SymbolInfo symb = getCurrentSymbolInfo(firstElem.toString());
			SymbolInfo typeSymb = getCurrentSymbolInfo(symb.getType());
			if (typeSymb!=null){
				if (idList.size()>0){
					List <IdDecl> attList = typeSymb.getAttList();	
					List <SymbolInfo> methodList = typeSymb.getMethodList();
					List <IdDecl> arrayList = typeSymb.getArrayList();
					IdDecl lastElem = idList.get(0);
					if (isMethod){
						result = result && ((methodList!=null)?containsMeth(methodList,lastElem):false);
					}else{
						if (isArray){
							result = result && ((arrayList!=null)?contains(arrayList,lastElem):false);
						}else{
							result = result && ((attList!=null)?contains(attList,lastElem):false);
						}
					};
					if (!result){
						int line = lastElem.getLineNumber();
						int col  = lastElem.getColumnNumber();
						new ir.error.Error(line,col,"Unreachable identifier "+lastElem.getName());

					}
				}
			}else{
				int line = symb.getLineNumber();
				int col  = symb.getColumnNumber();
				new ir.error.Error(line,col,"The class "+symb.getName()+" not exist");
			}
		}
	}

	private boolean contains(List<IdDecl> ids, IdDecl id){
		for (IdDecl idC : ids){
			if (idC.getName().equals(id.getName())){
				return true;
			}
		}
		return false;
	}

	private boolean containsMeth(List<SymbolInfo> ids, IdDecl id){
		for (SymbolInfo idC : ids){
			if (idC.getName().equals(id.getName())){
				return true;
			}
		}
		return false;
	}

	private boolean contains(SymbolInfo sym, int i,boolean isMethod){
		List<SymbolInfo> symList = this.symbolTable.get(i);
		for (SymbolInfo symbolInfo : symList){
			if (((symbolInfo.getName().equals(sym.getName())))&&(symbolInfo.isMethod()==isMethod)) 
			{
				if (!isMethod){
					if (symbolInfo.isArray()==sym.isArray()){
						return true;
					}
				}else{
					return true;	
				}
			}
		}
		return false;
	}

	private boolean contains(SymbolInfo sym, int i){
		List<SymbolInfo> symList = this.symbolTable.get(i);
		for (SymbolInfo symbolInfo : symList){
			if ((symbolInfo.getName().equals(sym.getName()))) 
			{
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