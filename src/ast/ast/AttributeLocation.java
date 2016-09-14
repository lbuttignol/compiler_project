package ir.ast;

import ir.ASTVisitor;
import java.util.List;


public class AttributeLocation extends Location {
	
	private int blockId;

	public AttributeLocation(List<String>ids) {
		super(ids);
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
		return ids.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
