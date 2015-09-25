package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.sequence.BasicMultiResolutionSetupImgLoader;
import mpicbg.spim.data.generic.sequence.ImgLoaderHint;
import net.imglib2.Dimensions;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.FloatType;

/**
 * A {@link SetupImgLoader} providing multiple resolutions of each image. By
 * convention, resolution level <em>0</em> is the full resolution.
 *
 * @param <T>
 *            the pixel type of images provided.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface MultiResolutionSetupImgLoader< T > extends BasicMultiResolutionSetupImgLoader< T >, SetupImgLoader< T >
{
	/**
	 * Get image at the specified timepoint and resolution level, converted to
	 * {@link FloatType}. If requested, the image is normalized to the range
	 * [0,1].
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image.
	 * @param level
	 *            resolution level for which to retrieve the image.
	 * @param normalize
	 *            whether the image should be normalized to [0,1].
	 * @param hints
	 *            optional hints regarding how to load the image.
	 * @return {@link FloatType} image
	 */
	public RandomAccessibleInterval< FloatType > getFloatImage( final int timepointId, final int level, boolean normalize, ImgLoaderHint... hints );

	/**
	 * Get the size of an image. If possible, load only the meta-data for the
	 * specified image. Note, that this should <em>not</em> get the meta-data
	 * from the {@link SequenceDescription} but pull it from the image file.
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image size.
	 * @param level
	 *            resolution level for which to retrieve the image size.
	 * @return the image size, or null if it could not be determined.
	 */
	public Dimensions getImageSize( final int timepointId, final int level );
}
