package net.noyark.www.annotations;

import net.noyark.www.list.ElementSubIndex;
import net.noyark.www.list.ParentElementPath;

/**
 * <p>
 * Here to illustrate the variable parameter limits
 * of some methods ,that is,the difference between the 
 * point path segmentation of the element object and the 
 * number of coordinates.
 * </p>
 * <p>
 * 	Such as:
 * 		<p>
 * 			The point path of a.b.c.d is divided into 4,if the
 * 			difference is 1,then index has 3 values,the difference
 * 			is 0,then four values,etc.
 * 		</p>
 * </p>
 * and the isParent 
 * Indicates whether the element path is the parent
 * path standard ,that is,to find a.b.c,If the parent
 * path is specified,then the path is filled in a.b
 * @author magiclu550
 * @since EQuery 024
 * @see net.noyark.www.list.ElementSubIndex
 * @see net.noyark.www.list.ParentElementPath 
 */
public @interface IndexMappingNumberDigital {
	/**
	 * the digital
	 */
	ElementSubIndex digital();
	/**
	 * whether is the partent element path
	 */
	ParentElementPath isParent() default ParentElementPath.FALSE;
}
