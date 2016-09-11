package ir.ast;

import ir.ASTVisitor;

public class ArithmeticBinOp extends BinOpExpr {

	public ArithmeticBinOp(Expression l, BinOpType op, Expression r){
		super(l,op,r);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}