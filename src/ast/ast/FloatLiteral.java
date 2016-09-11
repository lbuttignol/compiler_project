package ir.ast;

import ir.ASTVisitor;

public class FloatLiteral extends Literal {
	private String rawValue;
	private float value;
	
	/*
	 * Constructor for float lleiteral that takes a string as an input
	 * @param: String integer
	 */
	public FloatLiteral(String val){
		this.rawValue = val; // Will convert to float value in semantic check
		this.value = 0;
	}

	@Override
	public Type getType() {
		return Type.FLOAT;
	}

	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
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
