package ir.ast;

public enum BinOpType {
	PLUS, // Arithmetic
	MINUS,
	TIMES,
	DIV,
	MOD,
	SMALL, // Relational
	LTOE,
	BIGGER,
	GTOE,
	DISTINCT, // Equal
	EQUAL, 
	AND, // Conditional
	OR;
	
	@Override
	public String toString() {
		switch(this) {
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case TIMES:
				return "*";
			case DIV:
				return "/";
			case MOD:
				return "%";
			case SMALL:
				return "<";
			case LTOE:
				return "<=";
			case BIGGER:
				return ">";
			case GTOE:
				return ">=";
			case EQUAL:
				return "==";
			case DISTINCT:
				return "!=";
			case AND:
				return "&&";
			case OR:
				return "||";
		}
		
		return null;
	}
}
