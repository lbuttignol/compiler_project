package ir.semcheck;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import ir.error.Error; // define class error


// type checker, concrete visitor 
public class TypeEvaluationVisitor implements ASTVisitor {
	
	private List<Error> errors;

	public TypeEvaluationVisitor(){
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

	private void addError(AST a, String desc) {
		this.errors.add(new Error(a.getLineNumber(), a.getColumnNumber(), desc));
	}

	public List<Error> getErrors() {
		return this.errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
