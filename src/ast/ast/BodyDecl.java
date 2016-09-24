package ir.ast;
import ir.ASTVisitor;
import java.util.List;
public class BodyDecl extends AST{
	private Boolean isExtern;
	private Block block;

	public BodyDecl(Block block, int line, int col){
		super(line,col);
		this.block = block;
		this.isExtern = false;
	}

	public BodyDecl(Boolean isExtern, int line, int col){
		super(line,col);
		this.block = null;
		this.isExtern = isExtern;
	}

	public boolean isExtern(){
		return this.isExtern;
	}

	public void setExtern(Boolean isExtern){
		if (this.block!=null){
			// throw new ....
		}
		this.isExtern = isExtern;
	}

	public Block getBlock(){
		return this.block;
	}

	public void setBlock(Block block){
		if (isExtern){
			// throw new IllegalArgument ...
		}
		this.block = block;
	}
	
	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}
