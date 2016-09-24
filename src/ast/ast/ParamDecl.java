package ir.ast;
import ir.ASTVisitor;
import java.util.List;
public class ParamDecl extends Declaration{
	private String type;

	public ParamDecl(String type, String name, int line, int col){
		super(line,col,name);
		this.type = type.toUpperCase();
	}

	public String getType(){
		return this.type;
	}

	public void setType(String t){
		this.type = t;
	} 

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
