package ir.ast;

import java.util.LinkedList;
import java.util.List;
import ir.ASTVisitor;

public class Block extends Statement {
	private List<FieldDecl> variables;
	private List<Statement> statements;
	private int blockId;
	
	public Block(int bId, int line, int col){
		super(line,col);
		blockId = bId;
		variables = new LinkedList<FieldDecl>();
		statements = new LinkedList<Statement>();
	}
	
	/*
	public Block(int bId, List<Statement> s) {
		blockId = bId;
		statements = s;
	}

	public Block(int bId, List<FieldDecl> v){
		variables = v;
		blockId = bId;
	}
	*/
	
	public Block(int bId, List<FieldDecl> v, List<Statement> s, int line, int col){
		super(line,col);
		variables = v;
		statements = s;
		blockId = bId;
	}
	
	public void addVariable(FieldDecl v) {
		this.variables.add(v);
	}

	public List<FieldDecl> getVariable(){
		return this.variables;
	}

	public void addStatement(Statement s) {
		this.statements.add(s);
	}
		
	public List<Statement> getStatements() {
		return this.statements;
	} 
		
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@Override
	public String toString() {
		String rtn = "";
		
	    for (Statement s: statements) {
			rtn += s.toString() + '\n';
		}
		
		if (rtn.length() > 0) return rtn.substring(0, rtn.length() - 1); // remove last new line char
		
		return rtn; 
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
	
}
