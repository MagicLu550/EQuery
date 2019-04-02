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
 * finished on 3.5 ,2019 ,10:18
 * 
 * 
 */
package cn.gulesberry.www.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.gulesberry.www.entity.DSElement;
import cn.gulesberry.www.entity.SElement;
import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.exception.SElementClassCastException;
import cn.gulesberry.www.helper.XMLHelper;
import cn.gulesberry.www.manager.XMLManager;
import cn.gulesberry.www.use.SetMavenJar;
import net.noyark.www.interf.DefaultFile;
/**
 * This class inherits from XMLDocument and implements
 * the markup interface DefaultFile interface,which is used
 * to manipulate existing files.It will retain all the elements in
 * the original file and can be written later
 * @author magiclu550
 * @since EQuery 020
 * @since JDK 1.8
 * @see XMLDocument
 * @see net.noyark.www.interf.XMLDomFile
 */
public class DefaultDocument extends XMLDocument implements DefaultFile{
	private static final long serialVersionUID = 1L;
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
	 * 
	 *
	 */
	public DefaultDocument(String file, Document doc, XMLManager manager) {
		super(file, doc, manager, true);
		
	}
	/**
	 * This method can directly get the collection
	 * of objects related to the path
	 * @param element the element path
	 * @return the element object's list
	 */
	@Override
	public List<Element> getElements(String element) {
		List<Element> lists = new ArrayList<Element>();
		List<Element> list = getAllElements();
		for(Element e:list) {
			if(("."+root.getName()+"."+element).equals(e.getPath().replaceAll("/", "."))) {
				lists.add(e);
			}
		}
		return lists;
	}
	/**
	 * <p>
	 * 	This method can get the element object with this attribute
	 * 	by the property name
	 * </p>
	 * @param name the attribute's name
	 * @return the element object set
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Element> getElementsByAttribut(String name) {
		List<Element> list = getAllElements();
		for(Element e:list) {
			List<Attribute> attributes = e.attributes();
			for(Attribute a:attributes) {
				if(a.getName().equals(name)) {
					list.add(e);
				}
			}
		}
		return list;
	}
	/**
	 * <p>
	 * 	This method can get the element object with this attribute
	 * 	by the property name and value.
	 * </p>
	 * @param name the attribute's property name
	 * @param value the attribute's value
	 * @return the element object set
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Element> getElementsByAttribut(String name,String value) {
		List<Element> list = getAllElements();
		for(Element e:list) {
			List<Attribute> attributes = e.attributes();
			for(Attribute a:attributes) {
				if(a.getName().equals(name)) {
					if(a.getValue().equals(value)) {
						list.add(e);
					}
				}
			}
		}
		return list;
	}
	/**
	 * <p>
	 * 	This method can be used to convert the element
	 * 	object into a selement object,which encapsulates
	 * 	the geometry information of the element object 
	 * 	and participates in selement.
	 *</p>
	 * @param e tht element object
	 * @return the selement object
	 * @see Element
	 * @see SElement
	 */
	@Override
	public SElement getSElement(Element e) {
		Map<Element, Map<String,String>> map = manager.getElementMap();
		String path = XMLHelper.getElementPath(e);
		Map<String, String> infor = map.get(e);
		String ID = infor.get("ID");
		String text = e.getText();
		for(SElement se:list) {
			if(se.toString().equals(path+","+text+","+ID)) {
				return se;
			}
		}
		SElement dse = new DSElement(path,text,new int[] {Integer.parseInt(ID)});
		list.add(dse);
		return dse;
	}
	/**
	 *  <p>
	 * <code>setElementsText</code> is a batch modification information, 
	 * the basic principle is no different from setElementText.
	 * </p>
	 * @param e the SElement object
	 * @param text the element's new text
	 * @throws SElementClassCastCastException
	 */
	@Override
	public void setElementText(SElement e, String text) throws SElementClassCastException {
		Element element = XMLHelper.getElementByDElement(e,manager);
		element.setText(text);
		e.setText(text);
	}
	/**
	 * <p>
	 * The <code>elementExists</code> method can 
	 * determine if a tag is created.
	 * Presupposition exists to prevent data errors 
	 * from being overwritten by files again
	 * </p>
	 * @param e the SElement object
	 * @return if the element is not exists,return a false,or return a true
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws DocumentException
	 * @throws SElementClassCastException
	 * 
	 */
	@Override
	public boolean elementExists(SElement e) throws IllegalMappingException, IndexLengthException, DocumentException, SElementClassCastException {
		XMLHelper.throwDSElementException(e);
		Element element = XMLHelper.getElementByDElement(e,manager);
		return element!=null;
	}
	/**
	 *  <p>
	 * The <code>addChild</code> method can add the original label system again.
	 * However, the label path must be added to its previous parent tags (except the root tag), such as the original a.b.c tag.
	 * If you want to add a d-tag on its basis, write a.b.c.d and locate the coordinates, as explained on the interface
	 * </p>
	 * <p>
	 * You can pass in a Selement object or property value to operate
	 * </p>
	 * @param e the SElement object
	 * @return the element object
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws DocumentException
	 * @throws SElementClassCastException
	 * @throws IOException
	 */
	//SElement的路径为子元素路径
	@Override
	public Element addChild(SElement e)
			throws IllegalMappingException, IndexLengthException, IOException, DocumentException, SElementClassCastException {
		XMLHelper.throwDSElementException(e);
		Element element = XMLHelper.getElementByDElement(e,manager);
		Element ele = element.getParent().addElement(element.getName());
		return ele;
	}
	/**
	 *  <p>
	 * The <code>addElementText</code> method adds text information to the 
	 * specified element.but will not overwrite the original text information, 
	 * and will further follow the original text information.
	 * </p>
	 * @param e the SElement object
	 * @param text the text
	 * @throws SElementClassCastException
	 * 
	 * @see {@link XMLDocument#addElementText(String, String, int...)}
	 * @see net.noyark.www.interf.XMLDomFile#addElementText(SElement, String)
	 */
	@Override
	public void addElementText(SElement e, String text) throws SElementClassCastException {
		XMLHelper.throwDSElementException(e);
		Element ele = XMLHelper.getElementByDElement(e, manager);
		e.setText(e.getText()+text);
		ele.addText(text);
	}
	/**
	 * <p>
	 * <code>getAttribute</code> returns a hash table that stores all 
	 * tag attributes
	 * </p>
	 * @param se the SElement object
	 * @return the key is attribute's name,the value is attribute's value
	 * @throws SElementClassCastException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getAttribute(SElement se) throws SElementClassCastException {
		XMLHelper.throwDSElementException(se);
		Element element = XMLHelper.getElementByDElement(se, manager);
		Map<String, String> map = new HashMap<>();
		List<Attribute> list = element.attributes();
		for(Attribute a:list) {
			map.put(a.getName(), a.getValue());
		}
		return map;
	}
	/**
	 * <p>
	 * <code>addAttribute</code> is a method for adding an Attribute to a specific tag.
	 * Can accurately select xml tags and add specific Attributes to them
	 * , of course, it is recommended that you can judge whether the tag exists in the Attribute is added
	 * </p>
	 * @param e the SElement object
	 * @param name the attribute's name
	 * @param value the attribute's value
	 * @return the element object
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws DocumentException
	 * @throws SElementClassCastException
	 */
	@Override
	public Element addAttribute(SElement e, String name, String value)
			throws IllegalMappingException, IndexLengthException, DocumentException, SElementClassCastException {
		XMLHelper.throwDSElementException(e);
		Element element = XMLHelper.getElementByDElement(e, manager);
		element.addAttribute(name, value);
		return element;
	}
	/**
	 *  <p>
	 * The <code>setElementName</code> method can modify the name of the tag, and its path format 
	 * is the same as other methods.
	 * </p>
	 * </p>
	 * You can use this method to more easily locate the name of the label you want to modify.
	 * </p>
	 * @param e the SElement object
	 * @param newName the element 's new tag name	 
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws DocumentException
	 * @throws SElementClassCastException
	 */
	@Override
	public void setElementName(SElement e, String newName)
			throws IllegalMappingException, IndexLengthException, DocumentException, SElementClassCastException {
		XMLHelper.throwDSElementException(e);
		Element element = XMLHelper.getElementByDElement(e, manager);
		element.setName(newName);
		String path = XMLHelper.getElementPath(element);
		e.setPath(path);
	}
	/**
	 * <p>
	 * <code>getBrotherElementOn</code> gets the last sibling element
	 * If there is no next element, an array out of bounds exception will be thrown
	 * </p>
	 * @param s the SElement object
	 * @return the element on this element(don't care name)
	 * @throws IllegalMappingException
	 */
	@Override
	public Element getBrotherElementOn(SElement s) throws IllegalMappingException, SElementClassCastException {
		return getBrotherElement(s, -1);
	}
	/**
	 * <p>
	 *		This method can get the next brother element,if
	 *		there is no next element,it will throw an array
	 *		out of bounds exception.
	 * </p>
	 * @param s the SElement object
	 * @return the brother element object (next this element)
	 * @throws IllegalMappingException 
	 */
	@Override
	public Element getBrotherElementUnder(SElement s) throws IllegalMappingException, SElementClassCastException {
		return getBrotherElement(s, 1);
	}
	/**
	 *  <p>
	 * 	This method can get the first few elements of its
	 * 	class with its center,that is, the brother element above
	 * 	it.
	 * </p>
	 * @param s the SElement object
	 * @return the brothers(on) element list
	 * @throws IllegalMappingException
	 */
	@Override
	public List<Element> getBrotherElementsOn(SElement s) throws IllegalMappingException, SElementClassCastException {
		List<Element> elements = getChilds(s);
		int index = foundChildIndex(s);//获取其位置
		List<Element> lists = new ArrayList<>();
		if(index!=-1) {
			for(int i = 0;i<index;i++) {
				lists.add(elements.get(i));
			}
		}
		return lists;
	}
	/**
	 *  <p>
	 * 	The meaning of this method is to get the child elements of its
	 * 	parent class,which is essentially the set of sibling elements 
	 * 	of the current element.
	 * </p>
	 * @param s the SElement object
	 * @return the element set 
	 * @throws IllegalMappingException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Element> getChilds(SElement s) throws IllegalMappingException, SElementClassCastException {
		return getParent(s).elements();
	}
	/**
	 *  <p>
	 * 	Get the first position of the child element of
	 * 	the parent element of the element.
	 * </p>
	 * @param s the SElement object
	 * @return This element is in the first few positions of
	 * 				its class,regardless of the name
	 */
	@Override
	public int foundChildIndex(SElement s) throws IllegalMappingException, SElementClassCastException {
		Element element = XMLHelper.getElementByDElement(s, manager);
		int foundIndex = -1;
		List<Element> list = getChilds(s);
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getName().equals(element.getName())) {
				foundIndex = i;
				return foundIndex;
			}
		}
		return foundIndex;
	}
	/**
	 * <p>
	 * This method can get all the brother elements below
	 * </p>
	 * @param s the SElement object
	 * @return the element object set
	 * @throws IllegalMappingException
	 */
	@Override
	public List<Element> getBrotherElementsUnder(SElement s) throws IllegalMappingException, SElementClassCastException {
		List<Element> elements = getChilds(s);
		int index = foundChildIndex(s);//获取其位置
		List<Element> lists = new ArrayList<>();
		if(index!=-1) {
			for(int i = index+1;i<getParent(s).elements().size();i++) {
				lists.add(elements.get(i));
			}
		}
		return lists;
	}
	/**
	 * <p>
	 * 	This can get the parent element of the current element
	 * </p>
	 * @param s the parent element of this
	 * @return the element object
	 * @throws IllegalMappingException
	 */
	@Override
	public Element getParent(SElement s) throws IllegalMappingException, SElementClassCastException {
		Element e = XMLHelper.getElementByDElement(s, manager);
		return e.getParent();
	}
	/**
	 * This method can get the adjacent few bother elements
 	 * @param path the element object's path
	 * @param ind Adjacent number,positive number is below,
	 * 				negative number is above,such as 'a b c d e',with c as
	 * 				the origin,a is -1,d is 1.
	 * @return the element object
	 * @throws IllegalMappingException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Element getBrotherElement(SElement s, int ind) throws IllegalMappingException, SElementClassCastException {
		Element element = XMLHelper.getElementByDElement(s,manager);
		List<Element> list = element.elements();
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getName().equals(element.getName())) {
				return list.get(i-1);
			}
		}
		return null;
	}
	/**
	* <p>
	* The <code>addElements</code> method can add multiple sibling tags at once, in the path format a.b.c/d/e.
	* You can create three tags c d e under the c tag. The first tag (ie a) cannot be the root tag.
	* Also the first level label under root
	* </p>
	* @param element tag path
	* @param texts The text of the lowest level tags
	* @throws IllegalMappingException
	* when texts.length !=element.split(POINT )[elements.length-1].split("/").length
	* throws an exception
	* @throws IndexLengthException will throw an exception when there is a value in the index that is outside the bounds of the label.
	* @throws IOException
	* @throws DocumentException
	*/
	@Override
	public List<Element> addElements(String element, String... texts)
			throws IllegalMappingException, IndexLengthException, IOException, DocumentException {
		List<Element> all = new ArrayList<>();
		String[] childs = element.substring(element.lastIndexOf(".")+1).split(XMLDocument.SLASH);//子元素们
		String parent = element.substring(0,element.lastIndexOf("."));
		Element e = addElement(parent, " ");//设置父类
		int i = 0;
		for(String c:childs) {
			Element e1 = e.addElement(c);
			e1.setText(texts[i]);
			manager.whenChildTheElementIndex(parent+"."+c, e1, 0);
			all.add(e1);
			i++;
		}
		return all;
	}
	/**
	 * it can get the indexs of the element
	 * @param e the element object
	 * @return the element indexs array
	 */
	public int[] getIndexs(Element e) {
		String path = e.getUniquePath();
		int start = path.indexOf("/",1);
		path = path.substring(start+1).replaceAll("/", ".");
		String[] paths = path.split(POINT);
		int[] indexs = new int[paths.length];
		int i = 0;
		for(String p:paths) {
			if(p.indexOf("[")!=-1) {
				indexs[i] = Integer.parseInt(p.substring(p.indexOf("[")+1,p.indexOf("]")))-1;
			}else {
				indexs[i] = 0;
			}
			i++;
		}
		return indexs;
	}
	public void clearMapping() {
		String mapping = SetMavenJar.getInstance().getClass().getResource("/").getPath()+"mapping/"+file.replaceAll("/", "-");
		new File(mapping).delete();
	}
}
