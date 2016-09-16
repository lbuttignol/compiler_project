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
		if (types==null){
			types = new LinkedList<String>();
			types.add("INT");
			types.add("VOID");
			types.add("FLOAT");
			types.add("BOOLEAN");
			types.add("UNDEFINED");
			types.add("INTARRAY");
			types.add("BOOLARRAY");
			types.add("FLOATARRAY");
		}
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
