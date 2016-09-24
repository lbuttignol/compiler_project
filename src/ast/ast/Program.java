package ir.ast;
import ir.ASTVisitor;
import java.util.List;

public class Program extends AST{
	private List<ClassDecl> classDecl;

	public Program (List<ClassDecl> classDecl,int line, int col){
		super(line,col);
		this.classDecl = classDecl;
	}

	public List<ClassDecl> getClassDeclare(){
		return this.classDecl;
	}

	public void setClassDeclare(List<ClassDecl> classDecl){
		this.classDecl = classDecl;
	}

	public String toString(){
		return "";
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
