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

import net.noyark.www.annotations.Start;
import net.noyark.www.interf.Queryer;
/**
 * 	you can use this class to get the equery instance,
 * 	just return a equery instance,but if you use it again,
 * 	you can use start method without paramters
 * @author magiclu550
 * @see EQuery
 * @since JDK1.8
 * @since EQuery 026
 */
@Start(Queryer.class)
public class Core {
	/**This is the EQuery instance
	 * */
	private static Queryer queryer;
	/**
	 * if you start first,must use this method.
	 * @param getVersion {@link EQuery#EQuery(boolean, String)}
	 * @param xmlStartFile {@link EQuery#EQuery(boolean, String)}
	 * @return the Queryer instance;
	 * @see EQuery#EQuery(boolean, String)
	 */
	public static Queryer start() {
		if(queryer!=null) {
			return queryer;
		}
		queryer = new EQuery();
		return queryer;
	}
	/**
	 * @see Queryer#close()
	 * @see EQuery#close()
	 */
	public static void close() {
		queryer.close();
	}
}
