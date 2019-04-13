package net.noyark.www.interf;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;

public interface EPathAbastractApplication {
	List<Element> excuteShell(XMLDomFile xmlDomFile,StringBuilder builder) throws IOException, IllegalMappingException, IndexLengthException, DocumentException;
}
