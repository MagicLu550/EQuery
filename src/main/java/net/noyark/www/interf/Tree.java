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
package net.noyark.www.interf;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;


public interface Tree extends Serializable{
	
	/**
	 * you can get the branch of this tree by the index
	 * @param index the pos of the branches set
	 * @return the branch object
	 */
	public Branch getBranch(Integer index);
	
	/**
	 * It can get the native data
	 * @return the data list
	 */
	List<Map<Integer, Element>> getTree();
	/**
	 * It can get the number of the branches
	 * @return the branch number
	 */
	public int getBranchesNumber();
	/**
	 * It can update the tree(the element numbers and object)
	 * @param xdf the XMLDomFile object
	 */
	public void updateTree(XMLDomFile xdf);
}
