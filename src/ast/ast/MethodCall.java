package ir.ast;

import java.util.List;
import java.util.LinkedList;
public class MethodCall extends AST{
	private List<String> ids;
	private List<Expression> params;

	public MethodCall(List<String> ids, List<Expression> params){
		this.ids    = ids;
		this.params = params;
	}


	public MethodCall(){
		this.ids    = new LinkedList<String>();
		this.params = new LinkedList<Expression>();
	}
	
	public List<String> getIds(){
		return this.ids;
	}

	public void setIds(List<String> ids){
		this.ids = ids;
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


}