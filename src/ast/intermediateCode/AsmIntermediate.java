	package ir.intermediateCode;

import ir.ASTVisitor;
import ir.ast.*;
import ir.semcheck.*;
import ir.error.Error;

import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

public class AsmIntermediate implements ASTVisitor {
//	private String programName;
	private List<StatementCode> code;
	private VarLocation 		temporal;
	private Integer 			tempNum;
	private Integer 			ifCounter, whileCounter, forCounter;
	private Stack<LoopLabel> 	beginLoop, endFor;
	private Stack<LoopLabel> 	endLoop, endWile;

	private Integer actualOffset;
	public AsmIntermediate(){
		this.code 			= new LinkedList<StatementCode>();
		this.ifCounter 		= 0;
		this.whileCounter 	= 0;
		this.forCounter 	= 0;
		this.tempNum 		= 0;
		this.beginLoop 		= new Stack<LoopLabel>();
		this.endLoop 		= new Stack<LoopLabel>();
	}

	@Override
	public List<Error> stackErrors() {
		return null;
	}

	@Override
	public List<Error> getErrors() {
		return this.errors;
	}

	private void initActualOffset(){
		this.actualOffset =1;
	}

	private void initActualOffset(Integer val){
		this.actualOffset = val;
	}

	private Integer getActualOffset(){
		return this.actualOffset;
	}

	private Integer incActualOffset(){
		Integer aux = this.actualOffset;
		this.actualOffset++;
		return aux;
	}

	private Integer incActualOffsetArray(Integer cant){
		this.actualOffset 	= this.actualOffset + cant;
		Integer aux 		= this.actualOffset--;
		return aux;
	}

	@Override
	public void visit(AST stmt){}

	private VarLocation createTemporalLit(Literal stmt){
		String tName 	= "t" + getTemporalNumber().toString();
		List lName 		= new LinkedList<String>();
		lName.add(tName);
		VarLocation ret = new VarLocation(lName,stmt.getLineNumber(),stmt.getColumnNumber());
		ret.setType(stmt.getType().toUpperCase());
		ret.setOff(incActualOffset());
		Integer res=0;
		if (stmt.getType().toUpperCase().equals("BOOLEAN")){
			BooleanLiteral booleanLit = (BooleanLiteral) stmt;
			if (booleanLit.getValue()){
				res = 0;
			}else{
				res =1;
			}
		}
		if (stmt.getType().toUpperCase().equals("INTEGER")){
			IntLiteral intLit = (IntLiteral) stmt;
			res = intLit.getValue();
		}
		if ((stmt.getType().toUpperCase().equals("BOOLEAN"))||(stmt.getType().toUpperCase().equals("INTEGER")))
			addStatement(new StatementCode(OperationCode.ASSIGNCONST,new Operand(ret)	,new Operand(String.valueOf(res)),null));
		//AssignStmt initTemporal = new AssignStmt(ret,AssignOpType.ASSIGN,stmt,stmt.getLineNumber(),stmt.getColumnNumber());
		//initTemporal.accept(this);
		return ret;
	}
	
	private VarLocation createTemporal(Expression stmt,String type){
		String tName 	= "t" + getTemporalNumber().toString();
		List lName 		= new LinkedList<String>();
		lName.add(tName);
		VarLocation ret = new VarLocation(lName,stmt.getLineNumber(),stmt.getColumnNumber());
		ret.setType(type);
		ret.setOff(incActualOffset());
		return ret;
	}

	/*private VarLocation createTemporalArrLoc(ArrayLocation stmt){

	}*/

	@Override
	public void visit(ArithmeticBinOp stmt){
		Expression exprL 	= stmt.getLeftOperand();
		Expression exprR 	= stmt.getRightOperand();
		BinOpType op 		= stmt.getOperator();
		OperationCode c 	= createBinOpCode(exprL, op, exprR );
		VarLocation auxL, auxR;
		switch (operandsType(exprL,exprR)) {
			case II:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"INTEGER");
				break;
			case IF:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"FLOAT");
				break;
			case IB:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"INTEGER"); 					//	ver
				break;
			case FF:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"FLOAT");
				break;
			case FI:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"FLOAT");
				break;
			case FB:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"FLOAT"); 					//	ver
				break;
			case BB:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"BOOLEAN");
				break;
			case BI:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"INTEGER");				//ver
				break;
			case BF:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				temporal = createTemporal(stmt,"FLOAT");				//ver
				break;
			default:
				throw new IllegalStateException("The operands type has a incompatibl type");
		}
		this.addStatement(new StatementCode(c,new Operand(auxL),new Operand(auxR),new Operand(temporal)));
	}
	
	@Override
	public void visit(ArithmeticUnaryOp stmt){
		stmt.getOperand().accept(this);
		VarLocation aux;
		switch (stmt.getOperand().getType().toUpperCase()) {
			case "INTEGER":
				aux 	 = temporal; 
				temporal = createTemporal(stmt,"INTEGER");
				this.addStatement(new StatementCode(OperationCode.SUBUI,new Operand(aux),null,new Operand(temporal)));
				break;
			case "FLOAT":
				aux 	 = temporal;
				temporal = createTemporal(stmt,"FLOAT");
				this.addStatement(new StatementCode(OperationCode.SUBUF,new Operand(aux),null,new Operand(temporal)));
				break;
			default:
				throw new IllegalStateException("Wrong arithmetic unary type");

		}
	}
	
	@Override
	public void visit(ArrayIdDecl stmt){
		if (stmt.isAttribute()){
			switch (stmt.getType().toUpperCase()) {
				case "INTEGER":
					this.addStatement(new StatementCode(OperationCode.ARRAYDECLI,new Operand(stmt),null,null));
					break;
				case "FLOAT":
					this.addStatement(new StatementCode(OperationCode.ARRAYDECLF,new Operand(stmt),null,null));
					break;
				case "BOOLEAN":
					this.addStatement(new StatementCode(OperationCode.ARRAYDECLB,new Operand(stmt),null,null));
					break;
				default:
					throw new IllegalStateException("Some error in array type");
			}
		}
	}
	
	@Override
	public void visit(ArrayLocation stmt){
		stmt.getExpression().accept(this);
		VarLocation steps = temporal;
		this.temporal = this.createTemporal(new VarLocation(new LinkedList<String>(),stmt.getLineNumber(),stmt.getColumnNumber()),stmt.getType());
		this.addStatement(new StatementCode(OperationCode.ARRAYLOCI,new Operand(stmt),new Operand(steps),new Operand(temporal)));
	}
	
	@Override
	public void visit(AssignStmt stmt){
		VarLocation aux;
		switch (stmt.getOperator()) {
			case INCREMENT:
				/*switch(stmt.getExpression().getType().toUpperCase()){
					case INTEGER:
						stmt.getExpression().accept(this);
						aux = (IntLiteral) temporal; 
						this.addStatement(new StatementCode(OperationCode.ASSINCI,new Operand(aux),null,new Operand(stmt.getLocation())));
						break;
					case FLOAT:
						stmt.getExpression().accept(this);
						aux = (FloatLiteral) temporal;
						this.addStatement(new StatementCode(OperationCode.ASSINCF,new Operand(aux),null,new Operand(stmt.getLocation())));
						break;
					default:
						throw new IllegalStateException("Wrong assignation type");
						break;
				}*/
				if (stmt.getLocation() instanceof VarLocation){
					if (stmt.getExpression().getType().toUpperCase().compareTo("INTEGER")==0) {
						System.out.println("EXPRESSION");
						stmt.getExpression().accept(this);
						aux = temporal; 
						this.addStatement(new StatementCode(OperationCode.ASSINCI,new Operand(aux),null,new Operand(stmt.getLocation())));
					} else {
						if (stmt.getExpression().getType().toUpperCase().compareTo("FLOAT")==0) {
							stmt.getExpression().accept(this);
							aux = temporal;
							this.addStatement(new StatementCode(OperationCode.ASSINCF,new Operand(aux),null,new Operand(stmt.getLocation())));
						}else{
							throw new IllegalStateException("Wrong assignation type");
						}
					}
				}else{
					if (stmt.getLocation() instanceof ArrayLocation){
						if (stmt.getExpression().getType().toUpperCase().compareTo("INTEGER")==0) {
							ArrayLocation arr = (ArrayLocation) stmt.getLocation();
							arr.getExpression().accept(this);
							VarLocation steps = temporal;
							stmt.getExpression().accept(this);
							this.addStatement(new StatementCode(OperationCode.ASSIGNARRAYINCI,new Operand(temporal),new Operand(steps),new Operand(arr)));
						} else {
							if (stmt.getExpression().getType().toUpperCase().compareTo("FLOAT")==0) {
								ArrayLocation arr = (ArrayLocation) stmt.getLocation();
								arr.getExpression().accept(this);
								VarLocation steps = temporal;
								stmt.getExpression().accept(this);
								this.addStatement(new StatementCode(OperationCode.ASSIGNARRAYINCF,new Operand(temporal),new Operand(steps),new Operand(arr)));
							}else{
								throw new IllegalStateException("Wrong assignation type");
							}
						}
					}else{
						if(stmt.getLocation() instanceof AttributeLocation){
							if (stmt.getExpression().getType().toUpperCase().compareTo("INTEGER")==0) {
								System.out.println("EXPRESSION");
								stmt.getExpression().accept(this);
								aux = temporal; 
								this.addStatement(new StatementCode(OperationCode.ASSATTINCI,new Operand(aux),null,new Operand(stmt.getLocation())));
							} else {
								if (stmt.getExpression().getType().toUpperCase().compareTo("FLOAT")==0) {
									stmt.getExpression().accept(this);
									aux = temporal;
									this.addStatement(new StatementCode(OperationCode.ASSATTINCF,new Operand(aux),null,new Operand(stmt.getLocation())));
								}else{
									throw new IllegalStateException("Wrong assignation type");
								}
							}
						}
					}
				}
				

				break;
			case DECREMENT:
				/*switch (stmt.getExpression().getType().toUpperCase()){
					case INTEGER:
						stmt.getExpression().accept(this);
						aux = (IntLiteral) temporal; 
						this.addStatement(new StatementCode(OperationCode.ASSDECI,new Operand(aux),null,new Operand(stmt.getLocation())));
						break;
					case FLOAT:
						stmt.getExpression().accept(this);
						aux = (FloatLiteral) temporal;
						this.addStatement(new StatementCode(OperationCode.ASSDECF,new Operand(aux),null,new Operand(stmt.getLocation())));
						break;
					default:
						throw new IllegalStateException("Wrong assignation type");
						break;
				}*/
				if (stmt.getLocation() instanceof VarLocation){

					if (stmt.getExpression().getType().toUpperCase().compareTo("INTEGER")==0) {
						stmt.getExpression().accept(this);
						aux = temporal; 
						this.addStatement(new StatementCode(OperationCode.ASSDECI,new Operand(aux),null,new Operand(stmt.getLocation())));
					} else {
						if (stmt.getExpression().getType().toUpperCase().compareTo("FLOAT")==0) {
							stmt.getExpression().accept(this);
							aux = temporal;
							this.addStatement(new StatementCode(OperationCode.ASSDECF,new Operand(aux),null,new Operand(stmt.getLocation())));
						}else{
							throw new IllegalStateException("Wrong assignation type");
						}
					}
				}else{
					if (stmt.getLocation() instanceof ArrayLocation){
						if (stmt.getExpression().getType().toUpperCase().compareTo("INTEGER")==0) {
								ArrayLocation arr = (ArrayLocation) stmt.getLocation();
								arr.getExpression().accept(this);
								VarLocation steps = temporal;
								stmt.getExpression().accept(this);
								this.addStatement(new StatementCode(OperationCode.ASSIGNARRAYDECI,new Operand(temporal),new Operand(steps),new Operand(arr)));
							} else {
								if (stmt.getExpression().getType().toUpperCase().compareTo("FLOAT")==0) {
									ArrayLocation arr = (ArrayLocation) stmt.getLocation();
									arr.getExpression().accept(this);
									VarLocation steps = temporal;
									stmt.getExpression().accept(this);
									this.addStatement(new StatementCode(OperationCode.ASSIGNARRAYDECF,new Operand(temporal),new Operand(steps),new Operand(arr)));
								}else{
									throw new IllegalStateException("Wrong assignation type");
								}
							}
					}else{
						if(stmt.getLocation() instanceof AttributeLocation){
							if (stmt.getExpression().getType().toUpperCase().compareTo("INTEGER")==0) {
								stmt.getExpression().accept(this);
								aux = temporal; 
								this.addStatement(new StatementCode(OperationCode.ASSATTDECI,new Operand(aux),null,new Operand(stmt.getLocation())));
							} else {
								if (stmt.getExpression().getType().toUpperCase().compareTo("FLOAT")==0) {
									stmt.getExpression().accept(this);
									aux = temporal;
									this.addStatement(new StatementCode(OperationCode.ASSATTDECF,new Operand(aux),null,new Operand(stmt.getLocation())));
								}else{
									throw new IllegalStateException("Wrong assignation type");
								}
							}
						}
					}
				}
			break;
			case ASSIGN:
				if (stmt.getLocation() instanceof VarLocation){
					stmt.getExpression().accept(this);
					this.addStatement(new StatementCode(OperationCode.ASSIGNATION,new Operand(temporal),null, new Operand(stmt.getLocation())));
				}else{
					if (stmt.getLocation() instanceof ArrayLocation){
						ArrayLocation arr = (ArrayLocation) stmt.getLocation();
						arr.getExpression().accept(this);
						VarLocation steps = temporal;
						stmt.getExpression().accept(this);
						this.addStatement(new StatementCode(OperationCode.ASSIGNARRAY,new Operand(temporal),new Operand(steps),new Operand(arr)));
					}else{
						if (stmt.getLocation() instanceof AttributeLocation){
							AttributeLocation attLoc = (AttributeLocation) stmt.getLocation();
							stmt.getExpression().accept(this);
							this.addStatement(new StatementCode(OperationCode.ASSIGNATTR,new Operand(temporal),null,new Operand(attLoc)));	
						}
					}
				}
				break;

		}
	}
	
	@Override
	public void visit(AttributeArrayLocation stmt){
		/*switch (stmt.getType().toUpperCase()) {
			case "INTEGER":
				this.addStatement(new StatementCode(OperationCode.ATTARRAYLOCI,new Operand(stmt),null,null));
				break;
			case "FLOAT":
				this.addStatement(new StatementCode(OperationCode.ATTARRAYLOCF,new Operand(stmt),null,null));
				break;
			case "BOOLEAN":
				this.addStatement(new StatementCode(OperationCode.ATTARRAYLOCB,new Operand(stmt),null,null));
				break;
			default:
				throw new IllegalStateException("Some error in array type");
		}*/
	}
	

	@Override
	public void visit(AttributeLocation stmt){
		VarLocation temp =null;
		switch (stmt.getType().toUpperCase()) {
			case "INTEGER":
				this.temporal = this.createTemporal(new VarLocation(stmt.getIdsName(),stmt.getLineNumber(),stmt.getColumnNumber()),stmt.getType());
				this.addStatement(new StatementCode(OperationCode.ATTLOCI,new Operand(stmt),null,new Operand(temporal)));
				//this.addStatement(new StatementCode(OperationCode.ATTLOCI,new Operand(stmt),null,null));
				break;
			case "FLOAT":
				this.temporal = this.createTemporal(new VarLocation(stmt.getIdsName(),stmt.getLineNumber(),stmt.getColumnNumber()),stmt.getType());
				this.addStatement(new StatementCode(OperationCode.ATTLOCF,new Operand(stmt),null,new Operand(temporal)));
				//this.addStatement(new StatementCode(OperationCode.ATTLOCF,new Operand(stmt),null,null));
				break;
			case "BOOLEAN":
				this.temporal = this.createTemporal(new VarLocation(stmt.getIdsName(),stmt.getLineNumber(),stmt.getColumnNumber()),stmt.getType());
				this.addStatement(new StatementCode(OperationCode.ATTLOCB,new Operand(stmt),null,new Operand(temporal)));
				//this.addStatement(new StatementCode(OperationCode.ATTLOCB,new Operand(stmt),null,null));
				break;
			default:
				throw new IllegalStateException("Some error in array type");
		}
	}
	
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
		this.temporal = createTemporalLit(lit);
	}
	
	@Override
	public void visit(BreakStmt stmt){
		LoopLabel aux = this.endLoop.peek();
		switch (aux.getType()){
			case FOR :
				this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.ENDFOR.toString()+aux.getLabelNumber().toString()),null,null));
				break;
			case WHILE :
				this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.ENDWHILE.toString()+aux.getLabelNumber().toString()),null,null));
				break;
			default:
				new IllegalStateException("Wrong type on Break Statement");
		}
	}
	
	@Override
	public void visit(ClassDecl classDecl){
		List<FieldDecl> fieldDeclList = classDecl.getAttributes();
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		this.addStatement(new StatementCode(OperationCode.BEGINCLASS,new Operand(classDecl),null,null));
		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}
		for (MethodDecl methodDecl : methodDeclList){
			if (!methodDecl.getBody().isExtern())
				methodDecl.accept(this);
		}
		this.addStatement(new StatementCode(OperationCode.ENDCLASS,new Operand(classDecl),null,null));
	}
	
	@Override
	public void visit(ContinueStmt stmt){
		LoopLabel aux = this.endLoop.peek();
		switch (aux.getType()){
			case FOR :
				this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.INCFOR.toString()+aux.getLabelNumber().toString()),null,null));
				break;
			case WHILE :
				this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.ENDWHILE.toString()+aux.getLabelNumber().toString()),null,null));
				break;
			default:
				new IllegalStateException("Wrong type on Break Statement");
		}
	}
	
	@Override
	public void visit(EqBinOp stmt){
		Expression exprL = stmt.getLeftOperand();
		Expression exprR = stmt.getRightOperand();
		BinOpType op = stmt.getOperator();
		OperationCode c = createBinOpCode(exprL, op, exprR );
		VarLocation auxL, auxR;
		exprL.accept(this);
		auxL = temporal;
		exprR.accept(this);
		auxR = temporal;
		temporal = createTemporal(stmt,"BOOLEAN");
		this.addStatement(new StatementCode(c,new Operand(auxL),new Operand(auxR),new Operand(temporal)));
	}
	
	@Override
	public void visit(Expression stmt){}
	
	@Override
	public void visit(FieldDecl fieldDecl){
		String type = fieldDecl.getType().toUpperCase();
		List<IdDecl> idDeclList = fieldDecl.getNames();
		for ( IdDecl idDecl : idDeclList) {
			idDecl.accept(this);
		}	
	}
	
	@Override
	public void visit(FloatLiteral lit){
		this.temporal = createTemporalLit(lit);
	}
	
	@Override
	public void visit(ForStmt stmt){
		Integer forNum = this.getForCounter();
		this.beginLoop.push(new LoopLabel(LabelType.FOR,forNum));
		this.endLoop.push(new LoopLabel(LabelType.FOR, forNum));
		IdDecl counterName = stmt.getCounterName();
		counterName.accept(this);
		//this.addStatement(new StatementCode(OperationCode.IDDECL,new Operand(counterName),null,null));
		stmt.getInit().accept(this);
		VarLocation contInit = temporal; 
		List<String> nameLoc = new LinkedList<String>();
		nameLoc.add(counterName.getName());
		VarLocation counter = new VarLocation(nameLoc,counterName.getLineNumber(),counterName.getColumnNumber());
		counter.setType(contInit.getType());
		counter.setDeclaration(counterName);
		this.addStatement(new StatementCode(OperationCode.ASSIGNATION,new Operand(contInit),null,new Operand(counter)));
		stmt.getEnd().accept(this);
		VarLocation contEnd = temporal;
		this.addStatement(new StatementCode(OperationCode.BEGINFOR,new Operand(stmt),new Operand(forNum),null));
		RelationalBinOp forCond = new RelationalBinOp(counter,BinOpType.SMALL,contEnd,stmt.getLineNumber(),stmt.getColumnNumber());
		forCond.accept(this);
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,new Operand(temporal),new Operand(OperationCode.ENDFOR.toString()+forNum.toString()),null));
		stmt.getBody().accept(this);				
		this.addStatement(new StatementCode(OperationCode.INCFOR,new Operand(stmt),new Operand(forNum),null));
		this.addStatement(new StatementCode(OperationCode.INC,null,null,new Operand(counter)));
		this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.BEGINFOR.toString()+forNum.toString()),null,null));
		this.addStatement(new StatementCode(OperationCode.ENDFOR,new Operand(stmt),new Operand(forNum),null));
		this.beginLoop.pop();
		this.endLoop.pop();
	}
	
	@Override
	public void visit(IdDecl loc){
		if (!loc.isAttribute()){
			switch (loc.getType().toUpperCase().toUpperCase()) {
				case "INTEGER":
					this.addStatement(new StatementCode(OperationCode.INTDECL,new Operand(loc),null,null));
					break;
				case "FLOAT":
					this.addStatement(new StatementCode(OperationCode.FLOATDECL,new Operand(loc),null,null));
					break;
				case "BOOLEAN":
					this.addStatement(new StatementCode(OperationCode.BOOLDECL,new Operand(loc),null,null));
					break;

				default:
					this.addStatement(new StatementCode(OperationCode.OBJDECL,new Operand(loc),null,null));
					System.out.println("There is an object!!");
					//System.out.println("Some error in idDecl type");
			}
		}	
	}
	
	@Override
	public void visit(IfThenElseStmt stmt){
		Integer ifNum = this.getIfCounter();
		IntLiteral intLit = new IntLiteral(ifNum,stmt.getLineNumber(),stmt.getColumnNumber());
		this.addStatement(new StatementCode(OperationCode.BEGINIF,new Operand(stmt),new Operand(intLit), null));
		stmt.getCondition().accept(this); // this method set temporal variable with a condition value		
		VarLocation cond =  temporal;
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,new Operand(cond),new Operand(OperationCode.ELSEIF.toString()+intLit.toString()),null));
		stmt.getIfBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.ENDIF.toString()+intLit.toString()),null,null));
		this.addStatement(new StatementCode(OperationCode.ELSEIF,new Operand(stmt),new Operand(ifNum),null));
		stmt.getElseBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.ENDIF,new Operand(stmt),new Operand(ifNum),null));
	}
	
	@Override
	public void visit(IfThenStmt stmt){
		Integer ifNum = this.getIfCounter();
		IntLiteral intLit = new IntLiteral(ifNum,stmt.getLineNumber(),stmt.getColumnNumber());
		this.addStatement(new StatementCode(OperationCode.BEGINIF,new Operand(stmt),new Operand(intLit), null));
		stmt.getCondition().accept(this);
		VarLocation cond = temporal;
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,new Operand(cond),new Operand(OperationCode.ENDIF.toString()+intLit.toString()),null)); //saltar al fin de if
		stmt.getIfBlock().accept(this);
		this.addStatement(new StatementCode(OperationCode.ENDIF,new Operand(stmt),new Operand(ifNum),null));
	}
	
	@Override
	public void visit(IntLiteral lit){
		this.temporal = createTemporalLit(lit);
	}
	
	@Override
	public void visit(LogicalBinOp stmt){
		Expression exprL = stmt.getLeftOperand();
		Expression exprR = stmt.getRightOperand();
		BinOpType op = stmt.getOperator();
		OperationCode c = createBinOpCode(exprL, op, exprR );
		VarLocation auxL, auxR;
		exprL.accept(this);
		auxL = temporal;
		exprR.accept(this);
		auxR = temporal;
		temporal = createTemporal(stmt,"BOOLEAN");
		this.addStatement(new StatementCode(c,new Operand(auxL),new Operand(auxR),new Operand(temporal)));
	}
	
	@Override
	public void visit(LogicalUnaryOp stmt){
		stmt.getOperand().accept(this);
		VarLocation aux;
		if (stmt.getOperand().getType().toUpperCase().compareTo("BOOLEAN")==0) {
			aux = temporal; 
			temporal = createTemporal(stmt,"BOOLEAN");
			this.addStatement(new StatementCode(OperationCode.NOT,new Operand(aux),new Operand(temporal),null));
		}else{
			throw new IllegalStateException("Wrong boolean unary type");
		}
	}
	
	@Override
	public void visit(MethodCall stmt){
		String[] registers = {"rdi","rsi","rdx","rcx","r8","r9"};
		List<Expression> param = stmt.getParams();
		List<VarLocation> tempList = new LinkedList<VarLocation>();
		for (int cont = 0;cont < 6 && cont< param.size() ;cont++ ) {
			
			param.get(cont).accept(this);
			tempList.add(temporal);
		}
		for (int cont = 0;cont < 6 && cont< param.size() ;cont++ ) {
			this.addStatement(new StatementCode(OperationCode.PUSHPARAMS,new Operand(registers[cont]),new Operand(tempList.get(cont)), null));
		}
		if (stmt.getIds().size()>1){
			this.temporal = this.createTemporal(new VarLocation(new LinkedList<String>(),stmt.getLineNumber(),stmt.getColumnNumber()),stmt.getType());
			this.addStatement(new StatementCode(OperationCode.CALLOBJ,new Operand(stmt),new Operand(temporal),null));
		}else{
			this.temporal = this.createTemporal(new VarLocation(new LinkedList<String>(),stmt.getLineNumber(),stmt.getColumnNumber()),stmt.getType());
			this.addStatement(new StatementCode(OperationCode.CALL,new Operand(stmt),new Operand(temporal),null));		
		}
	} 
	
	@Override
	public void visit(MethodCallStmt stmt){
		stmt.getMethodCall().accept(this);
		/*
		String[] registers = {"rdi","rsi","rdx","rcx","r8","r9"};
		List<Expression> param = stmt.getMethodCall().getParams();
		for (int cont = 0;cont < 6 && cont< param.size() ;cont++ ) {
			
			param.get(cont).accept(this);
			this.addStatement(new StatementCode(OperationCode.PUSHPARAMS,new Operand(registers[cont]),new Operand(temporal), null));
			
		}
		this.temporal = this.createTemporal(new VarLocation(new LinkedList<String>(),stmt.getLineNumber(),stmt.getColumnNumber()),stmt.getMethodDecl().getType());
		this.addStatement(new StatementCode(OperationCode.CALL,new Operand(stmt),new Operand(temporal),null));
		*/
	}
	
	@Override
	public void visit(MethodDecl methodDecl){
		initActualOffset(methodDecl.getOff());
		String type = methodDecl.getType().toUpperCase();
		String name = methodDecl.getName();
		this.addStatement(new StatementCode(OperationCode.BEGINMETHOD,new Operand( methodDecl), null, null));
		List<ParamDecl> paramDeclList = methodDecl.getParams();
		String[] registers = {"rdi","rsi","rdx","rcx","r8","r9"};
		List<ParamDecl> param = methodDecl.getParams();
		for (int cont = 0;cont < 6 && cont< param.size() ;cont++ ) {
				this.temporal = this.createTemporal(new VarLocation(new LinkedList<String>(),param.get(cont).getLineNumber(),param.get(cont).getColumnNumber()),param.get(cont).getType());
				this.temporal.setOff(param.get(cont).getOff());
				this.addStatement(new StatementCode(OperationCode.PULLPARAMS,new Operand(registers[cont]),new Operand(temporal), null));
		}
		
		BodyDecl body = methodDecl.getBody();
		body.accept(this);
		methodDecl.setOff(getActualOffset());
		this.addStatement(new StatementCode(OperationCode.ENDMETHOD,new Operand( methodDecl), null, null));
	}
	
	@Override
	public void visit(ParamDecl paramDecl){
		String name = paramDecl.getName();
		String type = paramDecl.getType().toUpperCase();
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
		VarLocation auxL, auxR;
		switch (operandsType(exprL,exprR)) {
			case II:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			case IF:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			case IB:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			case FF:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			case FI:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			case FB:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			case BB:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			case BI:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			case BF:
				exprL.accept(this);
				auxL = temporal;
				exprR.accept(this);
				auxR = temporal;
				break;
			default:
				throw new IllegalStateException("The operands type has a incompatibl type");
		}
		temporal = createTemporal(stmt,"BOOLEAN");
		this.addStatement(new StatementCode(c,new Operand(auxL),new Operand(auxR),new Operand(temporal)));
	}
	
	@Override
	public void visit(ReturnStmt stmt){
		stmt.getExpression().accept(this);
		this.addStatement(new StatementCode(OperationCode.RET,new Operand(temporal),null,null));
	}
	
	@Override
	public void visit(ReturnVoidStmt stmt){
		this.addStatement(new StatementCode(OperationCode.RETVOID,null,null,null));
	}
	
	@Override
	public void visit(Skip stmt){
		stmt.accept(this);
	}
	
	@Override
	public void visit(Statement stmt){}
	
	@Override
	public void visit(VarLocation loc){
		temporal = loc;
	}
	
	@Override
	public void visit(WhileStmt stmt){
		Integer whileNum = this.getWhileCounter();
		this.beginLoop.push(new LoopLabel(LabelType.WHILE,whileNum));
		this.endLoop.push(new LoopLabel(LabelType.WHILE, whileNum));
		this.addStatement(new StatementCode(OperationCode.BEGINWHILE,new Operand(stmt),new Operand(whileNum),null));
		stmt.getCondition().accept(this);
		this.addStatement(new StatementCode(OperationCode.JMPFALSE,new Operand(temporal),new Operand(OperationCode.ENDWHILE.toString()+whileNum.toString()),null));
		stmt.getBody().accept(this);
		this.addStatement(new StatementCode(OperationCode.JMP,new Operand(OperationCode.BEGINWHILE.toString()+whileNum.toString()),null,null));
		this.addStatement(new StatementCode(OperationCode.ENDWHILE,new Operand(stmt),new Operand(whileNum),null));
		this.beginLoop.pop();
		this.endLoop.pop();
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

	private Integer getTemporalNumber(){
		Integer ret = this.tempNum;
		this.tempNum ++;
		return ret;
	}

	private OperandsType operandsType(Expression l, Expression r){
		String leftType = l.getType().toUpperCase();
		String rightType = r.getType().toUpperCase();
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

	public List<StatementCode> getPseudo(){
		return this.code;
	}

}