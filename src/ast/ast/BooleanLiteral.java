package ir.ast;

import ir.ASTVisitor;

public class BooleanLiteral extends Literal {
	private String rawValue;
	private Boolean alue;
	
	/*
	 * Constructor for boolean literal that takes a string as an input
	 * @param: String integer
	 */
	public BooleanLiteral(String val){
		rawValue = val; // Will convert to boolean value in semantic check
		value = null;
	}

	@Override
	public Type getType() {
		return Type.Boolean;
	}

	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}
	
	public String getRawValue() {
		return rawValue;
	}

	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

	@Override
	public String toString() {
		return rawValue;
	}

	@Override
	public void accept(ASTVisitor<T> v) {
		v.visit(this);
	}
}
