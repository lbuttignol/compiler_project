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
	public void visit(AST stmt){

	}

	@Override	
	public void visit(ArithmeticBinOp stmt){
		Expression exprLeft  = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		if (exprLeft.getType().equalsIgnoreCase("UNDEFINED")){
			//System.out.println("CLASS: "+exprLeft.toString());
			//System.out.println("accept de left "+ exprLeft.getClass());
			exprLeft.accept(this);
		}
		if(exprRight.getType().equalsIgnoreCase("UNDEFINED")){
			//System.out.println("accept de right");
			exprRight.accept(this);
		}
		//checkExpressionType(exprLeft);
		//checkExpressionType(exprRight);

		//System.out.println("Left expression type: " + exprLeft.getType());
		//System.out.println("Right expression type: " + exprRight.getType());
		if (!( ( exprLeft.getType().equalsIgnoreCase("INTEGER")&&(exprRight.getType().equalsIgnoreCase("INTEGER")) ) ||
			((exprLeft.getType().equalsIgnoreCase("FLOAT")&&(exprRight.getType().equalsIgnoreCase("FLOAT")) )))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Invalid artihmetic operation, both expressions should be INTEGER or FLOAT");
		}else{
			stmt.setType(exprLeft.getType());
		}
	}
	
	@Override
	public void visit(ArithmeticUnaryOp stmt){
		Expression expr = stmt.getOperand();
		expr.accept(this);
		//System.out.println("Unary expression type: " + stmt.getType());
		if (!((expr.getType().equalsIgnoreCase("INTEGER"))||(expr.getType().equalsIgnoreCase("FLOAT")))){
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), "Invalid aritmetic operation, "+expr.getType()+" expression expected");
		}else{
			stmt.setType(expr.getType());
		}
	} 
	
	@Override
	public void visit(ArrayIdDecl stmt){

	}
	
	@Override
	public void visit(ArrayLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last 		 = ids.size()-1;
		String type 	 = this.stack.getCurrentType(ids.get(last).getName());
		if (type!=null)
			loc.setType(type);
		Expression expr=loc.getExpression();
		expr.accept(this);
		if(!expr.getType().equalsIgnoreCase("INTEGER"))
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), "Only Integer index allowed");
	}
	
	@Override
	public void visit(AssignStmt assignStmt){
		//System.out.println("Assing visit running");
		Location loc = assignStmt.getLocation();
		loc.accept(this);
		Expression expr = assignStmt.getExpression();
		expr.accept(this);
		if (!loc.getType().equalsIgnoreCase(expr.getType())&& !loc.getType().equalsIgnoreCase("UNDEFINED")) {
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
		//System.out.println("Lastname: "+ lastName);
		String firstNameClass = ids.get(0).getType();
		//System.out.println("First name: "+firstNameClass);

		if (firstNameClass.equalsIgnoreCase("UNDEFINED")){
			String type = this.stack.getCurrentType(lastName);
			//System.out.println("TIPO:::"+type);
			if(type!=null){
				loc.setType(ids.get(last).getType());
			}
		}else{
			String classD = this.stack.getCurrentType(firstNameClass);
			//System.out.println("Class Name: "+classD);
			SymbolInfo classDecl = this.stack.getCurrentSymbolInfo(classD);
			//System.out.println("class :"+classDecl);
			List<IdDecl> atts = classDecl.getAttList();
			IdDecl aux = new IdDecl(lastName, loc.getLineNumber(), loc.getColumnNumber());
			if(this.stack.contains(atts,aux)){
				loc.setType(this.stack.currentId().getType());
			} else {
				new ir.error.Error(loc.getLineNumber(),loc.getColumnNumber(), "Not a valid attribute");
			}
		}
	}
	
	@Override
	public void visit(Block block){
		this.stack.newLevel();
		//System.out.println("Block visit running");
		List<FieldDecl> fieldDeclList = block.getVariable();
		List<Statement> stmtList = block.getStatements();

		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}
		//System.out.println(stmtList.size());
		for(Statement stmt : stmtList){
			//System.out.println("Statement class: "+ stmt.getClass());
			stmt.accept(this);
		}
		this.stack.closeLevel();
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

	}
	
	@Override
	public void visit(BreakStmt stmt){

	}
	
	@Override
	public void visit(ClassDecl classDecl){
		List<FieldDecl> fieldDeclList = classDecl.getAttributes();
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		this.stack.newLevel();
		
		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}

		for (MethodDecl methodDecl : methodDeclList){
			SymbolInfo a = new SymbolInfo(true, methodDecl.getType(), methodDecl);
			a.addParamList(methodDecl.getParams());
			this.stack.addDeclare(a);
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
		exprLeft.accept(this);
		exprRight.accept(this);
		if (exprLeft.getType().equalsIgnoreCase("INTEGER") || exprLeft.getType().equalsIgnoreCase("FLOAT") || exprLeft.getType().equalsIgnoreCase("BOOLEAN")){
			if (exprLeft.getType().equalsIgnoreCase(exprRight.getType())){
				stmt.setType("BOOLEAN");
			}else{
				new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be of the same type");
			}
		}else{
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Expressions of a basic type expected");
		}
	}

	@Override
	public void visit(Expression stmt){
		
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
			this.stack.addDeclare(new SymbolInfo(fieldDecl.getType(), idDecl));
		}
	}
	
	@Override
	public void visit(FloatLiteral lit){

	}
	
	@Override
	public void visit(ForStmt stmt){
		this.stack.newLevel();
		IdDecl counterName = stmt.getCounterName();
		this.stack.addDeclare(new SymbolInfo(counterName.getType(), counterName));

		Expression initValue = stmt.getInit();
		Expression endValue  = stmt.getEnd();
		initValue.accept(this);
		endValue.accept(this);

		if (!((initValue.getType().equalsIgnoreCase("INTEGER"))&&(endValue.getType().equalsIgnoreCase("INTEGER")))){
			new ir.error.Error(initValue.getLineNumber(),initValue.getColumnNumber(), "Both init and end value should be Integers");
		}

		Statement statement = stmt.getBody();
		statement.accept(this);
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(IdDecl loc){

	}
	
	@Override
	public void visit(IfThenElseStmt stmt){
		Expression condition = stmt.getCondition();
		condition.accept(this);
		if (!condition.getType().equalsIgnoreCase("BOOLEAN")){
			new ir.error.Error(condition.getLineNumber(),condition.getColumnNumber(), "Not a valid condition");
		}
		Statement ifBlock = stmt.getIfBlock();
		//System.out.println("If block type: "+ifBlock.getClass());
		ifBlock.accept(this);
		Statement elseBlock = stmt.getElseBlock();
		//System.out.println("else block type: "+elseBlock.getClass());
		elseBlock.accept(this);
	}
	
	@Override
	public void visit(IfThenStmt stmt){
		Expression condition = stmt.getCondition();
		condition.accept(this);
		if (!condition.getType().equalsIgnoreCase("BOOLEAN")){
			new ir.error.Error(condition.getLineNumber(),condition.getColumnNumber(), "Not a valid condition");
		}
		Statement ifBlock = stmt.getIfBlock();
		ifBlock.accept(this);
	}
	
	@Override
	public void visit(IntLiteral lit){
	
	}
	
	@Override
	public void visit(LogicalBinOp stmt){
		Expression exprLeft = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		exprLeft.accept(this);
		exprRight.accept(this);
		if (!((exprLeft.getType().equalsIgnoreCase("BOOLEAN"))&&(exprRight.getType().equalsIgnoreCase("BOOLEAN")))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be booleans");
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(LogicalUnaryOp stmt){
		Expression expr =stmt.getOperand();
		expr.accept(this);
		if (!expr.getType().equalsIgnoreCase("BOOLEAN")){
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), "Boolean expression expected");
		}else{
			stmt.setType("BOOLEAN");
		}
	}

	@Override
	public void visit(MethodCall methodCall){
		//System.out.println("Method call visit");
		List<IdDecl> ids = methodCall.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		//System.out.println("lastName: "+lastName);
		String type = this.stack.getCurrentType(lastName);

		if (type!=null){
			methodCall.setType(type);
			List<Expression> paramList = methodCall.getParams();
			List<IdDecl> formal = this.stack.getCurrentSymbolInfo(lastName).getAttList();
			//System.out.println("Size formal: "+formal.size()+", size actual:"+paramList.size());
			if(formal.size()==paramList.size()){
				for (IdDecl param : formal) {
					Expression current=paramList.get(formal.indexOf(param));
					//System.out.println("compara actual: ,(tipo: "+current.getType()+") formal: "+param.getName()+"(tipo: "+param.getType()+")");
					if (current.getType().equalsIgnoreCase("UNDEFINED"))
						current.accept(this);
						//System.out.println("after exp check: ,(tipo: "+current.getType()+")");
					//System.out.println("current type: "+current.getType());
					if (!param.getType().equalsIgnoreCase(current.getType()))
						new ir.error.Error(current.getLineNumber(),current.getColumnNumber(), "Parameter of type "+param.getType()+" expected");
				}
			}else{
				new ir.error.Error(methodCall.getLineNumber(),methodCall.getColumnNumber(), "Actual and formal argument lists differ in length");
			}
		}else{
			//System.out.println("tipo null");
		}
	}
	
	@Override
	public void visit(MethodCallStmt stmt){
		MethodCall methodCall = stmt.getMethodCall();
		methodCall.accept(this);
	}
	
	@Override
	public void visit(MethodDecl methodDecl){
		//System.out.println("Method declare visit");
		String type = methodDecl.getType();
		if (!Type.contains(type)){
			new ir.error.Error(methodDecl.getLineNumber(),methodDecl.getColumnNumber(), "Not a valid method type");
		}
		this.stack.newLevel();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		List<ParamDecl> paramDeclList = methodDecl.getParams();
		for (ParamDecl paramDecl : paramDeclList){
			//System.out.println("Param "+paramDecl.getName()+", type: "+paramDecl.getType());

			symbolInfoList.add(new SymbolInfo(paramDecl.getType(), paramDecl));
		}

		this.stack.addDeclareList(symbolInfoList);
		for (ParamDecl paramDecl : paramDeclList){
			paramDecl.accept(this);
		}
		BodyDecl body = methodDecl.getBody();
		body.accept(this);
		if (!body.isExtern()){
			List<Statement> stmts=body.getBlock().getStatements();
			for (Statement stmt: stmts){
				if(stmt instanceof ReturnStmt){
					ReturnStmt ret = (ReturnStmt) stmt;
					if (!ret.getExpression().getType().equalsIgnoreCase(type)){
						new ir.error.Error(stmt.getLineNumber(),stmt.getColumnNumber(), "This method should return an expression of type "+type);
					}
				}
				if(stmt instanceof ReturnVoidStmt){
					if (type!="VOID"){
						new ir.error.Error(stmt.getLineNumber(),stmt.getColumnNumber(), "This method is declared as void");
					}
				}
					
			}
		}
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(ParamDecl paramDecl){
		String type = paramDecl.getType();
		if (!Type.contains(type))
			new ir.error.Error(paramDecl.getLineNumber(),paramDecl.getColumnNumber(), "Not a valid type");
	}
	
	@Override
	public void visit(Program prog){
		this.stack.newLevel();
		List<ClassDecl> classDeclList = prog.getClassDeclare();
		for (ClassDecl classDecl : classDeclList){
			SymbolInfo classSymbol = new SymbolInfo(classDecl.getName(),classDecl);
			classSymbol.addAttList(classDecl.getAttributes());
			classSymbol.addMethodList(classDecl.getMethods());
			this.stack.addDeclare(classSymbol);
		}
		for (ClassDecl classDecl : classDeclList){
			classDecl.accept(this);
		}
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(RelationalBinOp stmt){
		Expression exprLeft = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		//System.out.println("ANTES.. left: "+exprLeft.getType()+" RIGHT:"+exprRight.getType());
		//System.out.println("clase left: "+exprLeft.getClass());
		exprLeft.accept(this);
		exprRight.accept(this);
		//System.out.println("Despues.. left: "+exprLeft.getType()+" RIGHT:"+exprRight.getType());
		if (!( (exprLeft.getType().equalsIgnoreCase("INTEGER")&&(exprRight.getType().equalsIgnoreCase("INTEGER")))||
			((exprLeft.getType().equalsIgnoreCase("FLOAT")&&(exprRight.getType().equalsIgnoreCase("FLOAT")) )))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be of an INTEGER or FLOAT type");
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(ReturnStmt stmt){
		//System.out.println("ReturnStmt visit");
		Expression expr = stmt.getExpression();
		if(expr.getType().equalsIgnoreCase("UNDEFINED"))
			expr.accept(this);
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
		List<IdDecl> ids = loc.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		String type = this.stack.getCurrentType(lastName);
		//System.out.println("Type of var location("+lastName+"): "+type);
		if(type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(WhileStmt stmt){
		Expression condition = stmt.getCondition();
		condition.accept(this);
		if (!condition.getType().equalsIgnoreCase("BOOLEAN")){
			new ir.error.Error(condition.getLineNumber(),condition.getColumnNumber(), "Condition should be a boolean");
		}
		Statement body = stmt.getBody();
		body.accept(this);
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
