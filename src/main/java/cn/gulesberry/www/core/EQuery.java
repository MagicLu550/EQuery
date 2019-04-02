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
 * author is a middle school student,so he doesn't have much time.
 * If you think his code is good for you,you can touch the 'star',thanks
 * 
 * 
 * 
 * 
 */
package cn.gulesberry.www.core;


import static cn.gulesberry.www.use.VersionManager.connectionVersionDB;

import java.io.IOException;

import cn.gulesberry.www.use.StartFactory;
import net.noyark.www.interf.Queryer;

/**
 * <table border='1px'>
 * 	<tr>
 * 		<td>
 * <div style='background-color:#CCCCFF'>
 * WEB:<a href="http://magic.noyark.net">web</a>
 * DOC:<a href="http://sxml.noyark.net">doc</a>
 * implementation the <code>{@link net.noyark.www.interf.Queryer}</code>
 * <p>
 * The <code>driver</code> class of the api can detect
 * the version number and whether you have 
 * installed the dependency library of this
 * api.It can automatically assemble the <i>dependency
 * library</i> and read and write the corresponding 
 * configuration folder to realize the hungry instantiation
 * </p>
 * <p>
 * This xml framework simplifies the operation of the persistence 
 * layer configuration file and supports the object's IOC, which greatly 
 * simplifies the operation and is compatible with the usual dom4j operations.
 * It can be used in parallel with the traditional method (of course, the corresponding
 * method has been provided in the framework. You must use the methods of the framework 
 * to add elements, but these methods have simplified operations on the framework, as well 
 * as the original operation. Because the elements are added to the framework to register 
 * elements to the mapping factory and are compatible with the operation of the selector,
 *  and the selector is added. Function, complete element selection mechanism, selector 
 * ::with * 6 features *:: ： 
 * 
 * <P>dynamic creation (DC) combined with read and write (RW) reflection path control (RPC) automatic assembly (AA) selection and 
 * converter (SC) object pool (OP) 
 * , search package instance (SPI)
 * <P>DC: meaning that objects can be created dynamically and created according to various forms of configuration files,
 *  which is very flexible
 * <P>RW: means that it can be compatible with the newly added elements of the document that has been written, and will not 
 * refresh the original created document.
 * <P>RPC:  meaning the rules of the elements are created by path and reflection, thus simply adding elements
 * <P>AA:  meaning that the maven dependencies can be automatically assembled by loading the framework driver.
 * <P>SC: built-in converter can convert dom4j Element object into SElement recording element geometry information, and then 
 * used for selector hash query element
 * <P>OP: the object pool can store the original object, the previously created object can be reused
 * <P>SPI: through the package path of the configuration file, search for the class with annotations, instantiate the object according to the annotation, 
 * and create the file at one time
 * </p>
 * </div>
 * 		</td>
 * 			</tr>
 * </table>
 * @author magiclu550
 * @since JDK1.8
 * @since EQuery 022
 * @see net.noyark.www.interf.StartCase
 * @see net.noyark.www.interf.Queryer
 */
public class EQuery implements Queryer{
	public static final int SID = 0x78701;
	/**
	 * This is the equery's version*/
	public static String VERSION;
	/**
	 *<p>
	 *	The <code>EQuery</code> constructor <i>determines</i>
	 *  whether the version can be detected
	 *  based on the <i>parameters</i>,and drives
	 *  and manages the basic information.
	 *  It is instantiated directly when used,
	 *  It is recommended to use the interface
	 *  as a reference.
	 *</p>
	 * @param getVersion whether you want to get the version or not
	 * @param file the instance creater driver file
	 * @see net.noyark.www.interf.StartCase#start(boolean, String)
	 */
	EQuery(){
			StartFactory.newInstance().start();
	}
	/**
	 * <p>
	 * The method has no practical meaning,the purpose
	 * is to prevent warnings from being fired.
	 * </p>
	 * @see net.noyark.www.interf.Queryer#close()
	 */
	public void close() {}
	/**
	 * you can use this method to get the version,and
	 * control the database server,check the new version
	 * @param check check whether you are new version
	 * @return the version
	 * @throws IOException you can see {@link IOException}
	 */
	public static String getEQueryVersion() throws IOException{
		connectionVersionDB();
		return "gulesberry tech SimpleXML API version-"+VERSION+" release SID= "+SID;
	}
}
