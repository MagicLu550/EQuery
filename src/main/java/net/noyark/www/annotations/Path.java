package net.noyark.www.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * The path used to specify the element.In
 * fact,the first value is element path.When
 * there is only one value,it can be omitted.
 * When it comes to expressions,the coordinates will be
 * used .For details,see the related methods.See ReadingXML
 * expression
 * @author magiclu550
 * @since EQuery 025
 * @since JDK 1.8
 * @see cn.gulesberry.www.reflect.ReadingXML
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Path {
	/**
	 * it is like the element path
	 * use the point path
	 * such as a.b.d etc.
	 */
	String path() default "";
	/**
	 * When you want to use the indexs,
	 * you can use it,like these
	 * addchild:
	 * addchilds:
	 * addelements:
	 * these equery expressions will use
	 * @see net.noyark.www.interf.XMLDomFile#addChild(String, String, int...)
	 * @see net.noyark.www.interf.XMLDomFile#addElementsIfExistsParent(String, java.util.List, int...)
	 * @see net.noyark.www.interf.XMLDomFile#addElements(String, String...)
	 * 
	 */
	int[] indexs() default {};
	/**
	 * if you only have the path,can use it
	 * addnew: can use it
	 * @see net.noyark.www.interf.XMLDomFile#addElement(String, String)
	 */
	String value() default "";
}
