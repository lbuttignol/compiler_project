package ir.ast;

import ir.ASTVisitor;


public class LogicalBinOp  extends BinOpExpr {

	public LogicalBinOp(Expression l, BinOpType op, Expression r, int line, int col){
		super(l,op,r,line,col);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}