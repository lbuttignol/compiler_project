package ir;

import ir.ast.*;

// Abstract visitor
public interface ASTVisitor {
// visit statements

	public void visit(AST stmt);
	public void visit(ArithmeticBinOp stmt);
	public void visit(ArithmeticUnaryOp stmt);
	public void visit(ArrayIdDecl stmt);
	public void visit(ArrayLocation stmt);
	public void visit(AssignStmt stmt);
	public void visit(AttributeArrayLocation stmt);
	public void visit(AttributeLocation stmt);
	public void visit(Block stmt); 		//VER
	public void visit(BodyDecl stmt);
	public void visit(BooleanLiteral lit);
	public void visit(BreakStmt stmt);
	public void visit(ClassDecl stmt);
	public void visit(ContinueStmt stmt);
	public void visit(EqBinOp stmt);
	public void visit(Expression stmt);
	public void visit(FieldDecl stmt);
	public void visit(FloatLiteral lit);
	public void visit(ForStmt stmt);
	public void visit(IdDecl loc);
	public void visit(IfThenElseStmt stmt);
	public void visit(IfThenStmt stmt);
	public void visit(IntLiteral lit);
	public void visit(LogicalBinOp stmt);
	public void visit(LogicalUnaryOp stmt);
	public void visit(MethodCall stmt);
	public void visit(MethodCallStmt stmt);
	public void visit(MethodDecl stmt);
	public void visit(ParamDecl stmt);
	public void visit(Program stmt);
	public void visit(RelationalBinOp stmt);
	public void visit(ReturnStmt stmt);
	public void visit(ReturnVoidStmt stmt);
	public void visit(Skip stmt);
	public void visit(Statement stmt);
	public void visit(VarLocation loc);
	public void visit(WhileStmt stmt);
	
}
