package mpicbg.spim.data.generic.sequence;

import net.imglib2.RandomAccessibleInterval;

public interface BasicSetupImgLoader< T >
{
	/**
	 * Get the un-normalized image of the default {@link #getImageType() pixel
	 * type} of this {@link BasicSetupImgLoader}.
	 *
	 * @param timepointId
	 *            timepoint which to retrieve the image.
	 * @return image of type T.
	 */
	public RandomAccessibleInterval< T > getImage( final int timepointId );

	public T getImageType();
}
