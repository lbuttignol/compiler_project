package ir.intermediateCode;

class LoopLabel {
	private LabelType type;
	private Integer labelNum;

	public LoopLabel(LabelType t, Integer num){
		this.type = t;
		this.labelNum = num;
	}

	public void setType(LabelType t){
		this.type = t;
	} 

	public void setLabelNumber(Integer num){
		this.labelNum = num;
	}

	public LabelType getType(){
		return this.type;
	}

	public Integer getLabelNumber(){
		return this.labelNum;
	}

	
}