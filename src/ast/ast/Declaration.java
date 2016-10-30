package ir.ast;

public abstract class Declaration extends AST{
	
	protected String name;
	private int offset = 0;


	public Declaration(int lineNumber, int columnNumber, String name){
		super(lineNumber,columnNumber);
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String toString(){
		return name;
	}

	public void setOff (Integer off){
		this.offset=off;
	}

	public int getOff(){
		return this.offset;
	}

}