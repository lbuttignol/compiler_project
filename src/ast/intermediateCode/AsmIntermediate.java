package ir.intermediateCode;

import ir.ASTVisitor;
import ir.ast.*;
import ir.semcheck.*;
import ir.error.*;

import java.util.List;
import java.util.LinkedList;
/*
dudas para consultar 
PARÁMETROS DE CADA StatementCode???


*/
public class AsmIntermediate implements ASTVisitor {
//	private String programName;
	private List<StatementCode> code;
	private AST temporal;
	private Integer ifCounter, whileCounter, forCounter;

	public AsmIntermediate(){
		this.code = new LinkedList<StatementCode>();
		this.ifCounter 	= 0;
		this.whileCounter = 0;
		this.forCounter = 0;
	}

	@Override
	public void visit(AST stmt){}
	
	@Override
	public void visit(ArithmeticBinOp stmt){
		Expression exprL = stmt.getLeftOperand();
		Expression exprR = stmt.getRightOperand();
		BinOpType op = stmt.getOperator();
		OperationCode c = createBinOpCode(exprL, op, exprR );
		this.addStatement(new StatementCode(c,exprL,exprR,temporal));
	}
	
	@Override
	public void visit(ArithmeticUnaryOp stmt){}
	
	@Override
	public void visit(ArrayIdDecl stmt){}
	
	@Override
	public void visit(ArrayLocation stmt){}
	
	@Override
	public void visit(AssignStmt stmt){}
	
	@Override
	public void visit(AttributeArrayLocation stmt){}
	
	@Override
	public void visit(AttributeLocation stmt){}
	
	@Override
	public void visit(Block block){
		List<FieldDecl> fieldDeclList = block.getVariable();
		List<Statement> stmtList = block.getStatements();
		for (FieldDecl fieldDecl: fieldDeclList) {
			fieldDecl.accept(this);
		}

		for (Statement stmt: stmtList) {
			stmt.accept(this);
		}
	} 
	
	@Override
	public void visit(BodyDecl body){
		if (body.isExtern()) {
			// what happen when the block is extern?????????????????????
		}else {
			Block block = body.getBlock();
			block.accept(this);	
		}
	}
	
	@Override
	public void visit(BooleanLiteral lit){
		this.temporal = lit;
	}
	
	@Override
	public void visit(BreakStmt stmt){}
	
	@Override
	public void visit(ClassDecl classDecl){
		List<FieldDecl> fieldDeclList = classDecl.getAttributes();
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		this.addStatement(new StatementCode(OperationCode.BEGINCLASS,classDecl,null,null));
		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}
		for (MethodDecl methodDecl : methodDeclList){
			methodDecl.accept(this);
		}
		this.addStatement(new StatementCode(OperationCode.ENDCLASS,classDecl,null,null));
	}
	
	@Override
	public void visit(ContinueStmt stmt){}
	
	@Override
	public void visit(EqBinOp stmt){
		Expression exprL = stmt.getLeftOperand();
		Expression exprR = stmt.getRightOperand();
		BinOpType op = stmt.getOperator();
		OperationCode c = createBinOpCode(exprL, op, exprR );
		this.addStatement(new StatementCode(c,exprL,exprR,temporal));
	}
	
	@Override
	public void visit(Expression stmt){}
	
	@Override
	public void visit(FieldDecl fieldDecl){
		String type = fieldDecl.getType();
		List<IdDecl> idDeclList = fieldDecl.getNames();
		for ( IdDecl idDecl : idDeclList) {
			String name = idDecl.getName();
			this.addStatement(new StatementCode(OperationCode.FIELD,idDecl,fieldDecl,null));
		}	
	}
	
	@Override
	public void visit(FloatLiteral lit){
		this.temporal = lit;
	}
	
	@Override
	public void visit(ForStmt stmt){
		Integer forNum = this.getForCounter();

	}
	
	@Override
	public void visit(IdDecl loc){}
	
	@Override
	public void visit(IfThenElseStmt stmt){
		Integer ifNum = this.getIfCounter();
		IntLiteral intLit = new IntLiteral(ifNum,stmt.getLineNumber(),stmt.getColumnNumber());
		this.addStatement(new StatementCode(OperationCode.BEGINIF,stmt,intLit, null));
		stmt.getCondition().accept(this);
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,stmt.getCondition(), intLit,null));
		stmt.getIfBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.ELSEBLOCK,stmt.getIfBlock(), intLit,null));
		stmt.getElseBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.ENDIF, stmt, intLit,null));
	}
	
	@Override
	public void visit(IfThenStmt stmt){
		//condition, ifBlock
		Integer ifNum = this.getIfCounter();
		IntLiteral intLit = new IntLiteral(ifNum,stmt.getLineNumber(),stmt.getColumnNumber());
		this.addStatement(new StatementCode(OperationCode.BEGINIF,stmt,intLit, null));
		stmt.getCondition().accept(this);
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,temporal, intLit,null)); //saltar al fin de if
		stmt.getIfBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.ENDIF, stmt, intLit,null));
	}
	
	@Override
	public void visit(IntLiteral lit){
		this.temporal = lit;
	}
	
	@Override
	public void visit(LogicalBinOp stmt){
		Expression exprL = stmt.getLeftOperand();
		Expression exprR = stmt.getRightOperand();
		BinOpType op = stmt.getOperator();
		OperationCode c = createBinOpCode(exprL, op, exprR );
		this.addStatement(new StatementCode(c,exprL,exprR,temporal));
	}
	
	@Override
	public void visit(LogicalUnaryOp stmt){}
	
	@Override
	public void visit(MethodCall stmt){}
	
	@Override
	public void visit(MethodCallStmt stmt){}
	
	@Override
	public void visit(MethodDecl methodDecl){
		String type = methodDecl.getType();
		String name = methodDecl.getName();
		this.addStatement(new StatementCode(OperationCode.BEGINMETHOD, methodDecl, null, null));
		List<ParamDecl> paramDeclList = methodDecl.getParams();
		for (ParamDecl paramDecl : paramDeclList) {
			paramDecl.accept(this);
		}
		BodyDecl body = methodDecl.getBody();
		body.accept(this);
		this.addStatement(new StatementCode(OperationCode.ENDMETHOD, methodDecl, null, null));
	}
	
	@Override
	public void visit(ParamDecl paramDecl){
		String name = paramDecl.getName();
		String type = paramDecl.getType();
		this.addStatement(new StatementCode(OperationCode.PARAMDECL, paramDecl, null, null));
	}
	
	@Override
	public void visit(Program prog){
		List<ClassDecl> classDeclList = prog.getClassDeclare();
		this.addStatement(new StatementCode(OperationCode.BEGINPROGRAM, prog, null, null));
		for (ClassDecl classDecl : classDeclList ) {
			classDecl.accept(this);
		}
		this.addStatement(new StatementCode(OperationCode.ENDPROGRAM, prog, null, null));
	}
	
	@Override
	public void visit(RelationalBinOp stmt){
		Expression exprL = stmt.getLeftOperand();
		Expression exprR = stmt.getRightOperand();
		BinOpType op = stmt.getOperator();
		OperationCode c = createBinOpCode(exprL, op, exprR );
		this.addStatement(new StatementCode(c,exprL,exprR,temporal)); 
	}
	
	@Override
	public void visit(ReturnStmt stmt){}
	
	@Override
	public void visit(ReturnVoidStmt stmt){}
	
	@Override
	public void visit(Skip stmt){}
	
	@Override
	public void visit(Statement stmt){}
	
	@Override
	public void visit(VarLocation loc){}
	
	@Override
	public void visit(WhileStmt stmt){}

	@Override
	public String toString(){
		String res = "";
		for (StatementCode stmt : code) {
			res = res + stmt.toString() + "\n";
		}
		return res;
	}

	private void addStatement(StatementCode instruction){
		this.code.add(instruction);
	}

	private Integer getIfCounter(){
		Integer aux = this.ifCounter;
		this.ifCounter ++;
		return aux;
	}

	private Integer getForCounter(){
		Integer aux = this.forCounter;
		this.forCounter ++;
		return aux;
	}

	private Integer getWhileCounter(){
		Integer aux = this.whileCounter;
		this.whileCounter ++;
		return aux;
	}

	private Operands operandsType(Expression l, Expression r){
		String leftType = l.getType();
		String rightType = r.getType();
		if ((leftType.equals("INTEGER")) && (rightType.equals("INTEGER"))) {
			return Operands.II;
		}
		if ((leftType.equals("INTEGER")) && (rightType.equals("FLOAT"))) {
			return Operands.IF;
		}
		if ((leftType.equals("INTEGER")) && (rightType.equals("BOOLEAN"))) {
			return Operands.IB;
		}
		if ((leftType.equals("FLOAT")) 	&& (rightType.equals("FLOAT"))) {
			return Operands.FF;
		}
		if ((leftType.equals("FLOAT")) 	&& (rightType.equals("INTEGER"))) {
			return Operands.FI;
		}
		if ((leftType.equals("FLOAT")) 	&& (rightType.equals("BOOLEAN"))) {
			return Operands.FB;
		}
		if ((leftType.equals("BOOLEAN")) && (rightType.equals("BOOLEAN"))) {
			return Operands.BB;
		}
		if ((leftType.equals("BOOLEAN")) && (rightType.equals("INTEGER"))) {
			return Operands.BI;
		}
		if ((leftType.equals("BOOLEAN")) && (rightType.equals("FLOAT"))) {
			return Operands.BF;
		}else {
			return null;
		}
	}

	private OperationCode createBinOpCode(Expression l, BinOpType op, Expression r){ 
		OperationCode result = null;
		Operands opType = operandsType(l,r);
		//String leftId = l.getId();
		//String rightId = r.getId();  
		switch (op){
			// Arithmetic
			case PLUS:
				switch (opType) {
					case II:
						result = OperationCode.ADDII;
						break;
					case IF:
						result = OperationCode.ADDIF;
						break;
					case FF:
						result = OperationCode.ADDFF;
						break;
					case FI:
						result = OperationCode.ADDFI;
						break;
				}
				break;
			case MINUS:
				switch (opType) {
					case II:
						result = OperationCode.SUBII;
						break;
					case IF:
						result = OperationCode.SUBIF;
						break;
					case FF:
						result = OperationCode.SUBFF;
						break;
					case FI:
						result = OperationCode.SUBFI;
						break;
				}
				break;
			case TIMES:
				switch (opType) {
					case II:
						result = OperationCode.MULII;
						break;
					case IF:
						result = OperationCode.MULIF;
						break;
					case FF:
						result = OperationCode.MULFF;
						break;
					case FI:
						result = OperationCode.MULFI;
						break;
				}
				break;
			case DIV:
				switch (opType) {
					case II:
						result = OperationCode.DIVII;
						break;
					case IF:
						result = OperationCode.DIVIF;
						break;
					case FF:
						result = OperationCode.DIVFF;
						break;
					case FI:
						result = OperationCode.DIVFI;
						break;
				}
				break;
			case MOD:
				switch (opType) {
					case II:
						result = OperationCode.MODII;
						break;
				}
				break;
			// Relational
			case SMALL: 
				switch (opType) {
					case II:
						result = OperationCode.SMALLII;
						break;
					case IF:
						result = OperationCode.SMALLIF;
						break;
					case FF:
						result = OperationCode.SMALLFF;
						break;
					case FI:
						result = OperationCode.SMALLFI;
						break;
				}
				break;
			case LTOE:
				switch (opType) {
					case II:
						result = OperationCode.LTOEII;
						break;
					case IF:
						result = OperationCode.LTOEIF;
						break;
					case FF:
						result = OperationCode.LTOEFF;
						break;
					case FI:
						result = OperationCode.LTOEFI;
						break;
				}
				break;
			case BIGGER:
				switch (opType) {
					case II:
						result = OperationCode.BIGGERII;
						break;
					case IF:
						result = OperationCode.BIGGERIF;
						break;
					case FF:
						result = OperationCode.BIGGERFF;
						break;
					case FI:
						result = OperationCode.BIGGERFI;
						break;
				}
				break;
			case GTOE:
				switch (opType) {
					case II:
						result = OperationCode.GTOEII;
						break;
					case IF:
						result = OperationCode.GTOEIF;
						break;
					case FF:
						result = OperationCode.GTOEFF;
						break;
					case FI:
						result = OperationCode.GTOEFI;
						break;
				}
				break;
			// Equal
			case DISTINCT: 
				switch (opType) {
					case II:
						return OperationCode.NEQII;
					case FF:
						return OperationCode.NEQFF;
					case BB:
						return OperationCode.NEQBB;
				}
				break;
			case EQUAL: 
				switch (opType) {
					case II:
						return OperationCode.EQII;
					case FF:
						return OperationCode.EQFF;
					case BB:
						return OperationCode.EQBB;
				}
				break;
			// Conditional
			case AND: 
				switch (opType) {
					case BB:
						result = OperationCode.ANDBB;
						break;
				}
				break;
			case OR:
				switch (opType) {
					case BB:
						result = OperationCode.ORBB;
						break;
				}
				break;
		}
		return result;
	}



}