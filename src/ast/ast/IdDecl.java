package ir.ast;

import ir.ASTVisitor;
import java.util.List;
import java.util.LinkedList;

public class IdDecl extends Declaration {
	String type;
	List<Integer> offset = new LinkedList<Integer>();

	public IdDecl( int line, int col){
		super(line,col,null);
	}

	public IdDecl (String name, int line, int col){
		super(line,col,name);
	}

	public IdDecl(String name, int line, int col, String type){
		super(line,col,name);
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String type){
		this.type = type;
	}

	public void setOff (Integer off){
		this.offset.add(off);
	}

	public Integer getOff(){
		return this.offset.get(this.offset.size()-1);
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}

