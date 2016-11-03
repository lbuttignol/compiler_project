package ir.ast;

import ir.ASTVisitor;
import java.util.List;
import java.util.LinkedList;

public class VarLocation extends Location {
	private int blockId;
	private Integer offset = 0;
	private Boolean isAttribute;

	public VarLocation(List<String> ids, int line, int col){
		super(ids,line,col);
		//List<String> ids = new LinkedList<String> ();
		//super(ids.add(id));
		this.blockId = -1;
		this.isAttribute = referenceToAtt();
	}

	public Boolean isAttribute(){
		return this.isAttribute;
	}

	public void setIsAttribute(Boolean isAttribute){
		this.isAttribute = isAttribute;
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

	public void setOff(Integer off){
		this.offset = off;
	}

	public Integer getOff(){
		if (getDeclaration()!=null){
			return this.getDeclaration().getOff();
		}else{
			return this.offset;
		}
	}

	public Boolean referenceToAtt(){
		if (getDeclaration()!=null){
			return this.isAttribute();
		}
		return false;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
