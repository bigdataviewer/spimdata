package mpicbg.spim.data.zarr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mpicbg.spim.data.generic.sequence.BasicImgLoader;
import org.scijava.Priority;
import org.scijava.annotations.Indexable;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Indexable
public @interface JsonImgLoaderIo
{
	/**
	 * Name for the format handled by the {@link BasicImgLoader}.
	 */
	String format();

	/**
	 * The concrete class of the {@link BasicImgLoader} that is loaded by the annotated class.
	 */
	Class< ? extends BasicImgLoader > type();

	double priority() default Priority.NORMAL;
}
