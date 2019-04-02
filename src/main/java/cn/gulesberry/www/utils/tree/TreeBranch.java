package cn.gulesberry.www.utils.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.dom4j.Element;

import cn.gulesberry.www.helper.XMLHelper;
import net.noyark.www.interf.Branch;

/**
 * This is a branch structure of a big tree.
 * A large tree has many branch objects.You 
 * can get nodes quickly and easily through branch
 * objects
 * @author magiclu550
 * @since EQuery 025
 * @since JDK1.8
 * @see Branch
 * @see Tree
 *
 */

public class TreeBranch implements Branch{
	private static final long serialVersionUID = 7069077482399277113L;
	/**
	 * This is the branch native data 
	 */
	private Map<Integer,Element> branch;
	/**
	 *This constructor can build various branches 
	 *through large trees;
	 * @param tree the tree object
	 * @param index the branch id
	 */
	public TreeBranch(ElementTree tree,int index) {
		branch = tree.getTree().get(index);
	}
	/**
	 * Get the specified node of this container
	 * @param index the node index
	 * @return the element object
	 */
	public Element getElement(int index) {
		return branch.get(index);
	}
	/**
	 * Get the specified node of this container,here
	 * return its path information
	 * @param index the node index
	 * @return the element point path
	 */
	public String getElementPath(int index) {
		return XMLHelper.getElementPath(getElement(index));
	}
	/**
	 * get the node number of this branch
	 * @return the node number
	 */
	public int getElementNumber() {
		return branch.size();
	}
	/**
	 * get all of the elements of this branch
	 * @return the element object set
	 */
	public List<Element> getElements() {
		Set<Entry<Integer,Element>> s = branch.entrySet();
		List<Element> elements = new ArrayList<Element>();
		for(Entry<Integer,Element> es:s) {
			Element e = es.getValue();
			elements.add(e);
		}
		return elements;
	}
	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "Branch [branch=" + branch + "]";
	}
}
