package cn.gulesberry.www.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;

import cn.gulesberry.www.helper.XMLHelper;
import cn.gulesberry.www.io.XMLDocument;
import cn.gulesberry.www.utils.table.NodeTable;
import net.noyark.www.interf.Branch;
import net.noyark.www.interf.Table;
import net.noyark.www.interf.Tree;
import net.noyark.www.interf.WaterMan;

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
public class TreeWaterMan implements WaterMan{
	private static final long serialVersionUID = 1L;
	/**
	 * The node table
	 */
	List<Table<Integer, Element>> elementBranchList;
	/**
	 * The node table's mapping path table
	 */
	List<Table<Integer, String>> informationBranchList;
	/**
	 * the map
	 */
	Map<Table<Integer, Element>, Table<Integer, String>> branchLengthMapper;
	/**
	 * The constructor can build change the tree to
	 * table
	 * @param tree the Tree Object
	 */
	public TreeWaterMan(Tree tree){
		branchLengthMapper = buildBranchLengthSystem(tree);
	}
	/**
	 * the method further differentiates the branches 
	 * of the tree and stores them in a table structure 
	 * map.
	 * @param tree the Tree object
	 * @return the map of table
	 */
	public Map<Table<Integer, Element>, Table<Integer, String>> buildBranchLengthSystem(Tree tree){
		Set<Integer> pathLengthesSet=new HashSet<>();//获取长度
		List<Set<Integer>> groups = new ArrayList<>();//每个分支分类
		for(int i = 0;i<tree.getBranchesNumber();i++) {
			Branch e1= tree.getBranch(i);
			for(int j = 0;j<e1.getElementNumber();j++) {
				String path = e1.getElementPath(j);
				pathLengthesSet.add(path.split(XMLDocument.POINT).length);
			}
			groups.add(pathLengthesSet);
			pathLengthesSet = new HashSet<>();
		}
		Table<Integer, String> allMap = new NodeTable<>();
		List<Table<Integer, String>> list = new ArrayList<>();
		Table<Integer, Element> allMapWithElement = new NodeTable<>();
		List<Table<Integer, Element>> listElement = new ArrayList<>();
		List<Map<String, Integer>> allList = new ArrayList<>();
		Map<String,Integer> all = new HashMap<>();
		Map<Table<Integer,Element>, Table<Integer, String>> mapping = new HashMap<>();
		for(int index=0;index<groups.size();index++) {
			List<Element> element = tree.getBranch(index).getElements();
			for(Element ele:element) {
				int length = XMLHelper.getElementPath(ele).split(XMLDocument.POINT).length;
				int size = (all.get(length+"")==null?0:all.get(length+""))+ele.elements().size();
				all.put(length+"",size);
				allMap.put(length,XMLHelper.getElementPath(ele)+":"+ele.elements().size()+":"+all.get(length+""));	
				allMapWithElement.put(length, ele);
			}
			allList.add(all);
			all = new HashMap<>();
			list.add(allMap);
			listElement.add(allMapWithElement);
			mapping.put(allMapWithElement, allMap);
			allMap = new NodeTable<>();
			allMapWithElement = new NodeTable<>();
		}
		elementBranchList = listElement;
		informationBranchList = list;
		return mapping;
	}
	/**
	 * get the map table
	 */
	public Map<Table<Integer, Element>, Table<Integer, String>>getMapping(){
		return branchLengthMapper;
	}
	/**
	 * get the element object table
	 */
	public List<Table<Integer, Element>> getElementBranchList(){
		return elementBranchList;
	}
	/**
	 * get the path object table
	 */
	public List<Table<Integer, String>> getInformationBranchList(){
		return informationBranchList;
	}
	/**
	 * update the table
	 */
	public void update(Tree tree) {
		branchLengthMapper = buildBranchLengthSystem(tree);
	}
	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return branchLengthMapper.toString();
	}
}
