package ir.ast;
public abstract class Literal extends Expression {

	public Literal( int line, int col){
		super(line,col);
	}
	/*
	 * @return: returns Type of Literal instance
	 */
	public abstract String getType();
}
