package ir.ast;
import ir.ASTVisitor;
public class Skip extends Statement{

	public Skip(int line, int col){
		super(line,col);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
