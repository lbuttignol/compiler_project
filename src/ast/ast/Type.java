package ir.ast;

public enum Type {
	INT,
	INTARRAY,
	FLOAT,
	BOOLEAN,
	VOID,
	UNDEFINED;

	@Override
	public String toString() {
		switch(this) {
			case INT:
				return "int";
			case VOID:
				return "void";
			case FLOAT:
				return "float";
			case BOOLEAN:
				return "boolean";
			case UNDEFINED:
				return "undefined";
			case INTARRAY:
				return "int[]";
		}
		
		return null;
	}
	
	public boolean isArray() {
		if (this == Type.INTARRAY) {
			return true;
		}
		
		return false;
	}
}
/*
package ir.ast;

import java.util.List;
import java.util.LinkedList;

public class Type {
	private static List<String> id;

	public void Type(){
		this.id = new LinkedList<String>();
		this.id.add("bool");
		this.id.add("boolArray");
		this.id.add("float");
		this.id.add("floatArray");
		this.id.add("integer");
		this.id.add("integerArray");
		this.id.add("void");
		this.id.add("undef");

	}

	
}
*/