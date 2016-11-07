package ir.intermediateCode;

public enum OperationCode {
// Declarations	
	BEGINPROGRAM, // Program 	- null			- null
	ENDPROGRAM  , // Program 	- null			- null
	BEGINCLASS  , // Class 		- null			- null
	ENDCLASS    , // Class 		- null			- null
	FIELD		, // FieldDecl	- type			- null
	BEGINMETHOD , // MethodDecl	- type			- null
	ENDMETHOD	, // MethodDecl	- type			- null
	PARAMDECL 	, // ParamDecl 	- type			- null
	LOADPARAM	, // ParamNAME	- type			- null  
	IDDECL		, // IdDecl 	- null			- null
	ARRAYDECLI	, // ArrayDecl 	- null 			- null
	ARRAYDECLF	, // ArrayDecl 	- null			- null
	ARRAYDECLB 	, // ArrayDecl 	- null			- null
	INTDECL		, // IdDecl 	- null			- null
	FLOATDECL	, // IdDecl 	- null			- null
	BOOLDECL	, // IdDecl 	- null			- null
	OBJDECL     , // IdDecl 	- null			- null
//Locations
	ARRAYLOCI	, // ArrayLocat - index			- Result
	ARRAYLOCF 	, // ArrayLocat - null			- null
	ARRAYLOCB 	, // ArrayLocat - null			- null
	ATTARRAYLOCI, // AttArrayLoc- null			- null
	ATTARRAYLOCF, // AttArrayLoc- null			- null
	ATTARRAYLOCB, // AttArrayLoc- null			- null
	ATTLOCI 	, // AttLoc 	- null			- Temporal
	ATTLOCF 	, // AttLoc 	- null			- Temporal
	ATTLOCB 	, // AttLoc 	- null			- Temporal
	VARLOCI 	, // VarLod 	- null 			- null
	VARLOCF 	, // VarLod 	- null 			- null
	VARLOCB 	, // VarLod 	- null 			- null

// Statements
	BEGINIF		, // IfStatement- intLitNumber	- null
	ENDIF		, // IfStatement- intLitNumber	- null
	ELSEIF		, // IfStatement- intLitNumber 	- null
	BEGINFOR	, // ForStat	- intLitNumber	- null
	INCFOR		, // ForStat	- intLitNumber	- null
	ENDFOR		, // ForStat	- intLitNumber	- null
	BEGINWHILE	, // WhileStat	- intLitNumber 	- null
	ENDWHILE	, // WhileStat	- intLitNumber	- null
	
// Unary Aruthmetic
	SUBUI		, // value null result
	SUBUF		, // 

// UnaryLogic
	NOT			, // 

// arithmetic operations	
	ADDII		, // IntOperand	- IntOperand 	- IntResult
	ADDFF		, // FloOperand	- FloOperand 	- FloResult
	ADDIF		, // IntOperand	- FloOperand	- FloResult
	ADDFI 		, // FloOperand	- IntOperand	- FloResult
	SUBII		, // IntOperand	- IntOperand	- IntResult
	SUBFF		, // FloOperand	- FloOperand	- FloResult
	SUBIF		, // IntOperand	- FloOperand	- FloResult
	SUBFI 		, // FloOperand	- IntOperand	- FloResult
	MULII		, // IntOperand	- IntOperand	- IntResult
	MULFF		, // FloOperand	- FloOperand	- FloResult
	MULIF		, // IntOperand	- FloOperand	- FloResult
	MULFI		, // IntOperand	- FloOperand	- FloResult
	DIVII		, // IntOperand	- IntOperand	- IntResult
	DIVFF		, // FloOperand	- FloOperand	- FloResult
	DIVIF		, // IntOperand	- FloOperand	- FloResult
	DIVFI		, // IntOperand	- FloOperand	- FloResult
	MODII		, // IntOperand - IntOperand	- IntResult

// Eq operations													//COMPARAMOS ENTEROS Y FLOTANTES?????????
	EQII		, // IntOperand	- IntOperand 	- BoolResult
	EQFF		, // FloOperand	- FloOperand 	- BoolReault
	EQIF		, // IntOperand	- FloOperand 	- BoolReault
	EQFI 		, // FloOperand	- IntOperand 	- BoolReault
	EQBB		, // BoolOperand- BoolOperand	- BoolResult
	NEQII		, // IntOperand	- IntOperand	- BoolResult
	NEQFF		, // FloOperand	- FloOperand	- BoolReault
	NEQIF 		, // IntOperand	- FloOperand 	- BoolReault
	NEQFI 		, // FloOperand	- IntOperand 	- BoolReault
	NEQBB		, // BoolOperand- BoolOperand	- BoolResult

// Relational operations
	SMALLII		, // IntOperand	- IntOperand	- BoolResult
	SMALLFF		, // FloOperand	- FloOperand	- BoolResult
	SMALLIF		, // IntOperand	- FloOperand	- BoolResult
	SMALLFI		, // IntOperand	- FloOperand	- BoolResult
	LTOEII		, // IntOperand	- IntOperand	- BoolResult
	LTOEFF		, // FloOperand	- FloOperand	- BoolResult
	LTOEIF		, // IntOperand	- FloOperand	- BoolResult
	LTOEFI		, // IntOperand	- FloOperand	- BoolResult
	BIGGERII	, // IntOperand	- IntOperand	- BoolResult
	BIGGERFF	, // FloOperand	- FloOperand	- BoolResult
	BIGGERIF	, // IntOperand	- FloOperand	- BoolResult
	BIGGERFI	, // IntOperand	- FloOperand	- BoolResult
	GTOEII		, // IntOperand	- IntOperand	- BoolResult
	GTOEFF		, // FloOperand	- FloOperand	- BoolResult
	GTOEIF		, // IntOperand	- FloOperand	- BoolResult
	GTOEFI		, // IntOperand	- FloOperand	- BoolResult

// Logical operations			
	ANDBB		, // BoolOperand- BoolOperand	- BoolResult
	ORBB		, // BoolOperand- BoolOperand	- BoolResult

// Jump
	JMPFALSE	, // BooleanLit	- Label 		- null	
	JMPTRUE		, // BooleanLit	- Label 		- null
	JMP 		, // Label 		- null			- null

// Assign
	ASSIGNATION	, // Variable 	- null			- Result 			//VER SI SE TIENE QIE DIVIDIR O NO°!!!!!""
	ASSIGNCONST , // Variable   - Literal
	ASSIGNATTR  , // Temporal	-				- Variable
	ASSATTINCI	, // Temporal 	- 				- Variable
	ASSATTINCF  , // Temporal   -               - Variable
	ASSIGNARRAY , // ArrayLocat - Index			- Result
	ASSIGNARRAYINCI,// ArrayLocat - Index			- Result
	ASSIGNARRAYINCF,// ArrayLocat - Index			- Result
	ASSINCI		, // Variable 	- null			- IntResult
	ASSDECI		, // Variable 	- null			- IntResult
	ASSINCF		, // Variable 	- null			- FloatResult
	ASSDECF		, // Variable 	- null			- FloatResult
	ASSATTDECI	, // Temporal 	- 				- Variable
	ASSATTDECF  , // Temporal   -               - Variable
	ASSIGNARRAYDECI,// ArrayLocat - Index			- Result
	ASSIGNARRAYDECF,// ArrayLocat - Index			- Result
	INC 		, // null 		- null			- VarDatoResult

// 
	PUSHPARAMS	, // MethodCall - null			- NULL
	PULLPARAMS	, // MethodCall - null			- NULL
	CALL 		, // methodCall - null 			- null 
	CALLOBJ		, // MethodCall - null 			- null
	RET 		, // VarLoc 	- null 			- null 
	RETVOID		; // null 		- null 			- null
	@Override
	public String toString(){
		switch (this) {
			// Declarations	
			case BEGINPROGRAM:
				return "BEGINPROGRAM";			
			case ENDPROGRAM:
				return "ENDPROGRAM";
			case BEGINCLASS:
				return "BEGINCLASS";
			case ENDCLASS:
				return "ENDCLASS";
			case FIELD:
				return "FIELD";
			case BEGINMETHOD:
				return "BEGINMETHOD";
			case ENDMETHOD:
				return "ENDMETHOD";
			case PARAMDECL:
				return "PARAMDECL";
			case LOADPARAM:
				return "LOADPARAM";  
			case IDDECL:
				return "IDDECL";				
			case ARRAYDECLI:
				return "ARRAYDECLI";
			case ARRAYDECLF:
				return "ARRAYDECLF";
			case ARRAYDECLB:
				return "ARRAYDECLB";
			case INTDECL:
				return "INTDECL";
			case FLOATDECL:
				return "FLOATDECL";
			case BOOLDECL:
				return "BOOLDECL";
			
			//Locations
			case ARRAYLOCI:
				return "ARRAYLOCI";
			case ARRAYLOCF:
				return "ARRAYLOCF";
			case ARRAYLOCB:
				return "ARRAYLOCB";
			case ATTARRAYLOCI:
				return "ATTARRAYLOCI";
			case ATTARRAYLOCF:
				return "ATTARRAYLOCF";
			case ATTARRAYLOCB:
				return "ATTARRAYLOCB";
			case ATTLOCI:
				return "ATTLOCI";
			case ATTLOCF:
				return "ATTLOCF";
			case ATTLOCB:
				return "ATTLOCB";
			case VARLOCI:
				return "VARLOCI";
			case VARLOCF:
				return "VARLOCF";
			case VARLOCB:
				return "VARLOCB";

		// Statements
			case BEGINIF:
				return "BEGINIF";
			case ENDIF:
				return "ENDIF";
			case ELSEIF:
				return "ELSEIF";
			case BEGINFOR:
				return "BEGINFOR";
			case INCFOR:
				return "INCFOR";
			case ENDFOR:
				return "ENDFOR";
			case BEGINWHILE:
				return "BEGINWHILE";
			case ENDWHILE:
				return "ENDWHILE";
			
		// Unary Aruthmetic
			case SUBUI:
				return "SUBUI";
			case NOT:
				return "NOT"; 	
			case ADDII:
				return "ADDII";
			case ADDFF:
				return "ADDFF";
			case ADDIF:
				return "ADDIF";
			case ADDFI:
				return "ADDFI";
			case SUBII:
				return "SUBII";
			case SUBFF:
				return "SUBFF";
			case SUBIF:
				return "SUBIF";
			case SUBFI:
				return "SUBFI";
			case MULII:
				return "MULII";
			case MULFF:
				return "MULFF";
			case MULIF:
				return "MULIF";
			case MULFI:
				return "MULFI";
			case DIVII:
				return "DIVII";
			case DIVFF:
				return "DIVFF";
			case DIVIF:
				return "DIVIF";
			case DIVFI:
				return "DIVFI";
			case MODII:
				return "MODII";

		// Eq operations													
			case EQII:
				return "EQII";
			case EQFF:
				return "EQFF";
			case EQIF:
				return "EQIF";
			case EQFI:
				return "EQFI";
			case EQBB:
				return "EQBB";
			case NEQII:
				return "NEQII";
			case NEQFF:
				return "NEQFF";
			case NEQIF:
				return "NEQIF";
			case NEQFI:
				return "NEQFI";
			case NEQBB:
				return "NEQBB";

		// Relational operations
			case SMALLII:
				return "SMALLII";
			case SMALLFF:
				return "SMALLFF";
			case SMALLIF:
				return "SMALLIF";
			case SMALLFI:
				return "SMALLFI";
			case LTOEII:
				return "LTOEII";
			case LTOEFF:
				return "LTOEFF";
			case LTOEIF:
				return "LTOEIF";
			case LTOEFI:
				return "LTOEFI";
			case BIGGERII:
				return "BIGGERII";
			case BIGGERFF:
				return "BIGGERFF";
			case BIGGERIF:
				return "BIGGERIF";
			case BIGGERFI:
				return "BIGGERFI";
			case GTOEII:
				return "GTOEII";
			case GTOEFF:
				return "GTOEFF";
			case GTOEIF:
				return "GTOEIF";
			case GTOEFI:
				return "GTOEFI";

		// Logical operations			
			case ANDBB:
				return "ANDBB";
			case ORBB:
				return "ORBB";

		// Jump
			case JMPFALSE:
				return "JMPFALSE";	
			case JMPTRUE:
				return "JMPTRUE";
			case JMP:
				return "JMP";

		// Assign
			case ASSIGNATION:
				return "ASSIGNATION";
			case ASSIGNCONST:
				return "ASSIGNCONST";
			case ASSIGNARRAY :
				return "ASSIGNARRAY "; 		
			case ASSINCI:
				return "ASSINCI";
			case ASSDECI:
				return "ASSDECI";
			case ASSINCF:
				return "ASSINCF";
			case ASSDECF:
				return "ASSDECF";
			case INC:
				return "INC";
			case PUSHPARAMS:
				return "PUSHPARAMS";
			case CALL:
				return "CALL";
			case RET:
				return "RET";
			case RETVOID:
				return "RETVOID";
				
		}

		return null;
	}
}