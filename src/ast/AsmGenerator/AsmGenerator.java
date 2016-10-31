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

	public AsmGenerator(List<StatementCode> program,String path,String name){
		this.intermediateCode = program;
		this.path = path;
		this.originalFileName = name;
		this.name = name.split("//.")[0]+".s";
		try{
			File file = new File(path+name);
			if (file.exists()){
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
					executeBeginProgram(stmt);
					break;
				case ENDPROGRAM:
					executeEndProgram(stmt);
					break;
				case BEGINCLASS:
					executeBeginClass(stmt);
					break;
				case ENDCLASS:
					executeEndClass(stmt);
					break;

				case FIELD:
					executeField(stmt);
					break;
				case BEGINMETHOD:
					executeBeginMethod(stmt);
					break;
				case ENDMETHOD:
					executeEndMethod(stmt);
					break;
				case PARAMDECL:
					executeParamDecl(stmt);
					break;
				case LOADPARAM:
					break;
				case IDDECL:
					break;
				case ARRAYDECLI:
					System.out.println( "ARRAYDECLI");
					break;
				case ARRAYDECLF:
					System.out.println( "ARRAYDECLF");
					break;
				case ARRAYDECLB:
					System.out.println( "ARRAYDECLB");
					break;
				case INTDECL:
					executeIntDecl(stmt);
					break;
				case FLOATDECL:
					System.out.println( "FLOATDECL");
					break;
				case BOOLDECL:
					executeBoolDecl(stmt);
					break;

			//Locations
				case ARRAYLOCI:
					System.out.println( "ARRAYLOCI");
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
					System.out.println( "ATTLOCI");
					break;
				case ATTLOCF:
					System.out.println( "ATTLOCF");
					break;
				case ATTLOCB:
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
					executeBeginIf(stmt);
					break;
				case ENDIF:
					executeEndIf(stmt);
					System.out.println( "ENDIF");
					break;
				case ELSEIF:
					executeElseIf(stmt);
					System.out.println( "ELSEIF");
					break;
				case BEGINFOR:
					executeBeginFor(stmt);
					System.out.println( "BEGINFOR");
					break;
				case INCFOR:
					executeIncFor(stmt);
					System.out.println( "INCFOR");
					break;
				case ENDFOR:
					executeEndFor(stmt);
					System.out.println( "ENDFOR");
					break;
				case BEGINWHILE:
					executeBeginWhile(stmt);
					System.out.println( "BEGINWHILE");
					break;
				case ENDWHILE:
					executeEndWhile(stmt);
					System.out.println( "ENDWHILE");
					break;
				
			// Unary Aruthmetic
				case SUBUI:
					executeSubUI(stmt);
					System.out.println( "SUBUI");
					break;
				case NOT:
					executeNot(stmt);
					System.out.println( "NOT"); 	
					break;
				case ADDII:
					executeAddII(stmt);
					System.out.println( "ADDII");
					break;
				case ADDFF:
					executeAddFF(stmt);
					System.out.println( "ADDFF");
					break;
				case SUBII:
					executeSubII(stmt);
					System.out.println( "SUBII");
					break;
				case SUBFF:
					executeSubFF(stmt);
					System.out.println( "SUBFF");
					break;
				case MULII:
					executeMulII(stmt);
					System.out.println( "MULII");
					break;
				case MULFF:
					executeMulFF(stmt);
					System.out.println( "MULFF");
					break;
				case DIVII:
					executeDivII(stmt);
					System.out.println( "DIVII");
					break;	
				case DIVFF:
					executeDivFF(stmt);
					System.out.println( "DIVFF");
					break;
				case MODII:
					executeModII(stmt);
					System.out.println( "MODII");
					break;
			// Eq operations													
				case EQII:
					executeEqII(stmt);
					System.out.println( "EQII");
					break;
				case EQFF:
					executeEqFF(stmt);
					System.out.println( "EQFF");
					break;
				case EQBB:
					executeEqBB(stmt);
					System.out.println( "EQBB");
					break;
				case NEQII:
					executeNeqII(stmt);
					System.out.println( "NEQII");
					break;
				case NEQFF:
					executeNeqFF(stmt);
					System.out.println( "NEQFF");
					break;
				case NEQBB:
					executeNeqBB(stmt);
					System.out.println( "NEQBB");
					break;

			// Relational operations
				case SMALLII:
					executeSmallII(stmt);
					System.out.println( "SMALLII");
					break;
				case SMALLFF:
					executeSmallFF(stmt);
					System.out.println( "SMALLFF");
					break;
				case LTOEII:
					executeLtoeII(stmt);
					System.out.println( "LTOEII");
					break;
				case LTOEFF:
					executeLtoeFF(stmt);
					System.out.println( "LTOEFF");
					break;
				case BIGGERII:
					executeBiggerII(stmt);
					System.out.println( "BIGGERII");
					break;
				case BIGGERFF:
					executeBiggerFF(stmt);
					System.out.println( "BIGGERFF");
					break;
				case GTOEII:
					executeGtoeII(stmt);
					System.out.println( "GTOEII");
					break;
				case GTOEFF:
					executeGtoeFF(stmt);
					System.out.println( "GTOEFF");
					break;

			// Logical operations			
				case ANDBB:
					executeAndBB(stmt);
					System.out.println( "ANDBB");
					break;
				case ORBB:
					executeOrBB(stmt);
					System.out.println( "ORBB");
					break;

			// Jump
				case JMPFALSE:
					executeJmpFalse(stmt);	
					break;

				case JMPTRUE:
					System.out.println( "JMPTRUE");
					break;
				case JMP:
					executeJmp(stmt);
					break;

			// Assign
				case ASSIGNATION:
					executeAssignation(stmt);
					System.out.println( "ASSIGNATION");
					break;
				case ASSIGNCONST:
					executeAssignConst(stmt);
					System.out.println("ASSIGNCONST");
					break;
				case ASSINCI:
					executeAssIncI(stmt);
					System.out.println( "ASSINCI");
					break;
				case ASSDECI:
					executeAssDecI(stmt);
					System.out.println( "ASSDECI");
					break;
				case ASSINCF:
					executeAssIncF(stmt);
					System.out.println( "ASSINCF");
					break;
				case ASSDECF:
					executeAssDecF(stmt);
					System.out.println( "ASSDECF");
					break;
				case INC:
					executeInc(stmt);
					System.out.println( "INC");
					break;

				case PUSHPARAMS:
					executePushParams(stmt);
					System.out.print("PUSHPARAMS");
					break;
				case CALL:
					executeCall(stmt);
					System.out.print( "CALL");
					break;
				case RET:
					executeRet(stmt);
					System.out.print( "RET");
					break;
				case RETVOID:
					System.out.print( "RETVOID");
					break;

			}			
		}catch(IOException e){
			e.printStackTrace();
		}

		System.out.println( "");
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
		writeFile(bw,".file "+originalFileName);
		writeFile(bw,".glob main");
		writeFile(bw,".type	main, @function");
	}

	private void executeEndProgram(StatementCode stmt) throws IOException{
		bw.write("# end program");
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
		Integer offSet = idDecl.getOff();
		writeFile(bw,"movq $0, -"+String.valueOf(offSet)+"(%rbp)");
	}

	private void executeBeginMethod(StatementCode stmt) throws IOException{
		MethodDecl methodDecl = (MethodDecl) stmt.getOperand1().getExpression();
		String label = methodDecl.getName();
		Integer methodOff = methodDecl.getOff();
		writeFile(bw,label+":");
		writeFile(bw,"enter $"+String.valueOf(methodOff)+",$0");
	}

	private void executeEndMethod(StatementCode stmt) throws IOException{
		writeFile(bw,"leave");
		writeFile(bw,"ret\n");
	}

	private void executeParamDecl(StatementCode stmt) throws IOException{
	
	}

	private void executeIntDecl(StatementCode stmt) throws IOException{
		IdDecl idDecl = (IdDecl) stmt.getOperand1().getExpression();
		Integer offSet = idDecl.getOff();
		writeFile(bw,"movq $0, -"+String.valueOf(offSet)+"(%rbp)");	
	}

	private void executeBoolDecl (StatementCode stmt) throws IOException{
		IdDecl idDecl = (IdDecl) stmt.getOperand1().getExpression();
		Integer offSet = idDecl.getOff();
		writeFile(bw,"movq $0, -"+String.valueOf(offSet)+"(%rbp)");	
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
		Integer conditionOffSet = condition.getOff();
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
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"imul $-1, %r10");
		writeFile(bw, "mov %r11, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeNot(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp $1, %r10");
		writeFile(bw,"cmove $0, %rax");
	}

	private void executeAddII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"add %r11, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
		
	}

	private void executeAddFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"add %r11, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeSubII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeSubFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw, "mov %r10, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeMulII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"imul %r10, %r11");
		writeFile(bw, "mov %r11, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeMulFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"imul %r10, %r11");
		writeFile(bw, "mov %r11, %rax");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeDivII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %rax");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r10");
		writeFile(bw,"idiv %r10");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeDivFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %rax");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r10");
		writeFile(bw,"idiv %r10");
		writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeModII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %rax");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r10");
		writeFile(bw,"idiv %r10");
		writeFile(bw, "mov %rdx, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeEqII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmove $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeEqFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmove $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeEqBB(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmove $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeNeqII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovne $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeNeqFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovne $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeNeqBB(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovne $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeSmallII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovl $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeSmallFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovl $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeLtoeII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovle $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeLtoeFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovle $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeBiggerII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovg $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}
	
	private void executeBiggerFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovg $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeGtoeII(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovge $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeGtoeFF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp %r10, %r11");
		writeFile(bw,"cmovge $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeAndBB(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $0, %rax");
		writeFile(bw,"cmp $0, %r10");
		writeFile(bw,"cmovne $1, %rax");
		writeFile(bw,"cmp $0, %r11");
		writeFile(bw,"cmovne $1, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeOrBB(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
		writeFile(bw,"mov $1, %rax");
		writeFile(bw,"cmp $0, %r10");
		writeFile(bw,"cmove $0, %rax");
		writeFile(bw,"cmp $0, %r11");
		writeFile(bw,"cmove $0, %rax");
		writeFile(bw,"mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeAssignation(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov %r10, -"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executeAssIncI(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand3.getOff())+"(%rbp), %r11");
		writeFile(bw,"add %r10, %r11");
		writeFile(bw,"mov %r11, -"+String.valueOf(operand1.getOff())+"(%rbp)");
	}

	private void executeAssDecI(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand3.getOff())+"(%rbp), %r11");
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw,"mov %r10, -"+String.valueOf(operand1.getOff())+"(%rbp)");
	}

	private void executeAssIncF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand3.getOff())+"(%rbp), %r11");
		writeFile(bw,"add %r10, %r11");
		writeFile(bw,"mov %r11, -"+String.valueOf(operand1.getOff())+"(%rbp)");
	}

	private void executeAssDecF(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
		writeFile(bw,"mov -"+String.valueOf(operand3.getOff())+"(%rbp), %r11");
		writeFile(bw,"sub %r11, %r10");
		writeFile(bw,"mov %r10, -"+String.valueOf(operand1.getOff())+"(%rbp)");
	}

	private void executeInc(StatementCode stmt) throws IOException{
		operand3 = (VarLocation) stmt.getOperand3().getExpression();
		writeFile(bw,"add $1,-"+String.valueOf(operand3.getOff())+"(%rbp)");
	}

	private void executePushParams(StatementCode stmt) throws IOException{
		String register = stmt.getOperand1().getName();
		operand2 = (VarLocation) stmt.getOperand2().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %"+register);
	}

	private void executeCall(StatementCode stmt) throws IOException{
		MethodCall methodCall = (MethodCall) stmt.getOperand1().getExpression();
		String lbl = methodCall.getIds().get(methodCall.getIds().size()-1).getName();
		writeFile(bw,"call "+lbl);
	}

	private void executeRet(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %rax");
	}

	private void executeAssignConst(StatementCode stmt) throws IOException{
		operand1 = (VarLocation) stmt.getOperand1().getExpression();
		String value = stmt.getOperand2().getName();
		writeFile(bw,"mov $"+value+", -"+String.valueOf(operand1.getOff())+"(%rbp)");

	}
}