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

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.use.StartFactory;
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
public class Core implements CoreBase{
	
	/**
	 * if you start,must use this method.
	 * @param getVersion {@link EQuery#EQuery(boolean, String)}
	 * @param xmlStartFile {@link EQuery#EQuery(boolean, String)}
	 * @return the Queryer instance;
	 * @see EQuery#EQuery(boolean, String)
	 */
	public static Queryer start() {
		return QUERY;
	}
	/**
	 * @see Queryer#close()
	 * @see EQuery#close()
	 */
	public static void close() {
		QUERY.close();
	}
	/**
	 * you can use this method startEpathShell in console
	 */
	public static void startEPathShell() {
		new Thread(()->{
			try {
				StartFactory.getApplication();
			} catch (IllegalMappingException | IndexLengthException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
