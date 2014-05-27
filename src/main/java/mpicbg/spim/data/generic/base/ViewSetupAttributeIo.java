package mpicbg.spim.data.generic.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import mpicbg.spim.data.sequence.ViewSetup;

import org.scijava.annotations.Indexable;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Indexable
public @interface ViewSetupAttributeIo
{
	/**
	 * Name for the handled {@link ViewSetup} attribute.
	 */
	String name();

	/**
	 * The class of the attribute that is loaded by the annotated class.
	 */
	Class< ? extends Entity > type();
}
