package mpicbg.spim.data.legacy;

import mpicbg.spim.data.sequence.SequenceDescription;
import mpicbg.spim.data.sequence.ViewId;
import mpicbg.spim.data.sequence.VoxelDimensions;
import net.imglib2.Dimensions;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.FloatType;

//@Deprecated
public interface LegacyImgLoader< T > extends LegacyBasicImgLoader< T >
{
	/**
	 * Get {@link FloatType} image. If requested, the image is normalized to the
	 * range [0,1].
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the image.
	 * @param normalize
	 *            whether the image should be normalized to [0,1].
	 * @return {@link FloatType} image
	 */
	public RandomAccessibleInterval< FloatType > getFloatImage( ViewId view, boolean normalize );

	/**
	 * Get the size of an image. If possible, load only the meta-data for the
	 * specified view. Note, that this should <em>not</em> get the meta-data from
	 * the {@link SequenceDescription} but pull it from the image file.
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the image size.
	 * @return the image size, or null if it could not be determined.
	 */
	public Dimensions getImageSize( ViewId view );

	/**
	 * Get the voxel size of an image. If possible, load only the meta-data for
	 * the specified view. Note, that this should <em>not</em> get the meta-data
	 * from the {@link SequenceDescription} but pull it from the image file.
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the voxel size.
	 * @return the voxel size, or null if it could not be determined.
	 */
	public VoxelDimensions getVoxelSize( ViewId view );
}
