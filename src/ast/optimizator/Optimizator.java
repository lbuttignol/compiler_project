package ir.semcheck;
import ir.ASTVisitor;
import ir.ast.*;
import ir.semcheck.*;
import java.util.List;
import java.util.LinkedList;
import ir.error.Error;

public class Optimizator implements ASTVisitor {

	@Override
	public List<Error> getErrors() {
		return this.errors;
	}

	@Override
	public List<Error> stackErrors(){
		return null;
	}

	@Override
	public void visit(AST stmt){
	
	}

	@Override
	public void visit(ArithmeticBinOp stmt){
	
	}

	@Override
	public void visit(ArithmeticUnaryOp stmt){
	
	}

	@Override
	public void visit(ArrayIdDecl stmt){
	
	}

	@Override
	public void visit(ArrayLocation stmt){
	
	}

	@Override
	public void visit(AssignStmt stmt){
	
	}

	@Override
	public void visit(AttributeArrayLocation stmt){
	
	}

	@Override
	public void visit(AttributeLocation stmt){
	
	}

	@Override
	public void visit(Block block){
		List<Statement> statements = block.getStatements();
		for (Statement statement : statements) {
			if (statement instanceof AbstractReturn || 
				statement instanceof BreakStmt 		|| 
				statement instanceof ContinueStmt) {

				List<Statement> newList = statements.subList(0,statements.indexOf(statement)+1);
				block.setStatements(newList);
				break;
			}
		}
	}

	@Override 		//VER
	public void visit(BodyDecl stmt){
	
	}

	@Override
	public void visit(BooleanLiteral lit){
	
	}

	@Override
	public void visit(BreakStmt stmt){
	
	}

	@Override
	public void visit(ClassDecl stmt){
	
	}

	@Override
	public void visit(ContinueStmt stmt){
	
	}

	@Override
	public void visit(EqBinOp stmt){
	
	}

	@Override
	public void visit(Expression stmt){
	
	}

	@Override
	public void visit(FieldDecl stmt){
	
	}

	@Override
	public void visit(FloatLiteral lit){
	
	}

	@Override
	public void visit(ForStmt stmt){
	
	}

	@Override
	public void visit(IdDecl loc){
	
	}

	@Override
	public void visit(IfThenElseStmt stmt){
	
	}

	@Override
	public void visit(IfThenStmt stmt){
	
	}

	@Override
	public void visit(IntLiteral lit){
	
	}

	@Override
	public void visit(LogicalBinOp stmt){
	
	}

	@Override
	public void visit(LogicalUnaryOp stmt){
	
	}

	@Override
	public void visit(MethodCall stmt){
	
	}

	@Override
	public void visit(MethodCallStmt stmt){
	
	}

	@Override
	public void visit(MethodDecl stmt){
	
	}

	@Override
	public void visit(ParamDecl stmt){
	
	}

	@Override
	public void visit(Program stmt){
	
	}

	@Override
	public void visit(RelationalBinOp stmt){
	
	}

	@Override
	public void visit(ReturnStmt stmt){
	
	}

	@Override
	public void visit(ReturnVoidStmt stmt){
	
	}

	@Override
	public void visit(Skip stmt){
	
	}

	@Override
	public void visit(Statement stmt){
	
	}

	@Override
	public void visit(VarLocation loc){
	
	}

	@Override
	public void visit(WhileStmt stmt){
	
	}

}