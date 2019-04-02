package net.noyark.www.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * <p>
 * After specifies a coordinate,the coordinate
 * refers to the first number of elements without
 * EQuery excpression or the path annoation specified by
 * the addElement expression.Add a path annotation with
 * only the element name below this annotation.This path
 * will be connected by default.On the specified path.
 * </p>
 * <p>
 * 	Such as:
 * 	<p>
 * 		@Path('a.b.c') int a = 1;
 * 	</p>
 * <p>
 * 		@Path('b')
 * 		@After(0) 
 * int b = 0;
 * </p>
 * Then the second b is actually a.b.c.b
 * </p>
 * @author magiclu550
 * @see cn.gulesberry.www.reflect.ReadingXML
 *
 */

@Retention(RUNTIME)
@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface After {
	/**Just the index*/
	int value();
}
