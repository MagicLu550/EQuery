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
package cn.gulesberry.www.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;


import cn.gulesberry.www.helper.XMLHelper;
import cn.gulesberry.www.io.XMLDocument;
import net.noyark.www.interf.Branch;
import net.noyark.www.interf.Tree;
import net.noyark.www.interf.XMLDomFile;

/**
 * 	This is the a tree with no root nodes.
 * The path tree structure is formed according
 * to the xml structure ,and a visual tre structure 
 * can be displayed for managing each node and each
 * branch of the xml file
 * @author magiclu550
 * @since EQuery 025
 * @since JDK1.8
 * @see Tree
 *
 */
public class ElementTree implements Tree{
	private static final long serialVersionUID = 1L;
	/**
	 * This is the Dividing structrue
	 */
	private List<Map<Integer,Element>> tree;
	/**
	 * the branches of this tree
	 */
	private List<Branch> branches;
	/**
	 * This constructor can build a tree structrue
	 * and organize the structure according to the
	 * <code>XMLDomFile</code> object
	 * @param xdf the XMLDomFile object
	 */
	public ElementTree(XMLDomFile xdf) {
		tree = buildTree(xdf);
		branches = buildBranches();
	}
	/**
	 * This method can divide branches according to 
	 * node paths and build big trees.
	 * @return the Branches of this tree
	 */
	private List<Branch> buildBranches() {
		List<Branch> branches = new ArrayList<>();
		for(int i = 0;i<tree.size();i++) {
			Branch branch = new TreeBranch(this, i);
			branches.add(branch);
		}
		return branches;
	}
	/**
	 * you can get the branch of this tree by the index
	 * @param index the pos of the branches set
	 * @return the branch object
	 */
	public Branch getBranch(Integer index){
		return branches.get(index);
	}
	/**
	 * This method can build large trees internally 
	 * ,through various properties of the nodes
	 * @param xdf the XMLDomFile object
	 * @return the tree
	 */
	private List<Map<Integer,Element>> buildTree(XMLDomFile xdf) {
		List<Element> es = xdf.getAllElements();
		List<Map<Integer,Element>> groups=new ArrayList<>();
		//划分树
		Map<Integer,Element> group = null;
		int index = 0;
		for(int i = 0;i<es.size();i++) {
			if((i==es.size()-1&&XMLHelper.getElementPath(es.get(i)).split(XMLDocument.POINT).length==1)||i==0||(XMLHelper.getElementPath(es.get(i)).split(XMLDocument.POINT).length==1)) {
					group = new HashMap<>();
					groups.add(group);
					index = 0;
			}
			group.put(index,es.get(i));
			index++;
		}
		return groups;
	}
	/**
	 * It can get the native data
	 * @return the data list
	 */
	public List<Map<Integer, Element>> getTree() {
		return tree;
	}
	/**
	 * It can get the number of the branches
	 * @return the branch number
	 */
	public int getBranchesNumber() {
		return branches.size();
	}
	/**
	 * It can update the tree(the element numbers and object)
	 * @param xdf the XMLDomFile object
	 */
	public void updateTree(XMLDomFile xdf) {
		tree = buildTree(xdf);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for(Branch branch:branches) {
			builder.append("|-"+i+"--"+"\t");
			List<Element> elements = branch.getElements();
			for(Element e:elements) {
				builder.append(XMLHelper.getElementPath(e)+"--"+"\t");
			}
			builder.append("\n");
			i++;
		}
		return builder.toString();
	}
	
}
