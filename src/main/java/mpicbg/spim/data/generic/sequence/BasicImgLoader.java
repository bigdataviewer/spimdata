package mpicbg.spim.data.generic.sequence;


/**
 * Provides images for a {@link AbstractSequenceDescription sequence}. By
 * {@link #getSetupImgLoader(int)} a {@link BasicSetupImgLoader} for each setup
 * of the sequence can be obtatined. The {@link BasicSetupImgLoader} then
 * provides images for each timepoint.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface BasicImgLoader
{
	/**
	 * Get the {@link BasicSetupImgLoader} for the specified view setup.
	 *
	 * @param setupId
	 *            view setup for which to get the {@link BasicSetupImgLoader}.
	 * @return {@link BasicSetupImgLoader} for the specified view setup.
	 */
	public BasicSetupImgLoader< ? > getSetupImgLoader( final int setupId );
}
