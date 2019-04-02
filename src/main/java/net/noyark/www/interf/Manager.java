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
 * finished on 3.10 ,2019 ,9:20
 * 
 * 
 */
package net.noyark.www.interf;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.dom4j.Element;

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
/**
* <p>
* Objects of this class will be created and matched 
*	the SimpleXML object is created.
* An xml file is used as a map match, based on which the xml
*	tag object is made
* Easier access to its unique tag information
* </p>
* <p>
* This class is used for internal development. It is not recommended for developers to manually create this object.
* </p>
* @author magiclu550
*	@since EQuery 019
*	@since JDK 1.8
*/
public interface Manager<E> extends Serializable{
	/**
	* <p>
	* <code>addSequeElement</code> method can be used when the tag is updated
	* Update the mapping file of the xml file for synchronization purposes, adding elements
	* When this method is automatically dispatched, the information of the xml file is updated.
	* </p>
	* @param element the element path
	* @param e the element object
	* @param start the 'for' start
	* @throws IllegalMappingException
	* @throws IndexLengthException
	* @throws IOException
	*/
	void addSequeElement(String element,Element e,int start) 
			throws IllegalMappingException, IndexLengthException, IOException;
	/**
	* <p>
	* <code>getElementInformationWithoutThisPath</code> method can be
	* The mapping matches the corresponding path information according to the label object, but the coordinates of this element are not attached.
	* Information, only the coordinates and path information of its parent element
	* </p>
	* @param e element object
	* @return matches the path information
	*/
	String getElementInformationWithoutThisPath(E e);
	/**
	* <p>
	* <code>getElementInformationWithThisPath</code> method can be
	* The mapping matches the corresponding path information according to the label object. This method is accompanied by the coordinates of this element.
	* Information, only the coordinates and path information of its parent element
	* </p>
	* @param e element object
	* @return matches the path information
	*/
	String getElementInformationWithThisPath(E e);
	/**
	*<p>
	* <code>whenChildTheElementIndex</code> method for label child elements
	* Schedule this method when updating, interact with the mapping of xml files, update mappings, elements of
	* The ID number is unique, and the corresponding ID number will be updated based on the location information of the element.
	*</p>
	* @param element the element path
	* @param e the element object of the last
	* @param index the indexs of the element object
	* @throws IllegalMappingException
	* @throws IOException
	* @throws IndexLengthException
	*/
	void whenChildTheElementIndex(String element,E e,int... index)
																				throws IllegalMappingException, IOException, IndexLengthException;
	/**
	* <p>
	* <code>getElementMap</code> gets the mapped mock element
	* </p>
	* @return
	*/
	Map<E, Map<String, String>> getElementMap();
}
