package net.noyark.www.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class added with this annotation can be instantiated
 * directly and create an xml file,provided that the ReadingXML class
 * is inherited and the package address is written in equery xml
 * @author magiclu550
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
//TODO add the package in the startXML.xml
public @interface XMLFile {
	/**
	 * the xml file name
	 */
	String fileName();
	/**
	 * The root element name
	 */
	String root();
	/**
	 * create the default object
	 * @see net.noyark.www.interf.DefaultFile
	 */
	boolean isDefault() default false;
	/**
	 * The xml namespace in the root element
	 */
	String xmlns() default "";
}
