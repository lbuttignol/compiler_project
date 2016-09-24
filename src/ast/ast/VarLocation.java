package ir.ast;

import ir.ASTVisitor;
import java.util.List;
import java.util.LinkedList;

public class VarLocation extends Location {
	private int blockId;

	public VarLocation(String id) {
		List<String> ids = new LinkedList<String> ();
		super(ids.add(id));
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
