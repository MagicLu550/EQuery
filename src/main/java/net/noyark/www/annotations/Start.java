package net.noyark.www.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * Flag annotation,used to indicate related classes
 * that are truned on
 * @author magiclu550
 * @since EQuery 027
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Start {
	/**
	 * 
	 * The start class
	 */
	Class<?> value();
}
