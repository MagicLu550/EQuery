![master](https://travis-ci.org/MagicLu550/EQuery.svg?branch=master)

![avatar](logo.jpeg)

[chinese] chinese.md
### * basic introduction
* This `xml framework` simplifies the operation of the persistence layer configuration file and supports the object's IOC, which greatly simplifies the operation and is compatible with the usual `dom4j` operation, which allows the framework to be used in parallel with the traditional method (of course, the framework already provides The corresponding method, you must use the framework method to add elements, but these methods have a simplified operation of the framework, there are original operations. Because adding elements in the framework can register elements to the mapping factory, and is compatible with the operation of the selector) And added a selector function, a complete element selection mechanism, selector:: with 6 major features::
`Dynamic creation (DC) ` `concurrent read and write (RW) ` `reverse control (RPC) ` `automatic assembly (AA) ` ` select conversion (SC) ` `object pool (OP) ` `search package instance ( SPI)`
---

* DC:
 Chinese (dong tai chuang jian), meaning that objects can be created dynamically and created according to various forms of configuration files, which is very flexible
* RW:
Chinese (jian tong du xie) means that it can be compatible with the newly added elements of the document that has been written, and will not refresh the original created document.
* RPC:
 Chinese (fan lu kong zhi), meaning the rules of the elements are created by path and reflection, thus simply adding elements
* AA:
 Chinese (zi dong zhuang pei), meaning that the maven dependencies can be automatically assembled by loading the framework driver.
* SC:
 Chinese (xuan ze zhuan huan), built-in converter can convert dom4j Element object into SElement recording element geometry information, and then used for selector hash query element
* OP:
Chinese (dui xiang chi), the object pool can store the original object, the previously created object can be reused
* SPI:
 Chinese (sou bao shi li), through the package path of the configuration file, search for the class with annotations, instantiate the object according to the annotation, and create the file at one time
```java
 / / Development use object in EQuery:
/**
Starting with version 028, you must use this method to load the driver. All the driver information is in
Driver.properties
*/
Core.start();

/*
In the process of use, the file directory of the factory method of XMLDomFile can omit the .xml. The second parameter is the root element. If the same object has been created directly, and the document is read again, the parameter can be omitted, namely:
getXMLQuery("a",false), the third is whether to enable thread safety type
If you don't want to create a new document and use an existing document, and you don't want to remove the original element, you can use the getDefaultXml method, then retain the original element, and you can get it. Later addition will only refresh the newly added element. Fill in the file attribute directly, of course, if there is a special path, you can add the input stream in the second parameter, the first parameter plus the path information as clear as possible, most of the special path is to read the resources folder, then the first path Can get the path with getClass
*/
XMLDomFile xdf = InstanceQueryer.getXMLQuery("a.xml","root",false);

XMLDomFile xdf = InstanceQueryer.getXMLQuery("a",this);
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

/ / Note that the array only supports the String[] type
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
> _ In addition to the built-in startXML configuration file, write _
> After version 030, support only writes the path of the parent package, the sub-package path is scanned by default
```java
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
