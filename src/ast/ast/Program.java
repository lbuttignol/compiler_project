package ir.ast;
import ir.ASTVisitor;
import java.util.List;

public class Program extends AST{
	private String programName;
	private List<ClassDecl> classDecl;

	public Program (List<ClassDecl> classDecl,String progName,int line, int col){
		super(line,col);
		this.classDecl = classDecl;
		this.programName = progName; 
	}

	public List<ClassDecl> getClassDeclare(){
		return this.classDecl;
	}

	public void setClassDeclare(List<ClassDecl> classDecl){
		this.classDecl = classDecl;
	}

	public void setProgramName(String pn){
		this.programName = pn;
	}

	public String getProgramName(){
		return this.programName;
	}

	public String toString(){
		return "";
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
