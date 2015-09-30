package mpicbg.spim.data.generic.sequence;

public interface TypedBasicImgLoader< T > extends BoundedTypeBasicImgLoader< T >
{
	@Override
	public BasicSetupImgLoader< T > getSetupImgLoader( final int setupId );
}
