package ir.ast;

import java.util.List;
public class BodyDecl extends AST{
	private Boolean isExtern;
	private BodyDecl body;

	public BodyDecl(BodyDecl body){
		this.body = body;
		this.isExtern = false;
	}

	public BodyDecl(Boolean isExtern){
		this.body = null;
		this.isExtern = isExtern;
	}

	public boolean isExtern(){
		return this.isExtern;
	}

	public void setExtern(Boolean isExtern){
		if (this.body!=null){
			// throw new ....
		}
		this.isExtern = isExtern;
	}

	public BodyDecl getBody(){
		return this.body;
	}

	public void setBody(BodyDecl body){
		if (isExtern){
			// throw new IllegalArgument ...
		}
		this.body = body;
	}
	
}