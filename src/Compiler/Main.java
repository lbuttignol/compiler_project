package Compiler;

import java_cup.runtime.*;
import ir.intermediateCode.*;
import ir.AsmGenerator.*;
import ir.semcheck.*;
import ir.ast.*;
import java.io.FileInputStream;

public class Main {

	public static void main(String args[]) throws Exception {
		Type type = new Type();

		ComplexSymbolFactory sf = new ComplexSymbolFactory();
		Parser parser;
		Symbol symbol;
		if (args.length==0) parser = new Parser(new Scanner(System.in,sf),sf);
		else parser = new Parser(new Scanner(new java.io.FileInputStream(args[0]),sf),sf);
		symbol = parser.parse();
		String programName = args[0];
		Program program = (Program) symbol.value; 
		program.setProgramName(programName);

		BuilderVisitor builderVisitor = new BuilderVisitor();
		program.accept(builderVisitor);
	    
	    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	    TypeEvaluationVisitor typeEV = new TypeEvaluationVisitor();
	    program.accept(typeEV);

	    System.out.println("START INTERMEDIATE CODE CREATION ************************************");
	    AsmIntermediate intermediateCode = new AsmIntermediate();
	    program.accept(intermediateCode);
	    // System.out.println(intermediateCode.toString());

	    AsmGenerator asm = new AsmGenerator(intermediateCode.getPseudo(),"./",args[0]);
	    asm.execute();

	}		
    public void syntax_error(Symbol sym){ 
	// Mute legacy Error Printing
    }
} 