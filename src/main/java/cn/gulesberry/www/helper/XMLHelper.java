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
 * finished on 3.5 ,2019 ,11:18
 * 
 * 
 */
package cn.gulesberry.www.helper;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gulesberry.www.entity.DSElement;
import cn.gulesberry.www.entity.ISElement;
import cn.gulesberry.www.entity.SElement;
import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.exception.SElementClassCastException;
import cn.gulesberry.www.io.DefaultDocument;
import cn.gulesberry.www.io.InitDocument;
import cn.gulesberry.www.io.XMLDocument;
import cn.gulesberry.www.manager.DefaultXMLManager;
import cn.gulesberry.www.manager.XMLManager;
import net.noyark.www.interf.Manager;
import net.noyark.www.interf.XMLDomFile;
import net.noyark.www.list.Query;
/**
 * <p>
 * This class is a method aggregation class
 * called inside the framework.It is a private 
 * method collection ,It is also the core code
 * part of the entire EQuery
 * </p>
 * @author magiclu550
 * @since EQuery 001
 */
public final class XMLHelper {
	/**This is the instance pool,all of the Dom Object
	 * are in there*/
	static List<XMLDocument> instancesPool = new ArrayList<XMLDocument>();
	/**It is used for the factory
	 * */
	static XMLHelper helper;
	static {
		helper = new XMLHelper();
	}
	/**
	 * <p>
	 * This method can search for the specified element,add 
	 * the previous parent element,and return the last element,
	 * Before that,other elements have been added successfully.
	 * </p>
	 * @param start the start index of element path
	 * @param elements the element path
	 * @param next element after the root element
	 * @param end the more than elements.length
	 * @return the last Element Object
	 */
	public static Element addElements(int start,String[] elements,Element next,int end,List<Element> list) {
		for(int i = start;i<elements.length+end;i++) {
			list.add(next);
			next = next.addElement(elements[i]);
		}
		return next;
	}
	/**
	 * <p>
	 *This method can superimpose the element level by superimposing'
	 * the element path, thus achieving the synchronization of element 
	 * addition and element query, and returning the element level.
	 * </p>
	 * @param element the path of the element
	 * @param index the indexs of the elements
	 * @param root the root element
	 * @return the element that is last one
	 * @throws IllegalMappingException you can see {@link IllegalMappingException}
	 */
	public static Element searchAddElement(String element,int[] index,Element root) throws IllegalMappingException {
		String[] elements = element.split(XMLDocument.POINT);
		throwMappingException(elements, index, 1);
		return addElement(elements, index, root);
	}
	/**
	 * <p>
	 * 	This METHOD encapsulates two methods and adds
	 *  element better
	 * </p>
	 * @param elements the element path
	 * @param index the indexs of element object
	 * @param root the root element
	 * @return the Element that be added
	 */
	private static Element addElement(String[] elements,int[] index,Element root) {
		Element par = element(elements, index, root);
		return par.addElement(elements[elements.length-1]);//index.length+1 == elements.length
	}
	/**
	 * <p>
	 * 	use the element path to search the element object,
	 * 	and used in XMLDom
	 * </p>
	 * @param elements the element names
	 * @param index the element indexs
	 * @param root the root element
	 * @return the last Element
	 */
	@SuppressWarnings("unchecked")
	public static Element searchElement(String[] elements,int[] index,Element root) {
		Element par = element(elements, index, root);
		List<Element> pars = par.elements(elements[elements.length-1]);//index.length == elements.length
		try {
			return pars.get(index[index.length-1]);//the last element the last index of index arr
		} catch (Exception e) {
			return par;
		}
	}
	
	/**<p>
	 * 	through this method,you can get the last
	 * 	second method
	 * </p>
	 * @param elements the element path
	 * @param index the elements index
	 * @param root the root element
	 * @return the last second element
	 */
	
	@SuppressWarnings("unchecked")
	public static Element element(String[] elements,int[] index,Element root) {
		List<Element> pars = root.elements(elements[0]);//get the biggest in the root element
		if(index.length >0) {
			Element par = pars.get(index[0]);
			for(int i = 1;i<elements.length-1;i++) {
				pars = par.elements(elements[i]);
				par = pars.get(index[i]);//the last second
			}
			return par;
		}
		return root;
	}
	
	/**
	 * <p>
	 * this method can determine whether it meets
	 * the conditions for throwing an exception
	 * </p>
	 * @param elements the element name(of path)
	 * @param index the element index
	 * @param i the Element.length sub index.length
	 * @throws IllegalMappingException you can see {@link IllegalMappingException}
	 */
	public static void throwMappingException(String[] elements,int[] index,int i) throws IllegalMappingException {
		if(elements.length != index.length+i) {
			if(i == 0) {
				throw new IllegalMappingException("elements.length!=index.length");
			}else {
				throw new IllegalMappingException("elements.length!=index.length+"+i);
			}
		}
	}
	/**
	 *@deprecated the method have a big bug
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static List<Element> elementQuery(List<Element> elementOne,String[] elements,int i) {
		for(Element e:elementOne) {
			elementOne = e.elements(elements[i]);
			if(i<elements.length-1) {
				elementOne = elementQuery(elementOne, elements, ++i);
			}
		}
		return elementOne;
	}
	/**
	 * Internal development in order to be compatible with developers without xml 
	 * suffix and xml suffix to write files.Custom, this method 
	 * will be called when instantiated, depending on the 
	 * developer's <code>file</code> property
	 * Is there an xml suffix to complement the .xml suffix?
	 * @param file
	 * @return
	 */
	public static String setFileName(String file) {
		String[] names = file.split(XMLDocument.POINT);
		if(names[names.length-1].equals("xml")) {
			return file;
		}else {
			return file+".xml";
		}
	}
	/**
	 * <p>
	 * This method is used to initialize an object for the first time
	 * , will be called in <code>InstanceQueryer</code>, used to 
	 * create objects, built through a set of paths and root
	 * </p>
	 * @param file　the xml file name
	 * @param rootElement the root element
	 * @return the XMLDocument Object
	 */
	static XMLDocument getByGroup(String file,String rootElement) {
		file = setFileName(file);
		InitDocument simpleXML;
		if((simpleXML =searchInstance(file, rootElement))!=null) {
			return simpleXML;
		}	
		simpleXML= new InitDocument(file, rootElement);
		instancesPool.add(simpleXML);
		return simpleXML;
	}
	/**
	 * <p>
	 * This method is used to initialize an object for the first time
	 * , will be called in <code>InstanceQueryer</code>, used to 
	 * create objects, built through a set of paths and root,and this 
	 * method can set the root "xmlns" attribute.
	 * </p>
	 * @param file　the xml file name
	 * @param rootElement the root element
	 * @return the XMLDocument Object
	 */
	public static XMLDomFile getByGroupWithUrl(String file,String rootElement,String url) {
		file = setFileName(file);
		InitDocument simpleXML;
		if((simpleXML =searchInstance(file, rootElement))!=null) {
			return simpleXML;
		}
		simpleXML= new InitDocument(file, rootElement,url);
		instancesPool.add(simpleXML);
		return simpleXML;
	}
	/**
	 * This is method can find the instance by the file and rootElement
	 * @param file the file name
	 * @param rootElement the root element
	 * @return the InitDocument instance
	 */
	static InitDocument searchInstance(String file,String rootElement) {
		for(XMLDocument xml:instancesPool) {
			if(xml.isDefault()==false) {
				String[] xmls = xml.toString().split(",");
				//xmls[0] file xmls[1] root
				if(xmls[1].equals(rootElement)) {
					if(xmls[0].equals(file)) {
						return (InitDocument)xml;
					}
				}
			}
		}	
		return null;
	}
	/**
	 * <p>
	 * This method is used to initialize an object for the second time. 
	 * It will be called in <p>InstanceQueryer</p> to search the object 
	 * and search through the path.
	 * </p>
	 * @param file the xml file
	 * @return the XMLFile Object
	 * @throws DocumentException see{@link DocumentException}
	 */
	static XMLDocument getByFile(String file) throws DocumentException {
		file = setFileName(file);
		return getByFileFormat(file);
	}
	/**
	 * it can use other file format.
	 * @param file the file name
	 * @return XMLDocument Object
	 */
	public static  XMLDocument getByFileFormat(String file) {
		for(XMLDocument xml:instancesPool) {
			if(xml.isDefault()==false) {
				String[] xmls = xml.toString().split(",");
				//xmls[0] file xmls[1] root
					if(xmls[0].equals(file)) {
						return xml;
				}
			}
		}
		InitDocument defSX = new InitDocument(file, "default");
		instancesPool.add(defSX);
		return defSX;
	}
	/**
	 * This method can retrieve all existing element objects 
	 * from the existing files, register them in the map, 
	 * and join the search factory. This method will not be 
	 * used in non-existent files,and this scenario is not 
	 * considered.
	 * @param e1s　the element list
	 * @param elements the empty list
	 */
	@SuppressWarnings("unchecked")
	public static void allDefaultElements(List<Element> e1s,List<Element> elements) {
		for(Element e2:e1s) {
			elements.add(e2);
			List<Element> e2s = e2.elements();
			allDefaultElements(e2s, elements);
		}
	}
	/**
	 * <p>
	 * 	This method is used to determine whether each coordinate 
	 * 	has exceeded its limit, resulting in the element map not 
	 * 	being found.See IndexLengthException for details.
	 * </p>
	 * @param index the elements indexs
	 * @param element the element path
	 * @param file the xml file name
	 * @throws IndexLengthException see{@link IndexLengthException}
	 * @throws IllegalMappingException see {@link IllegalMappingException}
	 * @throws DocumentException see {@link DocumentException}
	 * @see IndexLengthException
	 */
	public static void throwIndexLengthException(int[] index,String element,String file)
																			throws IndexLengthException, IllegalMappingException, DocumentException {
		String[] elements = element.split(XMLDocument.POINT);
		String elem = elements[0];
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 1;i<elements.length;i++) {
			list = new ArrayList<Integer>();
			System.gc();
			elem += "."+elements[i];
			String elemAfter = elem.substring(0,elem.lastIndexOf("."));
			for(int j = 0;j<i;j++) {
				list.add(index[j]);
			}
			String[] strings = listToArray(list);
			int[] is = arrayToIntArray(strings);
			int max = InstanceQueryer.getXMLQuery(file, true).getMaxPos(elemAfter,is);
			if(index[i-1]>max) {
				if(max!=-1)throw new IndexLengthException("index>the max index");
			}
		}
	}
	/**
	 * <p>
	 * 	Can convert a collection of integers 
	 *	to an array of strings, 
	 *	a tool for internal data conversion
	 * </p>
	 * @param list the int list
	 * @return the String array
	 */
	public static String[] listToArray(List<Integer> list) {
		String[] strings = list.toString().replace("[","").replace("]", "").split(", ");
		return strings;
	}
	/**
	 * <p>
	 * 	Convert an array of coordinate strings into a coordinate 
	 * 	string that can be converted directly for internal tools
	 * </p>
	 * @param strs the String array
	 * @return the String path
	 */
	public static String arrayToString(String[] strs) {
		return Arrays.toString(strs).replace("[","").replace("]", "").replaceAll(", ", ".");
	}
	/**
	 * <p>
	 * Convert a string array to an integer array if the string 
	 * array is legal, provided that it is a valid array, 
	 * otherwise a NumberFormat exception will be generated.
	 * </p>
	 * 
	 * @param strings the String array
	 * @return the int array
	 */
	public static int[] arrayToIntArray(String[] strings) {
		int[] is = new int[strings.length-1];
		for(int y = 0;y<is.length;y++) {
			is[y] = Integer.parseInt(strings[y]);
		}
		return is;
	}
	/**
	 *It can list the index in the mapping
	 *and under 3 methods,there is no Specific introduction
	 * @param parentElementName the parent elements's name
	 * @param parentString the parent on this
	 * @param elements all elements name
	 * @return all indexs mapping
	 */
	public static String[]  toListingTheElementPath(String[] parentElementName,String parentString,String[] elements) {
		String[] strings = new String[parentElementName.length];
		toListThat(parentElementName, parentString, elements, strings);
		return strings;
	}
	public static String toListTheElementPath(String[] parentElementName,String parentString,String[] elements) {
		return toListThat(parentElementName, parentString, elements, new String[parentElementName.length]);
	}
	private static String toListThat(String[] parentElementName,String parentString,String[] elements,String[] strings) {
		for(int i = 0;i<parentElementName.length;i++) {
			parentElementName[i] = elements[i];
			parentString = XMLHelper.arrayToString(parentElementName).replaceAll(XMLDocument.POINT+"null", "");	
			strings[i] = parentString;
		}
		return parentString;
	}
	/**
	 * <p>
	 * 	You can convert a String array to an Int array.
	 * </p>
	 * @param indexs the String array
	 * @return the int array
	 */
	public static int[] toIntArray(String[] indexs) {
		int[] is = new int[indexs.length];
		for(int i = 0;i<indexs.length;i++) {
			is[i] = Integer.parseInt(indexs[i]); 
		}
		return is;
	}
	/**
	 * <p>
	 * Method of comparing element information used in 
	 * constructing mapping feedback.
	 * </p>
	 * @param e the element object that be compared
	 * @param get_elements the all elements map
	 * @return the element path
	 */
	public static String getTheInformation(Element e,Map<Element, Map<String,String>> get_elements) {
		String path = null;
		Set<Element> s = get_elements.keySet();
		String eString = e.toString().trim();
		for(Element ele:s) {
			String lowest = ele.toString();
			if(lowest.substring(0,lowest.indexOf("[") ).trim().equals(eString.substring(0,eString.indexOf("[")).trim())){
				String id = get_elements.get(ele).get("ID");
				path = id.replace(XMLDocument.SLASH,".");//获取坐标
			}
		}
		return path;
	}
	/**
	 * Convert an array to a string
	 * @param elements tht String array
	 * @return the String with "[","]"
	 */
	public static String getString(String[] elements) {
		return Arrays.toString(elements);
	}
	/**
	 * <p>
	 * get all elements from the default xml,
	 * Internally used recursive implementation.
	 * </p>
	 * @param doc the Document object
	 * @param elements the element object collection
	 */
	@SuppressWarnings("unchecked")
	public static void getAll(Document doc,List<Element> elements) {
		Element root = doc.getRootElement();
		List<Element> root_e1s = root.elements();
		for(Element e:root_e1s) {
			elements.add(e);
			List<Element> e1s = e.elements();
			XMLHelper.allDefaultElements(e1s, elements);
		}
	}
	/**
	 * <p>
	 * This method determines whether an exception should be thrown by 
	 * judging whether it is an ISElement type object.
	 * </e>
	 * @param e the SElement object
 	 * @throws SElementClassCastException @{link SElementClassCastException}
	 */
	public static void throwISElementException(SElement e) throws SElementClassCastException {
		if(!(e instanceof ISElement)) {
			throw new SElementClassCastException("this instance is not the ISElement");
		}
	}
	/**
	 * <p>
	 * This method determines whether an exception should be thrown by 
	 * judging whether it is an DSElement type object.
	 * </p>
	 * @param e the SElement object
 	 * @throws SElementClassCastException @{link SElementClassCastException}
	 */
	public static void throwDSElementException(SElement e) throws SElementClassCastException {
		if(!(e instanceof DSElement)) {
			throw new SElementClassCastException("this instance is not the DSElement");
		}
	} 
	/**
	 * <p>
	 * Change the normal Element object 
	 * return path to a compatible point path
	 * </p>
	 * @param e the Element object
	 * @return the point path
	 */
	public static String getElementPath(Element e) {
		String path = e.getPath();
		int start = path.indexOf("/",1);
		path = path.substring(start+1).replaceAll("/", ".");
		return path;
	}
	/**
	 * <p>
	 * Get the Element object through DSElement
	 * </p>
	 * @param e the DSElement Object(super SElement)
	 * @param manager the Manager Object
	 * @return the Element object
	 * @throws SElementClassCastException {@link SElementClassCastException}
	 */
	public static Element getElementByDElement(SElement e,Manager<Element> manager) throws SElementClassCastException {
		XMLHelper.throwDSElementException(e);
		Element element = null;
		Map<Element, Map<String,String>> map = manager.getElementMap();
		Set<Entry<Element, Map<String, String>>> set = map.entrySet();
		for(Entry<Element, Map<String, String>> entry:set) {
			Map<String, String> infor = entry.getValue();
			if(infor.get("ID").equals(e.getIndexWithOutFrist()[0]+"")) {
				element = entry.getKey();
			}
		}
		return element;
	}
	/**
	 * <p>
	 * Get the object corresponding to 
	 * the existing file from the object pool, 
	 * it will be called in InstanceQueryer
	 * </p>
	 * @param file the default file name
	 * @return tje XMLDocument Object
	 */
	public static XMLDocument getDefaultPoor(String file) {
		file = XMLHelper.setFileName(file);
		for(XMLDocument xml:instancesPool) {
			if(xml.isDefault()==true) {
				if(xml.getFile().equals(file)) {
					return xml;
				}
			}
		}
		return null;
	}
	/**
	 * write the document into the mapping file
	 * @param doc the Document Object
	 * @param mapping the mapping file object
	 */
	public static void write(Document doc,File mapping) {
		try {
			FileOutputStream fos = new FileOutputStream(mapping);
			XMLWriter writer = new XMLWriter(fos,OutputFormat.createPrettyPrint());
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * If the file already exists, this method will be called during 
	 * instantiation, which will create a file storage file before the 
	 * file is stored, thus implementing the function of 
	 * file selection and saving.
	 * @param file the XML file name
	 * @param doc the Document object
	 * @param elements the elements names
	 * @return the XMLDocument Object
	 * @throws IllegalMappingException 
	 * @throws IOException
	 * @throws IndexLengthException
	 */
	public static XMLDocument setElements(String file,Document doc,List<Element> elements) throws IllegalMappingException, IOException, IndexLengthException {
		XMLManager manager = new DefaultXMLManager();
		DefaultDocument existXML = new DefaultDocument(file,doc,manager);
		//get all Elements
		XMLHelper.getAll(doc, elements);
		for(Element e:elements) {
			String path = XMLHelper.getElementPath(e);
			manager.whenChildTheElementIndex(path, e, 0);
		}
		instancesPool.add(existXML);
		return existXML;
	}
	/**
	 * it can get the helper Instance
	 * @return the helper instance
	 */
	public static XMLHelper getInstance() {
		return helper;
	}	
	public static List<Element> $(Query query,XMLDomFile xdf,String element,int[] index) {
		List<Element> list = new ArrayList<>();
		switch(query) {
		case ALL:
			return xdf.getAllElements();
		case BROTHER_ON:
			list.add(xdf.getBrotherElementOn(element,index));
			return list;
		case BROTHER_UNDER:
			list.add(xdf.getBrotherElementUnder(element, index));
			return list;
		case BROTHERS_ON:
			return xdf.getBrotherElementsOn(element,index);
		case BROTHERS_UNDER:
			return xdf.getBrotherElementsUnder(element, index);
		case FRIENDS:
			return xdf.getChilds(element, index);
		case ELEMENT:
			list.add(xdf.getElement(element,index));
			return list;
		case ELEMENTS:
			String pathGroup = element;
			return xdf.getElements(pathGroup);
		case ELEMENTS_ID:
			String id = element;
			return xdf.getElementsByID(id);
		case ELEMENTS_NAME:
			return xdf.getElementsByName(element);
		case ELEMENTS_TEXT:
			String text = element;
			return xdf.getElementsByText(text);
		case PARENT:
			list.add(xdf.getParent(element,index));
			return list;
		default:
			return list;
	}
}
	/**
	 * This method can selector the element set to get the element
	 * object by the attribute names
	 * @param list the element object list
	 * @param attributeName the attribute names
	 * @return the element object that have these attribute
	 */
	public static List<Element> getElementsByAttributes(List<Element> list,String[] attributeName){
		return getThis(true, list, attributeName);
	}
	/**
	 * this method can change the boolean
	 * @param b the boolean
	 * @param list the elemnt list
	 * @param attributeName the attribute names
	 * @return the element object list
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getThis(boolean b,List<Element> list,String[] attributeName) {
		List<String> attributeNameList = Arrays.asList(attributeName);
		List<Element> theElementsThatHave = new ArrayList<>();
		for(Element e:list) {
			List<Attribute> attributes = e.attributes();
			List<String> nameList = new ArrayList<>();
			for(Attribute a:attributes) {
				nameList.add(a.getName());
			}
			if(nameList.containsAll(attributeNameList)&&b) {
				theElementsThatHave.add(e);
			}
		}
		return theElementsThatHave;
	}
	/**
	 * Like the other method,this can get element by strict attribute name
	 * @param list the element list
	 * @param attributeName the attribute names
	 * @return the element object list
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getElementsByNameStrict(List<Element> list,String[] attributeName) {
		List<String> attributeNameList = Arrays.asList(attributeName);
		List<Element> theElementsThatHave = new ArrayList<>();
		for(Element e:list) {
			List<Attribute> attributes = e.attributes();
			List<String> nameList = new ArrayList<>();
			for(Attribute a:attributes) {
				nameList.add(a.getName());
			}
			if(nameList.containsAll(attributeNameList)&&(nameList.size()==attributeNameList.size())) {
				theElementsThatHave.add(e);
			}
		}
		return theElementsThatHave;
	}
	/**
	 * It can trim all of the text(String)
	 * @param strings the String array
	 * @return the string array that is trimed
	 */
	public static String[] trimAll(String[] array) {
		for(int i = 0;i<array.length;i++) {
			array[i] = array[i].trim();
		}
		return array;
	}
}