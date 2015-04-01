package mpicbg.spim.data.generic.sequence;

/**
 *
 *	TODO
 * @param <T>
 *           the pixel type
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public interface BasicImgLoader
{
	public BasicSetupImgLoader< ? > getSetupImgLoader( final int setupId );
}
