package cn.gulesberry.www.consolelog;

public class PrintCase {
	private String lang;
	private boolean justPrint;
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public boolean isJustPrint() {
		return justPrint;
	}
	public void setJustPrint(boolean justPrint) {
		this.justPrint = justPrint;
	}
	public PrintCase(String lang, boolean justPrint) {
		super();
		this.lang = lang;
		this.justPrint = justPrint;
	}
	
}
