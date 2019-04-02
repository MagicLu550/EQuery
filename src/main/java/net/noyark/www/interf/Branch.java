package net.noyark.www.interf;

import java.io.Serializable;
import java.util.List;

import org.dom4j.Element;



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

public interface Branch extends Serializable{
	/**
	 * Get the specified node of this container
	 * @param index the node index
	 * @return the element object
	 */
	public Element getElement(int index);
	/**
	 * Get the specified node of this container,here
	 * return its path information
	 * @param index the node index
	 * @return the element point path
	 */
	public String getElementPath(int index);
	/**
	 * get the node number of this branch
	 * @return the node number
	 */
	public int getElementNumber();
	/**
	 * get all of the elements of this branch
	 * @return the element object set
	 */
	List<Element> getElements();
}
