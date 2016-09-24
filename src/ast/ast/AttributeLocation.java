package ir.ast;

import java.util.List;

import ir.ASTVisitor;

public class AttributeLocation extends Location {
	
	private int blockId;


	public AttributeLocation(List<String>ids, int line, int col){
		super(ids,line,col);
		this.blockId = -1;
	}

/*	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
*/

	@Override
	public String toString() {
		return ids.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
