package ir.semcheck;

import ir.ASTVisitor;
import ir.ast.*;
import ir.semcheck.*;
import java.util.List;
import java.util.LinkedList;
import ir.error.*;

public class BuilderVisitor implements ASTVisitor {

	private SymbolTable stack;



	
	public BuilderVisitor(){
		this.stack = new SymbolTable();
	}
	
	@Override
	public void visit(AST stmt){}

	@Override	
	public void visit(ArithmeticBinOp expr){
		Expression leftExpr = expr.getLeftOperand();
		Expression rigthExpr= expr.getRightOperand();
		checkExpression(leftExpr);
		checkExpression(rigthExpr);
	}
	
	@Override
	public void visit(ArithmeticUnaryOp expr){
		Expression operand = expr.getOperand();
		checkExpression(operand);
	}
	
	@Override
	public void visit(ArrayIdDecl stmt){}
	
	@Override
	public void visit(ArrayLocation loc){
		List<String> ids = loc.getIds();
		List<SymbolInfo> symbolInfo = new LinkedList<SymbolInfo>();
		int col = loc.getColumnNumber();
		int line =  loc.getLineNumber();
		for (String id : ids){
			if (!(this.stack.reachable(new SymbolInfo(id,col,line)))){
				new ir.error.Error(line,col,"Unreachable identifier "+id);
			}			
		}
		Expression exprArray = loc.getExpression();
		checkExpression(exprArray);
	}
	
	@Override
	public void visit(AssignStmt stmt){
		//Location loc = stmt.getLocation();
		Expression expr = stmt.getExpression();
		if (stmt.getLocation() instanceof VarLocation){
			VarLocation loc = (VarLocation)stmt.getLocation();
			loc.accept(this);
		}
		if (stmt.getLocation() instanceof AttributeLocation){
			AttributeLocation loc = (AttributeLocation) stmt.getLocation();
			loc.accept(this);
		}
		if (stmt.getLocation() instanceof ArrayLocation){
			ArrayLocation loc = (ArrayLocation) stmt.getLocation();
			loc.accept(this);
		}

		if (stmt.getLocation() instanceof AttributeArrayLocation){
			AttributeArrayLocation loc = (AttributeArrayLocation) stmt.getLocation();
			loc.accept(this);
		}
		checkExpression(expr);
	}
	
	@Override
	public void visit(AttributeArrayLocation loc){
		List<String> ids = loc.getIds();
		List<SymbolInfo> symbolInfo = new LinkedList<SymbolInfo>();
		int col = loc.getColumnNumber();
		int line =  loc.getLineNumber();
		for (String id : ids){
			if (!(this.stack.reachable(new SymbolInfo(id,col,line)))){
				new ir.error.Error(line,col,"Unreachable identifier "+id);
			}			
		}
		Expression exprArray = loc.getExpr();
		checkExpression(exprArray);
	}
	
	@Override
	public void visit(AttributeLocation loc){
		List<String> ids = loc.getIds();
		List<SymbolInfo> symbolInfo = new LinkedList<SymbolInfo>();
		int col = loc.getColumnNumber();
		int line =  loc.getLineNumber();
		for (String id : ids){
			if (!(this.stack.reachable(new SymbolInfo(id,col,line)))){
				new ir.error.Error(line,col,"Unreachable identifier "+id);
			}			
		}
	}
	
	@Override
	public void visit(Block block){
		List<FieldDecl> fieldDeclList = block.getVariable();
		List<SymbolInfo> symbolInfoList  = new LinkedList<SymbolInfo>();

		/*************************************************/
		for (FieldDecl fieldDecl : fieldDeclList){
			for (IdDecl idDecl : fieldDecl.getNames()){
				symbolInfoList.add(new SymbolInfo(idDecl));
			}
		}
		List<Statement> statements = block.getStatements();
		this.stack.newLevel();
		this.stack.addDeclareList(symbolInfoList);

		for (Statement stmt : statements){
			checkStatement(stmt);
		}

		this.stack.closeLevel();

	} 		
	
	@Override
	public void visit(BodyDecl stmt){}
	
	@Override
	public void visit(BooleanLiteral lit){}
	
	@Override
	public void visit(BreakStmt stmt){}
	
	@Override
	public void visit(ClassDecl classDecl){
		System.out.println("Class "+classDecl.getName());
		List<FieldDecl> fieldDeclList = classDecl.getAttributes();
		List<SymbolInfo> symbolInfoList  = new LinkedList<SymbolInfo>();

		/*************************************************/
		for (FieldDecl fieldDecl : fieldDeclList){
			for (IdDecl idDecl : fieldDecl.getNames()){
				System.out.println("New field declared: "+idDecl.getName());
				symbolInfoList.add(new SymbolInfo(idDecl));
			}
		}
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		for (MethodDecl methodDecl : methodDeclList){
			symbolInfoList.add(new SymbolInfo(methodDecl));
		}
		this.stack.newLevel();
		this.stack.addDeclareList(symbolInfoList);

		//ys tenemos los metodos y variables globales

		for (MethodDecl methodDecl : methodDeclList){
			methodDecl.accept(this);
		}


		this.stack.closeLevel();
	
	}

	@Override
	public void visit(ContinueStmt stmt){}
	
	@Override
	public void visit(EqBinOp expr){
		Expression leftExpr = expr.getLeftOperand();
		Expression rigthExpr= expr.getRightOperand();
		checkExpression(leftExpr);
		checkExpression(rigthExpr);
	}

	public void checkExpression(Expression expr){
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
	public void visit(Expression expr){
		
	}
	
	
	@Override
	public void visit(FieldDecl stmt){}
	
	@Override
	public void visit(FloatLiteral lit){}
	
	@Override
	public void visit(ForStmt stmt){
		String counterName = stmt.getCounterName();
		int col = stmt.getColumnNumber();
		int line = stmt.getLineNumber();
		SymbolInfo symbolInfo = new SymbolInfo(counterName,col,line);
		this.stack.newLevel();
		this.stack.addDeclare(symbolInfo);
		
		Expression initValue = stmt.getInit();
		Expression endValue  = stmt.getEnd();
		Statement body = stmt.getBody();	
		checkExpression(initValue);
		checkExpression(endValue);
		checkStatement(body);

		this.stack.closeLevel();	
		
	}
	
	@Override
	public void visit(IdDecl loc){}
	
	@Override
	public void visit(IfThenElseStmt stmt){
		Expression condition = stmt.getCondition();
		Statement ifBlock    = stmt.getIfBlock();
		Statement elseBlock  = stmt.getElseBlock();
		checkExpression(condition);
		checkStatement(ifBlock);
		checkStatement(elseBlock);
	}
	
	@Override
	public void visit(IfThenStmt stmt){
		Expression condition = stmt.getCondition();
		Statement ifBlock    = stmt.getIfBlock();
		checkExpression(condition);
		checkStatement(ifBlock);
	}
	
	@Override
	public void visit(IntLiteral lit){}
	
	@Override
	public void visit(LogicalBinOp expr){
		Expression leftExpr = expr.getLeftOperand();
		Expression rigthExpr= expr.getRightOperand();
		checkExpression(leftExpr);
		checkExpression(rigthExpr);
	}
	
	@Override
	public void visit(LogicalUnaryOp expr){
		Expression operand = expr.getOperand();
		checkExpression(operand);
	}
	
	@Override
	public void visit(MethodCall methodCall){
		List<String> ids = methodCall.getIds();
		List<Expression> params = methodCall.getParams();
		int col = methodCall.getColumnNumber();
		int line   = methodCall.getLineNumber();
		for (String id : ids){
			if (!(this.stack.reachable(new SymbolInfo(id,col,line)))){
				new ir.error.Error(line,col,"Unreachable identifier "+id);
			}	
		}
		for (Expression param : params){
			checkExpression(param);
		}
		
	}
	
	@Override
	public void visit(MethodCallStmt methodCallStmt){
		MethodCall methodCall = methodCallStmt.getMethodCall();
		methodCall.accept(this);
	}
	
	@Override
	public void visit(MethodDecl method){
		System.out.println("Method Decl "+method.getName());
		List<ParamDecl> paramDeclList = method.getParams();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		symbolInfoList.add(new SymbolInfo(method));
		for (ParamDecl paramDecl : paramDeclList){
			symbolInfoList.add(new SymbolInfo(paramDecl));
		}
		if (!method.getBody().isExtern()){

			List<FieldDecl> fieldDeclList = method.getBody().getBlock().getVariable();
			for (FieldDecl fieldDecl : fieldDeclList){
				for (IdDecl idDecl : fieldDecl.getNames()){
					symbolInfoList.add(new SymbolInfo(idDecl));
				}
			}
		}
		this.stack.newLevel();
		this.stack.addDeclareList(symbolInfoList);
		if (!method.getBody().isExtern()){
			List<Statement> stmtList = method.getBody().getBlock().getStatements();
			for (Statement stmt : stmtList){
				checkStatement(stmt);
			}
		}
		/****************************************************/
		this.stack.closeLevel();


	}
	
	@Override
	public void visit(ParamDecl stmt){}
	
	@Override
	public void visit(Program prog){
		System.out.println("program");
		List<ClassDecl> classDeclList = prog.getClassDeclare();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		for (ClassDecl classDecl : classDeclList){
			symbolInfoList.add(new SymbolInfo(classDecl));
		}
		this.stack.newLevel();
		this.stack.addDeclareList(symbolInfoList);

		/***********************************************************/
		
		for (ClassDecl classDecl : classDeclList){
			classDecl.accept(this);
		}

		this.stack.closeLevel();
	}
	
	@Override
	public void visit(RelationalBinOp expr){
		Expression leftExpr = expr.getLeftOperand();
		Expression rigthExpr= expr.getRightOperand();
		checkExpression(leftExpr);
		checkExpression(rigthExpr);
	}
	
	@Override
	public void visit(ReturnStmt stmt){
		Expression expr = stmt.getExpression();
		checkExpression(expr);
	}
	
	@Override
	public void visit(ReturnVoidStmt stmt){}
	
	@Override
	public void visit(Skip stmt){}
	
	@Override
	public void visit(Statement stmt){

	}

	public void checkStatement(Statement stmt){
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
		List<String> ids = loc.getIds();
		List<SymbolInfo> symbolInfo = new LinkedList<SymbolInfo>();
		int col = loc.getColumnNumber();
		int line =  loc.getLineNumber();
		for (String id : ids){
			if (!(this.stack.reachable(new SymbolInfo(id,col,line)))){
				new ir.error.Error(line,col,"Unrecheable identifier "+id);
			}			
		}
	}
	
	@Override
	public void visit(WhileStmt stmt){
		Expression condition = stmt.getCondition();
		Statement body 		 = stmt.getBody();
		checkExpression(condition);
		checkStatement(body);
	}
	
}