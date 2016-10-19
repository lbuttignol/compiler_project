package ir.ast;

import ir.ASTVisitor;
import java.util.List;
import java.util.LinkedList;

public class IdDecl extends Declaration {
	String type;
	private int offset = 0;

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
		this.offset=off;
	}

	public int getOff(){
		return this.offset;
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

