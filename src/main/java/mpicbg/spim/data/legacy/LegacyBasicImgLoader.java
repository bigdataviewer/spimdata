package mpicbg.spim.data.legacy;

import mpicbg.spim.data.generic.sequence.BasicImgLoader;
import mpicbg.spim.data.sequence.ViewId;
import net.imglib2.RandomAccessibleInterval;

//@Deprecated
public interface LegacyBasicImgLoader< T >
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
