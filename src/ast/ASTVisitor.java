package ir;

import ir.ast.*;

// Abstract visitor
public interface ASTVisitor {
// visit statements
	public void visit(AST stmt);
	public void visit(AssignStmt stmt);
	public void visit(ReturnStmt stmt);
	public void visit(ReturnVoidStmt stmt);
	public void visit(IfThenStmt stmt);
	public void visit(IfThenElseStmt stmt);
	public void visit(Block stmt); 		//VER
	public void visit(BreakStmt stmt);
	public void visit(ContinueStmt stmt);
	public void visit(WhileStmt stmt);
	
// visit expressions
	public void visit(BinOpExpr expr);
	
// visit literals	
	public void visit(IntLiteral lit);

// visit locations	
	public void visit(VarLocation loc);
}
