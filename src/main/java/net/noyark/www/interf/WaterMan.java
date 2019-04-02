package net.noyark.www.interf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
/**
 *The tree is managed by the table structure.
 *and the structure , and the structure is
 *clearly seen.
 * 
 * @author magiclu550
 * @since EQuery 026
 * @since JDK1.8
 * @see WaterMan
 *
 */
public interface WaterMan extends Serializable{
	/**
	 * the method further differentiates the branches 
	 * of the tree and stores them in a table structure 
	 * map.
	 * @param tree the Tree object
	 * @return the map of table
	 */
	Map<Table<Integer, Element>, Table<Integer, String>> buildBranchLengthSystem(Tree tree);
	/**
	 * get the map table
	 */
	Map<Table<Integer, Element>, Table<Integer, String>>getMapping();
	/**
	 * get the element object table
	 */
	List<Table<Integer, Element>> getElementBranchList();
	/**
	 * get the path object table
	 */
	List<Table<Integer, String>> getInformationBranchList();
	/**
	 * update the table
	 */
	void update(Tree tree);
}
