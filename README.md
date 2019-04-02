![avatar](ui.jpeg)
![avatar](logo.jpeg)
# *EQuery 029 final*
## The Java xml framework based on dom4j
### the basic introduction
* This xml framework simplifies the operation of the persistence layer configuration file and supports the object's IOC, which greatly simplifies the operation and is compatible with the usual dom4j operations. It can be used in parallel with the traditional method (of course, the corresponding method has been provided in the framework. You must use the methods of the framework to add elements, but these methods have simplified operations on the framework, as well as the original operation. Because the elements are added to the framework to register the elements to the mapping factory and are compatible with the operation of the selector, and the selector is added. Function, complete element selection mechanism, selector ::with *  6 features *:: ：
dynamic creation (DC) combined with read and write (RW) reflection path control (RPC) automatic assembly (AA) selection and converter (SC) object pool (OP) , search package instance (SPI)
* DC:
 Chinese (::动态创建::), meaning that objects can be created dynamically and created according to various forms of configuration files, which is very flexible
---

* RW:
 Chinese (::兼同读写::) means that it can be compatible with the newly added elements of the document that has been written, and will not refresh the original created document.
* RPC:
 Chinese (::反路控制::), meaning the rules of the elements are created by path and reflection, thus simply adding elements
* AA:
 Chinese (::自动装配::), meaning that the maven dependencies can be automatically assembled by loading the framework driver.
* SC:
Chinese (::选择转换::), built-in converter can convert dom4j Element object into SElement recording element geometry information, and then used for selector hash query element
* OP:
 Chinese (::对象池::), the object pool can store the original object, the previously created object can be reused
* SPI:
 Chinese (::搜包实例::）, through the package path of the configuration file, search for the class with annotations, instantiate the object according to the annotation, and create the file at one time
---



### Code:
This is the simple code 
```java
// Development use object in EQuery:
Core.start();
/*
In the process of use, the file directory of the factory method of XMLDomFile can omit the .xml. The second parameter is the root element. If the same object has been created directly, and the document is read again, the parameter can be omitted, namely:
getXMLQuery("a",false), the third is whether to enable thread safety type
If you don't want to create a new document and use an existing document, and you don't want to remove the original element, you can use the getDefaultXml method, then retain the original element, and you can get it. Later addition will only refresh the newly added element. Fill in the file attribute directly, of course, if there is a special path, you can add the input stream in the second parameter, the first parameter plus the path information as clear as possible, most of the special path is to read the resources folder, then the first path Can get the path with getClass
*/
XMLDomFile xdf = InstanceQueryer.getXMLQuery("a.xml","root",false);
/*
At the same time, the development interface of automatic assembly is opened. Its function is to use this method if your jar package is imported, and you can use this method and provide their xml configuration file. This method will automatically read and load into pom.xml.
*/
SetMavenJar.getInstance().setDefaultMaven(this, "fill in the configuration file name, format and System.Maven.xml");
/*
File creation by reflection
There are two ways to create reflections, one class is annotated, and one class is unannotated.
But all need to inherit a ReadingXML class
readingXML has a number of constructors that correspond to the four constructors listed above (getDefaultXML, getXMLQuery...)
Use getXMLQuery demo here
*/
//no annotation type
Class Test1 extends ReadingXML{
Public Test1(String file,String root){
Super(file,root);
}
@path("a.b.c.d")
Public String A = "1";
}
/*
After instantiation, then call the save method
Can create file and xml element build

There are also EQuery expressions:

Note that including ':’

Addnew: Same as addElements function, does not require index attribute, can be omitted

Addchild: Same as addChild, you need to add the index attribute: indexs={}

Addchilds: the same function as addElementsIfExistsParent, requires the indexs property

Addelements: same as the function of addElements, does not require the indexs attribute

Case:
*/
 @Path(path = "a.b.c")

 Public String aString = "1";

 @Path(path="addchild:a.b.c",indexs= {0,0})

 Public String ab = "2";

 

 @Path(path="addchilds:a.b.c/d/e",indexs= {0,0})

 Public String[] abs = {"1","2","3"};

 

 @Path(path="addelements:a.b.c/d/e",indexs= {0,0})

 Public String[] absd = {"1","2","3"};

// Note that the array only supports the String[] type
```

```java
/*
In addition, there is an annotation form like this
*/
Package simpleXML.test;


Import cn.gulesberry.www.reflect.ReadingXML;
Import net.noyark.www.annocations.Path;
Import net.noyark.www.annocations.XMLFile;

@XMLFile(fileName="a.xml",root="root")
Public class Test extends ReadingXML{

@Path(path = "a.b.c")
Public String aString = "1";

@Path(path="addchild:a.b.c",indexs= {0,0})
Public String ab = "2";

@Path(path="addchilds:a.b.c/d/e",indexs= {0,0})
Public String[] abs = {"1","2","3"};

@Path(path="addelements:a.b.c/d/e",indexs= {0,0})
Public String[] absd = {"1","2","3"};

@Path(path="addelements:a.b.c/d/e",indexs= {0,0})
Public String[] absd1 = {"1","2","3"};

}
```
> _In addition, in the built-in startXML configuration file,  is written like this._

```xml
<xml-instance>
<!-- Test-Class -->
<xml-file package="simpleXML.test"/>
</xml-instance>
```
So configured

> In addition, the auto-assembly object can be assembled like this, still in the xmlStart configuration file 
```xml
<xml-instance>
<!-- Predefined objects -->
<xml>
<file name="default"></file>
<root name="root" ></root>
<is-sync state="false"></is-sync>
</xml>
</xml-instance>
```
Only need to use after
```java
XMLDomFile xdf = InstanceQueryer.getXMLQuery("default");
```

Annotation annotation

In the interface, there will be these two annotations

@getBrother
@ElementSubIndex

Meaning
The first is to get the coordinates and objects related to the sibling element.
The second is the difference between the length of the array after the point is divided by the length of the index variable form parameter, and the second parameter refers to whether the path is based on the parent element.

The final reminder is that you must use the maven project to import the plugin, you can use the build path to add, and finally, the configuration file is in the src/main/resources/ directory, you need to copy the configuration file to your directory.


the latest update:

Now you can use this code to make your root node have the xmlns default value.

```java
XMLDomFile xdf = InstanceQueryer.getXMLQuery("a.xml","root","www.noyark.net/xmlns",true)
```
```xml
Add the following to pom.xml and install the configuration file to use
<!--Specify the component warehouse of Nexus -->
  <dependencies>
  <dependency>
  <groupId>net.noyark</groupId>
  <artifactId>equery-framework</artifactId>
  <version>0.2.9</version>
</dependency>
  </dependencies>
<repositories>
    <repository>
        <id>public</id>
        <name>Team Maven Repository</name>
        <url>http://www.noyark.net:8081/nexus/content/groups/public/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
 
<!--Specify the plugin repository for Nexus -->
<pluginRepositories>
    <pluginRepository>
        <id>public</id>
        <name>Team Maven Repository</name>
        <url>http://www.noyark.net:8081/nexus/content/groups/public/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </pluginRepository>
</pluginRepositories>
```
The path to the driver load configuration file is set in the driver. The database must have .properties other without suffix. Starting from 028, define the configuration file path and driver information with driver.properties.

### * 基本介绍
* 这个xml框架简化了持久层配置文件的操作，并支持对象的IOC,大大简化了操作，同时兼容平常dom4j的操作，可以使框架与传统方式并行使用(当然，框架里面已经提供了相应的方法，必须使用框架的方法添加元素，但这些方法里面有框架的简化操作，也有原有的操作。因为在框架添加元素才能将元素注册到映射工厂，并兼容选择器的操作)，并且添加了选择器功能，有完善的元素选择机制，选择器::具备6大特性:: 
动态创建（DC） 兼同读写(RW) 反路控制(RPC) 自动装配（AA） 选择转换（SC）　对象池（OP），搜包实例（SPI）
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
Core.start();
/*
使用过程中，XMLDomFile的工厂方法的文件目录，可以省略.xml,第二个参数是root元素，如果直接已经创建过相同的对象，还要再次使用读取文档，可以省略这个参数，即：
getXMLQuery("a",false)即可，第三个是是否开启线程安全类型
如果不是要创建新文档而是使用已经存在的文档，并且不想去除原有的元素，则可以使用getDefaultXml方法，之后会保留原有元素，并且可以获取到，后期添加只会刷新 新添加的元素，直接填写file属性，当然，如果有特殊的路径，可以在第二个参数加入输入流，第一个参数加尽量明确的路径信息，大部分特殊路径为读取resources文件夹，那么第一个路径可以用getClass那种获取路径
*/
XMLDomFile xdf = InstanceQueryer.getXMLQuery("a.xml","root"，false);
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

//注意数组只支持String[]类型
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
```java
<xml-instance>
	<!-- Test-Class -->
	<xml-file package="simpleXML.test"/>
</xml-instance>
```
> 另外，自动装配对象可以如此装配，依然在xmlStart配置文件
```xml
<xml-instance>
<!-- 预定义对象 -->
	<xml>
		<file name="default"></file>
		<root name="root" ></root>
		<is-sync state="false"></is-sync>
		<url url=""><url>
	</xml>
</xml-instance>
```
之后只需要使用
```java
XMLDomFile xdf = InstanceQueryer.getXMLQuery("default");
```
获取对象即可
如此配置即可
标注注解

在接口中，会有这两种注解
@getBrother
@ElementSubIndex

意思为
第一个是获得与兄弟元素有关的坐标和对象
第二个是路径以点分割后的数组长度减去index可变形式参数的长度的差，其中其第二个参数是指路径是否以父元素为标准

最终需要提醒的是，您必须使用maven项目导入该插件，可以使用build path添加，最后，配置文件在src/main/resources/目录，您需要将里面配置文件复制到您的目录下即可
最新更新：

现在你可以使用这段代码，使得你的root节点拥有xmlns缺省值

```java
XMLDomFile xdf = InstanceQueryer.getXMLQuery("a.xml","root","www.noyark.net/xmlns",true)
```
```xml
将以下内容添加到pom.xml,并安装配置文件，即可使用 
<!--指定Nexus的构件仓库-->
  <dependencies>
  	<dependency>
  <groupId>net.noyark</groupId>
  <artifactId>equery-framework</artifactId>
  <version>0.2.9</version>
</dependency>
  </dependencies>
<repositories>
    <repository>
        <id>public</id>
        <name>Team Maven Repository</name>
        <url>http://www.noyark.net:8081/nexus/content/groups/public/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
 
<!--指定Nexus的插件仓库-->
<pluginRepositories>
    <pluginRepository>
        <id>public</id>
        <name>Team Maven Repository</name>
        <url>http://www.noyark.net:8081/nexus/content/groups/public/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </pluginRepository>
</pluginRepositories>
```
驱动加载配置文件的路径都在driver里设置，关于数据库的必须带.properties其他可以不加后缀，自028版本开始，以driver.properties定义配置文件路径和驱动信息

the doc web：[doc][3]

my web: [web][2]

the maven server: [maven][1]

[1]: http://www.noyark.net:8081/nexus/#welcome "Maven"
[2]: http://magic.noyark.net
[3]: http://sxml.noyark.net
