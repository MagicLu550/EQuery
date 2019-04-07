[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)
![LICENSE](https://img.shields.io/badge/license-Noyark-green.svg)
![LICENSE](https://img.shields.io/badge/version-0.3.0NEW-blue.svg)
![Build](https://img.shields.io/badge/build-passing-green.svg)
[![en](https://img.shields.io/badge/readme-english-orange.svg)](README.md)

![logo](logo.jpeg)
 * 基本介绍
* 这个`xml框架`简化了持久层配置文件的操作，并支持对象的IOC,大大简化了操作，同时兼容平常`dom4j`的操作，可以使框架与传统方式并行使用(当然，框架里面已经提供了相应的方法，必须使用框架的方法添加元素，但这些方法里面有框架的简化操作，也有原有的操作。因为在框架添加元素才能将元素注册到映射工厂，并兼容选择器的操作)，并且添加了选择器功能，有完善的元素选择机制，选择器::具备6大特性:: 
`动态创建（DC）` `兼同读写(RW)` `反路控制(RPC) ` `自动装配（AA）` ` 选择转换（SC）` `对象池（OP）` `搜包实例（SPI）`
---

* DC：
 中文（dong tai chuang jian），意思是可以动态的创建对象，并根据配置文件多种形式创建，非常灵活
* RW：
中文（jian tong du xie）,意思是可以兼容已经写过的文档的现成新添加的元素，不会刷新原来创建的文档。
* RPC：
 中文（fan lu kong zhi），意思是通过路径和反射制定元素的规则，从而简单的实现元素的添加操作
* AA：
 中文（zi dong zhuang pei），意思是可以通过加载框架驱动自动装配maven依赖
* SC：
 中文（xuan ze zhuan huan），内置转换器可以将dom4j的Element对象转化为记录元素几何信息的SElement，然后用于选择器散列查询元素
* OP：
中文（dui xiang chi），对象池可以存储原来的对象，先前创建过的对象可以被反复利用
* SPI：
 中文（sou bao shi li），通过配置文件的包路径，搜索到带有注解的类，并依据注解实例化对象，同时一次性创建文件
```java
 //EQuery中的开发使用对象：
/**
	从028版本开始，必须使用这个方法加载驱动，全部驱动信息在
	driver.properties
*/
Core.start();

/*
使用过程中，XMLDomFile的工厂方法的文件目录，可以省略.xml,第二个参数是root元素，如果直接已经创建过相同的对象，还要再次使用读取文档，可以省略这个参数，即：
getXMLQuery("a",false)即可，第三个是是否开启线程安全类型
如果不是要创建新文档而是使用已经存在的文档，并且不想去除原有的元素，则可以使用getDefaultXml方法，之后会保留原有元素，并且可以获取到，后期添加只会刷新 新添加的元素，直接填写file属性，当然，如果有特殊的路径，可以在第二个参数加入输入流，第一个参数加尽量明确的路径信息，大部分特殊路径为读取resources文件夹，那么第一个路径可以用getClass那种获取路径
*/
XMLDomFile xdf = InstanceQueryer.getXMLQuery("a.xml","root"，false);

XMLDomFile xdf = InstanceQueryer.getXMLQuery("a",this);
/*
同时开放了自动装配的开发接口,其作用是如果您的jar包被导入后，需要依赖库，则可以使用这个方法，并提供他们xml配置文件，该方法会自动读取并加载到pom.xml
*/
SetMavenJar.getInstance().setDefaultMaven(this,"此处填写配置文件名，格式和System.Maven.xml");
/*
通过反射进行文件创建
反射创建有两种方法，一种类加注解，一种类不加注解
但都需要继承一个ReadingXML类
readingXML有很多的构造方法，分别对应以上列举的四种构造器（getDefaultXML，getXMLQuery...）
这里使用getXMLQuery演示
*/
//无注解类型
class Test1  extends ReadingXML{
public Test1(String file,String root){
super(file,root);
}
@path("a.b.c.d")
public String A = "1";
}
/*
实例化后，然后调用save方法
可以实现创建文件和xml元素构建

此外还有EQuery表达式:

注意包含’:’

addnew: 同addElements的功能，不需要index属性,可以省略

addchild: 同addChild的功能，需要加indexs属性:indexs={}

addchilds:同addElementsIfExistsParent的功能，需要indexs属性

addelements:同addElements的功能,不需要indexs属性

案例:
*/
 @Path(path = "a.b.c")

 public String aString = "1";

 @Path(path="addchild:a.b.c",indexs= {0,0})

 public String ab = "2";

 

 @Path(path="addchilds:a.b.c/d/e",indexs= {0,0})

 public String[] abs = {"1","2","3"};

 

 @Path(path="addelements:a.b.c/d/e",indexs= {0,0})

 public String[] absd = {"1","2","3"};

//031版本开始，支持全部数组类型
```
```java
/*
此外，有注解形式是这样的
*/
package simpleXML.test;


import cn.gulesberry.www.reflect.ReadingXML;
import net.noyark.www.annocations.Path;
import net.noyark.www.annocations.XMLFile;

@XMLFile(fileName="a.xml",root="root")
public class Test extends ReadingXML{
	
	@Path(path = "a.b.c")
	public String aString = "1";
	
	@Path(path="addchild:a.b.c",indexs= {0,0})
	public String ab = "2";
	
	@Path(path="addchilds:a.b.c/d/e",indexs= {0,0})
	public String[] abs = {"1","2","3"};
	
	@Path(path="addelements:a.b.c/d/e",indexs= {0,0})
	public String[] absd = {"1","2","3"};
	
	@Path(path="addelements:a.b.c/d/e",indexs= {0,0})
	public String[] absd1 = {"1","2","3"};

}
```
> _另外在自带的startXML配置文件中，这样写的_
> 030版本后，支持只写父包的路径，子包路径默认被扫描
```java
<xml-instance>
	<!-- Test-Class -->
	<xml-file package="simpleXML.test"/>
</xml-instance>
```
如此配置即可
> 另外，自动装配对象可以如此装配，依然在xmlStart配置文件
```xml
<xml-instance>
<!-- 预定义对象 -->
	<xml>
		<file name="default"></file>
		<root name="root" ></root>
		<is-sync state="false"></is-sync>
	</xml>
</xml-instance>
```

之后只需要使用
```java
XMLDomFile xdf = InstanceQueryer.getXMLQuery("default",this);
```
获取对象即可
HOW TO USE EPATH
> 如何使用epath xml选择语言
* epath分为5个语言结构：
1. 通配表达式
	通配表达式有一个关键字:all关键字，使用该关键字可以获取全部的Element对象
2. 一维表达式
```xml
	该表达式语法:
		other [selector type]

	selector type是附属选择类型，有以下附属选择类型
	one 选择一个 示例:										a[0].b[0] [one];
	elements 选择多个 				  					a[0].b[0].c [elements];
	on 选择该元素的上面的一个元素(同级)
 			a[0].b[0].c[0] [on];
	under 选择该元素的下面的一个元素(同级)	
	a[0].b[0].c[0] [under];
	ons 选择该元素的上面的全部元素(同级) 
		a[0].b[0].c[0] [ons];
	unders 选择该元素的下面的全部元素(同级) 
	a[0].b[0].c[0] [unders];
	friends 选择全部同级元素                       	a[0].b[0].c[0] [friends];
	ids			通过ID属性选择元素 					IDVALUE [ids];					
	e_name  通过element元素的名字选择 		ELEMENTNAME	[e_name];
	text    通过element对象的text选择 		ELEMENTTEXT [text];
	parent	获取父元素									a[0].b[0].c[0] [parent]
	name		通过名字获取元素							name [name]
	案例:
	<root>
		<a>
			<b></b>
			<b></b>
		</a>
	</root>
	选择名字为a的元素:
		a [name]
	
```
3. .二维表达式
```xml
	该表达式语法:
		path/name middle [middleType];
	middle是附属选择限制，middleType是附属选择限制类型
	有以下附属选择限制类型:
		text			通过text和path进行选择元素		a[0].b[0].c[0] ExampleText [text];
		namespace	通过namespace进行选择元素		a[0].b[0].c[0] prefix,uri [namespace];
		key 通过attribute的key进行选择，支持多个key	a[0].b[0].c[0] key1,key2,key3 [key];
		value	通过attribute的value进行选择，支持多个value a[0].b[0].c[0] value1,value2 [value];
		key=value 通过attribute的key和value进行选择，支持多个key和value a[0].b[0].c[0] key1=value1,key2=value2 [key=value];
		key,only 通过attribute的key进行选择，这里only关键字的作用是获得element的元素必须和规定的key一一对应，不加only,则只要含有规定的即可 ，语法同上
		value,only 同上	
		text,name 通过text和element的name进行选择，a exmapleText [text,name];
		uri 通过命名空间的uri进行查询	 a[0].b[0].c[0] prefix [prefix];	 
		prefix 通过命名空间的prefix进行查询 a[0].b[0].c[0] uri [uri];
```
4. 多维表达式
```
	多维表达式可以查询更为复杂的条件,这里关键字基本可以随意拼接，除了
	语法:
	?为可有可无
	path/name middle... [name/path,?only,middleType...]
	name和only关键字必须在前面，且name在only前面
	这里的关键字
	path
	name
	namespace
	prefix
	uri
	only
	key
	value
	基本作用相同
```
5. 关键字
```
	one elements on under ons unders friends ids e_name text parent name
	namespace key value only uri prefix path  
```

最终需要提醒的是，您必须使用maven项目导入该插件，最后，配置文件在src/main/resources/目录，您需要将里面配置文件复制到您的目录下即可.

如何导入该插件？
```xml
<!-- 链接jar -->
<dependency>
  <groupId>net.noyark</groupId>
  <artifactId>equery-framework</artifactId>
  <version>0.3.1.2</version>
</dependency>
<!--链接私服-->
<repositories>
 <repository>
    <id>nexus</id>
    <name>Team Neux Repository</name><url>http://www.noyark.net:8081/nexus/content/groups/public/</url>
</repositories>
<pluginRepositories>
    <pluginRepository>
      <id>nexus</id>
      <name>Team Neux Repository</name>
      <url>http://www.noyark.net:8081/nexus/content/groups/public/</url>
    </pluginRepository>
 </pluginRepositories>

```

