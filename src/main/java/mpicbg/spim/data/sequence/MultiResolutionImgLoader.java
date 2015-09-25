package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.sequence.BasicMultiResolutionImgLoader;

/**
 * An {@link ImgLoader} providing multiple resolutions of each image. By
 * convention, resolution level <em>0</em> is the full resolution.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface MultiResolutionImgLoader extends BasicMultiResolutionImgLoader, ImgLoader
{
	/**
	 * Get the {@link MultiResolutionSetupImgLoader} for the specified view
	 * setup.
	 *
	 * @param setupId
	 *            view setup for which to get the
	 *            {@link MultiResolutionSetupImgLoader}.
	 * @return {@link MultiResolutionSetupImgLoader} for the specified view
	 *         setup.
	 */
	@Override
	public MultiResolutionSetupImgLoader< ? > getSetupImgLoader( final int setupId );
}
