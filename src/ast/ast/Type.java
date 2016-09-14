package ir.ast;
import java.util.List;
import java.util.LinkedList;

public class Type{

	private static List<String> types;

	public Type(){
		this.types = new LinkedList<String>();
		this.types.add("INT");
		this.types.add("VOID");
		this.types.add("FLOAT");
		this.types.add("BOOLEAN");
		this.types.add("UNDEFINED");
		this.types.add("INTARRAY");
		this.types.add("BOOLARRAY");
		this.types.add("FLOATARRAY");
	}

	public static void add (String type){
		if (!(types.contains(type.toUpperCase()))){
			add(type.toUpperCase());
		}else{
			// throw new Exception("Ya existe "+type);
		}
	}

	public static void delete(String type){
		if (types.contains(type.toUpperCase())){
			types.remove(type);
		}
	}


	public String toString(){
		return types.toString();
	}
}
