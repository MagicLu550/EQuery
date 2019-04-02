package net.noyark.www.list;
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
 * @author magiclu550
 * @since EQuery 024
 *
 */
public enum ElementSubIndex {
	/**
	 * the difference is 1
	 */
	ONE,
	/**
	 * the difference is -1
	 */
	NEG_ONE,
	/**
	 * the difference is 0
	 */
	ZERO,
	/**
	 * Special,amorphous
	 */
	NON_STATIC,
}
