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
 * finished on 3.27 ,2019 ,16:15
 * 
 * 
 */
package cn.gulesberry.www.exception;

/**
 * <p>
 * 	The role of the <i>exception</i> class is all of
 * 	the excpetion about XML Element found's base exception
 * </p>
 * @author magiclu550
 * @since JDK1.8
 * @since EQuery 005
 * @see java.lang.Exception
 */
public class ElementFoundException extends RuntimeException{

	private static final long serialVersionUID = 6488791977787496785L;
	/**
	 * @see java.lang.Exception#Exception()
	 */
	public ElementFoundException() {
		super();
	}
	/**
	 * override the protected method
	 * @see java.lang.Exception#Exception(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace)
	 * @param message the exception message
	 * @param cause the exception that caused this exception
	 * @param enableSuppression the boolean isEnable..
	 * @param writableStackTrace the boolean is Writable
	 */
	public ElementFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	/**
	 * @see java.lang.Exception#Exception(String, Throwable)
	 * @param message the exception message
	 * @param cause cause the exception that caused this exception
	*/
	public ElementFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * @see java.lang.Exception#Exception(String)
	 * @param message the exception message
	 */
	public ElementFoundException(String message) {
		super(message);
	}
	/**
	 * @see java.lang.Exception#Exception(Throwable)
	 * @param cause cause the exception that caused this exception
	 */
	public ElementFoundException(Throwable cause) {
		super(cause);
	}

}
