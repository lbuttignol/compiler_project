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
	IDDECL		, // IdDecl 	- null			- null				// ver si hay que dividir o no!!!!!11

// Statements
	BEGINIF		, // IfStatement- intLitNumber	- null
	ENDIF		, // IfStatement- intLitNumber	- null
	ELSEIF		, // IfStatement- intLitNumber 	- null
	BEGINFOR	, // ForStat	- intLitNumber	- null
	ENDFOR		, // ForStat	- intLitNumber	- null
	BEGINWHILE	, // WhileStat	- intLitNumber 	- null
	ENDWHILE	, // WhileStat	- intLitNumber	- null
	
// Unary Aruthmetic
	SUBUI		, // 
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
	ASSINCI		, // Variable 	- null			- IntResult
	ASSDECI		, // Variable 	- null			- IntResult
	ASSINCF		, // Variable 	- null			- FloatResult
	ASSDECF		, // Variable 	- null			- FloatResult
	INC 		; // null 		- null			- VarDatoResult

	@Override
	public String toString(){
		switch (this) {

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
				return "LOADPARAM";  /////////////////////////////ver!

			case BEGINIF:
				return "BEGINIF";
			case ENDIF:
				return "ENDIF";
			case ELSEIF:
				return "ELSEIF";
			case BEGINFOR:
				return "BEGINFOR";
			case ENDFOR:
				return "ENDFOR";
			case BEGINWHILE:
				return "BEGINWHILE";
			case ENDWHILE:
				return "ENDWHILE";
			
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

			case EQII:
				return "EQII";
			case EQFF:
				return "EQFF";
			case EQBB:
				return "EQBB";
			case NEQII:
				return "NEQII";
			case NEQFF:
				return "NEQFF";
			case NEQBB:
				return "NEQBB";

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

			case ANDBB:
				return "ANDBB";
			case ORBB:
				return "ORBB";

			case JMPFALSE:
				return "JMPFALSE";	
			case JMPTRUE:
				return "JMPTRUE";

			case ASSIGNATION:
				return "ASSIGNATION";
				
		}

		return null;
	}
}