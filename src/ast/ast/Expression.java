package ir.ast;
import ir.ASTVisitor;
public abstract class Expression extends AST{
	protected Expression expr;
	protected String type;
	private String id = "a";
	public Expression( int line, int col){
		super(line,col);
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String t) {
		this.type = t;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

}
