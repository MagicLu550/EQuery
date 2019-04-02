package cn.gulesberry.www.utils.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;

import cn.gulesberry.www.helper.XMLHelper;
import net.noyark.www.interf.Table;

import java.util.Set;

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


public class NodeTable<S,E> implements Table<S, E>{
	private static final long serialVersionUID = 865296186704282631L;
	/**
	 * this is the main of thetable
	 * */
	Map<S,List<E>> nt;
	public NodeTable() {
			nt = new HashMap<>();
	}
	/**
	* Put the table column into, it represents a row 
	* table, S is the primary key, E is the following 
	* element.
	* @param key
	* @param elements
	*/
	public void putAll(S key,@SuppressWarnings("unchecked") E...elements) {
		List<E> list = nt.get(key);
		if(list!=null) {
			for(E e:elements)
			list.add(e);
		}else {
			List<E> newList = new ArrayList<>();
			for(E e:elements) {
				newList.add(e);
			}
			synchronized (nt) {
				nt.put(key,newList);
			}
		}
	}
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
	public E get(S key,int col) {
		return nt.get(key).get(col);
	}
	/**
	 * It can get all of the values of this
	 * key 
	 * @param the key
	 * @param a row full of values of this key
	 */
	public List<E> getAll(S key){
		return nt.get(key);
	}
	/**
	 * Can get all the elements of the line where 
	 * this key is located.
	 * @param path the key
	 */
	@Override
	public int getPathElementLength(S path) {
		return nt.get(path).size();
	}
	/**
	 * Can get the number of rows in this table
	 * @return the number of rows
	 */
	@Override
	public int getPathesLength() {
		return nt.size();
	}
	/**
	 * This method can add elements to the line. 
	 * If there is no such line before, it will 
	 * be created automatically and added to the 
	 * element. If it already exists, it will be 
	 * added after the element.
	 * @param path the key
	 * @param the value of the key 
	 */
	public void put(S path,E e) {
		List<E> list = nt.get(path);
		if(list!=null) {
			list.add(e);
		}else {
			List<E> newList = new ArrayList<>();
			newList.add(e);
			nt.put(path, newList);
		}
	}
	/**
	* Get the number of storage of the entire table, that is
	* , the number of intersecting grids
	* @return Number of intersecting grids
	*/
	public int getSize() {
		int number = 0;
		Set<Entry<S, List<E>>> set = nt.entrySet();
		for(Entry<S, List<E>> e:set) {
			List<E> all = e.getValue();
			for(int i =0;i<all.size();i++) {
				number++;
			}
		}
		return number;
	}
	/**
	 * You can delete a column of data and
	 * delete only the existing data.If a column is
	 * incomplete,the existing part is deleted by
	 * default
	 * @param index the col index
	 */
	public void deleteCol(int index) {
		Set<Entry<S, List<E>>> set = nt.entrySet();
		for(Entry<S, List<E>> e:set) {
			List<E> all = e.getValue();
			if(index<all.size()) {
				all.remove(index);
			}
		}
	}
	/**
	 * You can delete the element of the specified
	 * position of the specified key
	 * @param path the key
	 * @param index the index of the line of the key
	 */
	public void deleteOne(String path,int index) {
		nt.get(path).remove(index);
	}
	/**
	 * it can delete a row of elements of the
	 * specified key
	 * @param path the key
	 */
	public void deletePath(String path) {
		nt.remove(path);
	}
	/**
	 * get all of the keys
	 * @return the key list
	 */
	public List<S> getKeys(){
		List<S> keys = new ArrayList<>();
		Set<Entry<S, List<E>>> set = nt.entrySet();
		for(Entry<S, List<E>> e:set) {
			keys.add(e.getKey());
		}
		return keys;
	}
	/**
	 * Returns the coordinate position of the existing
	 * element in it,the first time row coordinate,the 
	 * second is the column coordinate,if it does not exist
	 * ,returns an array with no value
	 * @param e the value
	 * @return the position
	 */
	public int[] getIndexs(E e){
		int i = 0;
		int j = 0;
		int[] indexs = new int[2];
		Set<Entry<S, List<E>>> set = nt.entrySet();
		for(Entry<S, List<E>> e1:set) {
			List<E> all = e1.getValue();
			for(E e2:all){
				if(e.equals(e2)){
					indexs[0] = i;
					indexs[1] = j;
					return indexs;
				}
				j++;
			}
			i++;
			j=0;
		}
		return indexs;
	}
	/**
	 * It gets a visual table
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		Set<Entry<S, List<E>>> set = nt.entrySet();
		StringBuilder builder = new StringBuilder();
		builder.append("key\tvalues\n");
		for(Entry<S, List<E>> e:set) {
			S s = e.getKey();
			List<E> all = e.getValue();
			builder.append(s+"|\t");
			for(E obj:all) {
				if(!(obj instanceof Element)) {
					
					builder.append(obj+"\t");
				}else {
					builder.append(XMLHelper.getElementPath((Element)obj)+"\t");
				}
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
}
