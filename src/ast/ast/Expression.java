package ir.ast;
import ir.ASTVisitor;
import java.util.List;
import java.util.LinkedList;

public abstract class Expression extends AST{
	protected Expression expr;
	protected String type;
	private String id  = "a";
	private List<Integer> offset = new LinkedList<Integer>();

	public Expression( int line, int col){
		super(line,col);
		type = "UNDEFINED";
	}

	public Expression( int line, int col, String type){
		super(line,col);
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String t) {
		this.type = t;
	}

	public void setOff(Integer off){
		this.offset.add(off);
	}

	public Integer getLastOff(){
		return this.offset.get(offset.size()-1);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

}
