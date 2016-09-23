package ir.ast;
import ir.ASTVisitor;
import java.util.List;
public class ClassDecl extends Declaration{
	private List<FieldDecl> attributes;
	private List<MethodDecl> methods;

	public ClassDecl (String name,List<FieldDecl> attributes, List<MethodDecl> methods, int line, int col){
		super(line,col,name);
		this.attributes = attributes;
		this.methods    = methods;
	}


	public List<FieldDecl> getAttributes(){
		return this.attributes;
	}

	public void setAttributes(List<FieldDecl> atts){
		this.attributes = atts;
	}

	public List<MethodDecl> getMethods(){
		return this.methods;
	}

	public void setMethods(List<MethodDecl> meth){
		this.methods = meth;
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
