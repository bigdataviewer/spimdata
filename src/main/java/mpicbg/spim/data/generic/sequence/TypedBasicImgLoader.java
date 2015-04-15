package mpicbg.spim.data.generic.sequence;

import mpicbg.spim.data.generic.sequence.BasicSetupImgLoader;

public interface TypedBasicImgLoader< T > extends BoundedTypeBasicImgLoader< T >
{
	@Override
	public BasicSetupImgLoader< T > getSetupImgLoader( final int setupId );
}
