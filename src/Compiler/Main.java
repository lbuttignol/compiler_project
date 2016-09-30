package Compiler;

import java_cup.runtime.*;
import ir.semcheck.*;
import ir.ast.*;

public class Main {

	public static void main(String args[]) throws Exception {
	Type type = new Type();

	ComplexSymbolFactory sf = new ComplexSymbolFactory();
	Parser parser;
	Symbol symbol;
	//String nameFile = 
	if (args.length==0) parser = new Parser(new Scanner(System.in,sf),sf);
	else parser = new Parser(new Scanner(new java.io.FileInputStream(args[0]),sf),sf);
	
	symbol = parser.parse();

	Program program = (Program) symbol.value; 

	BuilderVisitor builderVisitor = new BuilderVisitor();
	//program.accept(builderVisitor);
    
    System.out.println("**********************************************************");

    TypeEvaluationVisitor typeEV = new TypeEvaluationVisitor();
    program.accept(typeEV);

	}	
    public void syntax_error(Symbol sym){ 
	// Mute legacy Error Printing
    }
} 