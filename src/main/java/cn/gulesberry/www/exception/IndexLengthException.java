package cn.gulesberry.www.exception;
/**
 * <p>
 * 	The exception class is used to rertrieve whether
 * 	the coordinates exceed the maximum coordinate limit.
 * 	If the element pointed to by the coordinate pointer 
 * 	does not exist, the exception will be thrown
 * </p>
 * @author magiclu550
 * @since JDK1.8
 * @since EQuery 007
 * @see java.lang.Exception
 *
 */
public class IndexLengthException extends ElementFoundException {

	/**
	 * The serialVersionUID of Serializable interface
	 */
	private static final long serialVersionUID = -8532006548014377976L;
	/**
	 * @see java.lang.Exception#Exception()
	 */
	public IndexLengthException() {
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

	public IndexLengthException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @see java.lang.Exception#Exception(String, Throwable)
	 * @param message the exception message
	 * @param cause cause the exception that caused this exception
	*/
	public IndexLengthException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @see java.lang.Exception#Exception(String)
	 * @param message the exception message
	 */
	public IndexLengthException(String message) {
		super(message);
	}
	/**
	 * @see java.lang.Exception#Exception(Throwable)
	 * @param cause cause the exception that caused this exception
	 */
	public IndexLengthException(Throwable cause) {
		super(cause);
	}	
}