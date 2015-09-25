package mpicbg.spim.data.generic.sequence;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.realtransform.AffineTransform3D;

/**
 * A {@link BasicSetupImgLoader} providing multiple resolutions of each image. By
 * convention, resolution level <em>0</em> is the full resolution.
 *
 * @param <T>
 *            the pixel type of images provided.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface BasicMultiResolutionSetupImgLoader< T > extends BasicSetupImgLoader< T >
{
	/**
	 * Get the image at the specified timepoint and resolution level.
	 *
	 * <p>
	 * The returned image has pixel type {@code T} (an instance of {@code T} may
	 * be obtained by {@link #getImageType()}).
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image.
	 * @param level
	 *            resolution level for which to retrieve the image.
	 * @param hints
	 *            optional hints regarding how to load the image.
	 * @return image of type T.
	 */
	public RandomAccessibleInterval< T > getImage( final int timepointId, final int level, ImgLoaderHint... hints );

	/**
	 * Get the sub-sampling factors, indexed by resolution level and dimension.
	 * For example, a sub-sampling factor of 2 means the respective resolution
	 * level is scaled by 0.5 in the respective dimension.
	 *
	 * @return sub-sampling factors, indexed by resolution level and dimension.
	 */
	public double[][] getMipmapResolutions();

	/**
	 * Get the transformation from coordinates of the sub-sampled image of a a
	 * resolution level to coordinates of the full resolution image. The array
	 * of transforms is indexed by resolution level.
	 *
	 * @return array with one transformation for each mipmap level.
	 */
	public AffineTransform3D[] getMipmapTransforms();

	/**
	 * Get number of resolution levels.
	 *
	 * @return number of resolution levels.
	 */
	public int numMipmapLevels();
}
