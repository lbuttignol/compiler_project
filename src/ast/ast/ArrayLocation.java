package ir.ast;

import java.util.List;

import ir.ASTVisitor;

public class ArrayLocation extends Location {
	
	private int blockId;
	private Expression expr;

	public ArrayLocation(List<String> ids, Expression expr, int line, int col){
		super(ids,line,col);
		this.expr    = expr;
		this.blockId = -1;
	}
	
	public ArrayLocation(List<String> i, int line, int col){
		super(i,line,col);
	}

/*	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
*/	

	public Expression getExpression(){
		return this.expr;
	}

	public void setExpression(Expression expr){
		this.expr = expr;
	}

	@Override
	public String toString() {
		return ids.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

}
