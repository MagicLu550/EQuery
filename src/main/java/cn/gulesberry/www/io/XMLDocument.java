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
 * finished on 3.17 ,2019 ,9:43
 * 
 * 
 */
package cn.gulesberry.www.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gulesberry.www.conn.ConnectApplicationDB;
import cn.gulesberry.www.conn.ConnectionBase;
import cn.gulesberry.www.entity.SElement;
import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.exception.SynaxException;
import cn.gulesberry.www.helper.InstanceQueryer;
import cn.gulesberry.www.helper.XMLHelper;
import cn.gulesberry.www.manager.InitXMLManager;
import cn.gulesberry.www.manager.XMLManager;
import cn.gulesberry.www.utils.table.NodeTable;
import cn.gulesberry.www.utils.tree.ElementTree;
import cn.gulesberry.www.utils.tree.TreeWaterMan;
import net.noyark.www.console.Console;
import net.noyark.www.interf.Branch;
import net.noyark.www.interf.Manager;
import net.noyark.www.interf.Table;
import net.noyark.www.interf.Tree;
import net.noyark.www.interf.WaterMan;
import net.noyark.www.interf.XMLDomFile;
import net.noyark.www.list.Query;

/**
*  <p>
*  This is a simple XML selector tool that allows you to easily write and parse XML files.
*  There are many ways to do this, you can choose according to your needs.
*  </p>
*  <p>
*  The role of this class is to facilitate the reading and writing of XML, simplifying a large number of operations,
*  Make the label and content form a unified mapping, this class refers to dom4j this excellent xml parser
*  {@link org.dom4j.io.XMLWriter} and {@link org.dom4j.io.SAXReader}
*  , thank you for your use
*  <p>
*  <p>
*  the index starts from 0,is the first element of the same name in the same name.
*  the path don't write with the root element's name
*  </p>
*  @author magiclu550
*  @since JDK1.8
*  @since EQuery 001
*  @see net.noyark.www.interf.XMLDomFile
*  @see cn.gulesberry.www.io.DefaultDocument
*  @see cn.gulesberry.www.io.InitDocument
*/
public abstract class XMLDocument  implements XMLDomFile{
	
	private static final long serialVersionUID = 1L;
	/**This is the POINT for Regular expression
	 * */
	public static final String POINT = "\\u002E";
	/**This is the SLASH for Regular expression
	 * */
	public static final String SLASH = "/";
	/**
	 *xmlFile is the xml File,you must write the right file
	 *file can not exist,if it not exist,I can create it
	 *
	 */
	protected File xmlFile;
	/**
	 * This is the empty document
	 */
	protected Document doc;
	/**
	 * this is the out put stream,is used to write
	 */
	protected FileOutputStream fos;
	/**
	 * This is the root Element.
	 */
	protected Element root; 
	/**
	 * write the xml element
	 */
	protected XMLWriter writer;
	/**
	 * the xml file
	 */
	protected String file;
	/**
	 * the factory Manager
	 */
	protected Manager<Element> manager; 
	/**
	 * the Object poor for XMLDocument
	 */
	protected List<SElement> list;
	/**
	 * whether the default xml or not
	 */
	protected boolean isDefault;
	/**
	 * <p>
	 * This constructor is used to construct 
	 * xml file operation objects, which are applied to files that 
	 * did not exist before. Since version 020, 
	 * two constructors have been split. 
	 * The constructor does not allow developers to manipulate them. 
	 * It can only be built by the <code>InstanceQueryer</code> class.
	 * </p>
	 * @param file the file name
	 * @param rootElement the root Element name
	 * 
	 * @see cn.gulesberry.www.io.InitDocument#InitDocument(String, String)
	 * @see cn.gulesberry.www.io.DefaultDocument#DefaultDocument(String, Document, XMLManager)
	 * 
	 */
	public XMLDocument(String file,String rootElement,boolean isDefault) {
		this(isDefault,new InitXMLManager(),file,rootElement,null);
	}
	/**
	 * The xmlns default namespace can be added
	 * to the original constructor object.
	 * @param file the xml file name
	 * @param rootElement the root element name
	 * @param isDefault whether is the default object
	 * @param url the namespace xmlns =...
	 * 
	 * @see cn.gulesberry.www.io.XMLDocument#XMLDocument(String, String, boolean)
	 */
	public XMLDocument(String file,String rootElement,boolean isDefault,String url) {
		this(isDefault,new InitXMLManager(),file,rootElement,url);
	}
	/**
	 * <p>
	 * This constructor is used to build file manipulation objects for 
	 * manipulating existing files. After the 
	 * 020 version, the constructor has been 
	 * differentiated and developers are not 
	 * allowed to create objects themselves. 
	 * Objects must be built with the 
	 * <code>InstanceQueryer</code> class
	 * </p>
	 * @param file the file name
	 * @param doc the document object
	 * @param manager the manager object (Default manager)
	 * @param isDefault whether default object or not
	 * 
	 *
	 */
	public XMLDocument(String file,Document doc,XMLManager manager,boolean isDefault) {
		this(manager, isDefault,file,doc,null,null);
	}
	/**
	 *  <p>
	 * The <code>addElement</code> method can add tags directly. The first tag is not the root tag.
	 * , is the first subtag of <code>root</code>, and the tag hierarchy is separated by dots.
	 * </p>
	 * <p>
	 * It does not support adding subtags. You can add tags using the <code>addChild</code> method.
	 * This method can establish an xml file system at one time.
	 * No need to use more than one method, which simplifies operation.
	 * </p>
	 * @param element This tag is the tag path to be added, eg: a.b.c
	 * @param text The inner content of the smallest tag
	 * @return returns the added tag
	 * @throws IllegalMappingException throws an exception when the mapping is not valid
	 * @throws IndexLengthException throws an exception when the length is not legal
	 * @throws IOException
	 * 
	 * @see net.noyark.www.interf.XMLDomFile#addElement(String element,String text)
	 */
	public Element addElement(String element,String text) throws IllegalMappingException, IndexLengthException, IOException {
		List<Element> list = new ArrayList<>();
		Element next = null;
		String[] elements = element.split(POINT); 
		if(elements.length >0) {
			next = root.addElement(elements[0]);
			next = XMLHelper.addElements(1,elements,next,0,list);// get the last element
			next.addText(text);
		}
		manager.addSequeElement(element, next, 0);
		return next;
}
	/**
	* <p>
	* The <code>addChild</code> method can add the original label system again.
	* However, the label path must be added to its previous parent tags (except the root tag), such as the original a.b.c tag.
	* If you want to add a d-tag on its basis, write a.b.c.d and locate the coordinates, as explained on the interface
	* </p>
	* <p>
	* You can pass in a Selement object or property value to operate
	* </p>
	* @throws IOException
	* @throws DocumentException
	* 
	* @see net.noyark.www.interf.XMLDomFile#addChild(String, String, int...)
	*/
	public Element addChild(String element,String text,int... index) 
																throws IllegalMappingException,
																IndexLengthException, IOException,
																DocumentException {
		if(element.split(POINT) .length != 1) {
			Element e = XMLHelper.searchAddElement(element, index, root);
			e.addText(text);
			synchronized (manager) {
				manager.whenChildTheElementIndex(element,e, index);
			}
			return e;
		}else {
			Element e = root.addElement(element);
			synchronized (manager) {
				manager.whenChildTheElementIndex(element,e, index);
			}
			return e;
		}
	}
	/**
	  * <p>
	  * The <code>save</code> method is used to completely save an xml file. When the tag is added,
	  * can not be saved immediately, you must use it to save the xml
	  * </p>
	  * <p>
	  * Because it will overwrite your non-needed files, resulting in less than expected results
	  * </p>
	  * @throws IOException will throw this exception if you have errors in reading and writing
	  * @see net.noyark.www.interf.XMLDomFile#save()
	*/
	
	public void save() 
							throws IOException {
		try {
			fos = new FileOutputStream(xmlFile);
			writer = new XMLWriter(fos,OutputFormat.createPrettyPrint());
		} catch (Exception e) {
			e.printStackTrace();
		}
			writer.write(doc);
			writer.close();	
	}
	/**
	* <p>
	* The <code>save</code> method is used to completely save an xml file.
	* When the tag is added,can not be saved immediately, you must use it 
	* to save the xml, the same, it can be added to the output stream
	* </p>
	* <p>
	* Because it will overwrite your non-needed files, resulting in less than 
	* expected results
	* </p>
	* @throws IOException will throw this exception if you have errors in reading and writing
	* @param fos the out put stream
	* 
	* @see net.noyark.www.interf.XMLDomFile#save(FileOutputStream)
	*/
	public void save(FileOutputStream fos) 
			throws IOException {
			try {
				writer = new XMLWriter(fos,OutputFormat.createPrettyPrint());
				} catch (Exception e) {
					
				}
			writer.write(doc);
			writer.close();	
	}
		
	/**
	* <p>
	* The <code>isExists</code> method can determine if a file 
	* exists and returns a boolean value
	* </p>
	* @return file exists
	* @see net.noyark.www.interf.XMLDomFile#isExists()
	*/
	public boolean isExists() {
		return xmlFile.exists();
	}
	/**
	* <p>
	* The <code>elementExists</code> method can 
	* determine if a tag is created.
	* Presupposition exists to prevent data errors 
	* from being overwritten by files again
	* </p>
	* @param element tag path
	* @param index tag positioning
	* @throws IllegalMappingException when the element is split
	* When the length of the index does not match
	* @throws IndexLengthException will throw an exception when there is a value in the index that is outside the bounds of the label.
	* @throws DocumentException
	* 
	* @see {@link net.noyark.www.interf.XMLDomFile#elementExists(SElement)}
	*/
	public boolean elementExists(String element,int... index) 
																		throws IllegalMappingException, IndexLengthException, DocumentException {
		XMLHelper.throwIndexLengthException(index, element, file);
		String[] elements = element.split(POINT);
		XMLHelper.throwMappingException(elements, index,0);
		Element e = XMLHelper.searchElement(elements,index, root);
		return e != null;
	}
	/**
	 * <p>
	* setElementAttribut can accurately set the properties of the tag.
	* If you need to modify the properties of the tag, you can use it 
	* to solve the problem of the tag being overwritten. If you don't 
	* have this property, you can also add it. It is generally used in 
	* conditional judgment. .
	* </p>
	* @param element tag path
	* @param name attribute name
	* @param value attribute value
	* @param index positioning
	* @deprecated This method has a huge vulnerability and is not recommended.
	*/
	@Deprecated
	public void setElementAttribut(String element, String name, String value, int... index) {
		String[] elements = element.split(POINT);
		Element e = XMLHelper.searchElement(elements, index, root);
		e.addAttribute(name, value);
	}
	
	
	
	/**
	* <p>
	* The <code>addElementText</code> method adds text information to the 
	* specified element.but will not overwrite the original text information, 
	* and will further follow the original text information.
	* </p>
	* @param element tag path
	* @param text text label
	* @param index tag coordinates
	* 
	* @see XMLDomFile#addElementText(String, String, int...)
	*/
	public void addElementText(String element, String text, int... index) {
		String[] elements = element.split(POINT);
		if(elements.length >1) {
			Element e = XMLHelper.searchElement(elements, index, root);
			getSElement(e).setText(text);
			e.addText(text);
		}else {
			getSElement(root).setText(text);
			root.element(element).addText(text);
		}
	}
	/**
	* <p>
	* <code>load</code> method can load files
	* </p>
	* @deprecated does not need to use the load method to save directly
	*/
	@Deprecated
	public void load(){
		xmlFile = new File(file);
	}


	/**
	* <p>
	* The <code>getMaxPos</code> method gets the maximum value that 
	* the specified element index can take.is used to determine if 
	* your value is out of bounds, if the segmentation length of the 
	* element does not match the length of the index +1 will throw an 
	* illegal mapping exception.
	* </p>
	* @param element tag path
	* @param index coordinate point of the parent tag
	* @return specifies the maximum value that the element index can achieve
	* @throws IllegalMappingException when the element is split
	* When the length of the index does not match
	* 
	* @see XMLDomFile#getMaxPos(String, int...)
	*/
	public int getMaxPos(String element, int... index) throws IllegalMappingException {
		String[] elements = element.split(POINT);
		if(index.length>0) {
			XMLHelper.throwMappingException(elements, index, 1);
			Element e = XMLHelper.element(elements, index, root);
			return e.elements(elements[elements.length-1]).size()-1;
		}else {
			return root.elements(element).size()-1;
		}
		
	}
	
	/**
	* <p>
	* <code>setElementsText</code> is a batch modification information, 
	* the basic principle is no different from setElementText.
	* </p>
	* @param element tag path
	* @param text tag text
	* @param index The coordinates of the parent tag
	* @throws IndexLengthException will throw an exception when there is a value in the index that is outside the bounds of the label.
	* @throws IllegalMappingException when the element is split
	* When the length of the index does not correspond to +1
	* @throws DocumentException
	* 
	* @see {@link XMLDomFile#setElementsText(String, String, int...)}
	*/
	@SuppressWarnings("unchecked")
	public void setElementsText(String element, String text, int... index) throws IndexLengthException, IllegalMappingException, DocumentException {
		String[] elements = element.split(POINT);
		XMLHelper.throwIndexLengthException(index, element, text);
		XMLHelper.throwMappingException(elements, index, 1);
		if(index.length!=0) {
			Element e = XMLHelper.element(elements, index, root);
			List<Element> list = e.elements(elements[elements.length-1]);
			for(int i = 0;i<list.size();i++) {
				list.get(i).setText(text);
			}
		}else {
			List<Element> list = root.elements();
			for(Element e:list) {
				e.setText(text);
			}
		}
	}
	/**
	* <p>
	* <code>getText</code> method can get the text in the specified label
	* </p>
	* @param element
	* @param index
	* @return the text that not trim
	* @throws IllegalMappingException
	* 
	* @see {@link XMLDomFile#getText(String, int...)}
	*/
	public String getText(String element, int... index) {
		String[] elements = element.split(POINT);
		Element e =  XMLHelper.searchElement(elements, index, root);
		return e.getText();
	}
	/**
	* <p>
	* <code>getAttribute</code> returns a hash table that stores all 
	* tag attributes
	* </p>
	* @param element the element path
	* @param index the index of element,like others
	* @return the key and values(attribute name and attribute value)
	* 
	* @see XMLDomFile#getAttribute(String, int...)
	*/
	public HashMap<String, String> getAttribute(String element, int... index) {
		String[] elements = element.split(POINT);
		Element e =  XMLHelper.searchElement(elements, index, root);
		HashMap<String, String> map = new HashMap<String, String>();
		for(int i=0;i<e.attributeCount();i++) {
			String name = e.attribute(i).getName();
			String value = e.attributeValue(name);
			map.put(name, value);
		}
		return map;
	}
	/**
	* <p>
	* The <code>elementHaveAttribute</code> method can determine
	*  if the tag has attributes.
	* and return a boolean value
	* </p>
	* @param element tag path
	* @param index positioning
	* @return Is there a tag
	* @see net.noyark.www.interf.XMLDomFile#elementHaveAttribute(String, int...)
	*/
	public boolean elementHaveAttribute(String element, int... index) {
		Map<String,String> map = getAttribute(element, index);
		return !map.isEmpty();
	}
	/**
	 * <p>
	 * 	This method can determine whether the element has an
	 * 	attribute of this name,and return the value of the boolean
	 * 	type according to this.
	 * </p>
	 * @param element the element object's path
	 * @param name the attribute's name
	 * @param index the indexs of the element object
	 * @return whether have the attribute
	 */
	public boolean elementHaveAttribute(String element, String name,int... index) {
		Map<String,String> map = getAttribute(element, index);
		return map.get(name)!=null;
	}
	/**
	* <p>
	* override the <code>toString</code> method for use in object pools
	* </p>
	* @return the object information
	* 
	* @see Object#toString()
	*/
	@Override
	public String toString() {
		return this.file+","+this.root.getName();
	}
	/**
	* <p>
	* <code>deleteElement</code> can delete the specified element
	* </p>
	* @param element tag path
	* @param index tag coordinates
	* @throws IllegalMappingException This exception is thrown when the length of the element is split and the length of the index is different.
	* @see XMLDomFile#deleteElement(String, int...)
	*/
	public void deleteElement(String element,int...index) throws IllegalMappingException{
		String[] elements = element.split(POINT);
		XMLHelper.throwMappingException(elements, index, 0);
		Element e = XMLHelper.searchElement(elements, index, root);
		e.remove(e);
	}
	/**
	 * <p>
	 * 	This method can delete the specified attribute of the
	 * 	specified element
	 * </p>
	 * @param element the element object's path
	 * @param name the attribute's name
	 * @param index the indexs of the element
	 * @throws IllegalMappingException you can see {@link IllegalMappingException}
	 * 
	 * @see XMLDomFile#deleteElementAttribut(String, String, int...)
	 */
	public void deleteElementAttribut(String element,String name,int... index) throws IllegalMappingException{
		XMLHelper.throwMappingException(element.split(POINT), index, 0);
		Element e = XMLHelper.searchAddElement(element, index, root);
		Attribute attribute = e.attribute(name);
		e.remove(attribute);
	}
	/**
	 * <p>
	 * 	This method can get an element object by element
	 * 	coordinates and path
	 *</p>
	 * @param element the element path
	 * @param the element indexs
	 * @return the element object
	 */
	public Element getElement(String element, int... index) throws IllegalMappingException {
		return XMLHelper.searchElement(element.split(POINT), index, root);
	}
		/**
		 * <p>
		 * This method can get the name of the xml file
		 * </p>
		 * @return the file name
		 */
	public String getFile() {
		return xmlFile.getName();
	}
	/**
	* <p>
	* The <code>setElementsAttribut</code> method can modify the specified path in batches.
	* Attributes of batch elements
	* </p>
	* @param element the element path
	* @param name the attribute name
	* @param value the attribute value
	* @throws IllegalMappingException see {@link IllegalMappingException}
	*/
	public void setElementsAttribut(String element, String name, String value) 
			throws IllegalMappingException {
		List<Element> list = getElements(element);
		for(Element e:list) {
			e.addAttribute(name, value);
		}
	}
	/**
	* <p>
	* The <code>getElementsTextThatTrim</code> method can get one via the specified tag path.
	* There is a set of key-value pairs for the path and corresponding text, and the surrounding whitespace characters are removed.
	* </p>
	* @param element the element path
	* @return the element paths and texts
	*/
	public HashMap<String,String> getElementsTextThatTrim(String element) {
		HashMap<String, String> texts = new HashMap<String,String>();
		List<Element> elements = getElements(element);
		for(Element e:elements) {
			texts.put(e.getPath().replace(SLASH,"."),e.getText());
		}
		return texts;
	}
	/**
	* <p>
	* <code>deleteElements</code> can delete the specified set of elements
	* </p>
	* @param element tag parent path, path to the parent tag of the deleted tag
	*/
	
	public void deleteElements(String element) {
		List<Element> elements = getElements(element);
		for(Element e:elements) {
			e.remove(e);
		}
	}
	/**
	* Set the encoding rules for the document
	* @param encoding encoding
	*/
	public void setXMLEncoding(String encoding) {
		doc.setXMLEncoding(encoding);
	}
	/**
	* Get this file that already exists
	* @return is default file
	*/
	public boolean isDefault() {
		return isDefault;
	}
	/**
	* Get all element objects
	* @return all of the element objects 
	* except the root element
	* @deprecated you can use the getAllElements method,this method's
	* 							name is not good.
	*/
	@Deprecated
		public List<Element> getAllElement(){
			List<Element> elements = new ArrayList<>();
			XMLHelper.getAll(doc, elements);
			return elements;
		}
		/**
		* Get all element objects
		* @return all of the element objects 
		* except the root element
		*/
		public List<Element> getAllElements(){
			return getAllElement();
		}
		/**
		* Get encoded information
		* @return
		*/
		public String getEncoding() {
			return doc.getXMLEncoding();
		}
		/**
		 * get the manager of this object
		 * @return the manager
		 */
		public Manager<Element> getManager(){
			return manager;
		}
		
		/**
		* <p>
		* <code>getElements</code> gets all elements of the subordinate below the path, via index
		* to locate
		* </p>
		* @param element tag path
		* @param index precise positioning
		* @return returns a collection of element objects
		* @throws IllegalMappingException when the element is split
		* When the length of the index does not correspond to +1
		*/
		@SuppressWarnings("unchecked")
		public List<Element> getElements(String element, int... index) throws IllegalMappingException {
			String[] elements = element.split(POINT);
			XMLHelper.throwMappingException(elements, index, 1);
			Element e = XMLHelper.element(elements, index, root);
			return e.elements(elements[elements.length-1]);
		}
		/**
		* <p>
		* <code>addAttribute</code> is a method for adding an Attribute to a specific tag.
		* Can accurately select xml tags and add specific Attributes to them
		* , of course, it is recommended that you can judge whether the tag exists in the Attribute is added
		* </p>
		* @param element is operated label
		* @param name Attribute name
		* @param name Attribute value
		* @param index tag positioning
		* @return is the label that is being manipulated
		* @throws IllegalMappingException throws an exception when the positioning and tag levels do not match, element.split(POINT).length != index.length
		* @throws DocumentException
		*/
		public Element addAttribute(String element,String name,String value,int...index) 
																												throws IllegalMappingException, IndexLengthException, DocumentException{
			XMLHelper.throwIndexLengthException(index, element, file);
			String[] elements = element.split(POINT);
			XMLHelper.throwMappingException(elements, index,0);
			Element e =  XMLHelper.searchElement(elements, index, root);
			return e.addAttribute(name, value);
		}
		/**
		* <p>
		* The <code>setElementName</code> method can modify the name of the tag, and its path format 
		* is the same as other methods.
		* </p>
		* </p>
		* You can use this method to more easily locate the name of the label you want to modify.
		* </p>
		* @param element tag path
		* @param newName new name
		* @param index positioning
		* @exception IllegalMappingException When the length of element split does not correspond to the length of index
		* @throws DocumentException see {@link DocumentException}
		*/
		public void setElementName(String element, String newName,int...index) 
																												throws IllegalMappingException, IndexLengthException, DocumentException {
			XMLHelper.throwIndexLengthException(index, element,file);
			String[] elements = element.split(POINT);
			XMLHelper.throwMappingException(elements, index,0);
			Element e = XMLHelper.searchElement(elements, index, root);
			e.setName(newName);
			getSElement(e).setPath(XMLHelper.getElementPath(e));
		}
		/**
		* <p>
		* The <code>setElementText</code> method can be used to precisely modify the text information inside the tag.
		* You can use it to make changes when making logical decisions.
		* The internal information of the tag must be modified under the premise of the existence of this Element, otherwise invalid
		* </p>
		* @param element tag path
		* @param element tag text
		* @param index tag positioning
		*/
		public void setElementText(String element, String text, int... index) {
			String[] elements = element.split(POINT);
			if(elements.length >1) {
				Element e = XMLHelper.searchElement(elements, index, root);
				getSElement(e).setText(text);
				e.setText(text);
			}else {
				getSElement(root).setText(text);
				root.element(element).setText(text);
			}
		}
		
		
		/**
		* <p>
		* <code>getElementsByGroup</code> can be grouped to get element objects
		* Separated by ',', such as: a.b.c, a.b.d, c.d.g
		* </p>
		* @param pathGroup path
		* @return returns the label set corresponding to the path (name corresponding - label collection)
		* @since EQuery 021
		*/
	public Table<String, Element> getElementsByGroup(String pathGroup){
		List<Element> elements = getAllElement();
		Table<String, Element> table = new NodeTable<>();
		String[] paths = pathGroup.split(",");
		for(String path:paths) {
			List<Element> elementList = new ArrayList<>();
			for(Element e:elements) {
				String realPath = XMLHelper.getElementPath(e);
				if(path.equals(realPath)) {
					elementList.add(e);
				}
			}
			table.putAll(path, elementList.toArray(new Element[elements.size()]));
		}
		return table;
	}
	
	
	/**
	* <p>
	* <code>getBrotherElementOn</code> gets the last sibling element
	* If there is no next element, an array out of bounds exception will be thrown
	* </p>
	* @param path the element path
	* @param index the indexs of the path
	* @return the big brother element
	* @throws IllegalMappingException
	*/
	public Element getBrotherElementOn(String path,int...index) throws IllegalMappingException {
		return getBrotherElement(path, -1, index);
	}
	/**
	 * <p>
	 * 	The meaning of this method is to get the child elements of its
	 * 	parent class,which is essentially the set of sibling elements 
	 * 	of the current element.
	 * </p>
	 * @param path the element object's path
	 * @param index the indexs of the element's object 
	 * @return the element list
	 * @throws IllegalMappingException
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getChilds(String path,int...index) throws IllegalMappingException {
		Element element = getParent(path, index);
		List<Element> list = element.elements();//获得子元素们
		return list;
	}
	/**
	 * <p>
	 * 	This can get the parent element of the current element
	 * </p>
	 * @param path the child(this) element path
	 * @param index the child(this) element
	 * @return the element object
	 * @throws IllegalMappingException
	 */
	public Element getParent(String path,int...index) throws IllegalMappingException {
		path = path.substring(0,path.lastIndexOf("."));//get the parent path
		int[] smallIndex = new int[index.length-1];
		for(int i = 0;i<smallIndex.length;i++) {
			smallIndex[i] = index[i];//get parent indexs
		}
		Element element = getElement(path, smallIndex);//get parent element object
		return element;
	}
	/**
	 * <p>
	 *		This method can get the next brother element,if
	 *		there is no next element,it will throw an array
	 *		out of bounds exception.
	 * </p>
	 * @param path the element object's path
	 * @param index the indexs of the element
	 * @return the brother element object (next this element)
	 * @throws IllegalMappingException 
	 */
	public Element getBrotherElementUnder(String path,int...index) throws IllegalMappingException{
		return getBrotherElement(path, 1, index);
	}
	/**
	 * This method can get the adjacent few bother elements
 	 * @param path the element object's path
	 * @param ind Adjacent number,positive number is below,
	 * 				negative number is above,such as 'a b c d e',with c as
	 * 				the origin,a is -1,d is 1.
	 * @param index
	 * @return
	 * @throws IllegalMappingException
	 */
	public Element getBrotherElement(String path,int ind,int...index) throws IllegalMappingException {
		String name = path.substring(path.lastIndexOf(".")+1);
		List<Element> list = getChilds(path, index);
		for(int i = 0;i<list.size();i++) {//select from childs
			if(list.get(i).getName().equals(name)) {
				if(i>0) {
					return list.get(i+ind);
				}
			}
		}
		return null;
	}
	/**
	 * <p>
	 * 	This method can get the first few elements of its
	 * 	class with its center,that is, the brother element above
	 * 	it.
	 * </p>
	 * @param path
	 * @param index
	 * @return
	 * @throws IllegalMappingException
	 */
	public List<Element> getBrotherElementsOn(String path,int...index) throws IllegalMappingException{
		List<Element> brothers = new ArrayList<>();
		List<Element> list = getChilds(path, index);
		int indexOfThis = foundChildIndex(path, index);
		if(indexOfThis!=-1) {
			for(int i = 0;i<indexOfThis;i++) {
				brothers.add(list.get(i));
			}
		}
		return brothers;
	}
	/**
	 * <p>
	 * 	Get the first position of the child element of
	 * 	the parent element of the element.
	 * </p>
	 * @param path	the child element(this=child)
	 * @param index the child element's indexs(this=child)
	 * @return This element is in the first few positions of
	 * 				its class,regardless of the name
	 * @throws IllegalMappingException
	 */
	public int foundChildIndex(String path,int...index) throws IllegalMappingException {
		String name = path.substring(path.lastIndexOf(".")+1);
		List<Element> list = getChilds(path, index);
		int indexOfThis = -1;
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getName().equals(name)) {
				indexOfThis = i;
				return indexOfThis;
			}
		}
		return indexOfThis;
	}
	/**
	 * <p>
	 * This method can get all the brother elements below
	 * </p>
	 * @param path the element object's path
	 * @param index the indexs of the this element
	 * @return the element object set
	 * @throws IllegalMappingException
	 */
	public List<Element> getBrotherElementsUnder(String path,int... index) throws IllegalMappingException{
		List<Element> brothers = new ArrayList<>();
		int ind = foundChildIndex(path, index);
		List<Element> list = getChilds(path, index);
		if(ind!=-1) {
			for(int i = ind+1;i<getParent(path, index).elements().size();i++) {
				brothers.add(list.get(i));
			}
		}
		return brothers;
	}
	/**
	 * <p>
	 * 	The element name selector directly specifies the element name.
	 * 	No path is required.You can select all the elements related to 
	 * 	this name.
	 * </p>
	 * @param elementName the element name,not PATH!!!
	 * @return the element object set
	 */
	public List<Element> getElementsByName(String elementName){
		List<Element> all = getAllElement();
		List<Element> byName = new ArrayList<>();
		for(Element e:all) {
			if(e.getName().equals(elementName)) {
				byName.add(e);
			}
		}
		return byName;
	}
	/**
	 * <p>
	 * 	Namespace selector which lets you select all
	 * 	the elements through the namespace
	 * </p>
	 * @param prefix the prefix of the namespace
	 * @param uri the uri of the namespace
	 * @return the element object set
	 */
	public List<Element> getElementsByNameSpace(String prefix,String uri){
		List<Element> all = getAllElement();
		List<Element> byNameSpace = new ArrayList<>();
		for(Element e:all) {
			Namespace namespace = e.getNamespace();
			if(namespace.getPrefix().equals(prefix)&&namespace.getURI().equals(uri)) {
				byNameSpace.add(e);
			}
		}
		return byNameSpace;
	}
	/**
	 *  Namespace selector,which can be selected by prefix or uri
	 *  to have an element object containing this namespace
	 * @param isURI If true,it is searched by uri.If it is
	 * 				false,it is searched by prefix
	 * @param uriOrprefix the uri or prefix
	 * @return the element objects set
	 */
	public List<Element> getElementsByNameSpace(String uriOrprefix,boolean isURI){
		List<Element> all = getAllElement();
		List<Element> byNameSpace = new ArrayList<>();
		for(Element e:all) {
			Namespace namespace;
			if(isURI) {
				namespace = e.getNamespaceForURI(uriOrprefix);
			}else {
				namespace = e.getNamespaceForPrefix(uriOrprefix);
			}
			if(namespace!=null) {
				byNameSpace.add(e);
			}
		}
		return byNameSpace;
	}
	/**
	 *	Add more than the original,such as a.b.c.d.e/f/g,already
	 *	a.b.c.d,you can use this path as the parent path
	 *	of the element,index is the coordinates of the parent 
	 *	path,a.b.c.d is the four parent path,then the coordinates,
	 *	Corresponding to these 4 parent paths
	 *		IF SEE THESE EXCEPTION ,PLEASE SEE THEIR @ SEE
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 */
	public List<Element> addElementsIfExistsParent(String element,List<String> text,int... index) throws IllegalMappingException, IndexLengthException, IOException, DocumentException {
		String[] elements = element.split(POINT);
		String[] childs = elements[elements.length-1].split(SLASH);
		String parent = element.substring(0,element.lastIndexOf("."));
		List<Element> allRegChilds = new ArrayList<>();
		for(int i = 0;i<childs.length;i++) {
			if(i<text.size()) {
				Element e = addChild(parent+"."+childs[i],text.get(i),index);
				allRegChilds.add(e);
			}else {
				Element e = addChild(parent+"."+childs[i]," ",index);
				allRegChilds.add(e);
			}
		}
		return allRegChilds;
	}
		/**
	 * <code>getElementsByText</code> can select elements by the text
	 * 	embedded in the element to get the collection of elements
	 * @param text the element text
	 * @return the element object set
	 */
	public List<Element> getElementsByText(String text){
		List<Element> all = new ArrayList<>();
		List<Element> elements = getAllElement();
		for(Element e:elements) {
			if(e.getTextTrim().equals(text)) {
				all.add(e);
			}
		}
		return all;
	}
	/**
	 * This method can select an element by id and
	 * return a collection of List element objects,
	 * Note that the element name standard of id is ID
	 * @param id the element id attribute's name
	 * @return the element objects list
	 */
	public List<Element> getElementsByID(String id){
		return getElementsByAttribut("ID", id);
	}
	/**
	 *	This method can read another file of the specified 
	 *	element across files
	 *		IF SEE THESE EXCEPTION ,PLEASE SEE THEIR @ SEE
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 * @throws DocumentException 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Element readElementOtherInThis(String otherFile,String elements,int... indexs) throws DocumentException, IllegalMappingException, IndexLengthException, IOException {
		XMLDomFile simpleML =  InstanceQueryer.getDefaultXml(otherFile,this);
		Element e = simpleML.getElement(elements, indexs);
		if(e!=null) {
			List<Attribute> aList = e.attributes();
			Element thisE = addElement(XMLHelper.getElementPath(e), e.getText());
			for(Attribute a:aList) {
				thisE.add(a);
			}
			return e;
		}
		return null;
	}
	/**
	 * The wildcard selector can directly write the
	 *  path plus coordinates:
	 *  	Format:a[0].b[0].c[0],query is an enumerated
	 *  	type,the meaning of participating in the
	 *  	enumeration type
	 * @param element the element path with index
	 * @param query the enum. type,Specify the type
	 * 				of selector you want
	 * @return the element object set
	 * @see Query
	 */
	public List<Element> $(Query query,String element){
		String[] paths = element.split(POINT);
		List<Integer> indexs = new ArrayList<>();
		StringBuilder truly = new StringBuilder();
		for(String p:paths) {
			if(p.indexOf("[")!=-1) {
				String s = p.substring(p.indexOf("[")+1,p.indexOf("]"));
				indexs.add(Integer.parseInt(s));//get the indexs
				truly.append("."+p.substring(0,p.indexOf("[")));
			}
		}
		String t = truly.substring(1);
		Integer[] ind = indexs.toArray(new Integer[indexs.size()]);
		int[] indInt = new int[ind.length];
		for(int i = 0;i<indInt.length;i++) {
			indInt[i] =ind[i];
		}
		return XMLHelper.$(query, this, t,indInt);
	}
	/**
	 * Wild selector This selector needs to specify coordinates
	 * The path is the same as normal.The basic function is the same as
	 * the previous wildcard method
	 * @param element the element path
	 * @param query the enum. type,Specify the type
	 * 				of selector you want
	 * @param index the element indexs
	 * @return the element object set
	 * @see Query
	 * @throws IllegalMappingException 
	 */
	public List<Element> $(String element,Query query,int...index) throws IllegalMappingException{
		return XMLHelper.$(query, this, element, index);
	}

	/** 
	 * This method is used to adapt the selector
	 * The actual function is the same as the element
	 * object,but if you use the framework,you must use
	 * it.
	 * @param e the parent element object
	 * @param elementName this element's name
	 * @throws IOException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 */
	public Element addElementAfter(Element e,String elementName) throws IllegalMappingException, IndexLengthException, IOException {
		Element element = e.addElement(elementName);
		//路径，
		manager.addSequeElement(XMLHelper.getElementPath(element), element,0);
		return element;
	}
	/**
	 * If you want to add a doctype to your file
	 * you can use this method to add a doctype
	 * like html.
	 * @param name the doctype name
	 * @param publicId the doctype's public id
	 * @param systemId the doctype's system id
	 * @return the document of this
	 */
	public Document addDOCTYPE(String name,String publicId,String systemId) {
		return doc.addDocType(name, publicId, systemId);
	}
	/**
	 * you can use this method to get the root element
	 * @return the root element object
	 */
	public Element getRootElement() {
		return root;
	}
	/**
	 * <p>
	 * 	Added the operation of returning map on
	 * 	the basis of <code>addElements</code>,The
	 * 	map stores the element name and the corresponding
	 * 	element element.The actual function is the same as
	 * 	the <code>addElements</code> method
	 * </p>
	 * 	YOU CAN SEE THESE EXCEPTION ON @ SEE
	 * @throws DocumentException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 * @throws IOException
	 */
	public Map<String, Element> addElementsMap(String element,String... texts) throws IllegalMappingException, IndexLengthException, IOException, DocumentException{
		String[] elements = element.split(POINT);
		String[] childs = elements[elements.length-1].split("/");
		List<Element> es = addElements(element, texts);
		Map<String, Element> map = new HashMap<>();
		for(int i = 0;i<es.size();i++) {
			map.put(childs[i], es.get(i));
		}
		return map;
	}
	/**
	 * <p>
	 * This method can connect all the elements of a file
	 * except the root element to the orginal element in 
	 * the root of the file
	 * </p>
	 * 		YOU CAN SEE THESE EXCEPTION ON @ SEE
	 * @param xdf the XMLDomFile object that you want to add in this
	 * @throws IOException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 * @throws DocumentException 
	 */
	public void join(XMLDomFile xdf) throws IllegalMappingException, IndexLengthException, IOException, DocumentException {
		Tree tree = xdf.getTree();
		for(int i = 0;i<tree.getBranchesNumber();i++) {
			Branch branch = tree.getBranch(i);
			for(int j = 0;j<branch.getElementNumber();j++) {
				Element element = branch.getElement(j);
				if(getPointPath(element).split(POINT).length==1) {
					addElement(getPointPath(element), element.getText()==""?" ":element.getText());
				}else {
					int[] now = xdf.getIndexs(element);
					int[] indexs = new int[now.length-1];
					for(int index = 0;index<indexs.length;index++) {
						indexs[index] = now[index];
					}
					addChild(getPointPath(element),element.getText()==""?" ":element.getText(),indexs);
				}
			}
		}
	}
	/**
	 * <p>
	 * you can get the tree of this xml file
	 * </p>
	 * @return the tree instance
	 */
	public Tree getTree() {
		return new ElementTree(this);
	}
	/**
	 * you can get the table manager of the tree of this
	 * xml file
	 * @return
	 */
	public WaterMan getWaterMan() {
		return new TreeWaterMan(getTree());
	}
	/**
	 * <p>
	 * Change the normal Element object 
	 * return path to a compatible point path
	 * </p>
	 * @param e the SElement object
	 * @return the point path
	 * @see XMLHelper
	 */
	public String getPointPath(Element e) {
		return XMLHelper.getElementPath(e);
	}
	/**
	 * This method can export the xml file from the 
	 * database.In the xml file,the field name of the database
	 * table is represented by the name + 's' in the label name
	 * The element label name is represented by the field name in 
	 * the label,and there is a value under the field,the default
	 * database.The connection file is 'application.properties'.If
	 * you need a custom file,you can create a class taht extends the
	 * ConnectionBase class and then add a Class instance to the method 
	 * @param table the database's table name
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static XMLDomFile getXMLFromDB(String table,String file,@SuppressWarnings("unchecked") Class<? extends ConnectionBase>... connectionClass) throws SQLException, IllegalMappingException, IndexLengthException, IOException, InstantiationException, IllegalAccessException {
		Connection connection = null;
		if(connectionClass.length==0) {
			connection = new ConnectApplicationDB().getConn();
		}else {
			ConnectionBase conn = connectionClass[0].newInstance();
			connection = conn.getConn();
		}
		PreparedStatement ps = connection.prepareStatement("select * from "+table);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData data = rs.getMetaData();
		XMLDomFile xdf = InstanceQueryer.getXMLQuery(file,"sql", true);
		for(int i = 0;i<data.getColumnCount();i++) {
			String name = data.getColumnName(i);
			Element e = xdf.addElement(name+"s", " ");
			while(rs.next()) {
				Element child = xdf.addElementAfter(e,name);
				child.addText(rs.getObject(i)+"");
			}
		}
		return xdf;
	}
	/**
	 * The same function as <code>addElement</code>,it can return
	 * all the added elements here
	 * @return the elements that are added
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException
	 * @see XMLDocument#addElement(String, String) 
	 */
	public List<Element> addElementWithAll(String element,String text) throws IllegalMappingException, IndexLengthException, IOException, DocumentException{
		String[] paths = element.split(POINT);
		StringBuilder builder = new StringBuilder(paths[0]);
		List<Element> all = new ArrayList<Element>();
		Element e = addElement(paths[0], " ");
		all.add(e);
		for(int i = 1;i<paths.length;i++) {
			builder.append("."+paths[i]);
			if(i==paths.length-1) {
				e = addChild(builder.toString(),text,getIndexs(e));
			}else {
				e = addChild(builder.toString()," ",getIndexs(e));
			}
			all.add(e);
		}
		return all;
	}
	/**
	 * It can get the docType of this document
	 * @return the DocumentType object
	 */
	public DocumentType getDocType() {
		return doc.getDocType();
	}
	/**
	 * It can set the doc type
	 * @param docType the document type object
	 */
	public void addDocType(DocumentType docType) {
		doc.setDocType(docType);
	}
	/**
	 * get the document object;
	 * @return the document object
	 */
	public Document getDocument() {
		return doc;
	}
	/**
	 * This method can get the element objects by the attributes
	 * name.
	 * @param attributeName the attibute names
	 * @return the element object list
	 */
	public List<Element> getElementsByAttributes(String... attributeName) {
		List<Element> list = getAllElements();
		return XMLHelper.getElementsByAttributes(list, attributeName);
	}
	/**
	 * It can get the elements by the path and the attributes.
	 * @param path the element path
	 * @param attributeName the attribute names
	 * @return the element object list
	 */
	public List<Element> getElementsByAttributesAndPath(String path,String...attributeName) {
		List<Element> list = getAllElements();
		List<Element> theseName = new ArrayList<>();
		for(Element e:list) {
			String pointPath = getPointPath(e);
			if(pointPath.equals(path)) {
				theseName.add(e);
			}
		}
		return XMLHelper.getElementsByAttributes(theseName,attributeName);
	}
	/**
	 * This method can get the element objects by its name and attribute names
	 * @param name the element's tag name
	 * @param attributeName the attribute name
	 * @return the element objects
	 */
	public List<Element> getElementsByAttributesAndName(String name,String...attributeName) {
		List<Element> elements = getElementsByName(name);
		return XMLHelper.getElementsByAttributes(elements, attributeName);
	}
	/**
	 * This method can get the elements by the name and the attribute name,
	 * and the attribute's number.
	 * if you want to select element from only small list,you can use 
	 * <code>XMLHelper.getElementsByNameStrict</code>
	 * @param name the element name
	 * @param attribute the attribute names
	 * @return the element objects
	 */
	public List<Element> getElementsByAttributesAndNameOnlyThese(String name,String... attribute){
		List<Element> elements = getElementsByName(name);
		return XMLHelper.getElementsByNameStrict(elements,attribute);
	}
	/**
	 * This method can get the elements by the path and the attribute name,
	 * and the attribute's number.
	 * if you want to select element from only small list,you can use 
	 * <code>XMLHelper.getElementsByNameStrict</code>
	 * @param path the element point path
	 * @param attribute the attribute names
	 * @return the element objects
	 * 
	 * @see XMLHelper#getElementsByNameStrict(List, String[])
	 */
	public List<Element> getElementsByAttributesAndPathOnlyThese(String path,String... attribute){
		List<Element> elements = getElements(path);
		return XMLHelper.getElementsByNameStrict(elements,attribute);
	}
	/**
	 * The last element name will be added as an array for
	 * the specified number of times,provided that the previous parent
	 * node already exists.
	 * @param element the element path
	 * @param os the array texts
	 * @param indexs the indexs of parent
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void addArray(String element,Object[] os,int... indexs) throws IllegalMappingException, IndexLengthException, IOException, DocumentException {
		for(Object o:os) {
			addChild(element,o.toString(),indexs);
		}
	}
	/**
	 * You can use epath expressions
	 * to select elements,which is a simpler
	 * version of xpath
	 * <br>
	 * EXPRESSIONS SYNTAX v001:
	 * <br>
	 *	all element:
	 * 	all;
	 * <br>
	 * 	Common selection: 
	 * 	a[0].b[0].c[0] [selector options];
	 * 	<br>
	 * 	Coordinated:
	 * 	a[index].b[index].c[index] [selector options];
	 * <br>
	 * 	Without coordinates:
	 * 	a.b.c [selector options];
	 *  <br>
	 * 	only by text:
	 * 	text [text];
	 * <br>
	 * 	Name selection:
	 * 	name [name]
	 * <br>
	 * 	Namespace:
	 * 	path prefix/uri [prefix/uri];
	 * 	path prefix-uri [namespace];
	 * <br>
	 * 	attribute by name and value:
	 * 	path key=value,key=value [key=value];
	 * <br>
	 * 	attribute by attribute name
	 * 	path name,name [key];
	 * <br>
	 * 	attribute by attribute name and key name
	 * 	name name,name [key];
	 * <br>
	 * 	attribute by only these:
	 * 	name name,name [key,only];
	 * 	path key=value,key=value [key=value];
	 * <br>
	 * 	by text and name:
	 * 	name text [text,name];
	 * <br>
	 * 	by text and path:
	 * 	path text [text];
	 * <br>
	 * 	Sequenital pirority name>only>[uri-prefix/namespace>text>key=value/key/value] <br>
	 * 	complex:<br>
	 * 	path uri-prefix text [path,namespace,text];<br>
	 * 	path namespace-uri/prefix text [path,uri/prefix,text];<br>
	 * 	path text key=value [path,uri/prefix,key=value];<br>
	 * 	path  text key=value [path,text,key=value];<br>
	 * 	name namespace text [name,uri/prefix,text];<br>
	 * 	name text key=value [name,text,key=value];<br>
	 * 	name text key=value [name,text,key=value];<br>
	 * 	name text key [name,text,key];<br>
	 * 	name uri-prefix text [namespace];
	 * 	name text key [name,only,text,key];
	 * 	etc.
	 * @param expressions the epath expressions
	 * @return the elements  object list
	 * @see Query
	 */
	public List<Element> EPathSelector(String expressions) {
		try {
			String[] childSynax = expressions.split(" ");
			if(childSynax.length==1) {
				if(childSynax[0].equals("all")) {
					return getAllElements();
				}else {
					Console.err("the 'all' synax is error");
				}
			}else if(childSynax.length==2) {
				String selectorOption = childSynax[1].substring(childSynax[1].indexOf("[")+1,childSynax[1].indexOf("]"));//get the second
				switch (selectorOption) {
					case "one":
						return $(Query.ELEMENT,childSynax[0]);
					case "elements":
						return $(Query.ELEMENTS,childSynax[0]);
					case "on":
						return $(Query.BROTHER_ON,childSynax[0]);
					case "under":
						return $(Query.BROTHER_UNDER,childSynax[0]);
					case "ons":
						return $(Query.BROTHERS_ON,childSynax[0]);
					case "unders":
						return $(Query.BROTHERS_UNDER,childSynax[0]);
					case "friends":
						return $(Query.FRIENDS,childSynax[0]);
					case "ids":
						return $(Query.ELEMENTS_ID,childSynax[0]);
					case "e_name":
						return $(Query.ELEMENTS_NAME,childSynax[0]);
					case "text":
						return $(Query.ELEMENTS_TEXT,childSynax[0]);
					case "parent":
						return $(Query.PARENT,childSynax[0]);
					case "name":
						if(childSynax[0].indexOf("[")!=-1) {
							Console.err("the [name] selector synax error:no index");
						}
						return getElementsByName(childSynax[0]);
					default:
						Console.err("the [selector] synax is error");
						break;
					}
			}else if(childSynax.length==3) {
				List<Element> get = new ArrayList<>(); 
				String firstOption = childSynax[0];//path
				String middleOption = childSynax[1];//text
				String selectorOption = childSynax[2].substring(childSynax[2].indexOf("[")+1,childSynax[2].indexOf("]"));//get the third
				return selectByThreeSynax(get, firstOption, middleOption, selectorOption);
			//new version
			}else if(childSynax.length>=4){
				String firstOption = childSynax[0];//path
				String selectorOption = childSynax[childSynax.length-1].substring(childSynax[childSynax.length-1].indexOf("[")+1,childSynax[childSynax.length-1].indexOf("]"));//get the third
				List<String> options = Arrays.asList(selectorOption.split(","));
				List<Element> allElement;
				if(options.contains("name")) {
					allElement = getElementsByName(firstOption);
				}else {
					allElement = getElements(firstOption);//get all middle option
				}
				boolean isOnly = false;
				for(int i=0;i<options.size();i++) {
					String option = options.get(i);
					if(option.equals("only")){
						isOnly = true;
					}else if(!option.trim().equals("name")&&!option.trim().equals("path")&&!option.trim().equals("only")){
						String text;
						if(isOnly) {
							text = childSynax[i-1]+"";
						}else {
							text = childSynax[i]+"";
						}
						if(text.indexOf("[")!=-1) {
							text = text.substring(text.indexOf("[")+1,text.indexOf("]"));
						}
						if(option.equals("namespace")) {
							String[] nss = text.split("-"); 
							allElement = getByNameSpaceFromElements(allElement, nss[0],nss[1]);
						}else if(option.equals("prefix")){
							allElement = getByNameSpaceFromElements(allElement,text,null);
						}else if(option.equals("text")) {
							allElement = getByTextFromElements(allElement, text);
						}else if(option.equals("key=value")){
							String[] kv = text.split(",");
							allElement = getByAttributeFromElements(allElement,kv);
						}else if(option.equals("key")) {
							String[] keys = text.split(",");
							allElement = getByAttributeKeyFromElements(allElement,isOnly,true,keys);
						}else if(option.equals("value")) {
							String[] values = text.split(",");
							allElement = getByAttributeKeyFromElements(allElement, isOnly,false, values);
						}
					}
				}
				return allElement;
			}
		}catch(StringIndexOutOfBoundsException e2) {
			Console.err("java StringIndexOutOfBoundsException exception error:the element don't have index");
		}catch(IndexOutOfBoundsException e1) {
			Console.err("java IndexOutOfBoundsException exception error:the element in the indexs do not exist");
		}catch(SynaxException e3) {
			Console.err(e3.getMessage());
		}
		Console.err("error:");
		throw new SynaxException("the synax is error");
	}
	/**
	 * This method can parse the file that have epath
	 * @param file the epath file (.equery)
	 * @return the NodeTable(Line and Results)
	 * @throws IOException
	 * @throws DocumentException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 */
	public NodeTable<Integer,Element> sourceEPathFile(String file) throws IOException, IllegalMappingException, IndexLengthException, DocumentException{
		String[] expressions;
		if(file.endsWith(".xml")) {
			XMLDomFile xdf = InstanceQueryer.getDefaultXml(file,this);
			xdf.clearMapping();
			List<Element> epathes = xdf.getElements("epath");
			expressions = new String[epathes.size()];
			for(int i = 0;i<epathes.size();i++) {
				expressions[i] = epathes.get(i).getText();
			}
		}else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringBuilder builder = new StringBuilder();
			String expression = null;
			while((expression=reader.readLine())!=null) {
				builder.append(expression);
			}
			expressions = builder.toString().split(";");
			reader.close();
		}
		NodeTable<Integer, Element> table = new NodeTable<>();
		for(int i = 0;i<expressions.length;i++) {
			String exp = expressions[i].trim();
			try {
				List<Element> all = EPathSelector(exp);
				table.putAll(i,all.toArray(new Element[all.size()]));
			}catch(Exception s) {
				Console.err("on line "+(i+1)+" have a error:"+s.getMessage());
				return null;
			}
		}
		return table;
	}
	/**
	 * This method can select the elements in the element list
	 * @param list the element object list
	 * @param prefix if it is null,will only select by uri
	 * @param uri if it is null,will only select by prefix
	 * @return the element list
	 */
	public List<Element> getByNameSpaceFromElements(List<Element> list,String prefix,String uri){
		List<Element> elements = new ArrayList<>();
		for(Element e:list) {
			if(uri!=null && prefix !=null) {
				Namespace uriSpace = e.getNamespaceForURI(uri);
				Namespace prefixSpace = e.getNamespaceForPrefix(prefix);
				if(uriSpace!=null&&uriSpace.equals(prefixSpace)) {
					elements.add(e);
				}
			}else if(uri!=null) {
				Namespace uriSpace = e.getNamespaceForURI(uri);
				if(uriSpace!=null) {
					elements.add(e);
				}
			}else if(prefix!=null) {
				Namespace prefixSpace = e.getNamespaceForURI(prefix);
				if(prefixSpace!=null) {
					elements.add(e);
				}
			}
		}
		return elements;
	}
	
	/**
	 * This method can select the elements in the element list
	 * @param list the element list
	 * @param text the text
	 * @return the element object list
	 */
	private List<Element> getByTextFromElements(List<Element> list,String text){
		List<Element> elements = new ArrayList<>();
		for(Element e:list) {
			if(e.getText().equals(text)) {
				elements.add(e);
			}
		}
		return elements;
	}
	/**
	 * This method can select the elements in the element list
	 * @param list the element list
	 * @param isOnly whether is 'isOnly'
	 * @param key the keys
	 * @return the element object
	 */
	private List<Element> getByAttributeKeyFromElements(List<Element> list,boolean isOnly,boolean isKey,String... key){
		List<Element> get = new ArrayList<>();
		List<String> onlyValueAttrs = new ArrayList<>();
		List<Element> onlyList = getAllElements();// get all
		for(Element e:onlyList) {
			@SuppressWarnings("unchecked")
			List<Attribute> attributes = e.attributes();
			for(Attribute a:attributes) {
				if(isKey) {
					onlyValueAttrs.add(a.getName());
				}else {
					onlyValueAttrs.add(a.getValue());
				}
			}
			if(isOnly) {
				if(onlyValueAttrs.containsAll(Arrays.asList(key))&&onlyValueAttrs.size()==key.length) {
					get.add(e);
				}
			}else {
				if(onlyValueAttrs.containsAll(Arrays.asList(key))) {
					get.add(e);
				}
			}
		}
		return get;
	}
	/**
	 * It can get the elements by key and value
	 * @param list the list
	 * @param entrys the key-value
	 * @return the element objects
	 */
	@SuppressWarnings("unchecked")
	private List<Element> getByAttributeFromElements(List<Element> list,String[] entrys){
		List<Element> get = new ArrayList<>();
		Map<String, String> alls = new HashMap<>();
		for(String e:entrys) {
			
			String[] s = e.split("=");
			alls.put(s[0],s[1]);
		}
		int i = 0;
		for(Element e:list) {
			List<Attribute> attributes = e.attributes();
			for(Attribute a:attributes) {
				if(alls.containsKey(a.getName())&&alls.containsValue(a.getValue()))
					i++;
			}
			if(i==entrys.length) {
				get.add(e);
			}
		}
		return get;
	}
	
	/**
	 * <p>
	 * This constructor is used to build file manipulation objects for 
	 * manipulating existing files. After the 
	 * 020 version, the constructor has been 
	 * differentiated and developers are not 
	 * allowed to create objects themselves. 
	 * Objects must be built with the 
	 * <code>InstanceQueryer</code> class
	 * </p>
	 * @param file the file name
	 * @param doc the document object
	 * @param manager the manager object (Default manager)
	 * @param isDefault whether default object or not
	 * 
	 *
	 */
	private XMLDocument(boolean isDefault,Manager<Element> manager,String file,String root,String url) {
		this(manager, isDefault,file,DocumentHelper.createDocument(),root,url);
	}
	/**
	 * <p>
	 * This constructor is used to build file manipulation objects for 
	 * manipulating existing files. After the 
	 * 020 version, the constructor has been 
	 * differentiated and developers are not 
	 * allowed to create objects themselves. 
	 * Objects must be built with the 
	 * <code>InstanceQueryer</code> class
	 * </p>
	 * @param file the file name
	 * @param doc the document object
	 * @param manager the manager object (Default manager)
	 * @param isDefault whether default object or not
	 * 
	 *
	 */
	private XMLDocument(Manager<Element> manager,boolean isDefault,String file,Document doc,String root,String url) {
		this.doc = doc;
		if(url==null) {
			this.root = doc.getRootElement()==null?doc.addElement(root):doc.getRootElement();
		}else {
			this.root = doc.addElement(root,url);
		}
		this.file = file;
		this.manager = manager;
		this.xmlFile = new File(this.file);
		this.list = new ArrayList<SElement>();
		this.isDefault = isDefault;
	}
	/**
	 * This can select for three synax,only use inside
	 * @param get the list
	 * @param firstOption the first of childOption
	 * @param middleOption the middle of childOption
	 * @param selectorOption the last od childOption
	 * @return the element object list
	 */
	@SuppressWarnings("unchecked")
	private List<Element> selectByThreeSynax(List<Element> get,String firstOption,String middleOption,String selectorOption){
		switch(selectorOption) {
			case "text":
				List<Element> pathGet = getElements(firstOption);
				for(Element e:pathGet) {
					if(e.getText().equals(middleOption)) {
						get.add(e);
					}
				}
				return get;
			case "namespace":
				String[] group = middleOption.split(",");
				return getElementsByNameSpace(group[0],group[1]);
			case "key":
				String[] keys = middleOption.split(",");
				return getElementsByAttributes(keys);
			case "value":
				String[] values = middleOption.split(",");
				List<String> valueAttrs = new ArrayList<>();
				List<Element> list = getAllElements();// get all
				for(Element e:list) {
					List<Attribute> attributes = e.attributes();
					for(Attribute a:attributes) {
						valueAttrs.add(a.getValue());
					}
					if(valueAttrs.containsAll(Arrays.asList(values))) {
						get.add(e);
					}
				}
				return get;
			case "key=value":
				String[] entrys = middleOption.split(",");
				return getByAttributeFromElements(getAllElements(), entrys);
			case "uri":
				return getElementsByNameSpace(middleOption,true);
			case "prefix":
				return getElementsByNameSpace(middleOption, false);
			case "text,name":
				List<Element> byTexts = getElementsByName(firstOption);
				for(Element e:byTexts) {
					if(e.getText().equals(middleOption)) {
						get.add(e);
					}
				}
				return get;
			case "value,only":
				String[] onlyValues = middleOption.split(",");
				return getByAttributeKeyFromElements(getAllElements(), true,false,onlyValues);
		}
			throw new SynaxException("the synax is illegal");
	}
}