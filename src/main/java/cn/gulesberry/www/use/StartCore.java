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


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import cn.gulesberry.www.core.EQuery;
import cn.gulesberry.www.core.MainProperties;
import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.properties.SimpleProperties;
import net.noyark.www.console.Console;
import net.noyark.www.interf.StartCase;
/**
 * This class implements the StartCase interface,
 * which can be used to synthesize the driver-initiated
 * operation and issue the main information,which will
 * be used in the equery main driver.
 * @author magiclu550
 * @since EQuery 019
 * @since JDK 1.8
 * @see EQuery 
 */
public class StartCore implements StartCase{
	/**the main properties(driver.properties)
	 * */
	MainProperties mainProperties = MainProperties.getInstance();
	/**
	 * This is the main driver core,which can
	 *  detect if a class can be loaded,preform Maven assembly
	 *  ,and detect the latest version and current version
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	private void checkAndUpdate() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		try {
				SimpleProperties properties = new SimpleProperties(this, mainProperties.getVersionInfo());
				Console.log("======EQUERY XML FRAMEWORK DRIVER IS STARTING======");
				Console.log("GULESBERRY TECH NSH-------------[version:"+properties.getString("version","")+"]");
				check();
			} catch (ClassNotFoundException e) {
				try {
					SetMavenJar.getInstance().XQueryMessage();
					check();
				}catch(Exception e1) {
					Console.err(" 信息:编辑代码检查是否可以使用org.dom4j.Element");
					return;
				}
			}
	}
	private void checkAndInstance() throws ParserConfigurationException, SAXException, IOException {
		try {
			Console.err("信息: 检测装配成功........");
			Console.err("信息: is loading the "+mainProperties.getInstancePath()+" creating the new instance");
			SetMavenJar.getInstance().prepareInstance();
		}catch(Exception e) {
			Console.err("信息:装配已经完成");
		}
		if(mainProperties.getVersionCheck()) {
			Console.log("连接版本数据库...");
			Console.log(EQuery.getEQueryVersion());
		}
	}
	
	/**
	 * This method can see if the class can be driven
	 * @throws ClassNotFoundException
	 */
	private void check() throws ClassNotFoundException {
		Console.err("信息:检测是否装配...");
		Class.forName("org.dom4j.Element");
		Class.forName("org.apache.commons.dbcp.BasicDataSource");
	}
	/**
	 * @see SetMavenJar#annotationInstance(String)
	 * @param xmlDefault the file like xmlStart.xml
	 * @throws ClassNotFoundException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DocumentException
	 * @throws IllegalMappingException
	 * @throws IndexLengthException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void annotationInstance() throws ClassNotFoundException, ParserConfigurationException, SAXException, IOException, DocumentException, IllegalMappingException, IndexLengthException, InstantiationException, IllegalAccessException{
		SetMavenJar.getInstance().annotationInstance();
	}
	/**
	 * This method will drive the sorted order and will
	 * be used in the main drive
	 * @param getVersion true-check version false-no
	 * @param file the file like xmlStart
	 */
	public void start() {
		try {
			checkAndUpdate();
			checkAndInstance();
			annotationInstance();
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
}
