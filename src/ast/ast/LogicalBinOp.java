package ir.ast;

import ir.ASTVisitor;

public class LogicalBinOp <T extends Expression> extends BinOpExpr<T> {

	public LogicalBinOp(T l, BinOpType op, T r){
		super(l,op,r);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}