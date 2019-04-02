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

import java.io.File;

import cn.gulesberry.www.helper.XMLHelper;
/**
 * This class is used to manage mapping,
 * files for non-special paths.
 * @author magiclu550
 *@since EQuery 015
 *@since JDK1.8
 *@see XMLHelper
 */
public class DefaultXMLDocumentHelper {
	/**
	 * reset one mapping file
	 */
	public static void resetDocument(String file) {
		file = XMLHelper.setFileName(file);
		File mapping = new File(getSrc()+file);
		mapping.delete();
	}
	/**
	 *reset all mapping files
	 */
	public static void resetDocument() {
		File mapping = new File(getSrc());
		File[] files = mapping.listFiles();
		for(File f:files) {
			f.delete();
		}
	}
	/**
	 * get the mapping path
	 * @return the mapping path
	 */
	public static String getSrc() {
		return SetMavenJar.getInstance().getClass().getResource("/").getPath()+"mapping";
	}
}
