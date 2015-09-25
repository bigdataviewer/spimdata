package mpicbg.spim.data.generic.sequence;

// TODO: remove until needed?
public interface BoundedTypeBasicImgLoader< T > extends BasicImgLoader
{
	@Override
	public BasicSetupImgLoader< ? extends T > getSetupImgLoader( final int setupId );
}
