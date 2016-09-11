package ir.error;

public class Error{
	private String err;

	public Error(){
		this.err="";
	}

	public Error(int line, int column, String description){
		this.err="Error at line: "+ line +", Column: "+ column+". Description: "+ description+ ".";
		System.out.println(err);
	}
}