package org.noyark_test.bugfound.example.testcases;




import org.dom4j.Element;

import cn.gulesberry.www.core.Core;
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
	}
	@RuntimeBefore
	public void init() {
		System.out.println("初始化完成");
	}
	
	//TODO 解决配置文件路径问题
	//TODO XSD文件
	@RuntimeTest
	public void Test() throws Exception{
		Core.start();
		XMLDomFile xdf = InstanceQueryer.getXMLQuery("x.xml",true);
		System.out.println(xdf);
	}
	@RuntimeAfter
	public void dead() {
		System.out.println("结束");
	}
}
