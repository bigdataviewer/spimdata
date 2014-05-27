package mpicbg.spim.data.generic.sequence;

import mpicbg.spim.data.sequence.ViewId;
import net.imglib2.RandomAccessibleInterval;

/**
 *
 * @param <T>
 *           the pixel type
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public interface BasicImgLoader< T >
{
	/**
	 * Get the un-normalized image of the default {@link #getImageType() pixel
	 * type} of this {@link BasicImgLoader}.
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the image.
	 * @return image of type T.
	 */
	public RandomAccessibleInterval< T > getImage( ViewId view );

	public T getImageType();

}
