package mpicbg.spim.data.zarr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.scijava.annotations.Indexable;

import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.sequence.ViewSetup;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Indexable
public @interface JsonViewSetupAttributeIo
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
