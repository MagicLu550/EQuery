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
 * finished on 3.14 ,2019 ,9:18
 * 
 * 
 */
package net.noyark.www.console;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * it can print in the console
 * @author magiclu550
 * @see java.io.PrintStream#println(Object)
 */
public class Console {
	/**
	 * like System.out.println
	 * @param message
	 * @see System#out
	 */
	public static void log(Object message) {
		System.out.println(message);
	}
	/**
	 * the err print,with the date
	 * @param message
	 */
	public static void err(Object message) {
		SimpleDateFormat format = new SimpleDateFormat("MM-HH:mm:ss");
		String dateString0 = format.format(new Date());
		System.err.println(dateString0+" message："+message);
	}
}
