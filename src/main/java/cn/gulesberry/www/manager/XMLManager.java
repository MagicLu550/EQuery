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
 * finished on 3.9 ,2019 ,20:18
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
import cn.gulesberry.www.helper.XMLHelper;
import cn.gulesberry.www.io.XMLDocument;
import net.noyark.www.interf.Manager;
/**
 * <p>
 * The role of this class is to manage individual elements, 
 * reducing search time and avoiding.A media class of complex 
 * algorithms that records the information of each configuration file
 * and partition management, directly obtain the object instance 
 * through the specified id, etc., thereby.Solve the problem of 
 * searching global elements non-recursively
 * </p>
 * @author magiclu550
 * @since EQuery 005
 * @since JDK 1.8
 * @see Manager
 */
public abstract class XMLManager implements Manager<Element>{

	
	private static final long serialVersionUID = 1L;
	/**
	 * This is the endash symbol
	 */
	public static final String EN_DASH = "-";
	/**
	 * The element id
	 */
	private int ids;
	/**
	 * All of the element in there
	 */
	private Map<Integer, Map<String, Integer>> list_elements;
	/**
	 * the xml file's element
	 */
	private Map<Element,Map<String, String>> get_elements;
	/**
	 * This constructor is used to build two map objects
	 */
	public XMLManager(){
		//存储最后一个标签坐标值
		list_elements = new HashMap<Integer, Map<String, Integer>>();
		get_elements = new HashMap<Element, Map<String,String>>();
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
	public synchronized void addSequeElement(String element,Element e,int start) 
										throws IllegalMappingException, IndexLengthException, IOException {
		Map<String, Integer> list_element  = new HashMap<String, Integer>();
		String[] elements = element.split(XMLDocument.POINT);
		List<String> list = setIDAndName(elements, start,list_element);
		list.sort((String s1,String s2)->{
			return s2.length()-s1.length();
		});
		String def = elements[0];
		List<String> pathStr = new ArrayList<String>();
		pathStr.add(def);
		for(int i = 1;i<elements.length;i++) {
			def+="."+elements[i];
			pathStr.add(def);
		}
		pathStr.sort((String s1,String s2)->{
			return s2.length()-s1.length();
		});
		int i = 0;
		while(e.getParent()!=null) {
			Map<String, String> attribute = new HashMap<String, String>();
			attribute.put("ID",ids+list.get(i));
			attribute.put("path",pathStr.get(i));
			get_elements.put(e, attribute);
			e = e.getParent();
			i++;
		}
		list_elements.put(ids,list_element);
		ids++;
	}
	/**
	* <p>
	* <code>getElementInformationWithoutThisPath</code> method can be
	* The mapping matches the corresponding path information according to the label object, but the coordinates of this element are not attached.
	* Information, only the coordinates and path information of its parent element
	* </p>
	* @param e element object
	* @return matches the path information
	*/
	public String getElementInformationWithoutThisPath(Element e) {
		return XMLHelper.getTheInformation(e.getParent(),get_elements);
	}
	/**
	* <p>
	* <code>getElementInformationWithThisPath</code> method can be
	* The mapping matches the corresponding path information according to the label object. This method is accompanied by the coordinates of this element.
	* Information, only the coordinates and path information of its parent element
	* </p>
	* @param e element object
	* @return matches the path information
	*/
	public String getElementInformationWithThisPath(Element e) {
		return XMLHelper.getTheInformation(e,get_elements);
	}

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
	public synchronized void whenChildTheElementIndex(String element,Element e,int... index) throws IllegalMappingException, IOException, IndexLengthException {
		String path = getElementInformationWithoutThisPath(e);
		String[] indexs = path.split(EN_DASH);//sque-a-b-c-d 0-0-0
		int[] is = XMLHelper.toIntArray(indexs);
		//获取这个标签的路径，从而获取
		Map<String, Integer> map =  list_elements.get(is[0]);
		int i =map.get(element) == null?-1:map.get(element);
		map.put(element,i+1);
		Map<String, String> attribute = new HashMap<String, String>();
		attribute.put("ID", path+EN_DASH+map.get(element)+"");
		attribute.put("path", element);
		get_elements.put(e,attribute);
	}
	/**
	* <p>
	* Internally used method for setting ID and name for mapping
	* </p>
	* @param elements
	* @param start
	* @param sque
	* @param list_element
	* @return
	*/
	private List<String> setIDAndName(String[] elements,int start,Map<String, Integer> list_element) {
		String lowestElementID = "";
		List<String> names = new ArrayList<>();
		String name = "";
		for(int i = 0;i<elements.length;i++) {
				name = name+elements[i]+".";
				names.add(name.substring(0,name.lastIndexOf(".")));
		}
		for(int i = start;i<elements.length;i++) {
			list_element.put(names.get(i), list_element.get(names.get(i))==null?0:list_element.get(names.get(i)));
		}
		List<String> list = new ArrayList<>();
		for(int i = 0;i<elements.length;i++) {
			if(elements.length >=3) {
				for(int j = 0;j<i;j++) {
					Integer number = names.get(i)==null?0:list_element.get(names.get(i));
					lowestElementID = lowestElementID+"-"+number;
					list.add(lowestElementID);
				}
			}else {
				for(int j = 0;j<i+1;j++) {
					Integer number = names.get(i)==null?0:list_element.get(names.get(i));
					lowestElementID = lowestElementID+"-"+number;
					if(list.size()!=3&&j!=1) {
						list.add(lowestElementID);
					}
				}
			}
		}
		return list;
	}
	/**
	* <p>
	* <code>getElementMap</code> gets the mapped mock element
	* </p>
	* @return
	*/
	public Map<Element, Map<String, String>> getElementMap() {
		return get_elements;
	}
}