package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.sequence.BasicSetupImgLoader;
import mpicbg.spim.data.generic.sequence.ImgLoaderHint;
import net.imglib2.Dimensions;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.FloatType;

/**
 * A {@link BasicSetupImgLoader} that is able to also provide each image
 * converted to {@link FloatType}. Moreover, it provides voxel size and image
 * size for each image (usually without needing to completely read the image).
 *
 * @param <T>
 *            the pixel type of images provided.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface SetupImgLoader< T > extends BasicSetupImgLoader< T >
{
	/**
	 * Get image at the specified timepoint, converted to {@link FloatType}. If
	 * requested, the image is normalized to the range [0,1].
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image.
	 * @param normalize
	 *            whether the image should be normalized to [0,1].
	 * @param hints
	 *            optional hints regarding how to load the image.
	 * @return {@link FloatType} image
	 */
	public RandomAccessibleInterval< FloatType > getFloatImage( final int timepointId, boolean normalize, ImgLoaderHint... hints );

	/**
	 * Get the size of an image. If possible, load only the meta-data for the
	 * specified image. Note, that this should <em>not</em> get the meta-data
	 * from the {@link SequenceDescription} but pull it from the image file.
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image size.
	 * @return the image size, or null if it could not be determined.
	 */
	public Dimensions getImageSize( final int timepointId );

	/**
	 * Get the voxel size of an image. If possible, load only the meta-data for
	 * the specified image. Note, that this should <em>not</em> get the
	 * meta-data from the {@link SequenceDescription} but pull it from the image
	 * file.
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the voxel size.
	 * @return the voxel size, or null if it could not be determined.
	 */
	public VoxelDimensions getVoxelSize( final int timepointId );
}
