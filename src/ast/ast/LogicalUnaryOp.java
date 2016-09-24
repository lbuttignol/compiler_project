package ir.ast;

import ir.ASTVisitor;

public class LogicalUnaryOp extends UnaryOpExpr {

	public LogicalUnaryOp(UnaryOpType operator, Expression operand , int line, int col){
		super(operator,operand,line,col);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}