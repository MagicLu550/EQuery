package org.noyark_test.bugfound.example.testcases;


import cn.gulesberry.www.reflect.ReadingXML;
import net.noyark.www.annotations.After;
import net.noyark.www.annotations.Path;
import net.noyark.www.annotations.XMLFile;

@XMLFile(fileName="x.xml",root="root")
public class Test extends ReadingXML{
	@Path("a.b.c")
	public String aString = "1";
	
	@Path(path="addchild:a.b.c",indexs= {0,0})
	public String ab = "2";
	
	@Path(path="addchilds:a.b.c/d/e",indexs= {0,0})
	public String[] abs = {"1","2","3"};
	
	@Path(path="addelements:a.b.c/d/e",indexs= {0,0})
	public String[] absd = {"1","2","3"};
	
	@Path(path="addelements:a.b.c/d/e",indexs= {0,0})
	public String[] absd1 = {"1","2","3"};
	
	@Path("a")
	@After(1)
	public String after = "1";
}
