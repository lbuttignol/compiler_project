package ir.semcheck;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;

//Concrete visitor class
public class PrettyPrintVisitor implements ASTVisitor {
	private String formula;

	public PrettyPrintVisitor(){
	}

	// visit statements
	@Override
	public void visit(AssignStmt stmt){
	}

	@Override
	public void visit(ReturnStmt stmt){
	}

	@Override
	public void visit(ReturnVoidStmt stmt){
	}

	@Override
	public void  visit(IfThenStmt stmt){
	}

	@Override
	public void visit(IfThenElseStmt stmt){
	}

	@Override
	public void visit(Block stmt){
	}		//VER

	@Override
	public void visit(BreakStmt stmt){
	}

	@Override
	public void visit(ContinueStmt stmt){
	}

	@Override
	public void visit(WhileStmt stmt){
	}
	
	@Override
	public void visit(BinOpExpr expr){
	}
	
	@Override
	public void visit(IntLiteral lit){
	}

	@Override
	public void visit(VarLocation loc){
	}


	@Override
	public void visit(AST ast){
	}

}