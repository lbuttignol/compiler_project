package ir.intermediateCode;

import ir.ast.*;
public class Operand {
	private String labelName;
	private AST expression;
	private Integer labelNumber;

	public Operand(AST e,String name, Integer number){
		this.expression = e;
		this.labelName = name;
		this.labelNumber = number;
	}

	public Operand(AST e){
		this.expression = e;
		this.labelName = "";
		this.labelNumber = 0;
	}

	public Operand(String name){
		this.expression = null;
		this.labelName = name;
		this.labelNumber = 0;
	}

	public Operand(Integer number){
		this.expression = null;
		this.labelName = "";
		this.labelNumber = number;
	}

	public void setName(String name){
		this.labelName = name;	
	}

	public String getName(){
		return this.labelName;
	}

	public void setNumber(Integer number){
		this.labelNumber = number;	
	}

	public Integer getNumber(){
		return this.labelNumber;
	}

	public void setExpression(AST exp){
		this.expression = exp;	
	}

	public AST getExpression(){
		return this.expression;
	} 

	@Override
	public String toString(){
		return this.expression.toString() + this.labelName.toString() ;
	}
}