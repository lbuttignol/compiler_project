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
		Expression exprLeft = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		checkExpressionType(exprLeft);
		checkExpressionType(exprRight);

		System.out.println("Tipo operand izq -"+exprLeft.getType());
		System.out.println("Tipo operand der -"+exprRight.getType());

		if (!( (exprLeft.getType().equals("INTEGER")&&(exprRight.getType().equals("INTEGER")))||
			((exprLeft.getType().equals("FLOAT")&&(exprRight.getType().equals("FLOAT")) )))){
			System.out.println("Operacion aritmetica binaria no valida") ;
			// new Error ...
		}else{
			stmt.setType(exprLeft.getType());
		}


		
	}
	
	@Override
	public void visit(ArithmeticUnaryOp stmt){
		Expression expr = stmt.getOperand();
		checkExpressionType(expr);
		if (!((expr.getType().equals("INTEGER"))||(expr.getType().equals("FLOAT")))){
			System.out.println("Operacion aritmetica unaria no valida");
			// new Error .......
		}else{
			stmt.setType(expr.getType());
		}
	} 
	
	@Override
	public void visit(ArrayIdDecl stmt){}
	
	@Override
	public void visit(ArrayLocation loc){
		List<String> ids = loc.getIds();
		String type = this.stack.getCurrentType(ids.get(ids.size()-1));
		System.out.println("ARRAYLOCATION --- "+type);
		if (type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(AssignStmt assignStmt){
		Location loc =assignStmt.getLocation();
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


		System.out.println("LOC-"	+loc.getType());
		System.out.println("EXPR-"+expr.getType());
		if (!loc.getType().equals(expr.getType())){
			System.out.println("ERROR");
		}

	}
	
	@Override
	public void visit(AttributeArrayLocation loc){
		List<String> ids = loc.getIds();
		System.out.println("SIZE-"+String.valueOf(ids.size()));
		String type = this.stack.getCurrentType(ids.get(ids.size()-1));
		System.out.println("TYPE-"+type);
		if (type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(AttributeLocation loc){
		List<String> ids = loc.getIds();
				System.out.println("SIZE-"+String.valueOf(ids.size()-1));

		String type = this.stack.getCurrentType(ids.get(ids.size()-1));
				System.out.println("TYPE-"+type);

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
		System.out.println("ClassDecl - "+classDecl.getName());
		List<FieldDecl> fieldDeclList = classDecl.getAttributes();
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		this.stack.newLevel();
		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}
		for (MethodDecl methodDecl : methodDeclList){
			methodDecl.accept(this);
		}
		this.stack.closeLevel();

	}
	
	@Override
	public void visit(ContinueStmt stmt){}
	
	@Override
	public void visit(EqBinOp stmt){
		Expression exprLeft = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		checkExpressionType(exprLeft);
		checkExpressionType(exprRight);
		if ((exprLeft.getType().equals("INTEGER")^(exprRight.getType().equals("INTEGER")))){
			System.out.println("Operacion EQBINOP no valida");
			// new Error ...
		}else{
			if ((exprLeft.getType().equals("FLOAT")^(exprRight.getType().equals("FLOAT")))){
				System.out.println("Operacion EQBINOP no valida");
				// new Error ...
			}else{
				if (((exprLeft.getType().equals("BOOLEAN"))^(exprRight.getType().equals("BOOLEAN")))){
					System.out.println("Operacion EQBINOP no valida");
					// new Error ...
				}else{
					stmt.setType("BOOLEAN");
				}
			}
			
		}
	}

	@Override
	public void visit(Expression stmt){}

	public void checkExpressionType(Expression expr){
		if (expr instanceof VarLocation){
			System.out.println("VARLOCATION");
			VarLocation loc = (VarLocation)expr;
			loc.accept(this);
		}	
		if (expr instanceof AttributeLocation){
			System.out.println("ATTRIBUTELOCATION");

			AttributeLocation loc = (AttributeLocation) expr;
			loc.accept(this);
		}	
		if (expr instanceof ArrayLocation){
			System.out.println("ArrayLocation");

			ArrayLocation loc = (ArrayLocation) expr;
			loc.accept(this);
		}
		if (expr instanceof AttributeArrayLocation){
			System.out.println("AttributeArrayLocation");

			AttributeArrayLocation loc = (AttributeArrayLocation) expr;
			loc.accept(this);
		}
		if (expr instanceof MethodCall){
			System.out.println("MethodCall");

			MethodCall methodCall = (MethodCall) expr;
			methodCall.accept(this);
		}
		if (expr instanceof LogicalBinOp){
			System.out.println("LogicalBinOp");

			LogicalBinOp logBinOp = (LogicalBinOp) expr;
			logBinOp.accept(this);
		}
		if (expr instanceof ArithmeticBinOp){
						System.out.println("ArithmeticBinOp");

			ArithmeticBinOp arithBinOp = (ArithmeticBinOp) expr;
			arithBinOp.accept(this);
		}
		if (expr instanceof RelationalBinOp){
			System.out.println("RelationalBinOp");
			RelationalBinOp relBinOp = (RelationalBinOp) expr;
			relBinOp.accept(this);
		}
		if (expr instanceof EqBinOp){
			System.out.println("EqBinOp");
			EqBinOp eqBinOp = (EqBinOp) expr;
			eqBinOp.accept(this);
		}
		if (expr instanceof ArithmeticUnaryOp){
			System.out.println("ArithmeticUnaryOp");
			ArithmeticUnaryOp arithUnaryOp = (ArithmeticUnaryOp) expr;
			arithUnaryOp.accept(this);
		}
		if (expr instanceof LogicalUnaryOp){
			System.out.println("LogicalUnaryOp");
			LogicalUnaryOp logicalUnaryOp = (LogicalUnaryOp) expr;
			logicalUnaryOp.accept(this);
		}
			System.out.println("SALIDA EXPR");

	}
	
	@Override
	public void visit(FieldDecl fieldDecl){
		String type = fieldDecl.getType();
		if (!Type.contains(type)){
		System.out.println("Tipo de field decl no existe");
		 //	new Error
		}
		List<IdDecl> idDeclList = fieldDecl.getNames();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		for (IdDecl idDecl : idDeclList){
			type = idDecl.getType();
			String name = idDecl.getName();
			int col 	= idDecl.getColumnNumber();
			int line 	= idDecl.getLineNumber();
			System.out.println("IdDecl - "+name+"-"+type);
			symbolInfoList.add(new SymbolInfo(type,name,col,line));
		}
		this.stack.addDeclareList(symbolInfoList);
	}
	
	@Override
	public void visit(FloatLiteral lit){}
	
	@Override
	public void visit(ForStmt stmt){
		String name = stmt.getCounterName();
		String type = "INTEGER";
		int    col = stmt.getColumnNumber();
		int    line = stmt.getLineNumber();
		this.stack.addDeclare(new SymbolInfo(type,name,col,line));

		Expression initValue = stmt.getInit();
		Expression endValue  = stmt.getEnd();

		checkExpressionType(initValue);
		checkExpressionType(endValue);
		if ((initValue.getType().equals("INTEGER"))&&(endValue.getType().equals("INTEGER"))){
			IntLiteral init = (IntLiteral) initValue;
			IntLiteral end  = (IntLiteral) endValue;
			if (init.getValue()>end.getValue()){
				System.out.println("For- init value no es menor a igual a end value");
				// new Error -...
			}
		}else{
			System.out.println("For- condicion no valida");
			//Error
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
			System.out.println("IF then else - COndicino no valida");
			//new Error...
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
			System.out.println("If then - Condicion no valida");
			//new Error...
		}

		Statement ifBlock = stmt.getIfBlock();
		checkStatementType(ifBlock);
	}
	
	@Override
	public void visit(IntLiteral lit){}
	
	@Override
	public void visit(LogicalBinOp stmt){
		Expression exprLeft = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		checkExpressionType(exprLeft);
		checkExpressionType(exprRight);
		if (!((exprLeft.getType().equals("BOOLEAN"))&&(exprRight.getType().equals("BOOLEAN")))){
			System.out.println("Logical Bin Op - No valido");
			// new Error ...
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(LogicalUnaryOp stmt){
		Expression expr =stmt.getOperand();
		checkExpressionType(expr);
		if (!expr.getType().equals("BOOLEAN")){
			System.out.println("Logical Unary Op- No valdo");
			// new Error...
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(MethodCall methodCall){
		List<String> ids = methodCall.getIds();
		String type = this.stack.getCurrentType(ids.get(ids.size()-1));
		if (type!=null){ //VEEEEEEERRRRRRRRRRRRRRRRR ERROR YA CONTROLADO EN BUILDER
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
		System.out.println("MethodDecl - "+methodDecl.getName()+"-"+methodDecl.getType());
		String type = methodDecl.getType();
		if (!Type.contains(type)){
			System.out.println("Tupo de metodo no valido");
			// new Error ...
		}
		this.stack.newLevel();
		List<SymbolInfo> symbolInfoList= new LinkedList<SymbolInfo>();
		//type = methodDecl.getType();
		String name = methodDecl.getName();
		int col 	= methodDecl.getColumnNumber();
		int line 	= methodDecl.getLineNumber();
		symbolInfoList.add(new SymbolInfo(true,type,name,col,line));

		List<ParamDecl> paramDeclList = methodDecl.getParams();
		for (ParamDecl paramDecl : paramDeclList){
			type = paramDecl.getType();
			name = paramDecl.getName();
			col = paramDecl.getColumnNumber();
			line = paramDecl.getLineNumber();
			System.out.println("ParamDecl - "+name+"-"+type);
			symbolInfoList.add(new SymbolInfo(true,type,name,col,line));
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
			System.out.println("Tipo de parametro no valido");
			// new Error ....
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
			System.out.println("Relation Bin Op - No valido") ;
			// new Error ...
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(ReturnStmt stmt){
		Expression exp = stmt.getExpression();
		checkExpressionType(exp);
		System.out.println(this.stack.getMethodType());
		if ((this.stack.getMethodType()==null) || !(exp.getType().equals(this.stack.getMethodType()))) {
			System.out.println("Return stmt  no valido.");
			//new Error...
		}
	}
	
	@Override
	public void visit(ReturnVoidStmt stmt){}
	
	@Override
	public void visit(Skip stmt){}
	
	@Override
	public void visit(Statement stmt){}

	public void checkStatementType(Statement stmt){
					System.out.println("ENTRO STATEMENTS");

		if (stmt instanceof AssignStmt ){ 
			System.out.println("ASSIGSTM");
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
			System.out.println("BLOCK");
			Block block = (Block) stmt;
			block.accept(this);
		}
	}


	
	@Override
	public void visit(VarLocation loc){
		System.out.println("VARLOCATION");
		List<String> ids = loc.getIds();
		String type = this.stack.getCurrentType(ids.get(ids.size()-1));
		if(type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(WhileStmt stmt){
		Expression condition = stmt.getCondition();
		checkExpressionType(condition);
		if (!condition.getType().equals("BOOLEAN")){
			System.out.println("ERROR en while - condicion no es bool");
			//new Error...
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
