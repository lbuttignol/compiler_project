package Compiler;

import java_cup.runtime.*;
public class Main {

	public static void main(String args[]) throws Exception {
	ComplexSymbolFactory sf = new ComplexSymbolFactory();
	if (args.length==0) new Parser(new Scanner(System.in,sf),sf).parse();
	else new Parser(new Scanner(new java.io.FileInputStream(args[0]),sf),sf).parse();
    }

    public void syntax_error(Symbol sym){ 
	// Mute legacy Error Printing
    }
} 