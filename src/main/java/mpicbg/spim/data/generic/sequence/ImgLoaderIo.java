package mpicbg.spim.data.generic.sequence;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.scijava.Priority;
import org.scijava.annotations.Indexable;

/**
 * Annotation for a class that creates a {@link BasicImgLoader} for a specific
 * format (the format is specified in the XML "ImageLoader" element).
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@Indexable
public @interface ImgLoaderIo
{
	/**
	 * Name for the format handled by the {@link BasicImgLoader}.
	 */
	String format();

	/**
	 * The concrete class of the {@link BasicImgLoader} that is loaded by the annotated class.
	 */
	Class< ? extends BasicImgLoader< ? > > type();

	double priority() default Priority.NORMAL_PRIORITY;
}
