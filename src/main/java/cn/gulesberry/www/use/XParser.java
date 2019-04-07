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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import net.noyark.www.annotations.After;
import net.noyark.www.annotations.Path;
import net.noyark.www.interf.Parser;
import net.noyark.www.interf.XMLDomFile;
/**
 * This class is used to parse the subclass of
 * ReadingXML.It can parse the path into a normal
 * method to add elements.The internal core is actually the 
 * XMLDomFile type
 * @author magiclu550
 * @since EQuery 018
 * @since JDK 1.8
 * @see Parser 
 */
public class XParser implements Parser{
	/**
	 * This method can parse the ReadingXML's subclass
	 * @see XParser
	 * @see Parser
	 */
	public void parse(XMLDomFile xMl,Object o,Integer id,Map<Integer, Element> all) throws IllegalMappingException, IndexLengthException, IOException, DocumentException, IllegalArgumentException, IllegalAccessException  {
		Class<?> clazz = o.getClass();
		Field[] field = clazz.getFields();
		for(Field f:field) {
			Annotation[] annotations = f.getAnnotations();
			for(Annotation annotation:annotations) {
				if(annotation instanceof Path) {
					Path path = (Path)annotation;
					String elementPath = path.path();
					if(elementPath.equals("")) {
						elementPath = path.value();
					}
					if(elementPath.equals("")) {
						break;
					}
					int[] indexs = path.indexs();
					String text;
					if(f.getType().isArray()) {
						text = Arrays.toString((Object[])f.get(o));
					}else {
						text = f.get(o)+"";
					}
					String before = null;
					
					if(elementPath.indexOf(":")!=-1) {
						before = elementPath.substring(0,elementPath.indexOf(":")+1);
						elementPath = elementPath.substring(elementPath.indexOf(":")+1);
					}
					//addElement
					if(before==null||before.equals("addnew:")) {
						After after = f.getAnnotation(After.class);
						if(after!=null) {
							int pos = after.value();
							Element e = all.get(pos);
							Element added = xMl.addElementAfter(e,elementPath);
							added.addText(f.get(o)+"");
							break;
						}
						Element e = xMl.addElement(elementPath, text);
						id++;
						all.put(id,e);
						//addChild
					}else if(before.equals("addchild:")){
						Element e = xMl.addChild(elementPath,text,indexs);
						id++;
						all.put(id,e);
						//addElementsIf..
					}else if(before.equals("addchilds:")) {
						//必须为数组
						if(text.indexOf("[")!=-1) {
							String[] texts = text.replace("[", "").replace("]", "").split(", ");
							List<String> list = Arrays.asList(texts);
							List<Element> allChilds = xMl.addElementsIfExistsParent(elementPath,list, indexs);
							for(Element e:allChilds) {
								id++;
								all.put(id,e);
							}
						}else {
							throw new IllegalMappingException("Unusual correspondence,you may not use arrays corresponding to multidimensional annotations");
						}	
						//addElements
					}else if(before.equals("addelements:")){
						if(text.indexOf("[")!=-1) {
							String[] texts = text.replace("[", "").replace("]", "").split(", ");
							List<Element> allChilds = xMl.addElements(elementPath,texts);
							for(Element e:allChilds) {
								id++;
								all.put(id,e);
							}
						}else {
							throw new IllegalMappingException("Unusual correspondence,you may not use arrays corresponding to multidimensional annotations");
						}	
					}
				}
			}
		}
	
	}
}
