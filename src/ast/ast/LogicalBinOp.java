package ir.ast;

import ir.ASTVisitor;

public class LogicalBinOp extends BinOpExpr {

	public LogicalBinOp(Expression l, BinOpType op, Expression r){
		super(l,op,r);
	}

	@Override
	public void accept(ASTVisitor v) {
		PUv.visit(this);
	}
}