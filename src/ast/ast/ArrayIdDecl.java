package ir.ast;

import java.util.List;
public class ArrayIdDecl extends IdDecl {

	private int number;
	
	public ArrayIdDecl (String name,int number, int line, int col){
		super(name,line,col);
		this.number = number;
	}

	public int getNumber(){
		return this.number;
	}

	public void setNumber(int number){
		this.number = number;
	}

	@Override
	public String toString() {
		return this.name+"-"+String.valueOf(number);
	}

	/*
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
	*/
}

