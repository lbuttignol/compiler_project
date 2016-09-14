package ir.ast;

public abstract class Statement extends AST {
	
	public Statement(){}

	public Statement(int line, int col){
		super(line,col);
	}
}
