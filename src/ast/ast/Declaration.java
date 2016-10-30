package ir.ast;

public abstract class Declaration extends AST{
	
	protected String name;
	private Integer offset;

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

	public void setOffset(Integer val){
		this.offset = val;
	}

	public Integer getOffset(){
		return this.offset;
	}

}