package ir.ast;

import ir.ASTVisitor;
import java.util.List;


public class AttributeLocation extends Location {
	
	private int blockId;
	private List<String> ids;

	public AttributeLocation(String id, List<String>ids) {
		super(id);
		this.ids     = ids;
		this.blockId = -1;
	}
	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	
	public List<String> getIds(){
		return this.ids;
	}

	public void setIds(List<String> ids){
		this.ids = ids;
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
