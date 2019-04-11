/*GulesBerry Tech. Co. Ltd. (C) noyark-system Hector Group
 * (GHG China) @Freedom Web of java
 * @noyark - system group for xml
 * @github magiclu550 author
 * @github K.J author (English Noter)
 * @using dom4j
 * @school: JiaoNan No.1 middle School
 * 	override this none
 * 	please see our website:
 * 	###############################################
 * 						
 * 					     www.noyark.net/index.html
 * 
 * 	###############################################
 * 
 * @where China shandong qingdao
 * you can learn more from this class
 * 
 * 
 * finished on 3.12 ,2019 ,20:38
 * 
 * 
 */
package cn.gulesberry.www.helper;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.io.InitDocument;
import cn.gulesberry.www.io.XMLDocument;
import cn.gulesberry.www.use.SetMavenJar;
import net.noyark.www.interf.XMLDomFile;

import static cn.gulesberry.www.helper.XMLHelper.getByFile;
import static cn.gulesberry.www.helper.XMLHelper.getByGroup;
import static cn.gulesberry.www.helper.XMLHelper.getByGroupWithUrl;
import static cn.gulesberry.www.helper.XMLHelper.instancesPool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;


/**
 *	<p>
 *	This class can directly search for an
 *	instance that has already been created 
 *	If it is not created,it will create an object
 *	according to the developer's intention,and the
 *	built-in object pool,in order to save <i>memory</i> and
 *	reuse the instance 
 *</p>
 *<p>
 *  in the process of use, the file directory of the factory method 
 *  of XMLDomFile can omit the .xml. The second parameter is the root 
 *  element. If the same object has been created directly, and the document 
 *  is read again, the parameter can be omitted, namely:getXMLQuery("a",false), 
 *  the third is whether to enable thread safety type,If you don't want to create 
 *  a new document and use an existing document, and you don't want to remove the 
 *  original element, you can use the getDefaultXml method, then retain the original 
 *  element, and you can get it. Later addition will only refresh the newly added element. 
 *  Fill in the file attribute directly, of course, if there is a special path, you can add 
 *  the input stream in the second parameter, the first parameter plus the path information 
 *	as clear as possible, most of the special path is to read the resources folder, then the 
 *	first path Can get the path with getClass.
 *</p>
 * @author magicLu550
 * @since JDK1.8
 * @since EQuery 019
 */
public interface InstanceQueryer{
	/**
	 * <p>
	 * 	This method can be instantiated by the <i>object
	 * 	pool</i>,throw objects into the unified pool,and then
	 * 	search the corresponding object ,this object is not
	 * 	instantiated,then use this method to instantiate the
	 * 	object,if it has been instantiated,it is not recommended
	 * 	to use this method.
	 * </p>
	 * @param file the xml file name,you can add no or suffix
	 * @param rootElement the rootElement name
	 * @param isSync if you have two many thread,the best to 'true'
	 * @return the File instance
	 */
	static XMLDomFile getXMLQuery(String file,String rootElement,boolean isSync){
		if(isSync) {
			synchronized (instancesPool) {
				return getByGroup(file, rootElement);
			}
		}else {
			return getByGroup(file, rootElement);
		}
	}
	/**
	 * <p>
	 * 	This method can be instantiated by the <i>object
	 * 	pool</i>,throw objects into the unified pool,and then
	 * 	search the corresponding object ,this object is not
	 * 	instantiated,then use this method to instantiate the
	 * 	object,if it has been instantiated,it is not recommended
	 * 	to use this method.And this method can create the root element
	 * 	with 'xmlns' attribute
	 * </p>
	 * @param file the xml file name,you can add no or suffix
	 * @param rootElement the rootElement name
	 * @param isSync if you have two many thread,the best to 'true'
	 * @return the File instance
	 */
	static XMLDomFile getXMLQuery(String file,String rootElement,String url,boolean isSync) {
		if(isSync) {
			synchronized (instancesPool) {
				return getByGroupWithUrl(file, rootElement,url);
			}
		}else {
			return getByGroupWithUrl(file, rootElement, url);
		}
	}
	/**
	 * <p>
	 * 	This method can search for previously created
	 * 	methods.If you use an Object pre_initialized
	 * 	int the configuration file,or an object that
	 * 	created this file before,you can use this method 
	 * 	to search for the object,If the object does not exist
	 * 	return the default rootElement.the file can not end with
	 * 	.xml
	 * </p>
	 * @param file the xml file name
	 * @param isSync if you have two many thread,the best to 'true'
	 * @return The File instance
	 * @throws DocumentException if your document have some errors..
	 */
	static XMLDomFile getXMLQuery(String file,boolean isSync) throws DocumentException {
		if(isSync) {
			synchronized (instancesPool) {
				return getByFile(file);
			}
		}else {
			return getByFile(file);
		}
	}
	/**
	 * <p>
	 * 	If this file already exists and you want to
	 * 	add or read the content on the basis of far mail,
	 * 	you can use this method,this method can search all
	 *  the information through the file path,and create the 
	 *  original file content.If the file does not exist,throw
	 *  a file exception
	 *  	you can use file start with classpath:
	 * </p>
	 * @param file  the xml file name
	 * @param getStream whether get the inputstream of resources,if it is true,you cannot write
	 * @param o is always 'this'
	 * @return the XMLFile Instance
	 * @throws DocumentException if your document have some errors..
	 * @throws IllegalMappingException you can see the {@link cn.gulesberry.www.exception.IllegalMappingException}
	 * @throws IndexLengthException you can see the {@link cn.gulesberry.www.exception.IndexLengthException}
	 * @throws IOException if your File is not found
	 */
	static XMLDomFile getDefaultXml(String file,Object o,boolean... getStream) throws DocumentException, IllegalMappingException, IndexLengthException, IOException {
		List<Element> elements = new ArrayList<>();
		SAXReader reader = new SAXReader();	
		boolean isStream = false;
		if(getStream.length>0) {
			isStream = getStream[0];
		}
		if(isStream) {
			Reader reader2 = new InputStreamReader(o.getClass().getClassLoader().getResourceAsStream(file));
			Document doc = reader.read(reader2);
			XMLDomFile xdf = XMLHelper.setElements(file, doc, elements);
			xdf.save();
			return xdf;
		}
		if(file.startsWith("classpath:")) {
			file = (SetMavenJar.getInstance().getClass().getResource("/").getPath()+file.substring("classpath:".length())).replaceAll("test-classes", "classes");
		}
		XMLDocument xml = XMLHelper.getDefaultPoor(file);
		if(xml==null) {
				file = XMLHelper.setFileName(file);
				File xmlFile = new File(file);
			if(xmlFile.exists()) {
					File dir = new File(SetMavenJar.getInstance().getClass().getResource("/").getPath()+"/mapping");
					dir.mkdir();
					File mapping = new File(SetMavenJar.getInstance().getClass().getResource("/").getPath()+"mapping/"+file.replaceAll("/", "-"));
					Document doc;
					if(!mapping.exists()) {
						doc = reader.read(xmlFile);
						mapping.createNewFile();
						XMLHelper.write(doc,mapping);
					}else {
						doc = reader.read(mapping);
						doc.setName(file);
					}
					XMLDomFile xdf = XMLHelper.setElements(file, doc, elements);
					xdf.save();
					return xdf;
			}else {
				throw new NoSuchFileException(file);
			}
		}else {
			xml.save();
			return xml;
			}
	}
	/**
	 * <p>
	 * For xml files that read existing paths and
	 * have unclear paths,you can use this method,
	 * to pass in an input stream and just fill in
	 * the file name("if the file is ./a/b/c/a.xml
	 * ,you try to use the big path,the best is a/b/c/a.xml")
	 * Only read,and in the classpath
	 * </p>
	 * @param file  the xml file name
	 * @param stream the inputstream like this.getClass and so on
	 * @return the XMLFile Instance
	 * @throws DocumentException if your document have some errors..
	 * @throws IllegalMappingException you can see the {@link cn.gulesberry.www.exception.IllegalMappingException}
	 * @throws IndexLengthException you can see the {@link cn.gulesberry.www.exception.IndexLengthException}
	 * @throws IOException if your File is not found
	 */
	@Deprecated
	static XMLDomFile getDefaultXml(String file,InputStream stream) throws DocumentException, IllegalMappingException, IndexLengthException, IOException {
		String classpath = (SetMavenJar.getInstance().getClass().getResource("/").getPath()+file).replaceAll("test-classes", "classes");
		List<Element> elements = new ArrayList<>();	
		XMLDocument xml = XMLHelper.getDefaultPoor(classpath);
		if(xml==null) {
				SAXReader reader = new SAXReader();	
				File dir = new File(SetMavenJar.getInstance().getClass().getResource("/").getPath()+"mapping");
				dir.mkdir();
				File mapping = new File(SetMavenJar.getInstance().getClass().getResource("/").getPath()+"mapping/"+file.replaceAll("/", "-"));
				Document doc;
				if(!mapping.exists()) {
					doc = reader.read(stream);
					mapping.createNewFile();
					XMLHelper.write(doc,mapping);
				}else {
						doc = reader.read(mapping); 
						doc.setName(classpath);
				}
				return XMLHelper.setElements(SetMavenJar.getInstance().getClass().getResource("/").getPath(), doc, elements);
		}else {
				return xml;
		}
	}
	/**
	 * <p>
	 * 	This method can search for previously created
	 * 	methods.If you use an Object pre_initialized
	 * 	int the configuration file,or an object that
	 * 	created this file before,you can use this method 
	 * 	to search for the object,If the object does not exist
	 * 	return the default rootElement.You can use any format 
	 * 	of file name.If you want to build the xsd file,you can
	 * 	fill in the following content and the root leave empty string
	 * 	,the method will automatically add the xsd suffix,the latter 
	 * 	two parameters leave an empty string
	 * </p>
	 * @param file the xml file name()
	 * @param root the root element name
	 * @return The File instance
	 */
	static XMLDomFile getAnyFile(String file,String root,String targetNamespace,String xmlnsTns) {
		StringBuilder builder = new StringBuilder(file);
		if(!"".equals(targetNamespace)&&"".equals(xmlnsTns)) {
			if(!file.endsWith(".xsd")) {
				builder.append(".xsd");
			}
			synchronized (instancesPool) {
				XMLDomFile xdf = XMLHelper.searchInstance(file,"xs:schema");
				if(xdf!=null) {
					return xdf; 
				}else {
					xdf = new InitDocument(builder.toString(), "schema","http://www.w3.org/2001/XMLSchema");
					xdf.getRootElement().addNamespace("targetNamespace",targetNamespace);
					xdf.getRootElement().addNamespace("tns",xmlnsTns);
					xdf.getRootElement().addAttribute("elementFormDefault", "qualified");
					return xdf;
				}
			}
		}
		XMLDomFile xdf = XMLHelper.searchInstance(file,root);
		synchronized (instancesPool) {
			if(xdf!=null) {
				return xdf;
			}else {
				return new InitDocument(file, root);
			}
		}
	}
	/**
	 * <p>
	 * 	you can get the inputstream that read the resources simply
	 * </p>
	 * @param file the file's name in the resources
	 * @param o is always 'this'
	 * @return the ClassLoaderInputStream
	 */
	static InputStream getStream(String file,Object o) {
		return o.getClass().getClassLoader().getResourceAsStream(file);
	}

}
