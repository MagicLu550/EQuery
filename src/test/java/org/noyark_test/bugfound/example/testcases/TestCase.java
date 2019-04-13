package org.noyark_test.bugfound.example.testcases;

//import cn.gulesberry.www.core.Core;
import cn.gulesberry.www.helper.InstanceQueryer;
import cn.gulesberry.www.test.TestCore;
import net.noyark.www.annotations.RuntimeAfter;
import net.noyark.www.annotations.RuntimeBefore;
import net.noyark.www.annotations.RuntimeTest;
import net.noyark.www.interf.XMLDomFile;
@RuntimeTest
public class TestCase{
	public static void main(String[] args) {
		TestCore.start();
		//Core.startEPathShell();
	}
	public static void setBool(Boolean b) {
		b = true;
	}
	@RuntimeBefore
	public void init() {
	}
	
	@RuntimeTest
	public void Test() throws Exception{
		XMLDomFile xmlDomFile = InstanceQueryer.getDefaultXml("x.xml",this);
		xmlDomFile.excuteEPathShell("sh equery.func");
		xmlDomFile.excuteEPathShell("use in x.xml");
		xmlDomFile.excuteEPathShell("print select indexs in pathes  *");
		//xmlDomFile.excuteEPathShell("alias<name> select in *");
		//xmlDomFile.excuteEPathShell("print select in alias name");
		xmlDomFile.excuteEPathShell("a();");
		//xmlDomFile.excuteEPathShell("print d();");
	}
	@RuntimeAfter
	public void despory() {
		
	}
}
