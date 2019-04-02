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

import net.noyark.www.interf.StartCase;
/**
 *  The StartCase factory
 * @author magiclu550
 * @see StartCase
 * @see StartCore
 * @since EQuery 019
 * @since JDK 1.8
 */
public class StartFactory {
	/**
	 * This StartCase instance
	 */
	public static StartCase sc;
	static {
		sc = new StartCore();
	}
	/**
	 * Get the instance
	 * @return
	 */
	public static StartCase newInstance() {
		return sc;
	}
}
