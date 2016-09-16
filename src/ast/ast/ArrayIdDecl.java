package ir.ast;

import java.util.List;
public class ArrayIdDecl extends IdDecl {

	private Integer number;
	
	public ArrayIdDecl (String name,Integer number){
		super(name);
		this.number = number;
	}

	public Integer getNumber(){
		return this.number;
	}

	public void setNumber(Integer number){
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

