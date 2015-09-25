package mpicbg.spim.data.generic.sequence;

import net.imglib2.RandomAccessibleInterval;

/**
 * Provides images for one {@link BasicViewSetup setup} of a
 * {@link AbstractSequenceDescription sequence}.
 *
 * @param <T>
 *            the pixel type of images provided.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface BasicSetupImgLoader< T >
{
	/**
	 * Get the image at the specified timepoint.
	 *
	 * <p>
	 * The returned image has pixel type {@code T} (an instance of {@code T} may
	 * be obtained by {@link #getImageType()}).
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image.
	 * @param hints
	 *            optional hints regarding how to load the image.
	 * @return image of type T.
	 */
	public RandomAccessibleInterval< T > getImage( final int timepointId, ImgLoaderHint... hints );

	public T getImageType();
}
