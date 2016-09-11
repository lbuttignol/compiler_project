package ir.ast;

import ir.ASTVisitor;

public class VarLocation extends Location {
	private int blockId;

	public VarLocation(String id) {
		this.id = id;
		this.blockId = -1;
	}
	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
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
