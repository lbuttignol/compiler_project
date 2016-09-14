package ir.ast;
import java.util.List;
import java.util.LinkedList;

public abstract class Location extends Expression {
	protected List<String> ids;

	public Location(String id){
		this.ids = new LinkedList<String>();
		this.ids.add(id);
	}

	public Location(List<String> ids){
		this.ids = ids;
	}

	public Location(List<String> ids, Expression expr){
		this.ids  = ids;
		this.expr = expr;
	}

	public List<String> getIds(){
		return this.ids;
	}

	public void setIds(List<String> ids){
		this.ids = ids;
	}




	public String toString(){
		return ids.toString();
	}
}
