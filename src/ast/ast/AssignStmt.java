package ir.ast;

import ir.ASTVisitor;

public class AssignStmt extends Statement {
	private Location location;
	private Expression expr;
	private AssignOpType operator;

	public AssignStmt(Location loc, AssignOpType op, Expression e, int line, int col){
		super(line,col);
		this.location = loc;
		this.expr = e;
		this.operator = op;
	}
	
	public void setLocation(Location loc) {
		this.location = loc;
	}
	
	public Location getLocation(){
		return this.location;
	}
	
	public void setExpression(Expression e) {
		this.expr = e;
	}
	
	public Expression getExpression() {
		return this.expr;
	}
	
	public AssignOpType getOperator() {
		return operator;
	}

	public void setOperator(AssignOpType operator) {
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		return location.toString() + " " + operator.toString() + " " + expr.toString();
		
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
