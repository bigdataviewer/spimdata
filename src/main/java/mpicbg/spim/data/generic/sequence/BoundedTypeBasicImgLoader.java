package mpicbg.spim.data.generic.sequence;

import mpicbg.spim.data.generic.sequence.BasicImgLoader;
import mpicbg.spim.data.generic.sequence.BasicSetupImgLoader;

public interface BoundedTypeBasicImgLoader< T > extends BasicImgLoader
{
	@Override
	public BasicSetupImgLoader< ? extends T > getSetupImgLoader( final int setupId );
}
