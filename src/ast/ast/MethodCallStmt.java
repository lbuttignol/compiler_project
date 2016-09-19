package ir.ast;

public class MethodCallStmt extends Statement{
	MethodCall methodC;

	public MethodCallStmt(MethodCall mc, int line, int col){
		super(line,col);
		this.methodC = mc;
	}

	public MethodCall getMethodCall(){
		return this.methodC;
	}

	public void setMethodCall(MethodCall mc){
		this.methodC = mc;
	}

	public String toString(){
		return this.methodC.toString();
	}
}