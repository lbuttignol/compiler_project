package ir.ast;

import ir.ASTVisitor;

public class LogicalUnaryOp extends UnaryOpExpr {

	public LogicalUnaryOp(BinOpType operator, Expression operand ){
		super(operator,operand);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}