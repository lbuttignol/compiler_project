package ir.ast;

import ir.ASTVisitor;

public class ArrayLocation extends Location {
	
	private int blockId;
	private Expression expr;

	public ArrayLocation(String id, Expression expr) {
		super(id);
		this.expr    = expr;
		this.blockId = -1;
	}
	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	
	public Expression getIds(){
		return this.expr;
	}

	public void setIds(Expression ids){
		this.expr = expr;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
