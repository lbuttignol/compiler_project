package ir.ast;

public abstract class Expression extends AST{
	protected Expression expr;
	protected Type type;
	private String id = "a";
	public Expression(){
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type t) {
		this.type = t;
	}

	@Override
	public String toString(){
		return this.id;
	}
}
