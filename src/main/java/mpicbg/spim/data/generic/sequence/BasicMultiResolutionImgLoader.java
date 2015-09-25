package mpicbg.spim.data.generic.sequence;

/**
 * A {@link BasicImgLoader} providing multiple resolutions of each image. By
 * convention, resolution level <em>0</em> is the full resolution.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface BasicMultiResolutionImgLoader extends BasicImgLoader
{
	/**
	 * Get the {@link BasicMultiResolutionSetupImgLoader} for the specified view
	 * setup.
	 *
	 * @param setupId
	 *            view setup for which to get the
	 *            {@link BasicMultiResolutionSetupImgLoader}.
	 * @return {@link BasicMultiResolutionSetupImgLoader} for the specified view
	 *         setup.
	 */
	@Override
	public BasicMultiResolutionSetupImgLoader< ? > getSetupImgLoader( final int setupId );
}
