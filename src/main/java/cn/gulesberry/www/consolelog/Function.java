package cn.gulesberry.www.consolelog;

public class Function {
	private String field;
	private String[] args;
	
	Function(String field,String[] args){
		this.field = field;
		this.args = args;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
	
}
