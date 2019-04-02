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
 * finished on 3.14 ,2019 ,12:18
 * 
 * 
 */
package cn.gulesberry.www.entity;


/**
 * <p>
 * <code>DSElement</code> is used in existing file objects,
 * which define mapping information about the 
 * <code>{@link cn.gulesberry.www.entity.SElement}</code> object.ID,etc.,
 * and related attributes obtained from <code>SElement<code>,
 * DSElement is retrieved by id.
 * </p>
 * @author magiclu550
 * @since JDK1.8
 * @since EQuery 020
 * @see cn.gulesberry.www.entity.SElement
 * 
 */

public class DSElement extends SElement{

	private static final long serialVersionUID = 1L;
/**This is the ID of Element,
 * It can check the {@link org.dom4j.Element} Object directly*/
	private int[] ID;
	/**
	 * This constructor is not open to developers.It can
	 * convert <code>Element</code> to <code>SElement</code> 
	 * in <code>getSElement</code> and can be applied in the Queryer;
	 * @param element the path of the Element Object
	 * @param text the text of the Element
	 * @param ID the ID of the Element Mapping
	 * @see net.noyark.www.interf.XMLDomFile#getSElement(org.dom4j.Element)
	 * @see cn.gulesberry.www.entity.SElement#SElement(String, String)
	 * @see cn.gulesberry.www.entity.ISElement#ISElement(String, String, int[])
	 */
	public DSElement(String element, String text,int[] ID) {
		super(element, text);
		this.ID = ID;
	}
	/**
	 * <p>
	 * This method can get the <strong>mapping Id</strong>.The method 
	 * name is used to <i>synchronize</i> with other subclasses.
	 * This can be used to locate the specified <code>Element</code> 
	 * object
	 * </p>
	 * @return the array with the ID
	 * @see cn.gulesberry.www.entity.SElement#getIndexWithOutFrist()
	 */
	
	public int[] getIndexWithOutFrist() {
		return ID;
	}
	/**
	 * <p>
	 * This method override <i><code>toString</code></i>
	 * ,from {@link java.lang.Object}
	 * </p>
	 * </p>
	 * which optimizes <i>object pool</i> performance
	 * and searches for objects
	 * </p>
	 * @return the DSelement's all information
	 * @see java.lang.Object#toString()
	 * @see cn.gulesberry.www.entity.SElement#toString()
	 * @see cn.gulesberry.www.entity.ISElement#toString()
	 */
	public String toString() {
		return element+","+text+","+ID[0];
	}
}