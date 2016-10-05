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
	private AST temporal; // sould be always a  literal.......???????????
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
		Literal auxL, auxR;
		switch (operandsType(exprL,exprR)) {
			case II:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case IF:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			case IB:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case FF:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			case FI:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case FB:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case BB:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case BI:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case BF:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			default:
				throw new IllegalStateException("The operands type has a incompatibl type");
		}
		this.addStatement(new StatementCode(c,new Operand(auxL),new Operand(auxR),new Operand(temporal)));
	}
	
	@Override
	public void visit(ArithmeticUnaryOp stmt){
		stmt.getOperand().accept(this);
		Literal aux;
		if (stmt.getOperand().getType().compareTo("INTEGER")==0) {
			aux = (IntLiteral) temporal; 
			this.addStatement(new StatementCode(OperationCode.SUBUI,new Operand(aux),null,new Operand(temporal)));
		} else {
			if (stmt.getOperand().getType().compareTo("FLOAT")==0) {
				aux = (FloatLiteral) temporal;
				this.addStatement(new StatementCode(OperationCode.SUBUF,new Operand(aux),null,new Operand(temporal)));
			}else{
				throw new IllegalStateException("Wrong arithmetic unary type");
			}
		}
	}
	
	@Override
	public void visit(ArrayIdDecl stmt){}
	
	@Override
	public void visit(ArrayLocation stmt){}
	
	@Override
	public void visit(AssignStmt stmt){
		Literal aux;
		switch (stmt.getOperator()) {
			case INCREMENT:
				if (stmt.getExpression().getType().compareTo("INTEGER")==0) {
						stmt.getExpression().accept(this);
						aux = (IntLiteral) temporal; 
						this.addStatement(new StatementCode(OperationCode.ASSINCI,new Operand(aux),null,new Operand(stmt.getLocation())));
					} else {
						if (stmt.getExpression().getType().compareTo("FLOAT")==0) {
							stmt.getExpression().accept(this);
							aux = (FloatLiteral) temporal;
							this.addStatement(new StatementCode(OperationCode.ASSINCF,new Operand(aux),null,new Operand(stmt.getLocation())));
						}else{
							throw new IllegalStateException("Wrong assignation type");
						}
					}
				break;
			case DECREMENT:
					if (stmt.getExpression().getType().compareTo("INTEGER")==0) {
						stmt.getExpression().accept(this);
						aux = (IntLiteral) temporal; 
						this.addStatement(new StatementCode(OperationCode.ASSDECI,new Operand(aux),null,new Operand(stmt.getLocation())));
					} else {
						if (stmt.getExpression().getType().compareTo("FLOAT")==0) {
							stmt.getExpression().accept(this);
							aux = (FloatLiteral) temporal;
							this.addStatement(new StatementCode(OperationCode.ASSDECF,new Operand(aux),null,new Operand(stmt.getLocation())));
						}else{
							throw new IllegalStateException("Wrong assignation type");
						}
					}
				break;
			case ASSIGN:
				this.addStatement(new StatementCode(OperationCode.ASSIGNATION,new Operand(stmt.getExpression()),null, new Operand(stmt.getLocation())));
				break;
		}
	}
	
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
		this.addStatement(new StatementCode(OperationCode.BEGINCLASS,new Operand(classDecl),null,null));
		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}
		for (MethodDecl methodDecl : methodDeclList){
			methodDecl.accept(this);
		}
		this.addStatement(new StatementCode(OperationCode.ENDCLASS,new Operand(classDecl),null,null));
	}
	
	@Override
	public void visit(ContinueStmt stmt){}
	
	@Override
	public void visit(EqBinOp stmt){
		Expression exprL = stmt.getLeftOperand();
		Expression exprR = stmt.getRightOperand();
		BinOpType op = stmt.getOperator();
		OperationCode c = createBinOpCode(exprL, op, exprR );
		Literal auxL, auxR;
		switch (operandsType(exprL,exprR)) {
			case II:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case IF:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			case IB:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case FF:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			case FI:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case FB:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case BB:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case BI:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case BF:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			default:
				throw new IllegalStateException("The operands type has a incompatibl type");
		}
		this.addStatement(new StatementCode(c,new Operand(auxL),new Operand(auxR),new Operand(temporal)));
	}
	
	@Override
	public void visit(Expression stmt){}
	
	@Override
	public void visit(FieldDecl fieldDecl){
		String type = fieldDecl.getType();
		List<IdDecl> idDeclList = fieldDecl.getNames();
		for ( IdDecl idDecl : idDeclList) {
			String name = idDecl.getName();
			this.addStatement(new StatementCode(OperationCode.FIELD,new Operand(idDecl),new Operand(fieldDecl),null));
		}	
	}
	
	@Override
	public void visit(FloatLiteral lit){
		this.temporal = lit;
	}
	
	@Override
	public void visit(ForStmt stmt){
		Integer forNum = this.getForCounter();
		this.addStatement(new StatementCode(OperationCode.IDDECL,new Operand(stmt.getCounterName()),null,null));
		stmt.getInit().accept(this);
		IntLiteral contInit = (IntLiteral) temporal; 
		this.addStatement(new StatementCode(OperationCode.ASSIGNATION,new Operand(contInit),null,new Operand(stmt.getCounterName())));
		this.addStatement(new StatementCode(OperationCode.BEGINFOR,new Operand(stmt),new Operand(forNum),null));
		RelationalBinOp forCond = new RelationalBinOp(contInit,BinOpType.SMALL,stmt.getEnd(),stmt.getLineNumber(),stmt.getColumnNumber());
		forCond.accept(this);
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,new Operand(temporal),new Operand(OperationCode.ENDFOR.toString()+forNum.toString()),null));
		stmt.getBody().accept(this);
		this.addStatement(new StatementCode(OperationCode.INC,null,null,new Operand(contInit)));
		this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.BEGINFOR.toString()+forNum.toString()),null,null));
		this.addStatement(new StatementCode(OperationCode.ENDFOR,new Operand(stmt),new Operand(forNum),null));
	}
	
	@Override
	public void visit(IdDecl loc){}
	
	@Override
	public void visit(IfThenElseStmt stmt){
		Integer ifNum = this.getIfCounter();
		IntLiteral intLit = new IntLiteral(ifNum,stmt.getLineNumber(),stmt.getColumnNumber());
		this.addStatement(new StatementCode(OperationCode.BEGINIF,new Operand(stmt),new Operand(intLit), null));
		stmt.getCondition().accept(this); // this method set temporal variable with a condition value		
		BooleanLiteral cond = (BooleanLiteral) temporal;
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,new Operand(cond),new Operand(OperationCode.ELSEIF.toString()+intLit.toString()),null));
		stmt.getIfBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.ELSEIF,new Operand(stmt),new Operand(intLit),null));
		stmt.getElseBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.ENDIF,new Operand(stmt),new Operand(intLit),null));
	}
	
	@Override
	public void visit(IfThenStmt stmt){
		Integer ifNum = this.getIfCounter();
		IntLiteral intLit = new IntLiteral(ifNum,stmt.getLineNumber(),stmt.getColumnNumber());
		this.addStatement(new StatementCode(OperationCode.BEGINIF,new Operand(stmt),new Operand(intLit), null));
		stmt.getCondition().accept(this);
		BooleanLiteral cond = (BooleanLiteral) temporal;
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,new Operand(cond),new Operand(OperationCode.ENDIF.toString()+intLit.toString()),null)); //saltar al fin de if
		stmt.getIfBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.ENDIF,new Operand(stmt),new Operand(intLit),null));
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
		Literal auxL, auxR;
		switch (operandsType(exprL,exprR)) {
			case II:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case IF:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			case IB:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case FF:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			case FI:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case FB:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case BB:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case BI:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case BF:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			default:
				throw new IllegalStateException("The operands type has a incompatibl type");
		}
		this.addStatement(new StatementCode(c,new Operand(auxL),new Operand(auxR),new Operand(temporal)));
	}
	
	@Override
	public void visit(LogicalUnaryOp stmt){
		stmt.getOperand().accept(this);
		Literal aux;
		if (stmt.getOperand().getType().compareTo("BOOLEAN")==0) {
			aux = (BooleanLiteral) temporal; 
			this.addStatement(new StatementCode(OperationCode.NOT,new Operand(aux),null,null));
		}else{
			throw new IllegalStateException("Wrong boolean unary type");
		}
	}
	
	@Override
	public void visit(MethodCall stmt){} // math(p1, p2,p3)
	
	@Override
	public void visit(MethodCallStmt stmt){}
	
	@Override
	public void visit(MethodDecl methodDecl){
		String type = methodDecl.getType();
		String name = methodDecl.getName();
		this.addStatement(new StatementCode(OperationCode.BEGINMETHOD,new Operand( methodDecl), null, null));
		List<ParamDecl> paramDeclList = methodDecl.getParams();
		for (ParamDecl paramDecl : paramDeclList) {
			paramDecl.accept(this);
		}
		BodyDecl body = methodDecl.getBody();
		body.accept(this);
		this.addStatement(new StatementCode(OperationCode.ENDMETHOD,new Operand( methodDecl), null, null));
	}
	
	@Override
	public void visit(ParamDecl paramDecl){
		String name = paramDecl.getName();
		String type = paramDecl.getType();
		this.addStatement(new StatementCode(OperationCode.PARAMDECL,new Operand(paramDecl), null, null));
	}
	
	@Override
	public void visit(Program prog){
		List<ClassDecl> classDeclList = prog.getClassDeclare();
		this.addStatement(new StatementCode(OperationCode.BEGINPROGRAM,new Operand(prog), null, null));
		for (ClassDecl classDecl : classDeclList ) {
			classDecl.accept(this);
		}
		this.addStatement(new StatementCode(OperationCode.ENDPROGRAM, new Operand(prog), null, null));
	}
	
	@Override
	public void visit(RelationalBinOp stmt){
		Expression exprL = stmt.getLeftOperand();
		Expression exprR = stmt.getRightOperand();
		BinOpType op = stmt.getOperator();
		OperationCode c = createBinOpCode(exprL, op, exprR );
		Literal auxL, auxR;
		switch (operandsType(exprL,exprR)) {
			case II:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case IF:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			case IB:
				exprL.accept(this);
				auxL = (IntLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case FF:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			case FI:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case FB:
				exprL.accept(this);
				auxL = (FloatLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case BB:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (BooleanLiteral) temporal;
				break;
			case BI:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (IntLiteral) temporal;
				break;
			case BF:
				exprL.accept(this);
				auxL = (BooleanLiteral) temporal;
				exprR.accept(this);
				auxR = (FloatLiteral) temporal;
				break;
			default:
				throw new IllegalStateException("The operands type has a incompatibl type");
		}
		this.addStatement(new StatementCode(c,new Operand(auxL),new Operand(auxR),new Operand(temporal)));
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
	public void visit(WhileStmt stmt){
		Integer whileNum = this.getWhileCounter();
		this.addStatement(new StatementCode(OperationCode.BEGINWHILE,new Operand(stmt),new Operand(whileNum),null));
		stmt.getCondition().accept(this);
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,new Operand(temporal),new Operand(OperationCode.ENDWHILE.toString()+whileNum.toString()),null));
		stmt.getBody().accept(this);
		this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.BEGINWHILE.toString()+whileNum.toString()),null,null));
		this.addStatement(new StatementCode(OperationCode.ENDWHILE,new Operand(stmt),new Operand(whileNum),null));
	}

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

	private OperandsType operandsType(Expression l, Expression r){
		String leftType = l.getType();
		String rightType = r.getType();
		if ((leftType.equals("INTEGER")) && (rightType.equals("INTEGER"))) {
			return OperandsType.II;
		}
		if ((leftType.equals("INTEGER")) && (rightType.equals("FLOAT"))) {
			return OperandsType.IF;
		}
		if ((leftType.equals("INTEGER")) && (rightType.equals("BOOLEAN"))) {
			return OperandsType.IB;
		}
		if ((leftType.equals("FLOAT")) 	&& (rightType.equals("FLOAT"))) {
			return OperandsType.FF;
		}
		if ((leftType.equals("FLOAT")) 	&& (rightType.equals("INTEGER"))) {
			return OperandsType.FI;
		}
		if ((leftType.equals("FLOAT")) 	&& (rightType.equals("BOOLEAN"))) {
			return OperandsType.FB;
		}
		if ((leftType.equals("BOOLEAN")) && (rightType.equals("BOOLEAN"))) {
			return OperandsType.BB;
		}
		if ((leftType.equals("BOOLEAN")) && (rightType.equals("INTEGER"))) {
			return OperandsType.BI;
		}
		if ((leftType.equals("BOOLEAN")) && (rightType.equals("FLOAT"))) {
			return OperandsType.BF;
		}else {
			throw new IllegalStateException("Illegal operand type");
		}
	}

	private OperationCode createBinOpCode(Expression l, BinOpType op, Expression r){ 
		OperationCode result = null;
		OperandsType opType = operandsType(l,r); 
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
					default:
						new IllegalStateException("Operands has an incorrect type");
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
					default:
						new IllegalStateException("Operands has an incorrect type");
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
					default:
						new IllegalStateException("Operands has an incorrect type");
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
					default:
						new IllegalStateException("Operands has an incorrect type");
				}
				break;
			case MOD:
				switch (opType) {
					case II:
						result = OperationCode.MODII;
						break;
					default:
						new IllegalStateException("Operands has an incorrect type");
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
					default:
						new IllegalStateException("Operands has an incorrect type");
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
					default:
						new IllegalStateException("Operans has an incorrect type");
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
					default:
						new IllegalStateException("Operands has an incorrect type");
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
					default:
						new IllegalStateException("Operands has an incorrect type");
				}
				break;
			// Equal
			case DISTINCT: 
				switch (opType) {
					case II:
						result = OperationCode.NEQII;
						break;
					case FF:
						result = OperationCode.NEQFF;
						break;
					case BB:
						result = OperationCode.NEQBB;
						break;
					default:
						new IllegalStateException("Operands has an incorrect type");
				}
				break;
			case EQUAL: 
				switch (opType) {
					case II:
						result = OperationCode.EQII;
						break;
					case FF:
						result = OperationCode.EQFF;
						break;
					case BB:
						result = OperationCode.EQBB;
						break;
					default:
						new IllegalStateException("Operands has an incorrect type");
				}
				break;
			// Conditional
			case AND: 
				switch (opType) {
					case BB:
						result = OperationCode.ANDBB;
						break;
					default:
						new IllegalStateException("Operands has an incorrect type");
				}
				break;
			case OR:
				switch (opType) {
					case BB:
						result = OperationCode.ORBB;
						break;
					default:
						new IllegalStateException("Operands has an incorrect type");
				}
				break;
			default:
				new IllegalStateException("Operation no declarated");
		}
		return result;
	}



}