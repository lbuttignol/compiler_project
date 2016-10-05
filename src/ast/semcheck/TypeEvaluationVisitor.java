package ir.semcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import ir.ASTVisitor;
import ir.ast.*;
import ir.error.Error; // define class error
import ir.semcheck.SymbolTable;


// type checker, concrete visitor 
public class TypeEvaluationVisitor implements ASTVisitor {

	private SymbolTable stack;
	
	private List<Error> errors;

	public TypeEvaluationVisitor(){
		this.stack = new SymbolTable();
	}

	// visit statements
	
	@Override
	public void visit(AST stmt){}

	@Override	
	public void visit(ArithmeticBinOp stmt){
		Expression exprLeft  = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		checkExpressionType(exprLeft);
		checkExpressionType(exprRight);
		if (!( (exprLeft.getType().equals("INTEGER")&&(exprRight.getType().equals("INTEGER")))||
			((exprLeft.getType().equals("FLOAT")&&(exprRight.getType().equals("FLOAT")) )))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Operacion aritmetica binaria no valida");
		}else{
			stmt.setType(exprLeft.getType());
		}
		
	}
	
	@Override
	public void visit(ArithmeticUnaryOp stmt){
		Expression expr = stmt.getOperand();
		checkExpressionType(expr);
		if (!((expr.getType().equals("INTEGER"))||(expr.getType().equals("FLOAT")))){
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), "Operacion aritmetica unaria no valida");
		}else{
			stmt.setType(expr.getType());
		}
	} 
	
	@Override
	public void visit(ArrayIdDecl stmt){}
	
	@Override
	public void visit(ArrayLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last 		 = ids.size()-1;
		String type 	 = this.stack.getCurrentType(ids.get(last).getName());
		if (type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(AssignStmt assignStmt){

		Location loc = assignStmt.getLocation();
		
		if (loc instanceof VarLocation){
			VarLocation varLocation = (VarLocation) loc;
			varLocation.accept(this);
		}
		if (loc instanceof AttributeLocation){
			AttributeLocation attributeLocation = (AttributeLocation) loc;
			attributeLocation.accept(this);
		}
		if (loc instanceof ArrayLocation){
			ArrayLocation arrayLocation = (ArrayLocation) loc;
			arrayLocation.accept(this);
		}
		if (loc instanceof AttributeArrayLocation){
			AttributeArrayLocation attributeArrayLocation = (AttributeArrayLocation) loc;
			attributeArrayLocation.accept(this);
		}

		Expression expr = assignStmt.getExpression();
		checkExpressionType(expr);
		if (!loc.getType().equals(expr.getType())){
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), loc.getType()+" expression expected");
		}

	}
	
	@Override
	public void visit(AttributeArrayLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		String type = this.stack.getCurrentType(lastName);
		if (type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(AttributeLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		String type = this.stack.getCurrentType(lastName);
		if (type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(Block block){
		List<FieldDecl> fieldDeclList = block.getVariable();
		List<Statement> stmtList = block.getStatements();

		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}

		for(Statement stmt : stmtList){
			checkStatementType(stmt);
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
	public void visit(BooleanLiteral lit){}
	
	@Override
	public void visit(BreakStmt stmt){}
	
	@Override
	public void visit(ClassDecl classDecl){
		List<FieldDecl> fieldDeclList = classDecl.getAttributes();
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		this.stack.newLevel();
		
		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}

		for (MethodDecl methodDecl : methodDeclList){
			this.stack.addDeclare(new SymbolInfo(methodDecl));
		}

		for (MethodDecl methodDecl : methodDeclList){
			methodDecl.accept(this);
		}
		
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(ContinueStmt stmt){

	}
	
	@Override
	public void visit(EqBinOp stmt){
		Expression exprLeft = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		checkExpressionType(exprLeft);
		checkExpressionType(exprRight);
		if ((exprLeft.getType().equals("INTEGER")^(exprRight.getType().equals("INTEGER")))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "EQBINOP operation not valid");
		}else{
			if ((exprLeft.getType().equals("FLOAT")^(exprRight.getType().equals("FLOAT")))){
				new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "EQBINOP operation not valid");
			}else{
				if (((exprLeft.getType().equals("BOOLEAN"))^(exprRight.getType().equals("BOOLEAN")))){
					new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "EQBINOP operation not valid");
				}else{
					stmt.setType("BOOLEAN");
				}
			}
			
		}
	}

	@Override
	public void visit(Expression stmt){

	}

	public void checkExpressionType(Expression expr){
		if (expr instanceof VarLocation){
			VarLocation loc = (VarLocation)expr;
			loc.accept(this);
		}	
		if (expr instanceof AttributeLocation){
			AttributeLocation loc = (AttributeLocation) expr;
			loc.accept(this);
		}	
		if (expr instanceof ArrayLocation){
			ArrayLocation loc = (ArrayLocation) expr;
			loc.accept(this);
		}
		if (expr instanceof AttributeArrayLocation){
			AttributeArrayLocation loc = (AttributeArrayLocation) expr;
			loc.accept(this);
		}
		if (expr instanceof MethodCall){
			MethodCall methodCall = (MethodCall) expr;
			methodCall.accept(this);
		}
		if (expr instanceof LogicalBinOp){
			LogicalBinOp logBinOp = (LogicalBinOp) expr;
			logBinOp.accept(this);
		}
		if (expr instanceof ArithmeticBinOp){
			ArithmeticBinOp arithBinOp = (ArithmeticBinOp) expr;
			arithBinOp.accept(this);
		}
		if (expr instanceof RelationalBinOp){
			RelationalBinOp relBinOp = (RelationalBinOp) expr;
			relBinOp.accept(this);
		}
		if (expr instanceof EqBinOp){
			EqBinOp eqBinOp = (EqBinOp) expr;
			eqBinOp.accept(this);
		}
		if (expr instanceof ArithmeticUnaryOp){
			ArithmeticUnaryOp arithUnaryOp = (ArithmeticUnaryOp) expr;
			arithUnaryOp.accept(this);
		}
		if (expr instanceof LogicalUnaryOp){
			LogicalUnaryOp logicalUnaryOp = (LogicalUnaryOp) expr;
			logicalUnaryOp.accept(this);
		}

	}
	
	@Override
	public void visit(FieldDecl fieldDecl){
		String type = fieldDecl.getType();
		if (!Type.contains(type)){
			new ir.error.Error(fieldDecl.getLineNumber(),fieldDecl.getColumnNumber(), "Unexistent field declaration type");
		}
		List<IdDecl> idDeclList = fieldDecl.getNames();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		for (IdDecl idDecl : idDeclList){
			symbolInfoList.add(new SymbolInfo(idDecl));
		}
		this.stack.addDeclareList(symbolInfoList);
	}
	
	@Override
	public void visit(FloatLiteral lit){}
	
	@Override
	public void visit(ForStmt stmt){
		IdDecl counterName = stmt.getCounterName();
		this.stack.addDeclare(new SymbolInfo(counterName));

		Expression initValue = stmt.getInit();
		Expression endValue  = stmt.getEnd();

		checkExpressionType(initValue);
		checkExpressionType(endValue);
		if ((initValue.getType().equals("INTEGER"))&&(endValue.getType().equals("INTEGER"))){
			IntLiteral init = (IntLiteral) initValue;
			IntLiteral end  = (IntLiteral) endValue;
			if (init.getValue()>end.getValue()){
				new ir.error.Error(initValue.getLineNumber(),initValue.getColumnNumber(), "Init value should be less or equal than end value");
			}
		}else{
			new ir.error.Error(initValue.getLineNumber(),initValue.getColumnNumber(), "Both init and end value should be Integers");
		}

		Statement statement = stmt.getBody();
		checkStatementType(statement);
	}
	
	@Override
	public void visit(IdDecl loc){}
	
	@Override
	public void visit(IfThenElseStmt stmt){
		Expression condition = stmt.getCondition();
		checkExpressionType(condition);
		if (!condition.getType().equals("BOOLEAN")){
			new ir.error.Error(condition.getLineNumber(),condition.getColumnNumber(), "Not a valid condition");
		}

		Statement ifBlock = stmt.getIfBlock();
		checkStatementType(ifBlock);
		Statement elseBlock = stmt.getElseBlock();
		checkStatementType(elseBlock);
	}
	
	@Override
	public void visit(IfThenStmt stmt){
		Expression condition = stmt.getCondition();
		checkExpressionType(condition);
		if (!condition.getType().equals("BOOLEAN")){
			new ir.error.Error(condition.getLineNumber(),condition.getColumnNumber(), "Not a valid condition");
		}
		Statement ifBlock = stmt.getIfBlock();
		checkStatementType(ifBlock);
	}
	
	@Override
	public void visit(IntLiteral lit){
	
	}
	
	@Override
	public void visit(LogicalBinOp stmt){
		Expression exprLeft = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		checkExpressionType(exprLeft);
		checkExpressionType(exprRight);
		if (!((exprLeft.getType().equals("BOOLEAN"))&&(exprRight.getType().equals("BOOLEAN")))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be booleans");
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(LogicalUnaryOp stmt){
		Expression expr =stmt.getOperand();
		checkExpressionType(expr);
		if (!expr.getType().equals("BOOLEAN")){
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), "Boolean expression expected");
		}else{
			stmt.setType("BOOLEAN");
		}
	}

	@Override
	public void visit(MethodCall methodCall){
		List<IdDecl> ids = methodCall.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		String type = this.stack.getCurrentType(lastName);
		if (type!=null){
			methodCall.setType(type);
			List<Expression> paramList = methodCall.getParams();
			for (Expression param : paramList){
				checkExpressionType(param);
			}
		}
	}
	
	@Override
	public void visit(MethodCallStmt stmt){
		MethodCall methodCall = stmt.getMethodCall();
		methodCall.accept(this);
	}
	
	@Override
	public void visit(MethodDecl methodDecl){
		String type = methodDecl.getType();
		if (!Type.contains(type)){
			new ir.error.Error(methodDecl.getLineNumber(),methodDecl.getColumnNumber(), "Not a valid method type");
		}
		this.stack.newLevel();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		List<ParamDecl> paramDeclList = methodDecl.getParams();
		for (ParamDecl paramDecl : paramDeclList){
			symbolInfoList.add(new SymbolInfo(paramDecl));
		}

		this.stack.addDeclareList(symbolInfoList);
		for (ParamDecl paramDecl : paramDeclList){
			paramDecl.accept(this);
		}
		BodyDecl body = methodDecl.getBody();
		body.accept(this);

		this.stack.closeLevel();
	}
	
	@Override
	public void visit(ParamDecl paramDecl){
		String type = paramDecl.getType();
		if (!Type.contains(type)){
			new ir.error.Error(paramDecl.getLineNumber(),paramDecl.getColumnNumber(), "Not a valid type");
		}
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
		Expression exprLeft = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		checkExpressionType(exprLeft);
		checkExpressionType(exprRight);
		if (!( (exprLeft.getType().equals("INTEGER")&&(exprRight.getType().equals("INTEGER")))||
			((exprLeft.getType().equals("FLOAT")&&(exprRight.getType().equals("FLOAT")) )))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be of an integer or float type");
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(ReturnStmt stmt){
		Expression expr = stmt.getExpression();
		checkExpressionType(expr);
		if (this.stack.getMethodType()!=null){
			if(!(expr.getType().equals(this.stack.getMethodType()))) 
				new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), "This method should return an expression of type: "+this.stack.getMethodType());
		}
	}
	@Override
	public void visit(ReturnVoidStmt stmt){}
	
	@Override
	public void visit(Skip stmt){}
	
	@Override
	public void visit(Statement stmt){}

	public void checkStatementType(Statement stmt){

		if (stmt instanceof AssignStmt ){ 
			AssignStmt assignStmt = (AssignStmt) stmt;
			assignStmt.accept(this);
		}
		if (stmt instanceof MethodCallStmt){
			MethodCallStmt methodCallStmt = (MethodCallStmt) stmt;
			methodCallStmt.accept(this);
		}
		if (stmt instanceof IfThenStmt){
			IfThenStmt ifThenStmt = (IfThenStmt) stmt;
			ifThenStmt.accept(this);
		}
		if (stmt instanceof IfThenElseStmt){
			IfThenElseStmt ifThenElseStmt = (IfThenElseStmt) stmt;
			ifThenElseStmt.accept(this);
		}
		if (stmt instanceof ForStmt){
			ForStmt forStmt = (ForStmt) stmt;
			forStmt.accept(this);
		}
		if (stmt instanceof WhileStmt){
			WhileStmt whileStmt = (WhileStmt) stmt;
			whileStmt.accept(this);
		}
		if (stmt instanceof ReturnStmt){
			ReturnStmt returnStmt = (ReturnStmt) stmt;
			returnStmt.accept(this);
		}
		if (stmt instanceof ReturnVoidStmt){
			ReturnVoidStmt returnVoidStmt = (ReturnVoidStmt) stmt;
			stmt.accept(this);
		}
		if (stmt instanceof BreakStmt){
			
		}
		if (stmt instanceof ContinueStmt){

		}
		if (stmt instanceof Skip){

		}
		if (stmt instanceof Block){
			Block block = (Block) stmt;
			block.accept(this);
		}
	}


	
	@Override
	public void visit(VarLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		String type = this.stack.getCurrentType(lastName);
		if(type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(WhileStmt stmt){
		Expression condition = stmt.getCondition();
		checkExpressionType(condition);
		if (!condition.getType().equals("BOOLEAN")){
			new ir.error.Error(condition.getLineNumber(),condition.getColumnNumber(), "Condition should be a boolean");
		}
		Statement body = stmt.getBody();
		checkStatementType(body);
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
