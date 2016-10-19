package ir.ast;
import java.util.List;
import java.util.LinkedList;

public class Type{

	private static List<String> types;
	private final  static int NATIVE_TYPE_SIZE = 5;
	public Type(){
		this.types = new LinkedList<String>();
		this.types.add("INTEGER");
		this.types.add("VOID");
		this.types.add("FLOAT");
		this.types.add("BOOLEAN");
		this.types.add("UNDEFINED");
	}

	public static void add (String type){
		if (types==null){
			types = new LinkedList<String>();
			types.add("INTEGER");
			types.add("VOID");
			types.add("FLOAT");
			types.add("BOOLEAN");
			types.add("UNDEFINED");
		}else{
			if (!(types.contains(type.toUpperCase()))){
				types.add(type.toUpperCase());
			}else{
				// throw new Exception("Ya existe "+type);
			}
		}
	}

	public static boolean contains(String type){
		return types.contains(type.toUpperCase());
	}

	public static void delete(String type){
		if (types.contains(type.toUpperCase())){
			types.remove(type);
		}
	}

	public static boolean isNativeType(String type){
		return types.subList(0,NATIVE_TYPE_SIZE).contains(type.toUpperCase());
	}
	public String toString(){
		return types.toString();
	}
}
