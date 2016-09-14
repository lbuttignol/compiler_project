		package ir.ast;

import ir.ASTVisitor;
import java.util.List;

public class AttributeArrayLocation extends Location {
	
	private int blockId;
	private Expression expr;
	
	public AttributeArrayLocation(List<String>ids, Expression expr) {
		super(ids);
		this.expr    = expr;
		this.blockId = -1;
	}


	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	

	public Expression getExpr(){
		return this.expr;
	}

	public void setExpr(Expression expr){
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
