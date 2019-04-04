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
 * 
 * 
 */
package cn.gulesberry.www.use;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.gulesberry.www.core.MainProperties;
import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.helper.InstanceQueryer;
import cn.gulesberry.www.helper.XMLHelper;
import cn.gulesberry.www.io.XMLDocument;
import cn.gulesberry.www.reflect.ReadingXML;
import net.noyark.www.annotations.XMLFile;
import net.noyark.www.console.Console;
import net.noyark.www.interf.XMLDomFile;
/**
 * This class is used for automatic assembly of Maven,
 * and the processing of reflections.Except for the automatic
 * assembly open interface ,it is not allowed to be used by outsiders,
 * It is only used isnide the framework 
 * @author magiclu550
 * @since EQuery 025
 * @since JDK 1.8  
 */
public class SetMavenJar {
	public MainProperties mainProperties = MainProperties.getInstance();
	
	public static String version;
	static SetMavenJar jar;
	static {
		jar = new SetMavenJar();
	}
	/**
	 * get the setMavenJar's instance
	 * @return the instance
	 */
	public static SetMavenJar getInstance() {
		return jar;
	}
	/**
	 * Method for configuring Maven and displaying
	 * operational information
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws TransformerException 
	 * @throws DocumentException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 */
	public void XQueryMessage() throws ParserConfigurationException, SAXException, IOException, TransformerException, IllegalMappingException, IndexLengthException, DocumentException {
		Console.err("正在装配中，检测您是否为maven项目");
		Console.err("如果是maven项目将装配dom4j");
		Console.err("正在搜寻pom.xml");
		File file = new File("pom.xml");
		if(file.exists()) {
			Console.err("检测到您是maven项目");
			setDefaultMaven(this,mainProperties.getMavenPath());
		}else {
			Console.err("检测到您不是maven,请手动下载jar包");
		}
	}
	/**
	 * 
	 * <p>
	 * At the same time, the development interface of automatic assembly 
	 * is opened. Its function is to use this method if your jar package 
	 * is imported, and you can use this method and provide their xml 
	 * configuration file. This method will automatically read and load 
	 * into pom.xml.
	 * </p>
	 * 	like:
	 * 	<maven>
	 * <dom4j>
	 * <gid name="dom4j"></gid>
	 * <aid name="dom4j"></aid>
	 * <ver ver="1.6.1"></ver>
	 * </dom4j>
	 * <maven>
	 * o is always this.
	 * @param o  this object
	 * @throws ParserConfigurationException 
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException 
	 * @throws DocumentException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 */
	public void setDefaultMaven(Object o,String mavenDefault) throws ParserConfigurationException, SAXException, IOException, TransformerException, IllegalMappingException, IndexLengthException, DocumentException {
		mavenDefault = XMLHelper.setFileName(mavenDefault);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		File file = new File("pom.xml");
		org.w3c.dom.Document document = builder.parse(file);
		NodeList list = document.getElementsByTagName("dependencies");
		org.w3c.dom.Document document2 = null;
		//get the dtd path
		InputStream in = o.getClass().getClassLoader().getResourceAsStream(mavenDefault);
		String xmlDefault = XMLHelper.setFileName(mainProperties.getInstancePath());
		String path = this.getClass().getResource("/").getPath();
		String dtdPath = path.substring(0,path.indexOf("target"))+"/target/classes/"+mainProperties.getStartDTD();
		path = path.substring(0,path.indexOf("target"))+"target/classes/"+xmlDefault;
		XMLDomFile xdf = InstanceQueryer.getDefaultXml(path.substring(0,path.indexOf("target"))+"src/main/resources/"+mainProperties.getInstancePath(),this);
		xdf.addDOCTYPE("xml-instance",null,dtdPath);
		xdf.save();
		document2 = builder.parse(in);
		NodeList gids = document2.getElementsByTagName("gid");
		NodeList aids = document2.getElementsByTagName("aid");
		NodeList vers = document2.getElementsByTagName("ver");
		if(list.getLength()==0) {
			org.w3c.dom.Element domdDenp = document.createElement("dependencies");
			NodeList project = document.getElementsByTagName("project");
			project.item(0).appendChild(domdDenp);
			list = document.getElementsByTagName("dependencies");
		}
		for(int i = 0;i<gids.getLength();i++) {
			writeInto(i, gids, aids, vers,list, document);
		}
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "Yes");
		transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
		transformer.transform(new DOMSource(document), new StreamResult("pom.xml"));
	}
	/**
	 * Used to read out the configuration information
	 * of the object from the xmlStart configuration file,
	 * and automatically instantiate it.The created information
	 * can be obtained through the <code>getXMLQuery</code> method
	 * of two paramaters
	 * @param xmlDefault the file like xmlStart's name 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void prepareInstance() throws ParserConfigurationException, SAXException, IOException {
		String xmlDefault = XMLHelper.setFileName(mainProperties.getInstancePath());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(xmlDefault);
		org.w3c.dom.Document document = builder.parse(in);
		NodeList fileList = document.getElementsByTagName("file");
		NodeList rootList = document.getElementsByTagName("root");
		NodeList syncList = document.getElementsByTagName("is-sync");
		NodeList urlList = document.getElementsByTagName("url");
		//创建对象
		for(int i = 0;i<fileList.getLength();i++) {
			String f = fileList.item(i).getAttributes().item(0).getNodeValue();
			String r = rootList.item(i).getAttributes().item(0).getNodeValue();
			boolean s = Boolean.parseBoolean(syncList.item(i).getAttributes().item(0).getNodeValue());
			String u = urlList.item(i).getAttributes().item(0).getNodeValue();
			if(u.equals("")) {
				InstanceQueryer.getXMLQuery(f,r,s);
			}else {
				InstanceQueryer.getXMLQuery(f,r,u,s);
			}
		}
	}
	/**
	 * This can write the previous Maven pre-configured elements
	 * to the pom file.
	 * @param i the gid,aid,ver's index
	 * @param gids the gids list ,like the xml file's element tag name
	 * @param aids the aids list ,like the xml file's element tag name
	 * @param vers the vers list ,like the xml file's element tag name
	 * @param list the parent list
	 * @param document the Document object
	 * @throws SAXException
	 * @throws IOException
	 */
	private void writeInto(int i,NodeList gids,NodeList aids,NodeList vers,NodeList list,Document document) throws SAXException, IOException {
		String groupId = gids.item(i).getAttributes().item(0).getNodeValue();
		String artId = aids.item(i).getAttributes().item(0).getNodeValue();
		String version = vers.item(i).getAttributes().item(0).getNodeValue();
		NodeList pomGids = document.getElementsByTagName("groupId");
		NodeList pomAids = document.getElementsByTagName("artifactId");
		boolean have = false;
		for(int index = 0;index<pomGids.getLength();index++) {
			String gid = pomGids.item(index).getFirstChild().getNodeValue();
			String aid = pomAids.item(index).getFirstChild().getNodeValue();
			if(gid.equals(groupId)&&aid.equals(artId)) {
				have = true;
			}
		}
		if(!have) {
			org.w3c.dom.Element domdDenp = document.createElement("dependency");
			org.w3c.dom.Element domGroupId = document.createElement("groupId");
			org.w3c.dom.Element domArtId = document.createElement("artifactId");
			org.w3c.dom.Element domVersion = document.createElement("version");
			domGroupId.appendChild(document.createTextNode(groupId));
			domArtId.appendChild(document.createTextNode(artId));
			domVersion.appendChild(document.createTextNode(version));
			domdDenp.appendChild(domGroupId);
			domdDenp.appendChild(domArtId);
			domdDenp.appendChild(domVersion);
			list.item(0).appendChild(domdDenp);
		}
	}
	/**
	 * It can search according to the package path
	 * in the configuration file,search for the class 
	 * with the XmlFile annotation,and parse,Instantiate,create the file
	 * @param xmlDefault the file like xmlStart's name 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws DocumentException
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void annotationInstance() throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, DocumentException, IllegalMappingException, IndexLengthException, InstantiationException, IllegalAccessException{
		String xmlDefault = XMLHelper.setFileName(mainProperties.getInstancePath());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		String path = this.getClass().getResource("/").getPath();
		path = path.substring(0,path.indexOf("target"))+"target/classes/"+xmlDefault;
		InputStream input = new FileInputStream(path);
		org.w3c.dom.Document document = builder.parse(input);
		NodeList nl = document.getElementsByTagName("xml-file");
		List<String> packagePathes = new ArrayList<>();
		for(int i = 0;i<nl.getLength();i++) {
			String packet = nl.item(i).getAttributes().item(0).getNodeValue();
			packagePathes.add(packet);
		}
		scanPackage(packagePathes);
	}
	/**
	 * Its role is to further parse
	 * the obtained class and resolve it to
	 * an instance; 
	 * @param allPackages the package path list from xml
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws DocumentException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 */
	private void scanPackage(List<String> allPackages) throws IllegalMappingException, IndexLengthException, IllegalArgumentException, IllegalAccessException, IOException, DocumentException, ClassNotFoundException, InstantiationException {
		for(String pack:allPackages) {
			String classPath = this.getClass().getResource("/").getPath();
			String otherPath;
			if(classPath.indexOf("test-classes")!=-1) {
				otherPath = classPath.replace("test-classes","classes");
			}else {
				otherPath = classPath.replace("classes","test-classes");
			}
			String[] allPath = {classPath,otherPath};
			for(String path:allPath) {
				String packagePath = path+pack.replaceAll(XMLDocument.POINT,"/");
				File file = new File(packagePath);
				List<File> fileName = loadClass(file);
				for(File f1:fileName) {
					if(f1.getName().endsWith(".class")) {
						String classpath = f1.getPath();
						classpath = classpath.substring(classpath.indexOf("classes")+"classes".length()+1,classpath.indexOf(".class")).replaceAll("/",".");
						Class<?> clz = Class.forName(classpath);
						Annotation anno = clz.getDeclaredAnnotation(XMLFile.class);
						if(anno!=null){
							if(!clz.getSuperclass().getSimpleName().equals("ReadingXML")){
								throw new ClassNotFoundException("the superclass is not ReadingXML");
							}
							XMLFile xmlFile = (XMLFile)anno;
							String root = xmlFile.root();
							String file1 = xmlFile.fileName();
							boolean isDefault = xmlFile.isDefault();
							//获得类型
							ReadingXML object = (ReadingXML)clz.newInstance();
							if(isDefault){
								object.setDefaultXML(file1);
								object.setObject(object.getObject());
								object.save();
							}else{
								if(xmlFile.xmlns().equals("")) {
									object.setInitXML(file1, root);
								}else {
									object.setInitXML(file1, root,xmlFile.xmlns());
								}
								object.setObject(object.getObject());
								object.save();
							}
						}
					}
				}
			}
		}
	}
	/**
	 * It can get Package and Classes next the root package
	 * @param file the classpath file
	 * @return the file about classes and package
	 */
	private List<File> loadClass(File file) {
		List<File> allFiles = new ArrayList<File>();
		File[] files = file.listFiles();
		if(files!=null) {
			for(File f:files) {
				if(f.getName().endsWith(".class")) {
					allFiles.add(f);
				}
			}
		}
		searchFile(files, allFiles);
		return allFiles;
	}
	/**
	 * It can get all of the file about package
	 * @param files the file objects
	 * @param allFiles the package file
	 */
	private void searchFile(File[] files,List<File> allFiles) {
		if(files!=null) {
			for(File f:files) {
				if(f.isDirectory()){
					allFiles.add(f);
					File[] files2 = f.listFiles();
					searchFile(files2, allFiles);
				}
			}
		}
	}
}
