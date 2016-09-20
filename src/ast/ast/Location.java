package ir.ast;
import java.util.List;
import java.util.LinkedList;
import ir.ASTVisitor;
public abstract class Location extends Expression {
	protected List<String> ids;

	public Location(String id, int line, int col){
		super(line,col);
		this.ids = new LinkedList<String>();
		this.ids.add(id);
	}

	public Location(List<String> ids, int line, int col){
		super(line,col);
		this.ids = ids;
	}

	public Location(List<String> ids, Expression expr, int line, int col){
		super(line,col);
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

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}


}
