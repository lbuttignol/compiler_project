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
					writeFile(bw,"# begin program");
					writeFile(bw,".file "+originalFileName);
					writeFile(bw,".glob main");
					writeFile(bw,".type	main, @function");
					break;
				case ENDPROGRAM:
					bw.write("# end program");
					bw.flush();
					bw.close();
					break;
				case BEGINCLASS:
					writeFile(bw,"# begin class");
					ClassDecl classDecl = (ClassDecl) stmt.getOperand1().getExpression();
					String nameClass = classDecl.getName();
					writeFile(bw,nameClass+":");
					break;
				case ENDCLASS:
					writeFile(bw,"# end class");
					break;
				case FIELD: //Solo cuando se usa en block
					idDecl = (IdDecl) stmt.getOperand1().getExpression();
					offSet = idDecl.getOff();
					writeFile(bw,"movq $0, -"+String.valueOf(offSet)+"(%rbp)");
					break; 
				case BEGINMETHOD: 
					String[] registers = {"rdi","rsi","rdx","rcx","r8","r9"};	
					
					MethodDecl methodDecl = (MethodDecl) stmt.getOperand1().getExpression();
					label = methodDecl.getName();
					Integer methodOff = 0;
					writeFile(bw,label+":");
					writeFile(bw,"enter $"+String.valueOf(methodOff)+",$0");
					paramsDecl = methodDecl.getParams();
					for (int i=0;i<6&&i<paramsDecl.size();i++){
						//offSet = paramDecl.getOff();
					//	writeFile(bw,"mov %"+registers[i]+", -"+offSet+"(%rbp)");
					}
					// DONDE PONGO LOS ARGUMENTOS SI HAY MAS DE 6.
					break;
				case ENDMETHOD:
					writeFile(bw,"leave");
					writeFile(bw,"ret\n");
					break;
				case PARAMDECL:
					paramDecl = (ParamDecl) stmt.getOperand1().getExpression();
					offSet = paramDecl.getOff();
					writeFile(bw,"movq $0, -"+String.valueOf(offSet)+"(%rbp)");
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
					idDecl = (IdDecl) stmt.getOperand1().getExpression();
					offSet = idDecl.getOff();
					writeFile(bw,"movq $0, -"+String.valueOf(offSet)+"(%rbp)");
					System.out.println( "INTDECL");
					break;
				case FLOATDECL:
					System.out.println( "FLOATDECL");
					break;
				case BOOLDECL:
					System.out.println( "BOOLDECL");
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
					System.out.println( "BEGINIF");
					writeFile(bw,"BEGIN IF");
					break;
				case ENDIF:
					numberLbl = stmt.getOperand2().getNumber();
					writeFile(bw,OperationCode.ENDIF.toString()+String.valueOf(numberLbl)+": ");
					System.out.println( "ENDIF");
					break;
				case ELSEIF:
					numberLbl = stmt.getOperand2().getNumber();
					writeFile(bw,OperationCode.ELSEIF.toString()+String.valueOf(numberLbl)+": ");
					break;
				case BEGINFOR:
					Integer forNumber = stmt.getOperand2().getNumber();
					label = OperationCode.BEGINFOR.toString()+String.valueOf(forNumber);
					writeFile(bw,label+": ");
					System.out.println( "BEGINFOR");
					break;
				case INCFOR:
					numberLbl = stmt.getOperand2().getNumber();
					label = OperationCode.INCFOR.toString()+String.valueOf(numberLbl);
					writeFile(bw,label+" :");
					System.out.println( "INCFOR");
					break;
				case ENDFOR:
					numberLbl = stmt.getOperand2().getNumber();
					String label = OperationCode.ENDFOR.toString()+ String.valueOf(numberLbl);
					writeFile(bw,label+": ");
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
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"imul $-1, %r10");
					writeFile(bw, "mov %r11, %rax");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "SUBUI");
					break;
				case NOT:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp $1, %r10");
					writeFile(bw,"cmove $0, %rax");
					System.out.println( "NOT"); 	
					break;
				case ADDII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"add %r11, %r10");
					writeFile(bw, "mov %r10, %rax");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "ADDII");
					break;
				case ADDFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"add %r11, %r10");
					writeFile(bw, "mov %r10, %rax");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "ADDFF");
					break;
				case SUBII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"sub %r11, %r10");
					writeFile(bw, "mov %r10, %rax");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "SUBII");
					break;
				case SUBFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"sub %r11, %r10");
					writeFile(bw, "mov %r10, %rax");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "SUBFF");
					break;
				case MULII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"imul %r10, %r11");
					writeFile(bw, "mov %r11, %rax");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "MULII");
					break;
				case MULFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"imul %r10, %r11");
					writeFile(bw, "mov %r11, %rax");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "MULFF");
					break;
				case DIVII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %rax");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r10");
					writeFile(bw,"idiv %r10");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "DIVII");
					break;
				case DIVFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %rax");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r10");
					writeFile(bw,"idiv %r10");
					writeFile(bw, "mov %rax, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "DIVFF");
					break;
				case MODII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %rax");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r10");
					writeFile(bw,"idiv %r10");
					writeFile(bw, "mov %rdx, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "MODII");
					break;
			// Eq operations													
				case EQII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmove $0, %rax");
					System.out.println( "EQII");
					break;
				case EQFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmove $0, %rax");
					System.out.println( "EQFF");
					break;
				case EQBB:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmove $0, %rax");
					System.out.println( "EQBB");
					break;
				case NEQII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovne $0, %rax");
					System.out.println( "NEQII");
					break;
				case NEQFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovne $0, %rax");
					System.out.println( "NEQFF");
					break;
				case NEQBB:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovne $0, %rax");
					System.out.println( "NEQBB");
					break;

			// Relational operations
				case SMALLII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovl $0, %rax");
					System.out.println( "SMALLII");
					break;
				case SMALLFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovl $0, %rax");
					System.out.println( "SMALLFF");
					break;
				case LTOEII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovle $0, %rax");
					System.out.println( "LTOEII");
					break;
				case LTOEFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovle $0, %rax");
					System.out.println( "LTOEFF");
					break;
				case BIGGERII:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovg $0, %rax");
					System.out.println( "BIGGERII");
					break;
				case BIGGERFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovg $0, %rax");
					System.out.println( "BIGGERFF");
					break;
				case GTOEII:
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovge $0, %rax");
					System.out.println( "GTOEII");
					break;
				case GTOEFF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp %r10, %r11");
					writeFile(bw,"cmovge $0, %rax");
					System.out.println( "GTOEFF");
					break;

			// Logical operations			
				case ANDBB:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $0, %rax");
					writeFile(bw,"cmp $0, %r10");
					writeFile(bw,"cmovne $1, %rax");
					writeFile(bw,"cmp $0, %r11");
					writeFile(bw,"cmovne $1, %rax");
					System.out.println( "ANDBB");
					break;
				case ORBB:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand2 = (VarLocation) stmt.getOperand2().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand2.getOff())+"(%rbp), %r11");
					writeFile(bw,"mov $1, %rax");
					writeFile(bw,"cmp $0, %r10");
					writeFile(bw,"cmove $0, %rax");
					writeFile(bw,"cmp $0, %r11");
					writeFile(bw,"cmove $0, %rax");
					System.out.println( "ORBB");
					break;

			// Jump
				case JMPFALSE:
					VarLocation condition = (VarLocation) stmt.getOperand1().getExpression();
					Integer conditionOffSet = condition.getOff();
					label = stmt.getOperand2().getName();
					writeFile(bw,"mov -"+conditionOffSet+"(%rbp),%r11");
					writeFile(bw,"cmp $0, %r11");
					writeFile(bw,"jne "+label);
					System.out.println( "JMPFALSE");
					break;
				/*case JMPFALSEF:
					VarLocation condition = (VarLocation) stmt.getOperand1().getExpression();
					Integer conditionOffSet = condition.getOff();
					String label = stmt.getOperand2().getLabelName();
					writeFile(bw,"mov -"+conditionOffSet+"(%rbp),%r11");
					writeFile(bw,"cmp $0, %r11");
					writeFile(bw,"jg "+label);
					return "JMPFALSEF";
					break;*/
				case JMPTRUE:
					System.out.println( "JMPTRUE");
					break;
				case JMP:
					label = stmt.getOperand2().getName();
					writeFile(bw,"jmp "+label);
					System.out.println( "JMP");
					break;

			// Assign
				case ASSIGNATION:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov %r10, -"+String.valueOf(operand3.getOff())+"(%rbp)");
					System.out.println( "ASSIGNATION");
					break;
				case ASSINCI:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand3.getOff())+"(%rbp), %r11");
					writeFile(bw,"add %r10, %r11");
					writeFile(bw,"mov %r11, -"+String.valueOf(operand1.getOff())+"(%rbp)");
					System.out.println( "ASSINCI");
					break;
				case ASSDECI:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand3.getOff())+"(%rbp), %r11");
					writeFile(bw,"sub %r11, %r10");
					writeFile(bw,"mov %r10, -"+String.valueOf(operand1.getOff())+"(%rbp)");
					System.out.println( "ASSDECI");
					break;
				case ASSINCF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand3.getOff())+"(%rbp), %r11");
					writeFile(bw,"add %r10, %r11");
					writeFile(bw,"mov %r11, -"+String.valueOf(operand1.getOff())+"(%rbp)");
					System.out.println( "ASSINCF");
					break;
				case ASSDECF:
					operand1 = (VarLocation) stmt.getOperand1().getExpression();
					operand3 = (VarLocation) stmt.getOperand3().getExpression();
					writeFile(bw,"mov -"+String.valueOf(operand1.getOff())+"(%rbp), %r10");
					writeFile(bw,"mov -"+String.valueOf(operand3.getOff())+"(%rbp), %r11");
					writeFile(bw,"sub %r11, %r10");
					writeFile(bw,"mov %r10, -"+String.valueOf(operand1.getOff())+"(%rbp)");
					System.out.println( "ASSDECF");
					break;
				case INC:
					System.out.println( "INC");
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
}