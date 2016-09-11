package ir.ast;

public abstract class Location extends Expression {
	protected String id;

	public Location(String a){
		this.setId(a);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public String toString(){
		return id;
	}
}
