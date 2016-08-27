package Example;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
%%
%cup
%line
%column
%char
%class Scanner
%{
	public Scanner(java.io.InputStream r, ComplexSymbolFactory sf){
		this(r);
		this.sf=sf;
	}
	StringBuffer string = new StringBuffer();
	public Symbol symbol(String plaintext,int code){
	    return sf.newSymbol(plaintext,code,new Location("",yyline+1, yycolumn +1,yychar), new Location("",yyline+1,yycolumn+yylength(),yychar));
	}
	public Symbol symbol(String plaintext,int code,Integer number){
	    return sf.newSymbol(plaintext,code,new Location("",yyline+1, yycolumn +1,yychar), new Location("",yyline+1,yycolumn+yylength(),yychar),number);
	}
	private ComplexSymbolFactory sf;
%}
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}

LineTerminator 		= \r|\n|\r\n 
InputCharacter		= [ˆ\r\n]

IntegerLiteral 		= 0 | [1-9][0-9]*
RealLiteral			= {IntegerLiteral} "." {IntegerLiteral} ("e" "-"? {IntegerLiteral} )?
Identifier			= [a-zA-Z][a-zA-Z0-9_]*																	////////////CAMBIAR					

%state COMMENTSMULTILINE
%state COMMENTSENDOFLINE

%%

/*	keywords	*/

<YYINITIAL>{
"bool"			{ System.out.print("BOOL"); }
"break"			{ System.out.print("BREAK"); }
"class"			{ System.out.print("CLASS"); }
"continue"		{ System.out.print("CONTINUE"); }
"else"			{ System.out.print("ELSE"); }
"extern"		{ System.out.print("EXTERN"); }
"false"			{ System.out.print("FALSE"); }
"float"			{ System.out.print("FLOAT"); }
"for"			{ System.out.print("FOR"); }
"if"			{ System.out.print("IF"); }
"integer"		{ System.out.print("INTEGER"); }
"return"		{ System.out.print("RETURN"); }
"true"			{ System.out.print("TRUE"); }
"void"			{ System.out.print("VOID"); }
"while"			{ System.out.print("WHILE"); }

/* Comments */
"/*"			{ System.out.print("/*");yybegin(COMMENTSMULTILINE); }
"//"			{ System.out.print("//");yybegin(COMMENTSENDOFLINE); }

/*	assign operator	*/
"="				{ System.out.print("="); }
"+="			{ System.out.print("+="); }
"-="			{ System.out.print("=="); }

/*	concatenation operator 	*/ 						//ver!!!
";"				{ System.out.print(";"); }

/*	arith operators	*/
"+"				{ System.out.print("+"); }
"-"				{ System.out.print("-"); }
"*"				{ System.out.print("*"); }
"/"				{ System.out.print("/"); }
"%"				{ System.out.print("%"); }

/*		*/											//ver
"--"			{ System.out.print("--"); }
"++"			{ System.out.print("++"); }

/*	eq operators	*/
"=="			{ System.out.print("=="); }
"!="			{ System.out.print("!="); }

/*	rel operators	*/
"<"				{ System.out.print("<"); }
">"				{ System.out.print(">"); }
"<="			{ System.out.print("<="); }
">="			{ System.out.print(">="); }

/*	cond operators	*/
"&&"			{ System.out.print("&&"); }
"||"			{ System.out.print("||"); }
"!"				{ System.out.print("!"); }  		//ver!!!			

/*		*/											//ver!!!
"("				{ System.out.print("("); }
")"				{ System.out.print(")"); }

/*	literals	*/
{IntegerLiteral} { System.out.print("INTEGER NUMBER"); }
{RealLiteral}	{ System.out.print("REAL NUMBER"); }
{Identifier}	{ System.out.print("IDENTIFIER"); }

[ \t\r\f] 		{ /* ignore white space. */ }
[\n]			{ System.out.println(""); }
. 				{ System.err.print("Illegal character: "+yytext()); }

}

<COMMENTSMULTILINE> {
"*/"  			{ System.out.print("*/");yybegin(YYINITIAL); }
{LineTerminator} { System.out.println(""); }
. 				{ System.out.print(yytext()); }
}


<COMMENTSENDOFLINE> {
{LineTerminator} { System.out.println("");yybegin(YYINITIAL); }
. 				{ System.out.print(yytext()); }
}

/* error fallback */
[^]                              {  System.err.print("Illegal character: "+yytext()); }
