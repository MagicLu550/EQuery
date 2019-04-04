/*GulesBerry Tech. Co. Ltd. (C) noyark-system Hector Group
 * (GHG China) @Freedom Web of java
 * @noyark - system group for xml
 * @github magiclu550 author
 * @noter K.J author
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
 * finished on 3.18 ,2019 ,9:27
 * version 1.3.0
 * 
 * 
 */
package net.noyark.www.interf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentType;
import org.dom4j.Element;

import cn.gulesberry.www.entity.SElement;
import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.exception.SElementClassCastException;
import cn.gulesberry.www.helper.XMLHelper;
import cn.gulesberry.www.io.XMLDocument;
import cn.gulesberry.www.utils.table.NodeTable;
import net.noyark.www.annotations.GetBrother;
import net.noyark.www.annotations.IndexMappingNumberDigital;
import net.noyark.www.list.ElementSubIndex;
import net.noyark.www.list.ParentElementPath;
import net.noyark.www.list.Query;
/**
 * <table border='1px'>
 * 	<tr>
 * 		<td>
 * <div style='background-color:#CCCCFF'>
*  <p>
*  This is a simple XML selector tool that allows you to easily write and parse XML files.
*  There are many ways to do this, you can choose according to your needs.
*  </p>
*  <p>
*  The role of this class is to facilitate the reading and writing of XML, simplifying a large number of operations,
*  
*  Make the label and content form a unified mapping, this class refers to dom4j this excellent xml parser
*  {@link org.dom4j.io.XMLWriter} and {@link org.dom4j.io.SAXReader}
*  , thank you for your use
*  <p>
*  <p>
*  the index starts from 0,is the first element of the same name in the same name.
*  the path don't write with the root element's name
*  </p>
*  	<td>
*  <tr>
*  	</table>
*  @author magiclu550
*  @since JDK1.8
*  @since EQuery 001
*  @see net.noyark.www.interf.XMLDomFile
*  @see cn.gulesberry.www.io.DefaultDocument
*  @see cn.gulesberry.www.io.InitDocument
*/
public interface XMLDomFile extends Serializable,Cloneable{
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
	Element addElement(String element,String text) 
			throws IllegalMappingException,
			IndexLengthException,
			IOException;
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
	void save()  throws IOException;
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
	* @param the out put stream
	* 
	* @see net.noyark.www.interf.XMLDomFile#save(FileOutputStream)
	*/
	void save(FileOutputStream fos)  throws IOException;
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
	@IndexMappingNumberDigital(digital = ElementSubIndex.ZERO)
	Element addAttribute(String element,String name,String value,int...index) 
			throws IllegalMappingException,
			IndexLengthException,
			DocumentException;
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
	Element addAttribute(SElement e,String name,String value) 
			throws IllegalMappingException,
			IndexLengthException,
			DocumentException,
			SElementClassCastException;
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
	//这里的ElementPath以/为分割标准 a.v.a/b/c,则elementpaths长度为3
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	List<Element> addElements(String element,String... texts) 
			throws IllegalMappingException,
			IndexLengthException,
			IOException,
			DocumentException; 
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	void setElementName(String element,String newName,int... index) 
			throws IllegalMappingException, 
			IndexLengthException, 
			DocumentException;
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
	void setElementName(SElement e,String newName) 
			throws IllegalMappingException,
			IndexLengthException, 
			DocumentException,
			SElementClassCastException;
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
	@Deprecated
	void setElementAttribut(String element,String name,String value,int... index);
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
	void setElementsAttribut(String element,String name,String value) 
			throws IllegalMappingException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	void setElementText(String element,String text,int... index);
	/**
	 *  <p>
	 * <code>setElementsText</code> is a batch modification information, 
	 * the basic principle is no different from setElementText.
	 * </p>
	 * @param e the SElement object
	 * @param text the element's new text
	 * @throws SElementClassCastCastException
	 */
	void setElementText(SElement e,String text) 
			throws SElementClassCastException;
	
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.NEG_ONE)
	void setElementsText(String element,String text,int...index) 
			throws IndexLengthException,
			IllegalMappingException,
			DocumentException;
	/**
	 * <p>
	 * 	This method can get the element object with this attribute
	 * 	by the property name
	 * </p>
	 * @param name the attribute's name
	 * @return the element object set
	 */
	List<Element> getElementsByAttribut(String name);
	/**
	 * <p>
	 * 	This method can get the element object with this attribute
	 * 	by the property name and value.
	 * </p>
	 * @param name the attribute's property name
	 * @param value the attribute's value
	 * @return the element object set
	 */
	List<Element> getElementsByAttribut(String name,String value);
	/**
	* <p>
	* The <code>getElementsTextThatTrim</code> method can get one via the specified tag path.
	* There is a set of key-value pairs for the path and corresponding text, and the surrounding whitespace characters are removed.
	* </p>
	* @param element the element path
	* @return the element paths and texts
	*/
	HashMap<String, String> getElementsTextThatTrim(String element);
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
	List<Element> getElements(String element);
	/**
	 * <p>
	 * 	This method can get an element object by element
	 * 	coordinates and path
	 *</p>
	 * @param e
	 * @return
	 */
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	Element getElement(String element,int... index) 
			throws IllegalMappingException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.NEG_ONE,isParent=ParentElementPath.TRUE)
	List<Element> getElements(String element,int... index) 
			throws IllegalMappingException;
	/**
	* <p>
	* The <code>isExists</code> method can determine if a file 
	* exists and returns a boolean value
	* </p>
	* @return file exists
	* @see net.noyark.www.interf.XMLDomFile#isExists()
	*/
	boolean isExists();
	/**
	* <p>
	* <code>load</code> method can load files
	* </p>
	* @deprecated does not need to use the load method to save directly
	*/
	@Deprecated
	void load();
	/**
	* <p>
	* The <code>elementExists</code> method can 
	* determine if a tag is created.
	* Presupposition exists to prevent data errors 
	* from being overwritten by files again
	* </p>
	* @param e tag path
	* @param index tag positioning
	* @throws IllegalMappingException when the element is split
	* When the length of the index does not match
	* @throws IndexLengthException will throw an exception when there is a value in the index that is outside the bounds of the label.
	* @throws DocumentException
	* 
	* @see {@link net.noyark.www.interf.XMLDomFile#elementExists(SElement)}
	*/
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	boolean elementExists(String e,int...index) 
			throws IllegalMappingException,
			IndexLengthException,
			DocumentException;
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
	boolean elementExists(SElement e) 
			throws IllegalMappingException, 
			IndexLengthException,
			DocumentException,
			SElementClassCastException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	String getText(String element,int...index)
			throws IllegalMappingException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	HashMap<String,String> getAttribute(String element,int... index);
	Map<String, String> getAttribute(SElement se) 
			throws SElementClassCastException ;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	boolean elementHaveAttribute(String element,int...index);
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO,isParent=ParentElementPath.TRUE)
	int getMaxPos(String element, int... index) 
			throws IllegalMappingException;
	/**
	* <p>
	* <code>deleteElement</code> can delete the specified element
	* </p>
	* @param element tag path
	* @param index tag coordinates
	* @throws IllegalMappingException This exception is thrown when the length of the element is split and the length of the index is different.
	* @see XMLDomFile#deleteElement(String, int...)
	*/
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	void deleteElement(String element,int...index)
			throws IllegalMappingException;
	/**
	* <p>
	* <code>deleteElements</code> can delete the specified set of elements
	* </p>
	* @param element tag parent path, path to the parent tag of the deleted tag
	*/
	void deleteElements(String element);
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	void deleteElementAttribut(String element,String name,int... index) 
			throws IllegalMappingException;
	/**
	 * <p>
	 * 	This method can be used to convert the element
	 * 	object into a selement object,which encapsulates
	 * 	the geometry information of the element object 
	 * 	and participates in selement.
	 *</p>
	 * @param e the element object
	 * @return the selement object
	 * @see Element
	 * @see SElement
	 */
	SElement getSElement(Element e);
	/**
	* <p>
	* The <code>addChild</code> method can add the original label system again.
	* However, the label path must be added to its previous parent tags (except the root tag), such as the original a.b.c tag.
	* If you want to add a d-tag on its basis, write a.b.c.d and locate the coordinates, as explained on the interface
	* </p>
	* <p>
	* You can pass in a Selement object or property value to operate
	* </p>
	* @param SElement the element object
	* @throws IOException
	* @throws DocumentException
	* 
	* @see net.noyark.www.interf.XMLDomFile#addChild(String, String, int...)
	*/
	Element addChild(SElement e) 
			throws IllegalMappingException,
			IndexLengthException,
			IOException,
			DocumentException,
			SElementClassCastException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.NEG_ONE)
	Element addChild(String element,String text,int... index) 
				throws IllegalMappingException,
				IndexLengthException,
				IOException,
				DocumentException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	void addElementText(String element, String text, int... index);
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
	void addElementText(SElement e,String text) 
			throws SElementClassCastException ;
	/**
	 * <p>
	 * This method can get the name of the xml file
	 * </p>
	 * @return the file name
	 */
	String getFile();
	/**
	* Set the encoding rules for the document
	* @param encoding encoding
	*/
	void setXMLEncoding(String encoding);
	/**
	* Get this file that already exists
	* @return is default file
	*/
	boolean isDefault();
	/**
	* Get all element objects
	* @return all of the element objects 
	* except the root element
	* @deprecated you can use the getAllElements method,this method's
	* 							name is not good.
	*/
	@Deprecated
	List<Element> getAllElement();
	/**
	* Get all element objects
	* @return all of the element objects 
	* except the root element
	*/
	List<Element> getAllElements();
	/**
	 * get the manager of this object
	 * @return the manager
	 */
	Manager<Element> getManager();
	/**
	* <p>
	* <code>getElementsByGroup</code> can be grouped to get element objects
	* Separated by ',', such as: a.b.c, a.b.d, c.d.g
	* </p>
	* @param pathGroup path
	* @return returns the label set corresponding to the path (name corresponding - label collection)
	* @since EQuery 021
	*/
	Table<String, Element> getElementsByGroup(String elements);
	/**
	* Get encoded information
	* @return
	*/
	String getEncoding();
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	@GetBrother
	Element getBrotherElementOn(String path,int...index)
			throws IllegalMappingException;
	/**
	 * <p>
	 * <code>getBrotherElementOn</code> gets the last sibling element
	 * If there is no next element, an array out of bounds exception will be thrown
	 * </p>
	 * @param s the SElement object
	 * @return the element on this element(don't care name)
	 * @throws IllegalMappingException
	 */
	Element getBrotherElementOn(SElement s)
			throws IllegalMappingException,
			SElementClassCastException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	@GetBrother
	Element getBrotherElementUnder(String path,int...index)
			throws IllegalMappingException;
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
	Element getBrotherElementUnder(SElement s) 
			throws IllegalMappingException,
			SElementClassCastException;
	/**
	 * <p>
	 * 	This method can get the first few elements of its
	 * 	class with its center,that is, the brother element above
	 * 	it.
	 * </p>
	 * @param path the element path
	 * @param index the element index
	 * @return the element list
	 * @throws IllegalMappingException
	 */
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	@GetBrother
	List<Element> getBrotherElementsOn(String path,int...index) 
			throws IllegalMappingException;
	/**
	 * <p>
	 * <code>getBrotherElementOn</code> gets the last sibling element
	 * If there is no next element, an array out of bounds exception will be thrown
	 * </p>
	 * @param s the SElement object
	 * @return the element on this element(don't care name)
	 * @throws IllegalMappingException
	 */
	List<Element> getBrotherElementsOn(SElement s)
			throws IllegalMappingException,
			SElementClassCastException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	@GetBrother
	List<Element> getChilds(String path,int...index)
			throws IllegalMappingException;
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
	List<Element> getChilds(SElement s)
			throws IllegalMappingException,
			SElementClassCastException;
	/**
	 * This method can get the adjacent few bother elements
 	 * @param path the element object's path
	 * @param ind Adjacent number,positive number is below,
	 * 				negative number is above,such as 'a b c d e',with c as
	 * 				the origin,a is -1,d is 1.
	 * @param index the element index
	 * @return the element object (brother)
	 * @throws IllegalMappingException
	 */
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	@GetBrother
	Element getBrotherElement(String path,int ind,int...index) 
			throws IllegalMappingException;
	/**
	 * This method can get the adjacent few bother elements
 	 * @param s The SElement object
	 * @param ind Adjacent number,positive number is below,
	 * 				negative number is above,such as 'a b c d e',with c as
	 * 				the origin,a is -1,d is 1.
	 * @return the element object
	 * @throws IllegalMappingException
	 */
	Element getBrotherElement(SElement s,int ind)
			throws IllegalMappingException,
			SElementClassCastException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	@GetBrother
	int foundChildIndex(String path,int...index)
			throws IllegalMappingException;
	/**
	 *  <p>
	 * 	Get the first position of the child element of
	 * 	the parent element of the element.
	 * </p>
	 * @param s the SElement object
	 * @return This element is in the first few positions of
	 * 				its class,regardless of the name
	 */
	int foundChildIndex(SElement s)
			throws IllegalMappingException, 
			SElementClassCastException;
	/**
	 * <p>
	 * This method can get all the brother elements below
	 * </p>
	 * @param path the element object's path
	 * @param index the indexs of the this element
	 * @return the element object set
	 * @throws IllegalMappingException
	 */
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	@GetBrother
	List<Element> getBrotherElementsUnder(String path,int... index) 
			throws IllegalMappingException;
	/**
	 * <p>
	 * This method can get all the brother elements below
	 * </p>
	 * @param s the SElement object
	 * @return the element object set
	 * @throws IllegalMappingException
	 */
	List<Element> getBrotherElementsUnder(SElement s) 
			throws IllegalMappingException, 
			SElementClassCastException;
	/**
	 * <p>
	 * 	This can get the parent element of the current element
	 * </p>
	 * @param path the child(this) element path
	 * @param index the child(this) element
	 * @return the element object
	 * @throws IllegalMappingException
	 */
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	@GetBrother
	Element getParent(String path,int...index) 
			throws IllegalMappingException;
	/**
	 * <p>
	 * 	This can get the parent element of the current element
	 * </p>
	 * @param s the parent element of this
	 * @return the element object
	 * @throws IllegalMappingException
	 */
	Element getParent(SElement s) 
			throws IllegalMappingException, 
			SElementClassCastException;
	/**
	 * <p>
	 * 	The element name selector directly specifies the element name.
	 * 	No path is required.You can select all the elements related to 
	 * 	this name.
	 * </p>
	 * @param elementName the element name,not PATH!!!
	 * @return the element object set
	 */
	List<Element> getElementsByName(String elementName);
	/**
	 * <p>
	 * 	Namespace selector which lets you select all
	 * 	the elements through the namespace
	 * </p>
	 * @param prefix the prefix of the namespace
	 * @param uri the uri of the namespace
	 * @return the element object set
	 */
	List<Element> getElementsByNameSpace(String prefix,String uri);
	/**
	 *  Namespace selector,which can be selected by prefix or uri
	 *  to have an element object containing this namespace
	 * @param isURI If true,it is searched by uri.If it is
	 * 				false,it is searched by prefix
	 * @param uriOrprefix the uri or prefix
	 * @return the element objects set
	 */
	List<Element> getElementsByNameSpace(String uriOrprefix,boolean isURI);
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.NEG_ONE)
	@GetBrother
	List<Element> addElementsIfExistsParent(String element,List<String> text,int... index) 
			throws IllegalMappingException, 
			IndexLengthException,
			IOException,
			DocumentException; 
	/**
	 * <code>getElementsByText</code> can select elements by the text
	 * 	embedded in the element to get the collection of elements
	 * @param text the element text
	 * @return the element object set
	 */
	List<Element> getElementsByText(String text);
	/**
	 * This method can select an element by id and
	 * return a collection of List element objects,
	 * Note that the element name standard of id is ID
	 * @param id the element id attribute's name
	 * @return the element objects list
	 */
	List<Element> getElementsByID(String id);
	/**
	 *	This method can read another file of the specified 
	 *	element across files
	 *		IF SEE THESE EXCEPTION ,PLEASE SEE THEIR @ SEE
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 * @throws DocumentException 
	 * @throws IOException
	 */
	@IndexMappingNumberDigital(digital=ElementSubIndex.ZERO)
	Element readElementOtherInThis(String otherFile,String elements,int... indexs) 
			throws DocumentException,
			IllegalMappingException, 
			IndexLengthException, 
			IOException;
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.NON_STATIC)
	List<Element> $(String element,Query query,int...index) 
			throws IllegalMappingException;
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
	Element addElementAfter(Element e,String elementName) 
			throws IllegalMappingException, 
			IndexLengthException, 
			IOException;
	/**
	 * If you want to add a doctype to your file
	 * you can use this method to add a doctype
	 * like html.
	 * @param name the doctype name
	 * @param publicId the doctype's public id
	 * @param systemId the doctype's system id
	 * @return the document of this
	 */
	Document addDOCTYPE(String name,String publicId,String systemId);
	/**
	 * you can use this method to get the root element
	 * @return the root element object
	 */
	Element getRootElement();
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
	Map<String, Element> addElementsMap(String element,String... texts) 
			throws IllegalMappingException, 
			IndexLengthException, 
			IOException, 
			DocumentException;
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
	void join(XMLDomFile xdf) 
			throws IllegalMappingException, 
			IndexLengthException,
			IOException,
			DocumentException; 
	/**
	 * get the indexs of the element,like other method's indexs
	 * paramater
	 * @param e the element object
	 */
	int[] getIndexs(Element e);
	/**
	 * <p>
	 * you can get the tree of this xml file
	 * </p>
	 * @return the tree instance
	 */
	Tree getTree();
	/**
	 * you can get the table manager of the tree of this
	 * xml file
	 * @return
	 */
	WaterMan getWaterMan();
	/**
	 * <p>
	 * Change the normal Element object 
	 * return path to a compatible point path
	 * </p>
	 * @param e the SElement object
	 * @return the point path
	 * @see XMLHelper
	 */
	String getPointPath(Element e);
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.NON_STATIC)
	List<Element> $(Query query,String element) 
			throws IllegalMappingException;
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
	List<Element> addElementWithAll(String element,String text)
			throws IllegalMappingException,
			IndexLengthException,
			IOException,
			DocumentException;
	/**
	 * It can get the docType of this document
	 * @return the documentType object
	 */
	DocumentType getDocType();
	/**
	 * It can set the doc type
	 * @param docType the document type object
	 */
	void addDocType(DocumentType docType);
	/**
	 * get the document object;
	 * @return the document object
	 */
	Document getDocument();
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
	@IndexMappingNumberDigital(digital=ElementSubIndex.NEG_ONE)
	void addArray(String element,Object[] os,int... indexs) 
			throws IllegalMappingException, 
			IndexLengthException,
			IOException, 
			DocumentException;
	/**
	 * This method can get the element objects by the attributes
	 * name.
	 * @param attributeName the attibute names
	 * @return the element object list
	 */
	List<Element> getElementsByAttributes(String... attributeName);
	/**
	 * It can get the elements by the path and the attributes.
	 * @param path the element path
	 * @param attributeName the attribute names
	 * @return the element object list
	 */
	List<Element> getElementsByAttributesAndPath(String path,String...attributeName);
	/**
	 * This method can get the element objects by its name and attribute names
	 * @param name the element's tag name
	 * @param attributeName the attribute name
	 * @return the element objects
	 */
	List<Element> getElementsByAttributesAndName(String name,String...attributeName);
	/**
	 * This method can get the elements by the name and the attribute name,
	 * and the attribute's number.
	 * @param name the element name
	 * @param attribute the attribute names
	 * @return the element objects
	 */
	List<Element> getElementsByAttributesAndNameOnlyThese(String name,String... attribute);
	/**
	 * This method can get the elements by the path and the attribute name,
	 * and the attribute's number.
	 * @param path the element point path
	 * @param attribute the attribute names
	 * @return the element objects
	 */
	List<Element> getElementsByAttributesAndPathOnlyThese(String path,String... attribute);
	/**
	 * It can clean the mapping file(use in DefaultXML)
	 */
	default void clearMapping() {
		
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
	 * 	Sequenital pirority name>[uri-prefix/namespace>text>key=value/key/value]>only <br>
	 * 	complex:<br>
	 * 	path uri-prefix text [namespace,text];<br>
	 * 	path namespace-uri/prefix text [uri/prefix,text];<br>
	 * 	path text key=value [uri/prefix,key=value];<br>
	 * 	path  text key=value [text,key=value,only];<br>
	 * 	name namespace text [name,uri/prefix,text];<br>
	 * 	name text key=value [name,text,key=value];<br>
	 * 	name text key=value [name,text,key=value,only];<br>
	 * 	name text key [name,text,key];<br>
	 * 	name uri-prefix text [namespace];
	 * 	
	 * @param expressions the epath expressions
	 * @return the elements  object list
	 * @see Query
	 */
	List<Element> EPathSelector(String expressions);
	
	/**
	 * This method can select the elements in the element list
	 * @param list the element object list
	 * @param prefix if it is null,will only select by uri
	 * @param uri if it is null,will only select by prefix
	 * @return the element list
	 */
	List<Element> getByNameSpaceFromElements(List<Element> list,String prefix,String uri);
	
	/**
	 * This method can parse the file that have epath
	 * @param file the epath file (.equery)
	 * @return the NodeTable(Line and Results)
	 * @throws IOException
	 * @throws DocumentException 
	 * @throws IndexLengthException 
	 * @throws IllegalMappingException 
	 */
	NodeTable<Integer,Element> sourceEPathFile(String file) throws IOException, IllegalMappingException, IndexLengthException, DocumentException;
}