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
		if (exprLeft.getType().equals("UNDEFINED")){

			//System.out.println("CLASS: "+exprLeft.toString());
			//System.out.println("accept de left "+ exprLeft.getClass());
			exprLeft.accept(this);
		}
		if(exprRight.getType().equals("UNDEFINED")){
			//System.out.println("accept de right");
			exprRight.accept(this);
		}
		//checkExpressionType(exprLeft);
		//checkExpressionType(exprRight);

		//System.out.println("Left expression type: " + exprLeft.getType());
		//System.out.println("Right expression type: " + exprRight.getType());
		if (!( ( exprLeft.getType().equals("INTEGER")&&(exprRight.getType().equals("INTEGER")) ) ||
			((exprLeft.getType().equals("FLOAT")&&(exprRight.getType().equals("FLOAT")) )))){
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
		if (!((expr.getType().equals("INTEGER"))||(expr.getType().equals("FLOAT")))){
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
	}
	
	@Override
	public void visit(AssignStmt assignStmt){
		//System.out.println("Assing visit running");
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
		expr.accept(this);
		if (!loc.getType().equals(expr.getType())&& !loc.getType().equals("UNDEFINED")) {
			new ir.error.Error(expr.getLineNumber(),expr.getColumnNumber(), loc.getType()+" expression expected");
		}

	}
	
	@Override
	public void visit(AttributeArrayLocation loc){
		List<IdDecl> ids = loc.getIds();
		//System.out.println("SIZE-"+String.valueOf(ids.size()));
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		String type = this.stack.getCurrentType(lastName);
		//System.out.println("TYPE-"+type);
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
		if (exprLeft.getType().equals("INTEGER") || exprLeft.getType().equals("FLOAT") || exprLeft.getType().equals("BOOLEAN")){
			if (exprLeft.getType().equals(exprRight.getType())){
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
			symbolInfoList.add(new SymbolInfo(idDecl.getType(), idDecl));
		}
		this.stack.addDeclareList(symbolInfoList);
	}
	
	@Override
	public void visit(FloatLiteral lit){}
	
	@Override
	public void visit(ForStmt stmt){
		this.stack.newLevel();
		IdDecl counterName = stmt.getCounterName();
		this.stack.addDeclare(new SymbolInfo(counterName.getType(), counterName));

		Expression initValue = stmt.getInit();
		Expression endValue  = stmt.getEnd();

		initValue.accept(this);
		endValue.accept(this);
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
		if (!condition.getType().equals("BOOLEAN")){
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
		if (!condition.getType().equals("BOOLEAN")){
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
		if (!((exprLeft.getType().equals("BOOLEAN"))&&(exprRight.getType().equals("BOOLEAN")))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be booleans");
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(LogicalUnaryOp stmt){
		Expression expr =stmt.getOperand();
		expr.accept(this);
		if (!expr.getType().equals("BOOLEAN")){
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
					if (current.getType().equals("UNDEFINED"))
						current.accept(this);
						//System.out.println("after exp check: ,(tipo: "+current.getType()+")");

					if (!param.getType().equals(current.getType()))
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
					if (!ret.getExpression().getType().equals(type)){
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
			this.stack.addDeclare(classDecl);
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
		exprLeft.accept(this);
		exprRight.accept(this);
		if (!( (exprLeft.getType().equals("INTEGER")&&(exprRight.getType().equals("INTEGER")))||
			((exprLeft.getType().equals("FLOAT")&&(exprRight.getType().equals("FLOAT")) )))){
			new ir.error.Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be of an integer or float type");
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(ReturnStmt stmt){
		//System.out.println("ReturnStmt visit");
		Expression expr = stmt.getExpression();
		if(expr.getType().equals("UNDEFINED"))
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
		//System.out.println("VARLOCATION");
		List<IdDecl> ids = loc.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		String type = this.stack.getCurrentType(lastName);
		//System.out.println("Type of var location("+lastName+"): "+type);
		if(type!=null)
			loc.setType(type);
		SymbolInfo class = this.stack.getCurrentSymbolInfo(ids.get(0).getType());//obtengo symbol info clase
		List<SymbolInfo> meth;

	}
	
	@Override
	public void visit(WhileStmt stmt){
		Expression condition = stmt.getCondition();
		condition.accept(this);
		if (!condition.getType().equals("BOOLEAN")){
			//System.out.println("ERROR en while - condicion no es bool");
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
