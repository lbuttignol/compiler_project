package ir.ast;

import ir.ASTVisitor;

public class ArithmeticBinOp <T extends Expression> extends BinOpExpr<T> {

	public ArithmeticBinOp(T l, BinOpType op, T r){
		super(l,op,r);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}