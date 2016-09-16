package ir.ast;

import ir.ASTVisitor;

public class FloatLiteral extends Literal {
	private String rawValue;
	private Float value;
	
	/*
	 * Constructor for float lleiteral that takes a string as an input
	 * @param: String integer
	 */
	public FloatLiteral(Float val){
		//this.rawValue = val; // Will convert to float value in semantic check
		this.value = val;
	}

	@Override
	public String getType() {
		return "FLOAT";
	}

	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
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
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
