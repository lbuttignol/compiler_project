package ir.ast;

import ir.ASTVisitor;

public abstract class AST {
	private int lineNumber;
	private int colNumber;

	public AST(int line, int col){
		this.lineNumber = line;
		this.colNumber = col;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(int ln) {
		lineNumber = ln;
	}
	
	public int getColumnNumber() {
		return colNumber;
	}
	
	public void setColumnNumber(int cn) {
		colNumber = cn;
	}
	
/*
	public void accept(ASTVisitor v){
		v.visit(this);
	}
*/
}
