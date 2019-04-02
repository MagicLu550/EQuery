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
 * finished on 3.9 ,2019 ,14:18
 * 
 * 
 */
package cn.gulesberry.www.entity;

import java.io.Serializable;

/**
 * <p>
 * 	<i>For better manipulation of elements</i>,this class
 * 	is usually obtained by <code>getSElement</code>,
 * 	the <code>SElement</code>object corresponding to Element,
 * 	which is a reabstraction of the scattered information
 * 	,easy to partition management data and secure data 
 * 	security. 
 * </p>
 * @author magiclu550
 * @since JDK1.8
 * @since EQuery 003
 * @see org.dom4j.Element
 */
public abstract class SElement implements Serializable{
	private static final long serialVersionUID = 1L;
	/**This is the path of the Element object,such as a.b.c and so on*/
	protected String element;
	/**This is the Element inner text*/
	protected String text;
	/**
	 * <p>
	 * 	This construct is called by its <code>subclass</code> and is
	 * 	responsible for building subclass objects
	 * </p>
	 * @param element the path of the Element object
	 * @param text the Element inner Text
	 */
	public SElement(String element,String text) {
		this.element = element;
		this.text = text;
	}
	/**
	 * <p>
	 * 	you can get the path by using this method
	 * </p>
	 * @return the element path
	 */
	public String getElement() {
		return element;
	}
	/**
	 * <p>
	 * 	you can get the text by using this method
	 * </p>
	 * @return the element innerText
	 */
	public String getText() {
		return text;
	}
	/**
	 * <p>
	 * 	This is to change the <i>mapping information</i>
	 * 	when the Element object changes.In order to
	 * correspond,private changes are not allowed
	 * </p>
	 * @param text the text that changed
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * <p>
	 * 	This is to change the <i>mapping information</i>
	 * 	when the Element object changes.In order to
	 * correspond,private changes are not allowed
	 * </p>
	 * @param path the path that changed
	 */
	public void setPath(String path) {
		this.element = path;
	}
	/**
	 * <p>
	 * 	This method can get the main mapping information,
	 * 	It is overrided in the subclass. 
	 * </p>
	 * @return
	 */
	public abstract int[] getIndexWithOutFrist();
}
