package ir.ast;

public abstract class Expression extends AST{
	protected Expression expr;
	protected String type;
	private String id = "a";
	public Expression(){
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String t) {
		this.type = t;
	}

	@Override
	public String toString(){
		return this.id;
	}
}
