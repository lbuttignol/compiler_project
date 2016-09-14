package ir.ast;

import java.util.List;

import ir.ASTVisitor;

public class AttributeLocation extends Location {
	
	private int blockId;
	private List<String> ids;

	public AttributeLocation(String id, List<String> i){
		super(id);
		this.ids     = i;
		this.blockId = -1;
	}

	public AttributeLocation(List<String> i){
		super(i);
	}

/*	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
*/	
	public List<String> getIds(){
		return this.ids;
	}

	public void setIds(List<String> i){
		this.ids = i;
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
