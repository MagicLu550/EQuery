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
package cn.gulesberry.www.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
/**
* File object manager after getting an element from an existing file
* Rewritten the Manager method, in the Default file, the map is obtained by the ID serial number
* Therefore, when storing an element, the ID serial number of the Element element is stored,
* and the ID serial number is a program.
* Divided according to the natural ordering of the elements
* @author magiclu550
* @since EQuery 020
* @since JDK 1.8
* @see XMLManager
* 
 */
public class DefaultXMLManager extends XMLManager{
	private static final long serialVersionUID = 1L;
	/**
	 *element-ID
	 */
	private int ids = 0;
	/**
	 * the real mapping
	 */
	private HashMap<Element, Map<String,String>> mapping;

	// Manager将会为每个DefaultXML的元素分配一个ID,并存储在映射
	// DefaultXML将会重写方法，关于SElement获取的方法
	//
	/**
	 * This method just create the mapping instance
	 */
	public DefaultXMLManager() {
		super();
		mapping = new HashMap<Element, Map<String,String>>();
	}
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
	@Override
	public void addSequeElement(String element, Element e, int start)
			throws IllegalMappingException, IndexLengthException, IOException {
			List<Element> ls = new ArrayList<Element>();
			while(true) {
				ls.add(e);
				e = e.getParent();
				if(e.isRootElement()) {
					break;
				}
			}
			for(int i = 0;i<ls.size();i++) {
				inputOne(ls.get(i));
			}
	}
	/**
	 * override from superclass,it use the id and not indexs
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
	@Override
	public synchronized void whenChildTheElementIndex(String element, Element e, int... index)
			throws IllegalMappingException, IOException, IndexLengthException {
		inputOne(e);
	}
	/**
	* <p>
	* <code>getElementMap</code> gets the mapped mock element
	* </p>
	* @return the mapping map object
	*/
	@Override
	public Map<Element, Map<String, String>> getElementMap() {
		return mapping;
	}
	/**
	 *add the default element into mapping
	 * @param e the Element object
	 */
	private synchronized void inputOne(Element e) {
		String ID = ids+""; 
		Map<String, String> infor = new HashMap<>();
		infor.put("ID",ID);
		mapping.put(e, infor);
		ids++;
	}
}
