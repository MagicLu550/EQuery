package net.noyark.www.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * @see org.junit.Before
 * @author magiclu550
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface RuntimeBefore {
}
