package ir.semcheck;
import ir.ASTVisitor;
import ir.ast.*;
import ir.semcheck.*;
import java.util.List;
import java.util.LinkedList;
import ir.error.Error;

public class Optimizator implements ASTVisitor {

	private Literal temporal;
	private Boolean wasSeted=false;

	@Override
	public List<Error> getErrors() {
		return null;
	}

	@Override
	public List<Error> stackErrors(){
		return null;
	}

	public Literal getLiteralBinExpr(BinOpExpr stmt){
		Literal aux=null;
		switch (stmt.getOperator()) {
			case PLUS:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new IntLiteral(l.getValue()+r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new FloatLiteral(l.getValue()+r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case MINUS:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new IntLiteral(l.getValue()-r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new FloatLiteral(l.getValue()-r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case TIMES:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new IntLiteral(l.getValue()*r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new FloatLiteral(l.getValue()*r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case DIV:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new IntLiteral(l.getValue()/r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new FloatLiteral(l.getValue()/r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case MOD:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new IntLiteral(l.getValue()%r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new FloatLiteral(l.getValue()%r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case SMALL:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()<r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()<r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case LTOE:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()<=r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()<=r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case BIGGER:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()>r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()>r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case GTOE:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()>=r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()>=r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case EQUAL:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()==r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()==r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case DISTINCT:
				if(stmt.getLeftOperand() instanceof IntLiteral && stmt.getRightOperand() instanceof IntLiteral ){
					IntLiteral l,r;
					l = (IntLiteral) stmt.getLeftOperand(); 
					r = (IntLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()!=r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}else{
					FloatLiteral l,r;
					l = (FloatLiteral) stmt.getLeftOperand(); 
					r = (FloatLiteral) stmt.getRightOperand(); 
					aux = new BooleanLiteral(l.getValue()!=r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				}
				break;
			case AND:
				BooleanLiteral l,r;
				l = (BooleanLiteral) stmt.getLeftOperand(); 
				r = (BooleanLiteral) stmt.getRightOperand(); 
				aux = new BooleanLiteral(l.getValue()&&r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				break;
			case OR:
				l = (BooleanLiteral) stmt.getLeftOperand(); 
				r = (BooleanLiteral) stmt.getRightOperand(); 
				aux = new BooleanLiteral(l.getValue()||r.getValue(),stmt.getLineNumber(),stmt.getColumnNumber());
				break;
		}
		return aux;
	}

	@Override
	public void visit(AST stmt){
		return;
	}

	@Override
	public void visit(ArithmeticBinOp stmt){
		stmt.getLeftOperand().accept(this);
		if(this.wasSeted){
			stmt.setLeftOperand(temporal);
		}
		stmt.getRightOperand().accept(this);
		if(this.wasSeted){
			stmt.setRightOperand(temporal);
		}
		if(stmt.getLeftOperand() instanceof Literal && stmt.getRightOperand() instanceof Literal ){
			this.temporal = getLiteralBinExpr(stmt);
			this.wasSeted = true;
		}else{
			this.wasSeted = false;
		}
		return;
	}

	@Override
	public void visit(ArithmeticUnaryOp stmt){
		return;
	}

	@Override
	public void visit(ArrayIdDecl stmt){
		return;
	}

	@Override
	public void visit(ArrayLocation stmt){
		return;
	}

	@Override
	public void visit(AssignStmt stmt){
		return;
	}

	@Override
	public void visit(AttributeArrayLocation stmt){
		return;
	}

	@Override
	public void visit(AttributeLocation stmt){
		return;
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
			} else {
				statement.accept(this);
			}
		}
	}

	@Override
	public void visit(BodyDecl body){
		if (!body.isExtern()){
			Block block = body.getBlock();
			block.accept(this);
		}
	}

	@Override
	public void visit(BooleanLiteral lit){
		this.temporal = lit;
		this.wasSeted = true; 
		return;
	}

	@Override
	public void visit(BreakStmt stmt){
		return;
	}

	@Override
	public void visit(ClassDecl classDecl){
		List<FieldDecl> fieldDeclList   = classDecl.getAttributes();
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}
		for (MethodDecl methodDecl : methodDeclList){
			methodDecl.accept(this);
		}
	}

	@Override
	public void visit(ContinueStmt stmt){
		return;
	}

	@Override
	public void visit(EqBinOp stmt){
		stmt.getLeftOperand().accept(this);
		if(this.wasSeted){
			stmt.setLeftOperand(temporal);
		}
		stmt.getRightOperand().accept(this);
		if(this.wasSeted){
			stmt.setRightOperand(temporal);
		}
		if(stmt.getLeftOperand() instanceof Literal && stmt.getRightOperand() instanceof Literal ){
			this.temporal = getLiteralBinExpr(stmt);
			this.wasSeted = true;
		}else{
			this.wasSeted = false;
		}
		return;
	}

	@Override
	public void visit(Expression stmt){
		return;
	}

	@Override
	public void visit(FieldDecl stmt){
		return;
	}

	@Override
	public void visit(FloatLiteral lit){
		this.temporal = lit;
		this.wasSeted = true;
		return;
	}

	@Override
	public void visit(ForStmt forst){
		if (forst.getEnd() instanceof IntLiteral && forst.getInit() instanceof IntLiteral) {
			IntLiteral beg, end;
			beg = (IntLiteral) forst.getInit();
			end = (IntLiteral) forst.getEnd();
			if (!(beg.getValue()<=end.getValue())) {
				forst = null;
				return;
			}
		}
		Statement body = forst.getBody();
		body.accept(this);
	}

	@Override
	public void visit(IdDecl loc){
		return;
	}

	@Override
	public void visit(IfThenElseStmt ifte){
		if (ifte.getCondition() instanceof BooleanLiteral) {
			BooleanLiteral cond = (BooleanLiteral) ifte.getCondition();
			if (cond.getValue())
				ifte.setElseBlock(null);
			else
				ifte.setIfBlock(null);
			return;
		}
		Statement block = ifte.getIfBlock();
		block.accept(this);
		block = ifte.getElseBlock();
		block.accept(this);
	}

	@Override
	public void visit(IfThenStmt ift){
		if (ift.getCondition() instanceof BooleanLiteral) {
			BooleanLiteral cond = (BooleanLiteral) ift.getCondition();
			if (!cond.getValue())
				ift = null;
		}
		Statement block = ift.getIfBlock();
		block.accept(this);
	}

	@Override
	public void visit(IntLiteral lit){
		this.temporal = lit;
		this.wasSeted = true;
		return;
	}

	@Override
	public void visit(LogicalBinOp stmt){
		stmt.getLeftOperand().accept(this);
		if(this.wasSeted){
			stmt.setLeftOperand(temporal);
		}
		stmt.getRightOperand().accept(this);
		if(this.wasSeted){
			stmt.setRightOperand(temporal);
		}
		if(stmt.getLeftOperand() instanceof Literal && stmt.getRightOperand() instanceof Literal ){
			this.temporal = getLiteralBinExpr(stmt);
			this.wasSeted = true;
		}else{
			this.wasSeted = false;
		}
		return;
	}

	@Override
	public void visit(LogicalUnaryOp stmt){
		return;
	}

	@Override
	public void visit(MethodCall stmt){
		return;
	}

	@Override
	public void visit(MethodCallStmt stmt){
		return;
	}

	@Override
	public void visit(MethodDecl meth){
		BodyDecl body = meth.getBody();
		body.accept(this);
	}

	@Override
	public void visit(ParamDecl stmt){
		return;
	}

	@Override
	public void visit(Program prog){
		List<ClassDecl> classDeclList = prog.getClassDeclare();
		for (ClassDecl classDecl : classDeclList){
			classDecl.accept(this);
		}
	}

	@Override
	public void visit(RelationalBinOp stmt){
		stmt.getLeftOperand().accept(this);
		if(this.wasSeted){
			stmt.setLeftOperand(temporal);
		}
		stmt.getRightOperand().accept(this);
		if(this.wasSeted){
			stmt.setRightOperand(temporal);
		}
		if(stmt.getLeftOperand() instanceof Literal && stmt.getRightOperand() instanceof Literal ){
			this.temporal = getLiteralBinExpr(stmt);
			this.wasSeted = true;
		}else{
			this.wasSeted = false;
		}
		return;
	}

	@Override
	public void visit(ReturnStmt stmt){
		return;
	}

	@Override
	public void visit(ReturnVoidStmt stmt){
		return;
	}

	@Override
	public void visit(Skip stmt){
		return;
	}

	@Override
	public void visit(Statement stmt){
		return;
	}

	@Override
	public void visit(VarLocation loc){
		return;
	}

	@Override
	public void visit(WhileStmt whilest) {
		if (whilest.getCondition() instanceof BooleanLiteral) {
			BooleanLiteral cond = (BooleanLiteral) whilest.getCondition();
			if (!cond.getValue()) {
				whilest = null;
				return;
			}
		}
		Statement body = whilest.getBody();
		body.accept(this);
	}

}