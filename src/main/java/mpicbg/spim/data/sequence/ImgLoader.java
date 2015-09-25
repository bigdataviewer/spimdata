package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.sequence.BasicImgLoader;
import net.imglib2.type.numeric.real.FloatType;

/**
 * A {@link BasicImgLoader} that is able to also provide each image
 * converted to {@link FloatType}. Moreover, it provides voxel size and image
 * size for each image (usually without needing to completely read the image).
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface ImgLoader extends BasicImgLoader
{
	/**
	 * Get the {@link SetupImgLoader} for the specified view setup.
	 *
	 * @param setupId
	 *            view setup for which to get the {@link SetupImgLoader}.
	 * @return {@link SetupImgLoader} for the specified view setup.
	 */
	@Override
	public SetupImgLoader< ? > getSetupImgLoader( final int setupId );
}
