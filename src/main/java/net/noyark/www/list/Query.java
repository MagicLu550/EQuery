package net.noyark.www.list;
/**
 * the $ method in the XMLDomFile
 * Used to specify the type of 
 * selection for this selector
 * @author magiclu550
 * @since EQuery 025
 * @since JDK1.8
 * @see net.noyark.www.interf.XMLDomFile#$(Query, String)
 * @see net.noyark.www.interf.XMLDomFile#$(String, Query, int...) 
 */
public enum Query {
	/**
	 * Get all the elements
	 * @see net.noyark.www.interf.XMLDomFile#getAllElement() 
	 */
	ALL,
	/**
	 * Get the brother on this element
	 * @see net.noyark.www.interf.XMLDomFile#getBrotherElementOn(String, int...)
	 */
	BROTHER_ON,
	/**
	 * Get the brothers on this element
	 * @see net.noyark.www.interf.XMLDomFile#getBrotherElementsOn(String, int...)
	 */
	BROTHERS_ON,
	/**
	 * Get the brother under this element
	 * @see net.noyark.www.interf.XMLDomFile#getBrotherElementUnder(String, int...)
	 */
	BROTHER_UNDER,
	/**
	 * Get the brothers under this element
	 * @see net.noyark.www.interf.XMLDomFile#getBrotherElementsUnder(String, int...)
	 */
	BROTHERS_UNDER,
	/**
	 * Get the friends
	 * @see net.noyark.www.interf.XMLDomFile#getChilds(String, int...)
	 */
	FRIENDS,
	/**
	 * get the one element
	 * @see net.noyark.www.interf.XMLDomFile#getElement(String, int...)
	 */
	ELEMENT,
	/**
	 * get the elements by this element path
	 * @see net.noyark.www.interf.XMLDomFile#getElements(String)
	 */
	ELEMENTS,
	/**
	 * get the elements by id
	 * @see net.noyark.www.interf.XMLDomFile#getElementsByID(String)
	 */
	ELEMENTS_ID,
	/**
	 * get the elements by name
	 * @see net.noyark.www.interf.XMLDomFile#getElementsByName(String)
	 */
	ELEMENTS_NAME,
	/**
	 * get the elements by text
	 * @see net.noyark.www.interf.XMLDomFile#getElementsByText(String)
	 */
	ELEMENTS_TEXT,
	/**
	 * get the element parent
	 * @see net.noyark.www.interf.XMLDomFile#getParent(String, int...)
	 */
	PARENT,
}
