package ir.ast;

public enum UnaryOpType {
	MINUS,
	NOT;
	
	@Override
	public String toString() {
		switch(this) {
			
			case MINUS:
				return "-";
			case NOT:
				return "¬";
		}
		
		return null;
	}
}
