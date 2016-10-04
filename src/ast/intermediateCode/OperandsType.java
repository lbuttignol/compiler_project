package ir.intermediateCode;

public enum OperandsType {
	
	II, // Int op Int
	IF, // Int op Float
	IB, // Int op Bool
	FF, // Float op Float
	FI, // Float op Int
	FB, // Float op Bool
	BB, // Bool op Bool
	BI, // Bool op Int
	BF; // Bool op Float

	@Override
	public String toString(){
		switch (this){
			case II:
				return "Int op Int";
			case IF:
				return "Int op Float";
			case IB:
				return "Int op Bool";
			case FF:
				return "Float op Float";
			case FI:
				return "Float op Int";
			case FB:
				return "Float op Bool";
			case BB:
				return "Bool op Bool";
			case BI:
				return "Bool op Int";
			case BF:	
				return "Bool op Float";
		}
		return null;
	}
}