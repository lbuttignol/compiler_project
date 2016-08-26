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

Comment 			= {TraditionalComment} | {EndOfLineComment}
TraditionalComment 	= "/*" [^*] {CommentContent} "*"+ "/"
EndOfLineComment	= "//" {InputCharacter}* {LineTerminator}

CommentContent 		= ( [ˆ*] | "\*"+ [ˆ"/"*] )*

IntegerLiteral 		= 0 | [1-9][0-9]*
RealLiteral			= {IntegerLiteral} "." {IntegerLiteral} ("e" "-"? {IntegerLiteral} )?
Identifier			= [a-zA-Z][a-zA-Z0-9_]*																	////////////CAMBIAR					

%%

/*	keywords	*/
"bool"			{ System.out.println("BOOL"); }
"break"			{ System.out.println("BREAK"); }
"class"			{ System.out.println("CLASS"); }
"continue"		{ System.out.println("CONTINUE"); }
"else"			{ System.out.println("ELSE"); }
"extern"		{ System.out.println("EXTERN"); }
"false"			{ System.out.println("FALSE"); }
"float"			{ System.out.println("FLOAT"); }
"for"			{ System.out.println("FOR"); }
"if"			{ System.out.println("IF"); }
"integer"		{ System.out.println("INTEGER"); }
"return"		{ System.out.println("RETURN"); }
"true"			{ System.out.println("TRUE"); }
"void"			{ System.out.println("VOID"); }
"while"			{ System.out.println("WHILE"); }

/*	assign operator	*/
"="				{ System.out.println("="); }
"+="			{ System.out.println("+="); }
"-="			{ System.out.println("=="); }

/*	concatenation operator 	*/ 						//ver!!!
";"				{ System.out.println(";"); }

/*	arith operators	*/
"+"				{ System.out.println("+"); }
"-"				{ System.out.println("-"); }
"*"				{ System.out.println("*"); }
"/"				{ System.out.println("/"); }
"%"				{ System.out.println("%"); }

/*		*/											//ver
"--"			{ System.out.println("--"); }
"++"			{ System.out.println("++"); }

/*	eq operators	*/
"=="			{ System.out.println("=="); }
"!="			{ System.out.println("!="); }

/*	rel operators	*/
"<"				{ System.out.println("<"); }
">"				{ System.out.println(">"); }
"<="			{ System.out.println("<="); }
">="			{ System.out.println(">="); }

/*	cond operators	*/
"&&"			{ System.out.println("&&"); }
"||"			{ System.out.println("||"); }
"!"				{ System.out.println("!"); }  		//ver!!!			

/*		*/											//ver!!!
"("				{ System.out.println("("); }
")"				{ System.out.println(")"); }

/*	comments	*/									//NO ESTAN FUNCIONANDO VER
{Comment}	 	{ System.out.println("COMMENT"); }

/*	literals	*/
{IntegerLiteral} { System.out.println("INTEGER NUMBER"); }
{RealLiteral}	{ System.out.println("REAL NUMBER"); }
{Identifier}	{ System.out.println("IDENTIFIER"); }

[ \t\r\n\f] 	{ /* ignore white space. */ }
. 				{ System.err.println("Illegal character: "+yytext()); }
