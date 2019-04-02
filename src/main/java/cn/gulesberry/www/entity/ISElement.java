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
 * finished on 3.14 ,2019 ,13:18
 * 
 */
package cn.gulesberry.www.entity;

import java.util.Arrays;
/**
 * <p>
 *	This class will be generated in the <i>innate
 *	initial</i> file,the Element is <strong>converted</strong> to
 *	SElement by the <code>getSElement</code> method,and the 
 *	<code>Element</code> object is retrieved by coordinates.
 *	<i>The polymorphic mechanism makes it less important</i>
 * </p>
 * @author magiclu550
 * @since JDK1.8 
 * @since EQuery 020
 * @see cn.gulesberry.www.entity.SElement
 */
public class ISElement extends SElement{

	private static final long serialVersionUID = 1L;
	/**This is the indexs of the Element
	 * ,like the 'index' param of queryer*/
	private int[] index;
	/**
	 * <p>
	 * This constructor is not recommended for
	 * devleopers.It is called when the <code>getSElement</code>
	 * method is built.Its coordinate sequence number
	 * is obtained from the label map
	 * </p>
	 * @param element the Element object's path
	 * @param text the Element object's text inner
	 * @param index the indexs of the element in the document
	 * @see net.noyark.www.interf.XMLDomFile#getSElement(org.dom4j.Element)
	 * @see cn.gulesberry.www.entity.SElement#SElement(String, String)
	 * @see cn.gulesberry.www.entity.ISElement#ISElement(String, String, int[])
	 */
	public ISElement(String element, String text, int[] index) {
		super(element, text);
		this.index = index;
	}
	/**
	 * @deprecated the index have a mistake that the last index is wrong 
	 * @return
	 */
	@Deprecated
	public int[] getIndex() {
		return index;
	}
	/**
	 * <p>
	 * 	This method can <strong>directly obtain the coordinate</strong> 
	 * 	points of the elements,corresponding to the mapping
	 * 	coordinates,optimize the previous <code>getIntex</code> method,and
	 * 	<i>solve the problem of the last index.</i> 
	 * </p>
	 * @return the indexs of the Element
	 */
	public int[] getIndexWithOutFrist() {
		int[] index_then = new int[index.length-1];
		for(int i =0;i<index_then.length;i++) {
			index_then[i] = index[i];
		}
		return index_then;
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
	 * @see cn.gulesberry.www.entity.DSElement#toString()
	 */
	@Override
	public String toString() {
		return element+","+Arrays.toString(index)+","+text;
	}
}