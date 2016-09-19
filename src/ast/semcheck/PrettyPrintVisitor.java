package ir.semcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import ir.ASTVisitor;
import ir.ast.*;

//Concrete visitor class
public class PrettyPrintVisitor implements ASTVisitor {
	private String formula;

	public PrettyPrintVisitor(){
	}
	
	@Override	
	public void visit(ArithmeticBinOp stmt){}
	
	@Override
	public void visit(ArithmeticUnaryOp stmt){}
	
	@Override
	public void visit(ArrayIdDecl stmt){}
	
	@Override
	public void visit(ArrayLocation stmt){}
	
	@Override
	public void visit(AssignStmt stmt){
		stmt.getLocation().accept(this);
		System.out.println(stmt.getOperator());
		stmt.getExpression().accept(this);
	}
	
	@Override
	public void visit(AttributeArrayLocation stmt){}
	
	@Override
	public void visit(AttributeLocation stmt){}
	
	@Override
	public void visit(Block stmt){
		System.out.println(" { ");
		System.out.println(" variables: ");
		Iterator<FieldDecl> varIt = stmt.getVariable().listIterator();
		while(varIt.hasNext()){
			varIt.next().accept(this);
		}
		System.out.println(" statements: ");
		Iterator<Statement> stmtIt = stmt.getStatements().listIterator();
		while(stmtIt.hasNext()){
			stmtIt.next().accept(this);
		}
		System.out.println(" } ");
	}
	
	@Override
	public void visit(BodyDecl stmt){}
	
	@Override
	public void visit(BoolenLiteral lit){}
	
	@Override
	public void visit(BreakStmt stmt){
		System.out.println(stmt.toString());
	}
	
	@Override
	public void visit(ClassDecl stmt){}
	
	@Override
	public void visit(ContinueStmt stmt){
		System.out.println(stmt.toString());
	}
	
	@Override
	public void visit(EqBinOp stmt){}
	
	@Override
	public void visit(FieldDecl loc){
		System.out.println(" Type ");
		System.out.println(loc.getType());
		System.out.println(" Declared identifiers: ");
		Iterator<IdDecl> ideIt = loc.getName().listIterator();
		while(ideIt.hasNext()){
			ideIt.next().accept(this);
		}
	}
	
	@Override
	public void visit(FloatLiteral lit){}
	
	@Override
	public void visit(ForStmt stmt){}
	
	@Override
	public void visit(IdDecl loc){
		System.out.println(loc.getName()+" ,");
	}
	
	@Override
	public void visit(IfThenElseStmt stmt){
		System.out.println("( if ");
		stmt.getCondition().accept(this);
		System.out.println(" then ");
		stmt.getIfBlock().accept(this);
		System.out.println(" ) else (");
		stmt.getElseBlock().accept(this);
		System.out.println(" ) ");
	}
	
	@Override
	public void  visit(IfThenStmt stmt){
		System.out.println("( if ");
		stmt.getCondition().accept(this);
		System.out.println(" then ");
		stmt.getIfBlock().accept(this);
		System.out.println(" ) ");
	}
	
	@Override
	public void visit(IntLiteral lit){
		System.out.println(lit.toString());
	}
	
	@Override
	public void visit(LogicalBinOp stmt){}
	
	@Override
	public void visit(LogicalUnaryOp stmt){}
	
	@Override
	public void visit(MethodCall stmt){}
	
	@Override
	public void visit(MethodCallStmt stmt){}
	
	@Override
	public void visit(MethodDecl stmt){}
	
	@Override
	public void visit(ParamDecl stmt){}
	
	@Override
	public void visit(Program stmt){}
	
	@Override
	public void visit(RelationalBinOp stmt){}
	
	@Override
	public void visit(ReturnStmt stmt){
		System.out.println("return");
		stmt.getExpression().accept(this);
	}
	
	@Override
	public void visit(ReturnVoidStmt stmt){
		System.out.println(stmt.toString());
	}
	
	@Override
	public void visit(Skip stmt){}
	
	@Override
	public void visit(Statement stmt){}
	
	@Override
	public void visit(VarLocation loc){
		System.out.println(loc.getBlockId());
		Iterator<String> locIt = loc.getIds().listIterator();
		while(locIt.hasNext()){
			System.out.println(locIt.next());
		}
	}
	
	@Override
	public void visit(WhileStmt stmt){
		System.out.println("( while ");
		stmt.getCondition().accept(this);
		System.out.println(" do ");
		stmt.getBody().accept(this);
		System.out.println(" ) ");
	}
	
}