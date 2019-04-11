package org.noyark_test.bugfound.example.testcases;

import cn.gulesberry.www.core.Core;
import net.noyark.www.annotations.RuntimeAfter;
import net.noyark.www.annotations.RuntimeBefore;
import net.noyark.www.annotations.RuntimeTest;
@RuntimeTest
public class TestCase{
	public static void main(String[] args) {
		//TestCore.start();
		Core.startEPathShell();
	}
	public static void setBool(Boolean b) {
		b = true;
	}
	@RuntimeBefore
	public void init() {
	}
	
	@RuntimeTest
	public void Test() throws Exception{
	}
	@RuntimeAfter
	public void despory() {
		
	}
}
