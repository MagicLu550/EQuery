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
 * finished on 3.14 ,2019 ,22:18
 * 
 * 
 */
package cn.gulesberry.www.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.gulesberry.www.entity.ISElement;
import cn.gulesberry.www.entity.SElement;
import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.exception.SElementClassCastException;
import cn.gulesberry.www.helper.XMLHelper;
import cn.gulesberry.www.manager.XMLManager;
import net.noyark.www.interf.InitFile;
/**
 * This class inherits from XMLDocument and
 * implements the InitFile tag interface,It
 * is used for xml files generated before the
 * object is created and saved.The object can
 * be constructed bt <code>getXMLQuery</code>.The develop
 * is not allowed to create this object by itself.
 * Created by the <code>InstanceQueryer</code> class to take effect.
 * @author magiclu550
 * @since EQuery 020
 * @since JDK 1.8
 * @see XMLDocument
 * @see net.noyark.www.interf.XMLDomFile
 * @see cn.gulesberry.www.helper.InstanceQueryer
 */
public class InitDocument extends XMLDocument implements InitFile{

	private static final long serialVersionUID = 1L;
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
	public InitDocument(String file, String rootElement) {
		super(file, rootElement, false);
	}
	/**
	 * The xmlns default namespace can be added
	 * to the original constructor object.
	 * @param file the xml file name
	 * @param rootElement the root element name
	 * @param url the namespace xmlns =...
	 * 
	 * @see cn.gulesberry.www.io.XMLDocument#XMLDocument(String, String, boolean)
	 */
	public InitDocument(String file, String rootElement,String url) {
		super(file, rootElement, false,url);
	}
	/**
	 * This method can directly get the collection
	 * of objects related to the path
	 * @param element the element path
	 * @return the element object's list
	 */
	public List<Element> getElements(String element) {
		List<Element> list = new ArrayList<Element>();
		Set<Element> ls = manager.getElementMap().keySet();
		for(Element ele:ls) {
			SElement se = getSElement(ele);
			if(se.getElement().equals(element)) {
				list.add(ele);
			}
		}
		return list;
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
	public List<Element> getElementsByAttribut(String name) {
			List<Element> elementsList = new ArrayList<Element>();
			Map<Element, Map<String, String>> map = manager.getElementMap();
			Set<Entry<Element, Map<String, String>>> es = map.entrySet();
			for(Entry<Element, Map<String, String>> e:es) {
				Element e_Element = e.getKey();
				List<Attribute> e_e_a = e_Element.attributes();
				for(Attribute e_a:e_e_a) {
					if(e_a.getName().equals(name)) {
						elementsList.add(e_Element);
					}
				}
			}
			return elementsList;
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
	public List<Element> getElementsByAttribut(String name, String value) {
			List<Element> elementsList = new ArrayList<Element>();
			Map<Element, Map<String, String>> map = manager.getElementMap();
			Set<Entry<Element, Map<String, String>>> es = map.entrySet();
			for(Entry<Element, Map<String, String>> e:es) {
				Element e_Element = e.getKey();
				List<Attribute> e_e_a = e_Element.attributes();
				for(Attribute e_a:e_e_a) {
					if(e_a.getName().equals(name)&&e_a.getValue().equals(value)) {
						elementsList.add(e_Element);
					}
				}
			}
			return elementsList;
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
	public SElement getSElement(Element e) {
			String path = e.getPath();
			String[] paths = path.split(XMLDocument.SLASH);
			String pointPath = paths[2];
			for(int i = 3;i<paths.length;i++) {
				pointPath = pointPath+"."+paths[i];
			}
			String text = e.getText();
			String numberPath = manager.getElementInformationWithThisPath(e);
			int[] indexs = XMLHelper.toIntArray(numberPath.split(XMLManager.EN_DASH));
			for(int i = 0;i<list.size();i++) {
					if(list.get(i).toString().equals(pointPath+","+Arrays.toString(indexs)+","+text)) {
						return list.get(i);
					}
			}
			SElement se = new ISElement(pointPath, text, indexs);
			list.add(se);
					
			return se;
		
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
	public List<Element> addElements(String element,String... texts) throws IllegalMappingException, IndexLengthException, IOException, DocumentException {
			List<Element> all = new ArrayList<>();
			String[] elements = element.split(POINT);
			//elements保留前一段
			String[] child = elements[elements.length-1].split(SLASH);
			if(child.length != texts.length) {
				throw new IllegalMappingException("child element number != the texts ");
			}
			String parentString = "";
			String[] parentElementName = new String[elements.length-1];
			parentString = XMLHelper.toListTheElementPath(parentElementName, parentString, elements);
			Element es = addElement(parentString, " ");
			SElement se = getSElement(es);
			for(int i = 0;i<child.length;i++) {
				Element e = addChild(parentString+"."+child[i], texts[i], se.getIndexWithOutFrist());
				all.add(e);
			}
			return all;
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
	public void addElementText(SElement e,String text) throws SElementClassCastException {
		XMLHelper.throwISElementException(e);
		addElementText(e.getElement(), text, e.getIndexWithOutFrist());
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
	public boolean elementExists(SElement e) throws IllegalMappingException, IndexLengthException, DocumentException, SElementClassCastException {
		XMLHelper.throwISElementException(e);
		return elementExists(e.getElement(), e.getIndexWithOutFrist());
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
	public Element addChild(SElement e) throws IllegalMappingException, IndexLengthException, IOException, DocumentException, SElementClassCastException {
		XMLHelper.throwISElementException(e);
		return addChild(e.getElement(), e.getText(), e.getIndexWithOutFrist());
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
	public Map<String, String> getAttribute(SElement se) throws SElementClassCastException {
		XMLHelper.throwISElementException(se);
		String[] elements = se.getElement().split(POINT);
		Element e =  XMLHelper.searchElement(elements,se.getIndexWithOutFrist(), root);
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
	public Element addAttribute(SElement e,String name,String value) throws IllegalMappingException, IndexLengthException, DocumentException, SElementClassCastException {
		XMLHelper.throwISElementException(e);
		return addAttribute(e.getElement(),name,value,e.getIndexWithOutFrist());
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
	public void setElementName(SElement e,String newName) throws IllegalMappingException, IndexLengthException, DocumentException, SElementClassCastException {
		XMLHelper.throwISElementException(e);
		setElementName(e.getElement(), newName,e.getIndexWithOutFrist());
	}
	/**
	 *  <p>
	 * <code>setElementsText</code> is a batch modification information, 
	 * the basic principle is no different from setElementText.
	 * </p>
	 * @param e the SElement object
	 * @param text the element's new text
	 * @throws cn.gulesberry.www.exception.SElementClassCastException
	 */
	public void setElementText(SElement e,String text) throws SElementClassCastException {
		XMLHelper.throwISElementException(e);
		setElementText(e.getElement(), text,e.getIndexWithOutFrist());
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
	public Element getBrotherElementOn(SElement s) throws IllegalMappingException {
		
		return getBrotherElementOn(s.getElement(),s.getIndexWithOutFrist());
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
	public Element getBrotherElementUnder(SElement s) throws IllegalMappingException {
		
		return getBrotherElementUnder(s.getElement(),s.getIndexWithOutFrist());
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
	public List<Element> getBrotherElementsOn(SElement s) throws IllegalMappingException {
		
		return getBrotherElementsOn(s.getElement(), s.getIndexWithOutFrist());
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
	@Override
	public List<Element> getChilds(SElement s) throws IllegalMappingException {
		
		return getChilds(s.getElement(), s.getIndexWithOutFrist());
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
	public int foundChildIndex(SElement s) throws IllegalMappingException {
		
		return foundChildIndex(s.getElement(),s.getIndexWithOutFrist());
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
	public List<Element> getBrotherElementsUnder(SElement s) throws IllegalMappingException {
		
		return getBrotherElementsUnder(s.getElement(),s.getIndexWithOutFrist());
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
	public Element getParent(SElement s) throws IllegalMappingException {
		
		return getParent(s.getElement(),s.getIndexWithOutFrist());
	}
	/**
	 * This method can get the adjacent few bother elements
 	 * @param s the SElement object
	 * @param ind Adjacent number,positive number is below,
	 * 				negative number is above,such as 'a b c d e',with c as
	 * 				the origin,a is -1,d is 1.
	 * @return the element object
	 * @throws IllegalMappingException
	 */
	@Override
	public Element getBrotherElement(SElement s, int ind) throws IllegalMappingException {
		
		return getBrotherElement(s.getElement(), ind,s.getIndexWithOutFrist());
	}
	/**
	 * it can get the indexs of the element
	 * @param e the element object
	 * @return the element indexs array
	 */
	public int[] getIndexs(Element e) {
		return getSElement(e).getIndexWithOutFrist();
	}
}
