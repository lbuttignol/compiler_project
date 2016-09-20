package ir.ast;
import ir.ASTVisitor;
public abstract class Literal extends Expression {

	public Literal( int line, int col){
		super(line,col);
	}
	/*
	 * @return: returns Type of Literal instance
	 */
	public abstract String getType();

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

}
