package ir.ast;

import ir.ASTVisitor;
import ir.ast.Type;

public class BooleanLiteral extends Literal {
	private String rawValue;
	private Boolean value;
	//private Type type = (Type)BOOLEAN;
	
	/*
	 * Constructor for boolean literal that takes a string as an input
	 * @param: String integer
	 */
	public BooleanLiteral(String val){
		this.rawValue = val; // Will convert to boolean value in semantic check
		this.value = null;
	}

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	public String getStringValue() {
		return this.rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	public Boolean getValue() {
		return this.value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}
	
	public String getRawValue() {
		return this.rawValue;
	}

	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

	@Override
	public String toString() {
		return this.rawValue;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
