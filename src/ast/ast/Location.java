package ir.ast;
import java.util.List;
import java.util.LinkedList;
import ir.ASTVisitor;
public abstract class Location extends Expression {
	protected List<IdDecl> ids;
	protected List<String> idsName;
	private Declaration decl;

	public Location(List<String> ids, int line, int col){
		super(line,col);
		this.idsName = ids;
		this.ids = new LinkedList<IdDecl>();
		for(String id: ids){
			this.ids.add(new IdDecl(id, line, col, this.getType()));
		}
	}

	public Location(List<String> ids, Expression expr, int line, int col){
		super(line,col);
		this.idsName = ids;
		this.ids = new LinkedList<IdDecl>();
		for(String id: ids){
			this.ids.add(new IdDecl(id, line, col, this.getType()));
		}
		this.expr = expr;
	}

	public List<IdDecl> getIds(){
		return this.ids;
	}

	public void setIds(List<IdDecl> ids){
		for(IdDecl id: ids){
			ids.add(id);
		}
	}

	public List<String> getIdsName(){
		return this.idsName;
	}

	public void setIdsName(List<String> ids){
	    this.idsName=ids;
	}

	public String toString(){
		return ids.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

	public void setDeclaration(Declaration d){
		this.decl = d;
	}

	public Declaration getDeclaration(){
		return this.decl;
	}

}
