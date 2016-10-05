package ir.ast;

import ir.ASTVisitor;
import java.util.List;
import java.util.LinkedList;


public class MethodCall extends Expression{

	private List<IdDecl> ids;
	private List<Expression> params;

	public MethodCall(List<String> ids, List<Expression> params, int line, int col){
		super(line,col);
		this.ids = new LinkedList<IdDecl>();
		for(String id: ids){
			this.ids.add(new IdDecl(id, line, col));
		}
		this.params = params;
	}


	public MethodCall(int line, int col){
		super(line,col);
		this.ids    = new LinkedList<IdDecl>();
		this.params = new LinkedList<Expression>();
	}
	
	public List<IdDecl> getIds(){
		return this.ids;
	}

	public void setIds(List<IdDecl> ids){
		this.ids = new LinkedList<IdDecl>();
		for(IdDecl id: ids){
			this.ids.add(id);
		}
	}

	public List<Expression> getParams () {
		return this.params;
	}

	public void setParams(List<Expression> params){
		this.params = params;
	}

	public String toString(){
		return this.ids.toString() +"-"+ this.params.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

}
