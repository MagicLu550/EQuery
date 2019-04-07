[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](996.ICULICENSE)
[![LICENSE](https://img.shields.io/badge/license-Noyark-green.svg)](NoyarkLICENSE)
![LICENSE](https://img.shields.io/badge/version-0.3.0NEW-blue.svg)
![master](https://img.shields.io/badge/build-passing-green.svg)
[![zh](https://img.shields.io/badge/readme-chinese-orange.svg)](chinese.md)
![avatar](logo.jpeg)



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

/ / Starting with version 031, support for all array types
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
<is-sync state="false"></is-sync>
</xml>
</xml-instance>
```

Only need to use after
```java
XMLDomFile xdf = InstanceQueryer.getXMLQuery("default",this);
```
Get the object
HOW TO USE EPATH
> How to select a language using epath xml
* epath is divided into 5 language structures:
Wildcard expression
The wildcard expression has a keyword: the all keyword, which can be used to get all the Element objects.
2. One-dimensional expression
```xml
The expression syntax:
Other [selector type]

The selector type is an auxiliary selection type with the following auxiliary selection types.
One Select an example: a[0].b[0] [one];
Elements select multiple a[0].b[0].c [elements];
On selects the top element of the element (same level)
 a[0].b[0].c[0] [on];
Under select one of the elements below this element (same level)
a[0].b[0].c[0] [under];
Ons selects all elements above the element (same level)
a[0].b[0].c[0] [ons];
Unders selects all elements below this element (same level)
a[0].b[0].c[0] [unders];
Friends select all sibling elements a[0].b[0].c[0] [friends];
Ids selects the element IDVALUE [ids] by the ID attribute;
E_name selects ELEMENTNAME [e_name] by the name of the element element;
Text Select ELEMENTTEXT [text];
Parent gets the parent element a[0].b[0].c[0] [parent]
Name Get the element by name name [name]
Case:
<root>
<a>
<b></b>
<b></b>
</a>
</root>
Select the element with the name a:
a [name]

```
3. Two-dimensional expression
```xml
The expression syntax:
Path/name middle [middleType];
Middle is an attachment selection limit, and middleType is an attachment selection restriction type.
There are the following subsidiary selection restriction types:
Text Select element by text and path a[0].b[0].c[0] ExampleText [text];
Namespace selects the element by namespace a[0].b[0].c[0] prefix,uri [namespace];
Key is selected by the attribute key and supports multiple keys a[0].b[0].c[0] key1, key2, key3 [key];
Value is selected by the value of the attribute, supporting multiple values ​​a[0].b[0].c[0] value1, value2 [value];
Key=value is selected by the attribute and value of the attribute, supporting multiple keys and values ​​a[0].b[0].c[0] key1=value1,key2=value2 [key=value];
Key,only Select by the attribute of the attribute, where the role of the only keyword is to obtain the element of the element must correspond to the specified key one by one, without adding, as long as the specified is required, the syntax is the same as above
Value,only Same as above
Text, name is selected by the name of text and element, a exmapleText [text, name];
Uri queries through the URI of the namespace a[0].b[0].c[0] prefix [prefix];
Prefix queries through the prefix of the namespace a[0].b[0].c[0] uri [uri];
```
4. Multidimensional expressions
```
Multidimensional expressions can query more complex conditions, where keywords can be spliced ​​at will, except
Grammar:
? is optional
Path/name middle... [name/path,?only,middleType...]
The name and only keywords must be in front, and the name is in front of only
Key here
Path
Name
Namespace
Prefix
Uri
Only
Key
Value
Basically the same
```
5. Keywords
```
One elements on under ons unders friends ids e_name text parent name
Context key value only uri prefix path
```

The final reminder is that you must use the maven project to import the plugin. Finally, the configuration file is in the src/main/resources/ directory. You need to copy the configuration file to your directory.

How to import the plugin?
```xml
<!-- link jar -->
<dependency>
  <groupId>net.noyark</groupId>
  <artifactId>equery-framework</artifactId>
  <version>0.3.1.1</version>
</dependency>
<!--Link private service-->
<repositories>
 <repository>
    <id>nexus</id>
    <name>Team Neux Repository</name><url>http://www.noyark.net:8081/nexus/content/groups/public/</url>
    </repository>
</repositories>
<pluginRepositories>
    <pluginRepository>
      <id>nexus</id>
      <name>Team Neux Repository</name>
      <url>http://www.noyark.net:8081/nexus/content/groups/public/</url>
    </pluginRepository>
 </pluginRepositories>

```

web: [web]

doc: [doc]

maven: [maven]

[web]: http://magic.noyark.net

[doc]: http://sxml.noyark.net

[maven]: http://www.noyark.net:8081/nexus

[chinese.md]: chinese.md
