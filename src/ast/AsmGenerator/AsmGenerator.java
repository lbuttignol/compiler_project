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
					System.out.println( "LOADPARAM");  
					break;
				case IDDECL:
					System.out.println( "IDDECL");				
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
					System.out.println( "BEGINWHILE");
					break;
				case ENDWHILE:
					System.out.println( "ENDWHILE");
					break;
				
			// Unary Aruthmetic
				case SUBUI:
					System.out.println( "SUBUI");
					break;
				case NOT:
					System.out.println( "NOT"); 	
					break;
				case ADDII:
					System.out.println( "ADDII");
					break;
				case ADDFF:
					System.out.println( "ADDFF");
					break;
				case ADDIF:
					System.out.println( "ADDIF");
					break;
				case ADDFI:
					System.out.println( "ADDFI");
					break;
				case SUBII:
					System.out.println( "SUBII");
					break;
				case SUBFF:
					System.out.println( "SUBFF");
					break;
				case SUBIF:
					System.out.println( "SUBIF");
					break;
				case SUBFI:
					System.out.println( "SUBFI");
					break;
				case MULII:
					System.out.println( "MULII");
					break;
				case MULFF:
					System.out.println( "MULFF");
					break;
				case MULIF:
					System.out.println( "MULIF");
					break;
				case MULFI:
					System.out.println( "MULFI");
					break;
				case DIVII:
					System.out.println( "DIVII");
					break;
				case DIVFF:
					System.out.println( "DIVFF");
					break;
				case DIVIF:
					System.out.println( "DIVIF");
					break;
				case DIVFI:
					System.out.println( "DIVFI");
					break;
				case MODII:
					System.out.println( "MODII");
					break;

			// Eq operations													
				case EQII:
					System.out.println( "EQII");
					break;
				case EQFF:
					System.out.println( "EQFF");
					break;
				case EQIF:
					System.out.println( "EQIF");
					break;
				case EQFI:
					System.out.println( "EQFI");
					break;
				case EQBB:
					System.out.println( "EQBB");
					break;
				case NEQII:
					System.out.println( "NEQII");
					break;
				case NEQFF:
					System.out.println( "NEQFF");
					break;
				case NEQIF:
					System.out.println( "NEQIF");
					break;
				case NEQFI:
					System.out.println( "NEQFI");
					break;
				case NEQBB:
					System.out.println( "NEQBB");
					break;

			// Relational operations
				case SMALLII:
					System.out.println( "SMALLII");
					break;
				case SMALLFF:
					System.out.println( "SMALLFF");
					break;
				case SMALLIF:
					System.out.println( "SMALLIF");
					break;
				case SMALLFI:
					System.out.println( "SMALLFI");
					break;
				case LTOEII:
					System.out.println( "LTOEII");
					break;
				case LTOEFF:
					System.out.println( "LTOEFF");
					break;
				case LTOEIF:
					System.out.println( "LTOEIF");
					break;
				case LTOEFI:
					System.out.println( "LTOEFI");
					break;
				case BIGGERII:
					System.out.println( "BIGGERII");
					break;
				case BIGGERFF:
					System.out.println( "BIGGERFF");
					break;
				case BIGGERIF:
					System.out.println( "BIGGERIF");
					break;
				case BIGGERFI:
					System.out.println( "BIGGERFI");
					break;
				case GTOEII:
					System.out.println( "GTOEII");
					break;
				case GTOEFF:
					System.out.println( "GTOEFF");
					break;
				case GTOEIF:
					System.out.println( "GTOEIF");
					break;
				case GTOEFI:
					System.out.println( "GTOEFI");
					break;

			// Logical operations			
				case ANDBB:
					System.out.println( "ANDBB");
					break;
				case ORBB:
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
					System.out.println( "ASSIGNATION"); 		
					break;
				case ASSINCI:
					System.out.println( "ASSINCI");
					break;
				case ASSDECI:
					System.out.println( "ASSDECI");
					break;
				case ASSINCF:
					System.out.println( "ASSINCF");
					break;
				case ASSDECF:
					System.out.println( "ASSDECF");
					break;
				case INC:
					System.out.println( "INC");
					break;
				case PUSHPARAMS:
					System.out.print("PUSHPARAMS");
					break;
				case CALL:
					System.out.print( "CALL");
					break;
				case RET:
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
		String[] registers = {"rdi","rsi","rdx","rcx","r8","r9"};	
		MethodDecl methodDecl = (MethodDecl) stmt.getOperand1().getExpression();
		String label = methodDecl.getName();
		Integer methodOff = 0;
		writeFile(bw,label+":");
		writeFile(bw,"enter $"+String.valueOf(methodOff)+",$0");
		List<ParamDecl> paramDecl = methodDecl.getParams();
		for (int i=0;i<6&&i<paramDecl.size();i++){
			//offSet = paramDecl.getOff();
		//	writeFile(bw,"mov %"+registers[i]+", -"+offSet+"(%rbp)");
		}
		// DONDE PONGO LOS ARGUMENTOS SI HAY MAS DE 6.
	}

	private void executeEndMethod(StatementCode stmt) throws IOException{
		writeFile(bw,"leave");
		writeFile(bw,"ret\n");
	}

	private void executeParamDecl(StatementCode stmt) throws IOException{
		/*ParamDecl paramDecl = (ParamDecl) stmt.getOperand1().getExpression();
		Integer offSet = paramDecl.getoff();
		writeFile(bw,"movq $0, -"+String.valueOf(offSet)+"(%rbp)");
		*/
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

	private void executeJmpFalse(StatementCode stmt) throws IOException{
		VarLocation condition = (VarLocation) stmt.getOperand1().getExpression();
		Integer conditionOffSet = condition.getOff();
		String label = stmt.getOperand2().getName();
		writeFile(bw,"mov -"+conditionOffSet+"(%rbp),%r11");
		writeFile(bw,"cmp $0, %r11");
		writeFile(bw,"jne "+label);
	}

	private void executeJmp(StatementCode stmt) throws IOException{
		String label = stmt.getOperand2().getName();
		writeFile(bw,"jmp "+label);
	}
}