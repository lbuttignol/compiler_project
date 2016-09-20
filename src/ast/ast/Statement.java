package ir.ast;
import ir.ASTVisitor;
public abstract class Statement extends AST {

	public Statement(int line, int col){
		super(line,col);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

}
