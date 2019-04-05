[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)
![LICENSE](https://img.shields.io/badge/license-Noyark-green.svg)
![LICENSE](https://img.shields.io/badge/version-0.3.0NEW-blue.svg)
![Build](https://img.shields.io/badge/build-passing-green.svg)
[![en](https://img.shields.io/badge/readme-english-orange.svg)](README.md)

![logo](logo.jpeg)
### * 基本介绍
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
如果不是要创建新文档而是使用已经存在的文档，并且不想去除原有的元素，则可以使用getDefaultXml方法，之后会保留原有元素，
