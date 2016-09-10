package ir;

import ir.ast.*;

// Abstract visitor
public interface ASTVisitor<T> {
// visit statements
	T visit(AssignStmt stmt);
	T visit(ReturnStmt stmt);
	T visit(ReturnVoidStmt stmt);
	T visit(IfThenStmt stmt);
	T visit(IfThenElseStmt stmt);
	T visit(BlockStmt stmt); 		//VER
	T visit(BreakStmt stmt);
	T visit(ContinueStmt stmt);
	T visit(WhileStmt stmt);
	
// visit expressions
	T visit(BinOpExpr expr);;
	
// visit literals	
	T visit(IntLiteral lit);

// visit locations	
	T visit(VarLocation loc);
}
