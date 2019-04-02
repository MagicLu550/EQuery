package net.noyark.www.interf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * This table data structure, through the 
 * implementation of hash tables and common 
 * collections, can freely add elements and 
 * reduce elements to the table, where path 
 * is the header value of the table, followed 
 * by several elements, through the coordinates 
 * and path can be positioned a specific Element, 
 * the toString method can return a visualization 
 * of the table structure
 * @author magiclu550
 *
 * @param <S> the key type
 * @param <E> the values type
 * 
 * @since EQuery 023
 * @since JDK1.8
 * @see Map
 * @see List
 */
public interface Table<S,E> extends Serializable{
	/**
	* Put the table column into, it represents a row 
	* table, S is the primary key, E is the following 
	* element.
	* @param key
	* @param elements
	*/
	@SuppressWarnings("unchecked")
	public void putAll(S path,E...elements);
	/**
	 * This method can add elements to the line. 
	 * If there is no such line before, it will 
	 * be created automatically and added to the 
	 * element. If it already exists, it will be 
	 * added after the element.
	 * @param path the key
	 * @param the value of the key 
	 */
	public void put(S path,E element);
	/**
	* col refers to the first few columns, will be in the following 
	* table structure, of course, the column of the table structure 
	* can not be uniform, you can look up the value value by referring 
	* to the visualization table.
	* <table border='1'>
	* <tr style='background-color:green'>
	* <td>&nbsp;</td>
	* <td>&nbsp;</td>
	* <td>col1</td>
	* <td>col2</td>
	* <td>col3</td>
	* <td>col4</td>
	* </tr>
	* <tr>
	* <td>row1</td>
	* <td>path</td>
	* <td>E1</td>
	* <td>E2</td>
	* <td>E3</td>
	* <td>E4</td>
	* </tr>
	* <tr>
	* <td>row2</td>
	* <td>path</td>
	* <td>E1</td>
	* <td>E2</td>
	* <td>E3</td>
	* <td>E4</td>
	* </tr>
	* <tr>
	* <td>row2</td>
	* <td>path</td>
	* <td>E1</td>
	* <td>E2</td>
	* <td>E3</td>
	* <td>E4</td>
	* </tr>
	* </table>
	* @param key the table's 
	* @param col the values's index of this key row
	* @return
	*/
	E get(S path,int col);
	int getPathElementLength(S path);
	int getPathesLength();
	List<E> getAll(S path);
	/**
	* Get the number of storage of the entire table, that is
	* , the number of intersecting grids
	* @return Number of intersecting grids
	*/
	int getSize();
	/**
	 * You can delete a column of data and
	 * delete only the existing data.If a column is
	 * incomplete,the existing part is deleted by
	 * default
	 * @param index the col index
	 */
	void deleteCol(int index);
	/**
	 * You can delete the element of the specified
	 * position of the specified key
	 * @param path the key
	 * @param index the index of the line of the key
	 */
	void deleteOne(String path,int index);
	/**
	 * it can delete a row of elements of the
	 * specified key
	 * @param path the key
	 */
	public void deletePath(String path);
	
	/**
	 * get all of the keys
	 * @return the key list
	 */
	List<S> getKeys();
	/**
	 * Returns the coordinate position of the existing
	 * element in it,the first time row coordinate,the 
	 * second is the column coordinate,if it does not exist
	 * ,returns an array with no value
	 * @param e the value
	 * @return the position
	 */
	int[] getIndexs(E e);
}
