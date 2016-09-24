package ir.ast;
import ir.ASTVisitor;

public abstract class AbstractReturn extends Statement{
	public AbstractReturn(int line, int col){
		super(line,col);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}

}
