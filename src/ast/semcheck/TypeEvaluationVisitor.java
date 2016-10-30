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

	private int coffpar;
	private int coffvar;

	private final static int VARSIZE=1;

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
			exprLeft.accept(this);
		}
		if(exprRight.getType().equalsIgnoreCase("UNDEFINED")){
			exprRight.accept(this);
		}
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
		Expression expr  = loc.getExpression();
		expr.accept(this);
		if(!expr.getType().equalsIgnoreCase("INTEGER"))
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), "Only Integer index allowed");
	}
	
	@Override
	public void visit(AssignStmt assignStmt){
		Location loc    = assignStmt.getLocation();
		loc.accept(this);
		Expression expr = assignStmt.getExpression();
		expr.accept(this);
		if (!loc.getType().equalsIgnoreCase(expr.getType()) && 
			!loc.getType().equalsIgnoreCase("UNDEFINED")) {
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
		if (last==0){
			String type = this.stack.getCurrentType(lastName);
			if(type!=null){
				loc.setType(ids.get(last).getType());
			}
		}else{
			String firstNameClass 	= ids.get(0).getName();
			String classD 			= this.stack.getCurrentType(firstNameClass);
			SymbolInfo classDecl 	= this.stack.getCurrentSymbolInfo(classD);
			List<IdDecl> atts 		= classDecl.getAttList();
			IdDecl aux 				= new IdDecl(lastName, loc.getLineNumber(), loc.getColumnNumber());
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
		List<FieldDecl> fieldDeclList 	= block.getVariable();
		List<Statement> stmtList 		= block.getStatements();

		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}
		for(Statement stmt : stmtList){
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
		List<FieldDecl> fieldDeclList 	= classDecl.getAttributes();
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
		Expression exprLeft  = stmt.getLeftOperand();
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
		List<IdDecl> idDeclList 		= fieldDecl.getNames();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		for (IdDecl idDecl : idDeclList){
			if(idDecl instanceof ArrayIdDecl){
				ArrayIdDecl arr 	= (ArrayIdDecl) idDecl;
				coffvar 			= coffvar-arr.getNumber()*VARSIZE;
			}else if (!Type.contains(idDecl.getType())) {
				SymbolInfo idClass 	= this.stack.getCurrentSymbolInfo(idDecl.getType());
				coffvar 			= idClass.getAttList().size()*VARSIZE;
			}else{
				coffvar 			= coffvar-VARSIZE;
			}
			idDecl.setOff(coffvar);
			this.stack.addDeclare(new SymbolInfo(fieldDecl.getType(), idDecl));
		}
		coffvar = coffvar + idDeclList.size()*VARSIZE;
	}

	@Override
	public void visit(FloatLiteral lit){

	}

	@Override
	public void visit(ForStmt stmt){
		this.stack.newLevel();
		IdDecl counterName   = stmt.getCounterName();
		this.stack.addDeclare(new SymbolInfo(counterName.getType(), counterName));

		Expression initValue = stmt.getInit();
		Expression endValue  = stmt.getEnd();
		initValue.accept(this);
		endValue.accept(this);

		if (!((initValue.getType().equalsIgnoreCase("INTEGER"))&&(endValue.getType().equalsIgnoreCase("INTEGER")))){
			new ir.error.Error(initValue.getLineNumber(),initValue.getColumnNumber(), "Both init and end value should be Integers");
		}

		Statement statement  = stmt.getBody();
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
		Statement ifBlock 	= stmt.getIfBlock();
		ifBlock.accept(this);
		Statement elseBlock = stmt.getElseBlock();
		elseBlock.accept(this);
	}

	@Override
	public void visit(IfThenStmt stmt){
		Expression condition = stmt.getCondition();
		condition.accept(this);
		if (!condition.getType().equalsIgnoreCase("BOOLEAN")){
			new ir.error.Error(condition.getLineNumber(),condition.getColumnNumber(), "Not a valid condition");
		}
		Statement ifBlock 	 = stmt.getIfBlock();
		ifBlock.accept(this);
	}
	
	@Override
	public void visit(IntLiteral lit){

	}
	
	@Override
	public void visit(LogicalBinOp stmt){
		Expression exprLeft  = stmt.getLeftOperand();
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
		Expression expr = stmt.getOperand();
		expr.accept(this);
		if (!expr.getType().equalsIgnoreCase("BOOLEAN")){
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), "Boolean expression expected");
		}else{
			stmt.setType("BOOLEAN");
		}
	}

	@Override
	public void visit(MethodCall methodCall){
		List<IdDecl> ids 			= methodCall.getIds();
		int last 		 			= ids.size()-1;
		String lastName  			= ids.get(last).getName();
		List<Expression> paramList  = methodCall.getParams();
		List<IdDecl> formal 		= new LinkedList<IdDecl>();
		if(last==0){
			String type = this.stack.getCurrentType(lastName);
			if (type!=null){
				methodCall.setType(type);
				formal = this.stack.getCurrentSymbolInfo(lastName).getAttList();
			} else {
				return;
			}
		}else{
			String firstName 		 = ids.get(0).getName();
			String type 		   	 = this.stack.getCurrentType(firstName);
			SymbolInfo instance 	 = this.stack.getCurrentSymbolInfo(type);
			List<SymbolInfo> methods = instance.getMethodList();
			if(this.stack.containsMeth(methods, new IdDecl(lastName, methodCall.getLineNumber(), methodCall.getColumnNumber()))){
				methodCall.setType(this.stack.currentS().getType());
				formal = this.stack.currentS().getAttList();				
			}else{
				return;
			}
		}
		if(formal.size()==paramList.size()){
			for (IdDecl param : formal) {
				Expression current = paramList.get(formal.indexOf(param));
				if (current.getType().equalsIgnoreCase("UNDEFINED"))
					current.accept(this);
				if (coffpar>(6*VARSIZE)){
					current.setOff(coffpar);
				}
				coffpar = coffpar+VARSIZE;
				if (!param.getType().equalsIgnoreCase(current.getType()))
					new ir.error.Error(current.getLineNumber(),current.getColumnNumber(), "Parameter of type "+param.getType()+" expected");
			}
			coffpar = coffpar-formal.size()*VARSIZE;
		}else{
			new ir.error.Error(methodCall.getLineNumber(),methodCall.getColumnNumber(), "Actual and formal argument lists differ in length");
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
		List<ParamDecl> paramDeclList 	= methodDecl.getParams();
		for (ParamDecl paramDecl : paramDeclList){
			symbolInfoList.add(new SymbolInfo(paramDecl.getType(), paramDecl));
		}

		this.stack.addDeclareList(symbolInfoList);
		for (ParamDecl paramDecl : paramDeclList){
			paramDecl.accept(this);
		}
		BodyDecl body = methodDecl.getBody();
		body.accept(this);
		if (!body.isExtern()){
			List<Statement> stmts = body.getBlock().getStatements();
			for (Statement stmt : stmts){
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
		this.coffvar = 0;
		this.coffpar = 0;
		this.stack.newLevel();
		List<ClassDecl> classDeclList = prog.getClassDeclare();
		for (ClassDecl classDecl : classDeclList){
			SymbolInfo classSymbol 	  = new SymbolInfo(classDecl.getName(),classDecl);
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
		Expression exprLeft  = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		exprLeft.accept(this);
		exprRight.accept(this);
		if (!( (exprLeft.getType().equalsIgnoreCase("INTEGER")&&(exprRight.getType().equalsIgnoreCase("INTEGER")))||
			((exprLeft.getType().equalsIgnoreCase("FLOAT")&&(exprRight.getType().equalsIgnoreCase("FLOAT")) )))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be of an INTEGER or FLOAT type");
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(ReturnStmt stmt){
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
		int last 		 = ids.size()-1;
		String lastName  = ids.get(last).getName();
		String type 	 = this.stack.getCurrentType(lastName);
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
