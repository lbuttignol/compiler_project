package ir.AsmGenerator;

import ir.ast.*;
import ir.intermediateCode.*;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class AsmGenerator {
	private List<StatementCode> intermediateCode;
	
	private File assemblyCode;
	private String path;
	private String name;
	private String originalFileName;
	private FileWriter fw;
	private BufferedWriter bw;
	private VarLocation operand1;
	private VarLocation operand2;
	private VarLocation operand3;
	private Integer offSet;
	private IdDecl idDecl;
	private List<ParamDecl> paramsDecl;
	private ParamDecl paramDecl;
	private String label;
	private Integer numberLbl;

	private final static Integer VARSIZE = 8;

	public AsmGenerator(List<StatementCode> program,String path,String name){
		this.intermediateCode = program;
		this.path = path;
		this.originalFileName = name;
		this.name = name.split("/")[name.split("/").length-1];
		this.name = name.split("\\.")[0]+".s";
		try{
			File file = new File(this.path+this.name);
			if (!file.exists()){
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void translate(StatementCode stmt){		
		try{
			switch (stmt.getOperationCode()) {
			// Declarations
				case BEGINPROGRAM:
					System.out.print("BEGINPROGRAM");
					executeBeginProgram(stmt);
					break;
				case ENDPROGRAM:
					System.out.print("ENDPROGRAM");
					executeEndProgram(stmt);
					break;
				case BEGINCLASS:
					System.out.print("BEGINCLASS");
					executeBeginClass(stmt);
					break;
				case ENDCLASS:
					System.out.print("ENDCLASS");
					executeEndClass(stmt);
					break;

				case FIELD:
					System.out.print("FIELD");
					executeField(stmt);
					break;
				case BEGINMETHOD:
					System.out.print("BEGINMETHOD");
					executeBeginMethod(stmt);
					break;
				case ENDMETHOD:
					System.out.print("ENDMETHOD");
					executeEndMethod(stmt);
					break;
				case PARAMDECL:
					System.out.print("PARAMDECL");
					executeParamDecl(stmt);
					break;
				case LOADPARAM:
					System.out.print("LOADPARAM");
					break;
				case IDDECL:
					System.out.print("IDDECL");
					break;
				case ARRAYDECLI:
					System.out.print("ARRAYDECLI");
					executeArrDeclInt(stmt);
					break;
				case ARRAYDECLF:
					System.out.println( "ARRAYDECLF");
					break;
				case ARRAYDECLB:
					System.out.println( "ARRAYDECLB");
					break;
				case INTDECL:
					System.out.print("INTDECL");
					executeIntDecl(stmt);
					break;
				case FLOATDECL:
					System.out.println( "FLOATDECL");
					break;
				case BOOLDECL:
					System.out.print("BOOLDECL");
					executeBoolDecl(stmt);
					break;
				case OBJDECL:
					executeObjDecl(stmt);

			//Locations
				case ARRAYLOCI:
					System.out.println( "ARRAYLOCI");
					executeArrayLocI(stmt);
					break;
				case ARRAYLOCF:
					System.out.println( "ARRAYLOCF");
					break;
				case ARRAYLOCB:
					System.out.println( "ARRAYLOCB");
					break;
				case ATTARRAYLOCI:
					System.out.println( "ATTARRAYLOCI");
					break;
				case ATTARRAYLOCF:
					System.out.println( "ATTARRAYLOCF");
					break;
				case ATTARRAYLOCB:
					System.out.println( "ATTARRAYLOCB");
					break;
				case ATTLOCI:
					executeAttLocI(stmt);
					System.out.println( "ATTLOCI");
					break;
				case ATTLOCF:
					System.out.println( "ATTLOCF");
					break;
				case ATTLOCB:
					executeAttLocB(stmt);
					System.out.println( "ATTLOCB");
					break;
				case VARLOCI:
					System.out.println( "VARLOCI");
					break;
				case VARLOCF:
					System.out.println( "VARLOCF");
					break;
				case VARLOCB:
					System.out.println( "VARLOCB");
					break;

			// Statements
				case BEGINIF:
					System.out.print("BEGINIF");
					executeBeginIf(stmt);
					break;
				case ENDIF:
					System.out.println( "ENDIF");
					executeEndIf(stmt);
					break;
				case ELSEIF:
					System.out.println( "ELSEIF");
					executeElseIf(stmt);
					break;
				case BEGINFOR:
					System.out.println( "BEGINFOR");
					executeBeginFor(stmt);
					break;
				case INCFOR:
					System.out.println( "INCFOR");
					executeIncFor(stmt);
					break;
				case ENDFOR:
					System.out.println( "ENDFOR");
					executeEndFor(stmt);
					break;
				case BEGINWHILE:
					System.out.println( "BEGINWHILE");
					executeBeginWhile(stmt);
					break;
				case ENDWHILE:
					System.out.println( "ENDWHILE");
					executeEndWhile(stmt);
					break;
				
			// Unary Aruthmetic
				case SUBUI:
					System.out.println( "SUBUI");
					executeSubUI(stmt);
					break;
				case NOT:
					System.out.println( "NOT"); 	
					executeNot(stmt);
					break;
				case ADDII:
					System.out.println( "ADDII");
					executeAddII(stmt);
					break;
				case ADDFF:
					System.out.println( "ADDFF");
					executeAddFF(stmt);
					break;
				case SUBII:
					System.out.println( "SUBII");
					executeSubII(stmt);
					break;
				case SUBFF:
					System.out.println( "SUBFF");
					executeSubFF(stmt);
					break;
				case MULII:
					System.out.println( "MULII");
					executeMulII(stmt);
					break;
				case MULFF:
					System.out.println( "MULFF");
					executeMulFF(stmt);
					break;
				case DIVII:
					System.out.println( "DIVII");
					executeDivII(stmt);
					break;	
				case DIVFF:
					System.out.println( "DIVFF");
					executeDivFF(stmt);
					break;
				case MODII:
					System.out.println( "MODII");
					executeModII(stmt);
					break;
			
			// Eq operations													
				case EQII:
					System.out.println( "EQII");
					executeEqII(stmt);
					break;
				case EQFF:
					System.out.println( "EQFF");
					executeEqFF(stmt);
					break;
				case EQBB:
					System.out.println( "EQBB");
					executeEqBB(stmt);
					break;
				case NEQII:
					System.out.println( "NEQII");
					executeNeqII(stmt);
					break;
				case NEQFF:
					System.out.println( "NEQFF");
					executeNeqFF(stmt);
					break;
				case NEQBB:
					System.out.println( "NEQBB");
					executeNeqBB(stmt);
					break;

			// Relational operations
				case SMALLII:
					System.out.println( "SMALLII");
					executeSmallII(stmt);
					break;
				case SMALLFF:
					System.out.println( "SMALLFF");
					executeSmallFF(stmt);
					break;
				case LTOEII:
					System.out.println( "LTOEII");
					executeLtoeII(stmt);
					break;
				case LTOEFF:
					System.out.println( "LTOEFF");
					executeLtoeFF(stmt);
					break;
				case BIGGERII:
					System.out.println( "BIGGERII");
					executeBiggerII(stmt);
					break;
				case BIGGERFF:
					System.out.println( "BIGGERFF");
					executeBiggerFF(stmt);
					break;
				case GTOEII:
					System.out.println( "GTOEII");
					executeGtoeII(stmt);
					break;
				case GTOEFF:
					System.out.println( "GTOEFF");
					executeGtoeFF(stmt);
					break;

			// Logical operations			
				case ANDBB:
					System.out.println( "ANDBB");
					executeAndBB(stmt);
					break;
				case ORBB:
					System.out.println( "ORBB");
					executeOrBB(stmt);
					break;

			// Jump
				case JMPFALSE:
					System.out.print("JMPFALSE");
					executeJmpFalse(stmt);	
					break;
				case JMPTRUE:
					System.out.println( "JMPTRUE");
					break;
				case JMP:
					System.out.print("JMP");
					executeJmp(stmt);
					break;

			// Assign
				case ASSIGNATION:
					System.out.println( "ASSIGNATION");
					executeAssignation(stmt);
					break;
				case ASSIGNCONST:
					System.out.println("ASSIGNCONST");
					executeAssignConst(stmt);
					break;
				case ASSIGNARRAY:
					System.out.print("ASSIGNARRAY");
					executeAssignArray(stmt);
					break;
				case ASSIGNATTR:
					executeAssignAttr(stmt);
					System.out.println("ASSIGNATTR");
					break;
				case ASSATTINCI:
					executeAssignAttIncI(stmt);
					break;
				case ASSATTINCF:
					executeAssignAttIncF(stmt);
					break;
				case ASSINCI:
					System.out.println( "ASSINCI");
					executeAssIncI(stmt);
					break;
				case ASSDECI:
					System.out.println( "ASSDECI");
					executeAssDecI(stmt);
					break;
				case ASSINCF:
					System.out.println( "ASSINCF");
					executeAssIncF(stmt);
					break;
				case ASSDECF:
					System.out.println( "ASSDECF");
					executeAssDecF(stmt);
					break;
				case ASSATTDECI:
					executeAssignAttDecrI(stmt);
					break;
				case ASSATTDECF:
					executeAssignAttDecrF(stmt);
					break;
				case INC:
					System.out.println( "INC");
					executeInc(stmt);
					break;

				case PUSHPARAMS:
					System.out.print("PUSHPARAMS");
					executePushParams(stmt);
					break;
				case CALL:
					System.out.print( "CALL");
					executeCall(stmt);
					break;
				case CALLOBJ:
					executeCallObj(stmt);
					System.out.println("CALLOBJ");
					break;
				case RET:
					System.out.print( "RET");
					executeRet(stmt);
					break;
				case RETVOID:
					System.out.print( "RETVOID");
					executeRetVoid(stmt);
					break;

			}			
		}catch(IOException e){
			e.printStackTrace();
		}

		System.out.println("");
	}

	private void executeArrayLocI(StatementCode stmt)throws IOException{
		//tengo que obtener el offset del arreglo, luego obtener el valor del index y una vez resuelto sumarle al 
		// offset del arreglo el valor del index, ese offset se lo asigno al resultado
		ArrayLocation arr = (ArrayLocation) stmt.getOperand1().getExpression();
		Integer arrOfSet = arr.getDeclaration().getOff();
		VarLocation index = (VarLocation) stmt.getOperand2().getExpression();
		Integer indVal = index.getOff();
		VarLocation aux = (VarLocation) stmt.getOperand3().getExpression();
		Integer temOff = aux.getOff();
		writeFile(bw,"lea -"+String.valueOf(arrOfSet*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(indVal*VARSIZE)+"(%rbp), %r11");
		writeFile(bw,"mov (%r10, %r11, "+String.valueOf(VARSIZE)+"), %rcx");
		writeFile(bw,"mov %rcx, -"+String.valueOf(temOff*VARSIZE)+"(%rbp)");
	}

	public void execute(){
		for (StatementCode stmtCode : this.intermediateCode){
			translate(stmtCode);
		}
	}

	private void writeFile(BufferedWriter bwen, String content) throws IOException{
		bw.write(content);
		bw.newLine();
	}

	private void executeBeginProgram(StatementCode stmt) throws IOException{
		writeFile(bw,"# begin program");
		writeFile(bw,".file \""+this.originalFileName+"\"");
		writeFile(bw,".globl main");
		writeFile(bw,".type	main, @function");
	}

	private void executeEndProgram(StatementCode stmt) throws IOException{
		bw.flush();
		bw.close();
	}

	private void executeBeginClass(StatementCode stmt) throws IOException{
		writeFile(bw,"# begin class");
		ClassDecl classDecl = (ClassDecl) stmt.getOperand1().getExpression();
		String nameClass = classDecl.getName();
		writeFile(bw,nameClass+":");
	}

	private void executeEndClass(StatementCode stmt) throws IOException{
	}

	private void executeField(StatementCode stmt) throws IOException{
		IdDecl idDecl = (IdDecl) stmt.getOperand1().getExpression();
		Integer offSet = idDecl.getOff()*VARSIZE;
		writeFile(bw,"mov $0, -"+String.valueOf(offSet)+"(%rbp)");
	}

	private void executeBeginMethod(StatementCode stmt) throws IOException{
		MethodDecl methodDecl = (MethodDecl) stmt.getOperand1().getExpression();
		String label="";
		if (methodDecl.getName().compareTo("main")!=0){
			label = methodDecl.getClassRef().getName()+"_"+methodDecl.getName();
		}else{
			label = methodDecl.getName();
		}
		Integer methodOff = methodDecl.getOff()*VARSIZE;
		writeFile(bw,label+":");
		writeFile(bw,"enter $"+String.valueOf(methodOff)+",$0");
	}

	private void executeEndMethod(StatementCode stmt) throws IOException{
		writeFile(bw,"leave");
		writeFile(bw,"ret\n");
	}

	private void executeArrDeclInt(StatementCode stmt)throws IOException{
		ArrayIdDecl idDecl = (ArrayIdDecl) stmt.getOperand1().getExpression();
		Integer offSet = idDecl.getOff()*VARSIZE;
		writeFile(bw,"mov $0, %r10");	
		writeFile(bw,"mov %r10, -"+String.valueOf(offSet)+"(%rbp)");
	}

	private void executeParamDecl(StatementCode stmt) throws IOException{
		ParamDecl paramDecl = (ParamDecl) stmt.getOperand1().getExpression();
		Integer offSet = paramDecl.getOff()*VARSIZE;
		writeFile(bw,"mov $0, %r10");	
		writeFile(bw,"mov %r10, -"+String.valueOf(offSet)+"(%rbp)");	
	}

	private void executeIntDecl(StatementCode stmt) throws IOException{
		IdDecl idDecl = (IdDecl) stmt.getOperand1().getExpression();
		System.out.println(idDecl.getName());
		Integer offSet = idDecl.getOff()*VARSIZE;
		writeFile(bw,"mov $0, %r10");	
		writeFile(bw,"mov %r10, -"+String.valueOf(offSet)+"(%rbp)");	
	}

	private void executeBoolDecl (StatementCode stmt) throws IOException{
		IdDecl idDecl = (IdDecl) stmt.getOperand1().getExpression();
		Integer offSet = idDecl.getOff()*VARSIZE;
		writeFile(bw,"mov $0, %r10");	
		writeFile(bw,"mov %r10, -"+String.valueOf(offSet)+"(%rbp)");		
	}

	
	private void executeObjDecl(StatementCode stmt) throws IOException{
		IdDecl idDecl = (IdDecl) stmt.getOperand1().getExpression();
		Integer offSet = idDecl.getOff()*VARSIZE;
		writeFile(bw,"mov $0, %r10");	
		writeFile(bw,"mov %r10, -"+String.valueOf(offSet)+"(%rbp)");
	}

	private void executeBeginIf (StatementCode stmt) throws IOException{
		writeFile(bw,"# begin if ");
	}

	private void executeEndIf (StatementCode stmt) throws IOException{
		Integer numberLbl = stmt.getOperand2().getNumber();
		writeFile(bw,OperationCode.ENDIF.toString()+String.valueOf(numberLbl)+": ");
	}

	private void executeElseIf(StatementCode stmt) throws IOException{
		Integer numberLbl = stmt.getOperand2().getNumber();
		writeFile(bw,OperationCode.ELSEIF.toString()+String.valueOf(numberLbl)+": ");
	}

	private void executeBeginFor(StatementCode stmt) throws IOException{
		Integer forNumber = stmt.getOperand2().getNumber();
		String label = OperationCode.BEGINFOR.toString()+String.valueOf(forNumber);
		writeFile(bw,label+": ");
	}

	private void executeIncFor (StatementCode stmt) throws IOException{
		Integer lblNumber = stmt.getOperand2().getNumber();
		String label = OperationCode.INCFOR.toString()+String.valueOf(lblNumber);
		writeFile(bw,label+": ");
	}

	private void executeEndFor(StatementCode stmt) throws IOException{
		Integer lblNumber = stmt.getOperand2().getNumber();
		String label = OperationCode.ENDFOR.toString()+ String.valueOf(lblNumber);
		writeFile(bw,label+": ");	
	}

	private void executeBeginWhile(StatementCode stmt) throws IOException{
		Integer whileNumber = stmt.getOperand2().getNumber();
		String label = OperationCode.BEGINWHILE.toString()+String.valueOf(whileNumber);
		writeFile(bw,label+": ");
	}

	private void executeEndWhile(StatementCode stmt) throws IOException{
		Integer lblNumber = stmt.getOperand2().getNumber();
		String label = OperationCode.ENDWHILE.toString()+ String.valueOf(lblNumber);
		writeFile(bw,label+": ");
	}

	private void executeJmpFalse(StatementCode stmt) throws IOException{
		VarLocation condition = (VarLocation) stmt.getOperand1().getExpression();
		Integer conditionOffSet = condition.getOff()*VARSIZE;
		String label = stmt.getOperand2().getName();
		writeFile(bw,"mov -"+conditionOffSet+"(%rbp),%r11");
		writeFile(bw,"cmp $0, %r11");
		writeFile(bw,"jne "+label);
	}

	private void executeJmp(StatementCode stmt) throws IOException{
		String label = stmt.getOperand1().getName();
		writeFile(bw,"jmp "+label);
	}

	private void executeSubUI(StatementCode stmt) throws IOException{
		System.out.println("SubUI");
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();

		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		writeFile(bw,"imul $-1, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeNot(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp $1, %r10");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmove %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeAddII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw,"add %r11, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeAddFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw,"add %r11, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeSubII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeSubFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeMulII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw,"imul %r10, %r11");
		writeFile(bw, "mov %r11, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeMulFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw,"imul %r10, %r11");
		writeFile(bw, "mov %r11, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeDivII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"xor %rdx, %rdx");
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeDivFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"xor %rdx, %rdx");
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r11");
			writeFile(bw,"mov (%rbx,%r11,8),%rax");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %rax");
		} 
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r11");
			writeFile(bw,"idivq (%rbx,%r11,8)");
		}else{
			writeFile(bw,"idivq -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp)");
		}
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeModII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"xor %rdx, %rdx");
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw, "mov %rdx, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeEqII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmove %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeEqFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmove %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeEqBB(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmove %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeNeqII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovne %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeNeqFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovne %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeNeqBB(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovne %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeSmallII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovl %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeSmallFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovl %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeLtoeII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovle %rdx, %rax");		
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeLtoeFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovle %rdx, %rax");		
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeBiggerII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovg %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}
	
	private void executeBiggerFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovg %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeGtoeII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovge %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeGtoeFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmovge %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeAndBB(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov $0, %rax");
		writeFile(bw,"cmp $0, %r10");
		writeFile(bw,"mov $1, %rdx");
		writeFile(bw,"cmovne %rdx, %rax");
		writeFile(bw,"cmp $0, %r11");
		writeFile(bw,"mov $1, %rdx");
		writeFile(bw,"cmovne %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeOrBB(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r8");
			writeFile(bw,"mov (%rbx,%r8,8),%r10");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");

		}
		if (operand2.isAttribute()){
			Integer offset = operand2.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r9");
			writeFile(bw,"mov (%rbx,%r9,8),%r11");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");

		}
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp $0, %r10");
		writeFile(bw,"mov $0, %rdx");
		writeFile(bw,"cmove %rdx, %rax");
		writeFile(bw,"cmp $0, %r11");
		writeFile(bw,"cmove %rdx, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeAssignation(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov %r10, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeAssignAttr(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		AttributeLocation attLoc = (AttributeLocation) stmt.getOperand3().getExpression();
		Integer attLocOff = attLoc.getOff()*VARSIZE;
		Integer attOff = null;
		ClassDecl classDecl = (ClassDecl) ((IdDecl)attLoc.getDeclaration()).getClassRef();
		Boolean find = false;
		System.out.println(classDecl.getName());
		System.out.println(attLoc.getIds().get(1));
		for (FieldDecl fieldDecl: classDecl.getAttributes()){
			for (IdDecl idDecl : fieldDecl.getNames()){
				if (idDecl.getName().compareTo(attLoc.getIds().get(1).getName())==0){
					attOff = idDecl.getOff();
					find = true;
					break;
				}
				if (find)
					break;
			}
			if (find)
				break;	
		}
		System.out.println(String.valueOf(attOff));
		writeFile(bw,"lea -"+String.valueOf(attLocOff)+"(%rbp),%rcx");
		writeFile(bw,"mov $"+String.valueOf(attOff)+", %rdx");
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp) ,%r10");
		writeFile(bw,"mov %r10,(%rcx,%rdx,8)");
		
	}
	private void executeAssIncI(StatementCode stmt) throws IOException{
		System.out.println("ASS");
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp), %r11");
		writeFile(bw,"add %r10, %r11");
		writeFile(bw,"mov %r11, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeAssDecI(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp), %r11");
		writeFile(bw,"sub %r10, %r11");
		writeFile(bw,"mov %r11, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeAssIncF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp), %r11");
		writeFile(bw,"add %r10, %r11");
		writeFile(bw,"mov %r11, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeAssDecF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp), %r11");
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw,"mov %r10, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
	}

	public void executeAssignAttIncI(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		AttributeLocation attLoc = (AttributeLocation) stmt.getOperand3().getExpression();
		Integer attLocOff = attLoc.getOff()*VARSIZE;
		Integer attOff = null;
		ClassDecl classDecl = (ClassDecl) ((IdDecl)attLoc.getDeclaration()).getClassRef();
		Boolean find = false;
		System.out.println(classDecl.getName());
		System.out.println(attLoc.getIds().get(1));
		for (FieldDecl fieldDecl: classDecl.getAttributes()){
			for (IdDecl idDecl : fieldDecl.getNames()){
				if (idDecl.getName().compareTo(attLoc.getIds().get(1).getName())==0){
					attOff = idDecl.getOff();
					find = true;
					break;
				}
				if (find)
					break;
			}
			if (find)
				break;	
		}
		writeFile(bw,"lea -"+String.valueOf(attLocOff)+"(%rbp),%rcx");
		writeFile(bw,"mov $"+String.valueOf(attOff)+", %rdx");
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov (%rcx,%rdx,8),%r11");
		writeFile(bw,"add %r10, %r11");
		writeFile(bw,"mov %r11, (%rcx,%rdx,8)");
	}

	public void executeAssignAttIncF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		AttributeLocation attLoc = (AttributeLocation) stmt.getOperand3().getExpression();
		Integer attLocOff = attLoc.getOff()*VARSIZE;
		Integer attOff = null;
		ClassDecl classDecl = (ClassDecl) ((IdDecl)attLoc.getDeclaration()).getClassRef();
		Boolean find = false;
		System.out.println(classDecl.getName());
		System.out.println(attLoc.getIds().get(1));
		for (FieldDecl fieldDecl: classDecl.getAttributes()){
			for (IdDecl idDecl : fieldDecl.getNames()){
				if (idDecl.getName().compareTo(attLoc.getIds().get(1).getName())==0){
					attOff = idDecl.getOff();
					find = true;
					break;
				}
				if (find)
					break;
			}
			if (find)
				break;	
		}
		writeFile(bw,"lea -"+String.valueOf(attLocOff)+"(%rbp),%rcx");
		writeFile(bw,"mov $"+String.valueOf(attOff)+", %rdx");
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov (%rcx,%rdx,8),%r11");
		writeFile(bw,"add %r10, %r11");
		writeFile(bw,"mov %r11, (%rcx,%rdx,8)");
	}

	private void executeAssignAttDecrI(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		AttributeLocation attLoc = (AttributeLocation) stmt.getOperand3().getExpression();
		Integer attLocOff = attLoc.getOff()*VARSIZE;
		Integer attOff = null;
		ClassDecl classDecl = (ClassDecl) ((IdDecl)attLoc.getDeclaration()).getClassRef();
		Boolean find = false;
		System.out.println(classDecl.getName());
		System.out.println(attLoc.getIds().get(1));
		for (FieldDecl fieldDecl: classDecl.getAttributes()){
			for (IdDecl idDecl : fieldDecl.getNames()){
				if (idDecl.getName().compareTo(attLoc.getIds().get(1).getName())==0){
					attOff = idDecl.getOff();
					find = true;
					break;
				}
				if (find)
					break;
			}
			if (find)
				break;	
		}
		writeFile(bw,"lea -"+String.valueOf(attLocOff)+"(%rbp),%rcx");
		writeFile(bw,"mov $"+String.valueOf(attOff)+", %rdx");
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov (%rcx,%rdx,8),%r11");
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw,"mov %r10, (%rcx,%rdx,8)");
	}

	private void executeAssignAttDecrF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		AttributeLocation attLoc = (AttributeLocation) stmt.getOperand3().getExpression();
		Integer attLocOff = attLoc.getOff()*VARSIZE;
		Integer attOff = null;
		ClassDecl classDecl = (ClassDecl) ((IdDecl)attLoc.getDeclaration()).getClassRef();
		Boolean find = false;
		System.out.println(classDecl.getName());
		System.out.println(attLoc.getIds().get(1));
		for (FieldDecl fieldDecl: classDecl.getAttributes()){
			for (IdDecl idDecl : fieldDecl.getNames()){
				if (idDecl.getName().compareTo(attLoc.getIds().get(1).getName())==0){
					attOff = idDecl.getOff();
					find = true;
					break;
				}
				if (find)
					break;
			}
			if (find)
				break;	
		}
		writeFile(bw,"lea -"+String.valueOf(attLocOff)+"(%rbp),%rcx");
		writeFile(bw,"mov $"+String.valueOf(attOff)+", %rdx");
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov (%rcx,%rdx,8),%r11");
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw,"mov %r10, (%rcx,%rdx,8)");
	}

	private void executeInc(StatementCode stmt) throws IOException{
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		if (operand3.isAttribute()){
			Integer offset = operand3.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r11");
			writeFile(bw,"mov (%rbx,%r11,8),%r10");
			writeFile(bw,"add $1, %r10");
			writeFile(bw,"mov %r10,(%rbx,%r11,8))");

		}else{
			writeFile(bw,"mov -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp), %r10");
			writeFile(bw,"add $1, %r10");
			writeFile(bw,"mov %r10, -"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");
		}
	}

	private void executePushParams(StatementCode stmt) throws IOException{
		String register = stmt.getOperand1().getName();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp), %"+register);
	}

	private void executeCall(StatementCode stmt) throws IOException{
		MethodCall methodCall = null;
		if (stmt.getOperand1().getExpression() instanceof MethodCallStmt){
			methodCall = (MethodCall) ((MethodCallStmt)stmt.getOperand1().getExpression()).getMethodCall();
		}else{
			methodCall = (MethodCall) stmt.getOperand1().getExpression();

		}
		String lbl=null;
		if ((methodCall.getMethodDecl()!=null)&&(!methodCall.getMethodDecl().getBody().isExtern())&&(methodCall.getIds().get(0).getName().compareTo("main")!=0)){
			lbl = methodCall.getMethodDecl().getClassRef().getName()+"_"+methodCall.getIds().get(methodCall.getIds().size()-1).getName();
		}else		{
			lbl=methodCall.getIds().get(methodCall.getIds().size()-1).getName();;
		}
		writeFile(bw,"call "+lbl);
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		writeFile(bw,"mov %rax, -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp)");
	} 

	private void executeCallObj(StatementCode stmt) throws IOException{
    	MethodCall methodCall = (MethodCall) stmt.getOperand1().getExpression();

		writeFile(bw,"lea -"+String.valueOf(methodCall.getObject().getOff()*VARSIZE)+"(%rbp),%rbx");
		System.out.println("DASDAS");
		System.out.println( methodCall.getMethodDecl());
		String className = methodCall.getObject().getClassRef().getName();
		String lbl = className+"_"+methodCall.getIds().get(1).getName();
		writeFile(bw,"call "+lbl);
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		writeFile(bw,"mov %rax, -"+String.valueOf(operand2.getOff()*VARSIZE)+"(%rbp)");
	}

	private void executeRet(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		if (operand1.isAttribute()){
			Integer offset = operand1.getOff();
			writeFile(bw,"mov $"+String.valueOf(offset)+",%r10");
			writeFile(bw,"mov (%rbx,%r10,8),%rax");
		}else{
			writeFile(bw,"mov -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp), %rax");
		}
		writeFile(bw,"leave");
		writeFile(bw,"ret\n");
	}

	private void executeRetVoid(StatementCode stmt) throws IOException{
		writeFile(bw,"leave");
		writeFile(bw,"ret\n");
	}

	private void executeAssignConst(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		String value = stmt.getOperand2().getName();
		writeFile(bw,"mov $"+value+", %r10");	
		writeFile(bw,"mov %r10, -"+String.valueOf(operand1.getOff()*VARSIZE)+"(%rbp)");	
	}

	private void executeAssignArray(StatementCode stmt) throws IOException{
		ArrayLocation arr = (ArrayLocation) stmt.getOperand3().getExpression();
		Integer arrOfSet = arr.getDeclaration().getOff();
		VarLocation index = (VarLocation) stmt.getOperand2().getExpression();
		Integer indVal = index.getOff();
		VarLocation aux = (VarLocation) stmt.getOperand1().getExpression();
		Integer temOff = aux.getOff();
		writeFile(bw,"lea -"+String.valueOf(arrOfSet*VARSIZE)+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(indVal*VARSIZE)+"(%rbp), %r11");
		writeFile(bw,"mov -"+String.valueOf(temOff*VARSIZE)+"(%rbp), %rcx");
		writeFile(bw,"mov %rcx, (%r10, %r11, "+String.valueOf(VARSIZE)+")");
	}

	private void executeAttLocI(StatementCode stmt) throws IOException{
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		AttributeLocation attLoc = (AttributeLocation) stmt.getOperand1().getExpression();
		Integer attLocOff = attLoc.getOff()*VARSIZE;
		Integer attOff = null;
		ClassDecl classDecl = (ClassDecl) ((IdDecl)attLoc.getDeclaration()).getClassRef();
		Boolean find = false;
		System.out.println(classDecl.getName());
		System.out.println(attLoc.getIds().get(1));
		for (FieldDecl fieldDecl: classDecl.getAttributes()){
			for (IdDecl idDecl : fieldDecl.getNames()){
				if (idDecl.getName().compareTo(attLoc.getIds().get(1).getName())==0){
					attOff = idDecl.getOff();
					find = true;
					break;
				}
				if (find)
					break;
			}
			if (find)
				break;	
		}
		writeFile(bw,"lea -"+String.valueOf(attLocOff)+"(%rbp),%rcx");
		writeFile(bw,"mov $"+String.valueOf(attOff)+", %rdx");
		writeFile(bw,"mov (%rcx,%rdx,8),%r10");
		writeFile(bw,"mov %r10,-"+String.valueOf(operand3.getOff()*VARSIZE)+"(%rbp)");	
	}

	private void executeAttLocB(StatementCode stmt) throws IOException{
		executeAttLocI(stmt);
	}

}