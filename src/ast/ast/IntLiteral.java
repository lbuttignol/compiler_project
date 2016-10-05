package ir.ast;

import ir.ASTVisitor;

public class IntLiteral extends Literal {
	private String rawValue;
	private Integer value;
	
	/*
	 * Constructor for int literal that takes a string as an input
	 * @param: String integer
	 */
	public IntLiteral(String val, int line, int col){
		super(line,col,"INTEGER");
		rawValue = val; // Will convert to int value in semantic check
		value = Integer.valueOf(val);
	}
	public IntLiteral(Integer val,int line, int col){
		super(line,col,"INTEGER");
		rawValue = val.toString();
		value = Integer.valueOf(val);
	}

	@Override
	public String getType() {
		return "INTEGER";
	}

	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
		this.value = Integer.valueOf(stringValue);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		this.rawValue = String.valueOf(value);

	}
	
	public String getRawValue() {
		return rawValue;
	}

	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
