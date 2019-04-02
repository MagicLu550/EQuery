/*GulesBerry Tech. Co. Ltd. (C) noyark-system Hector Group
 * (GHG China) @Freedom Web of java
 * @noyark - system group for xml
 * @github magiclu550 author
 * @noter K.J author 
 * @qq 843983728
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
 * 
 * author is a middle school student,so he doesn't have much time.If you think his code is good for you,you
 * can touch the 'star',thanks
 * 
 * 
 * finished on 3.20 ,2019 ,10:46
 * 
 * 
 */
package cn.gulesberry.www.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.helper.InstanceQueryer;
import cn.gulesberry.www.use.ParserFactory;
import net.noyark.www.interf.XMLDomFile;

/**
 * <p>
 * At the same time, the development interface of automatic assembly 
 * is opened. Its function is to use this method if your jar package 
 * is imported, and you can use this method and provide their xml 
 * configuration file. This method will automatically read and load 
 * into pom.xml.
 * </p>
 * like:
 * 	public class Test extends ReadingXML{
 * 		public Test(String file,String root){
 * 			super(file,root)
 * 		}
 * 		@Path(path="a.b.c")
 * 		public int a = 0;
 * 	}
 * you can use the annotations:
 * 	@XMLFile(fileName="x.xml",root="root")
 * 	public class Test extends ReadingXML{
 * 		@Path(path="a.b.c")
 * 		public int a = 0;
 * 	}
 * and write the basicXML.xml
 * @author magiclu550
 * @since EQuery 025
 * @since JDK 1.8
 * @see XMLDomFile
 *
 */
public abstract class ReadingXML {
	/**The xmldomfile instance
	 * */
	protected XMLDomFile xMl;
	/**
	 * The ReadingXML object
	 */
	protected Object o;
	/**
	 * The element id
	 */
	protected Integer id = -1;
	/**all the element that have number
	 * */
	protected Map<Integer, Element> all;
	/**
	 * The Constructors is like the InstanceQueryer's all methods
	 * @see InstanceQueryer
	 * */
	public ReadingXML(String file,String root,boolean isSync) throws IndexLengthException, IOException, IllegalArgumentException, IllegalAccessException, IllegalMappingException, DocumentException {
		this();
		xMl = InstanceQueryer.getXMLQuery(file,root,isSync);
		setObject(getObject());
	}
	public ReadingXML(String file,boolean isSync) throws DocumentException, IndexLengthException, IOException, IllegalArgumentException, IllegalAccessException, IllegalMappingException {
		this();
		xMl = InstanceQueryer.getXMLQuery(file,isSync);
		setObject(getObject());
	}
	public ReadingXML(String file) throws DocumentException, IllegalMappingException, IndexLengthException, IOException, IllegalArgumentException, IllegalAccessException {
		this();
		xMl = InstanceQueryer.getDefaultXml(file);
		setObject(getObject());
	}
	public ReadingXML(String file,InputStream in) throws DocumentException, IllegalMappingException, IndexLengthException, IOException, IllegalArgumentException, IllegalAccessException {
		this();
		xMl = InstanceQueryer.getDefaultXml(file,in);
		setObject(getObject());
	}
	/**
	 * When use the annotation,will use this constructor
	 */
	public ReadingXML() {
		all = new HashMap<>();
	}
	/**
	 * This method can parse the <code>Path</code> annotation
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws DocumentException
	 * 
	 * @see net.noyark.www.annotations.Path
	 */
	public synchronized void parsePath() throws IllegalMappingException, IndexLengthException, IOException, IllegalArgumentException, IllegalAccessException, DocumentException {
		
		ParserFactory.newInstance().parse(xMl, o, id, all);
	}
	/**
	 * it can save the xml file
	 * @throws IOException
	 */
	public void save() throws IOException {
		xMl.save();
	}
	/**
	 * This is used in the parse Method(such as annotations)
	 * @param o this
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void setObject(Object o) throws IllegalArgumentException, IllegalAccessException, IllegalMappingException, IndexLengthException, IOException, DocumentException {
		this.o = o;
		parsePath();
	}
	/**
	 * It can get the XMLDomFile instance for the xml file
	 * @return the XMLDomFile Object
	 */
	public XMLDomFile getXMLInstance(){
		return xMl;
	}
	/**
	 * You can set the specified text,index is the order of @ Path
	 * @param index the index of Path annotation
	 * @param text the new text
	 */
	public void setText(int index,String text) {
		all.get(index).setText(text);
	}
	/**
	 * It is used in parse the XMLFile Annotation
	 * @param file the xml file name
	 * @param root the root element name
	 */
	public void setInitXML(String file,String root) {
		xMl = InstanceQueryer.getXMLQuery(file,root,true);
	}
	/**
	 * It is used in parse the XMLFile Annotation
	 * @param file the xml file name
	 * @param root the root element name
	 * @param url the xmlns's url
	 */
	public void setInitXML(String file,String root,String url) {
		xMl = InstanceQueryer.getXMLQuery(file,root,url,true);
	}
	/**
	 * It is used in parse the XMLFile Annotation,when isDefault = true
	 * @param file the xml file name
	 */
	public void setDefaultXML(String file) {
		try {
			InstanceQueryer.getDefaultXml(file);
		} catch (DocumentException | IllegalMappingException | IndexLengthException | IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * It can get the ReadingXML's subclass
	 * @return the subclass instance
	 */
	public Object getObject() {
		return this;
	}
}
