package mpicbg.spim.data.generic.sequence;

//TODO: remove until needed
public interface TypedBasicImgLoader< T > extends BoundedTypeBasicImgLoader< T >
{
	@Override
	public BasicSetupImgLoader< T > getSetupImgLoader( final int setupId );
}
