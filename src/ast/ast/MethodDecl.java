package ir.ast;
import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;

public class MethodDecl extends Declaration{
	private String type;
	private List<ParamDecl> params;
	private BodyDecl body;

	public MethodDecl( String type,String name,List<ParamDecl> params, 
					   BodyDecl body, int line, int col){
		super(line,col,name);
		this.type      = type.toUpperCase();
		this.params    = params;
		this.body      = body;

	}

	public MethodDecl( String type,String name, 
					   BodyDecl body, int line, int col){
		super(line,col,name);
		this.type      = type.toUpperCase();
		this.params    =  new LinkedList<ParamDecl>();
		this.body      = body;

	}

	public String getType(){
		return this.type;
	}

	public void setType(String t){
		this.type = t;
	}

	public List<ParamDecl> getParams(){
		return this.params;
	}

	public void setParams(List<ParamDecl> p){
		this.params = p;
	}

	public BodyDecl getBody(){
		return this.body;
	}

	public void setBody(BodyDecl b){
		this.body = b;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
