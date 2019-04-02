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
 * author is a middle school student,so he doesn't have much time.If you think his code is good for you,you
 * can touch the 'star',thanks
 * 
 * 
 * finished on 3.8 ,2019 ,21:29
 * 
 * 
 */
package cn.gulesberry.www.runtime;

/**
 * Encapsulated retrieval memory return information
 * @author magiclu550
 * @since ETest 001
 * @since EQuery 009
 * @since JDK 1.8
 * @see Runtime
 */

public class RuntimeListener {
	/**The runtime instance
	 * */
	private static Runtime runtime;
	/**1MB = 1024*1024 byte
	 * */
	public static final int MB = 1024*1024;
	static {
		runtime = Runtime.getRuntime();
	}
	/**
	 * Get the maximum memory that jvm can mine
	 * @return the max memory that jvm can mine
	 */
	public static String getMaxMemory() {
		return "the max memory is "+runtime.maxMemory()/MB+"M";
	}
	/**
	 * Get the actual maximum memory of jvm
	 * @return the maximum memory of jvm
	 */
	public static String getTotalMemory() {
		return "the total memory is "+runtime.totalMemory()/MB+"M";
	}
	/**
	 * get the free memory
	 * @return the free memory
	 */
	public static String getFreeMemory() {
		return "the free memory is "+ runtime.freeMemory()/MB+"M";
	}
	/**
	 *Get the processor being utilized number
	 * @return the number
	 */
	public static String getAvailableProcessors() {
		return "the AvailableProcessors is "+runtime.availableProcessors(); 
	}
	/**
	 * Get the memory you are using
	 * @return the memory that are using
	 */
	public static String getTheUsingMemory() {
		return "the memory that is used is "+((runtime.totalMemory()-runtime.freeMemory())/MB)+"M";
	}
}
