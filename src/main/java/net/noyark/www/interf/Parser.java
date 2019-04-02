package net.noyark.www.interf;

import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.use.XParser;
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
public interface Parser {
	/**
	 * This method can parse the ReadingXML's subclass
	 * @see XParser
	 * @see Parser
	 */
	void parse(XMLDomFile xMl,Object o,Integer id,Map<Integer, Element> all) throws IllegalMappingException, IndexLengthException, IOException, DocumentException, IllegalArgumentException, IllegalAccessException;
}
