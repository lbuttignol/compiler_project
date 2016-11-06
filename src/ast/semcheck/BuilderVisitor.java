package ir.semcheck;

import ir.ASTVisitor;
import ir.ast.*;
import ir.semcheck.*;
import java.util.List;
import java.util.LinkedList;
import ir.error.*;

public class BuilderVisitor implements ASTVisitor {

	private SymbolTable stack;
	private int inLoop = 0;
	public BuilderVisitor(){
		this.stack = new SymbolTable();
	}
	public SymbolTable getStack(){
		return this.stack;
	}

	@Override
	public List<ir.error.Error> getErrors() {
		return this.errors;
	}
	
	@Override
	public List<ir.error.Error> stackErrors() {
		return this.stack.getErrors();
	}
	@Override
	public void visit(AST stmt){

	}

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
	public void visit(ArrayIdDecl id){
		String type = id.getType();
		int index = id.getNumber();
		int line = id.getLineNumber();
		int col  = id.getColumnNumber();
		if(index<1){
			this.errors.add(new ir.error.Error(line,col,"Array size is not valid"));

		}
		if (!Type.isNativeType(type)){
			SymbolInfo sym = this.stack.getCurrentSymbolInfoClass(type);
			if (sym!=null){
				id.setClassRef((ClassDecl)sym.getReference());
			}
		}
		this.stack.addDeclare(new SymbolInfo(type,id,index));
	}
	
	@Override
	public void visit(ArrayLocation loc){
		List<IdDecl> ids = loc.getIds();
		List<SymbolInfo> symbolInfo = new LinkedList<SymbolInfo>();
		int col = loc.getColumnNumber();
		int line =  loc.getLineNumber();
		for (IdDecl id : ids){
			SymbolInfo symb = new SymbolInfo(id);
			symb.setIndex(1);
			if (!(this.stack.reachable(symb,false))) {
				this.errors.add(new ir.error.Error(line,col,"Unreachable identifier "+id.getName()));
			}			
		}
		IdDecl id = ids.get(ids.size()-1);
		SymbolInfo referencesDecl = this.stack.getCurrentSymbolInfo(id.getName());
		if (referencesDecl!=null)
			loc.setDeclaration(referencesDecl.getReference());
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
		List<IdDecl> ids = loc.getIds();
		List<SymbolInfo> symbolInfo = new LinkedList<SymbolInfo>();
		int col = loc.getColumnNumber();
		int line =  loc.getLineNumber();
		this.stack.reachable(ids,false,true);
		Expression exprArray = loc.getExpr();
		checkExpression(exprArray);
		IdDecl id = ids.get(0);
		SymbolInfo referencesDecl = this.stack.getCurrentSymbolInfo(id.getName());
		if (referencesDecl!=null)
			loc.setDeclaration(referencesDecl.getReference());

	}
	
	@Override
	public void visit(AttributeLocation loc){
		List<IdDecl> ids = loc.getIds();
		List<SymbolInfo> symbolInfo = new LinkedList<SymbolInfo>();
		int col = loc.getColumnNumber();
		int line =  loc.getLineNumber();
		this.stack.reachable(ids,false,false);	
		IdDecl id = ids.get(0);
		SymbolInfo referencesDecl = this.stack.getCurrentSymbolInfo(id.getName());
		if (referencesDecl!=null)
			loc.setDeclaration(referencesDecl.getReference());
	}
	
	@Override
	public void visit(Block block){
		List<FieldDecl> fieldDeclList = block.getVariable();
		List<SymbolInfo> symbolInfoList  = new LinkedList<SymbolInfo>();

		/*************************************************/
		for (FieldDecl fieldDecl : fieldDeclList){
			for (IdDecl idDecl : fieldDecl.getNames()){
				if (idDecl instanceof ArrayIdDecl){
					SymbolInfo symb = new SymbolInfo(idDecl);
					symb.setIndex(1);
					symbolInfoList.add(symb);
				}else{
					symbolInfoList.add(new SymbolInfo(idDecl));
				}
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
	public void visit(BreakStmt stmt){
		int col = stmt.getColumnNumber();
		int line =  stmt.getLineNumber();
		if (inLoop==0)
			this.errors.add(new ir.error.Error(line,col,"Break statement is not inside of a loop"));
	}
	
	@Override
	public void visit(ClassDecl classDecl){
		this.stack.newLevel();
		List<FieldDecl> fieldDeclList = classDecl.getAttributes();

		/*************************************************/
		

		
		for (FieldDecl fieldDecl : fieldDeclList){
			if (!Type.isNativeType(fieldDecl.getType()))
				this.errors.add(new ir.error.Error(fieldDecl.getLineNumber(),fieldDecl.getColumnNumber(),"The class attributes should be of a native type."));

			fieldDecl.setIsAttribute(true);
			fieldDecl.accept(this);
		}
		
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		
		List<SymbolInfo>methodsIds = new LinkedList<SymbolInfo>();
		SymbolInfo methPar;
		for (MethodDecl methodDecl : methodDeclList){
			methPar = new SymbolInfo(true,methodDecl);
			methPar.addParamList(methodDecl.getParams());
			methodsIds.add(methPar);
		}
		this.stack.addDeclareList(methodsIds);

		//ya tenemos los metodos y variables globales

		for (MethodDecl methodDecl : methodDeclList){
			methodDecl.setClassRef(classDecl);
			methodDecl.accept(this);
		}
		this.stack.closeLevel();
	}

	@Override
	public void visit(ContinueStmt stmt){
		int col = stmt.getColumnNumber();
		int line =  stmt.getLineNumber();
		if (inLoop==0)
			this.errors.add(new ir.error.Error(line,col,"Continue statement is not inside of a loop"));
	}
	
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

	public void visit(Expression expr){}
	
	
	@Override
	public void visit(FieldDecl fieldDecl){
		String type = fieldDecl.getType();
		for (IdDecl idDecl : fieldDecl.getNames()){
			idDecl.setType(type);
			if (!Type.isNativeType(type)){
				SymbolInfo sym = this.stack.getCurrentSymbolInfoClass(type);
				if (sym!=null){
					idDecl.setClassRef((ClassDecl)sym.getReference());
				}
			}
			idDecl.setIsAttribute(fieldDecl.isAttribute());
			idDecl.accept(this);
		}
	}
	
	@Override
	public void visit(FloatLiteral lit){}
	
	@Override
	public void visit(ForStmt stmt){
		IdDecl counterName = stmt.getCounterName();
		SymbolInfo symbolInfo = new SymbolInfo(counterName);
		this.stack.newLevel();
		this.stack.addDeclare(symbolInfo);

		Expression initValue = stmt.getInit();
		Expression endValue  = stmt.getEnd();
		Statement body = stmt.getBody();	
		checkExpression(initValue);
		checkExpression(endValue);
		inLoop++;
		checkStatement(body);
		inLoop--;
		this.stack.closeLevel();	
		
	}
	
	@Override
	public void visit(IdDecl id){

		this.stack.addDeclare(new SymbolInfo(id.getType(),id));
	}
	
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

		List<IdDecl> ids = methodCall.getIds();
		List<Expression> params = methodCall.getParams();
		int col = methodCall.getColumnNumber();
		int line   = methodCall.getLineNumber();
		if (ids.size()>1){
			this.stack.reachable(ids,true,false);
			SymbolInfo referencesDecl = this.stack.getCurrentSymbolInfo(ids.get(0).getName());
			System.out.println(referencesDecl);
			if (referencesDecl!=null)
				methodCall.setObject((IdDecl)referencesDecl.getReference());
			referencesDecl = this.stack.getCurrentSymbolInfo(ids.get(1).getName());
						System.out.println(referencesDecl);

			if (referencesDecl!=null)
				methodCall.setMethodDecl((MethodDecl)referencesDecl.getReference());
		}else {
			for (IdDecl id : ids){
				if (!(this.stack.reachable(new SymbolInfo(id),true))) {
					this.errors.add(new ir.error.Error(line,col,"Unreachable identifier "+id.getName()));
				}			
			}
			SymbolInfo referencesDecl = this.stack.getCurrentSymbolInfo(ids.get(0).getName());
			if (referencesDecl!=null)
				methodCall.setMethodDecl((MethodDecl)referencesDecl.getReference());
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
		this.stack.newLevel();
		List<ParamDecl> paramDeclList = method.getParams();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		symbolInfoList.add(new SymbolInfo(method));
		for (ParamDecl paramDecl : paramDeclList){
			paramDecl.accept(this);
		}
		if (!method.getBody().isExtern()){
			List<FieldDecl> fieldDeclList = method.getBody().getBlock().getVariable();
			for (FieldDecl fieldDecl : fieldDeclList){
				fieldDecl.accept(this);
			}
		}
		this.stack.addDeclareList(symbolInfoList);
		if (!method.getBody().isExtern()){
			List<Statement> stmtList = method.getBody().getBlock().getStatements();
			for (Statement stmt : stmtList){
				checkStatement(stmt);
			}
			if (!method.getType().equals("VOID")){
				checkReturnStmt(stmtList,true,method.getLineNumber(),method.getColumnNumber());
			}
			else{
				if(checkReturnStmt(stmtList,false,method.getLineNumber(),method.getColumnNumber()))
						new ir.error.Error(method.getLineNumber(),method.getColumnNumber(),"Invalid return statement");
			}

		}
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(ParamDecl paramDecl){
		this.stack.addDeclare(new SymbolInfo(paramDecl));
	}
	
	@Override
	public void visit(Program prog){
		hasMainClass(prog);
		List<ClassDecl> classDeclList = prog.getClassDeclare();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		for (ClassDecl classDecl : classDeclList){
			SymbolInfo classSymb = new SymbolInfo(classDecl);
			classSymb.addAttList(classDecl.getAttributes());
			classSymb.addMethodList(classDecl.getMethods());
			symbolInfoList.add(classSymb);
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
			if (stmt instanceof IfThenElseStmt){
				IfThenElseStmt ifThenElseStmt = (IfThenElseStmt) stmt;
				ifThenElseStmt.accept(this);
			}else{
				IfThenStmt ifThenStmt = (IfThenStmt) stmt;
				ifThenStmt.accept(this);
			}
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
			BreakStmt breakStmt = (BreakStmt) stmt;
			breakStmt.accept(this);
		}
		if (stmt instanceof ContinueStmt){
			ContinueStmt continueStmt = (ContinueStmt) stmt;
			continueStmt.accept(this);
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
		List<SymbolInfo> symbolInfo = new LinkedList<SymbolInfo>();
		int col = loc.getColumnNumber();
		int line =  loc.getLineNumber();

		for (IdDecl id : ids){
			if (!(this.stack.reachable(new SymbolInfo(id),false))) {
				this.errors.add(new ir.error.Error(line,col,"Unreachable identifier "+id.getName()));
			}			
		}
		IdDecl id = ids.get(ids.size()-1);
		SymbolInfo referencesDecl = this.stack.getCurrentSymbolInfo(id.getName());
		if (referencesDecl!=null)
			loc.setDeclaration(referencesDecl.getReference());
	}
	
	@Override
	public void visit(WhileStmt stmt){
		Expression condition = stmt.getCondition();
		Statement body 		 = stmt.getBody();
		checkExpression(condition);
		inLoop++;
		checkStatement(body);
		inLoop--;
	}

	private void hasMainClass(Program prog){
		boolean existsMainClass = false;
		boolean existsMainMethod = false;
		for (ClassDecl classDecl : prog.getClassDeclare()){
			if (classDecl.getName().toUpperCase().equals("MAIN")){
				existsMainClass = true;
				for (MethodDecl methodDecl : classDecl.getMethods()){
					if (methodDecl.getName().equals("main")){
						existsMainMethod = true;
						break;
					}
				}
				break;
			}
		}
		int line = prog.getLineNumber();
		int col  = prog.getColumnNumber();
		if (!existsMainClass){
			this.errors.add(new ir.error.Error(line,col,"The Main class not exist"));

		}else{
			if (!existsMainMethod){
			this.errors.add(new ir.error.Error(line,col,"The main method not exist"));
		}
		}
	}

	public boolean checkReturnStmt(List<Statement> stmtL,boolean printErrors,int line, int col){
		boolean thereIsReturnStmt = false;
		boolean result = true;
		for (Statement stmt : stmtL){
			thereIsReturnStmt = thereIsReturnStmt || (stmt instanceof ReturnStmt);
		}
		if (!thereIsReturnStmt){
			result =hasReturnStmt(stmtL,line,col);
			if ((!result)&& printErrors){
				this.errors.add(new ir.error.Error(line,col,"Missing a return statement"));
			}
			return result;
		}else{
			return true;
		}
		
	}

	public boolean hasReturnStmt(List<Statement> stmtL,int line, int col){
		boolean res = false;
		if (stmtL.size()>0){
			int counter = 0;
			for (Statement stmt : stmtL){
				boolean result = false;
				if (stmt instanceof IfThenStmt){
					List<Statement> ifStmtList  = new  LinkedList<Statement>();
					List<Statement> elseStmtList = new  LinkedList<Statement>();

					if (stmt instanceof IfThenElseStmt){
						IfThenElseStmt ifThenElse = (IfThenElseStmt) stmt;
						ifStmtList.add(ifThenElse.getIfBlock());
						elseStmtList.add(ifThenElse.getElseBlock());
						result = result||(hasReturnStmt(ifStmtList,line,col)&&hasReturnStmt(elseStmtList,line,col));
					}else{
						IfThenStmt ifThenStmt = (IfThenStmt) stmt;
						ifStmtList.add(ifThenStmt.getIfBlock());
						result = result || hasReturnStmt(ifStmtList,line,col);
					}
				}else{
					if (stmt instanceof Block){
						Block block = (Block) stmt;
						result = result || checkReturnStmt(block.getStatements(),false,line,col);
					} else{

						if ((stmt instanceof ReturnStmt))
							result = true; 
					}
				}
				res = res || result ;
			}
		}else{
			return false;
		}
		return res;
	}
}
