package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.sequence.BasicImgLoader;

public interface ImgLoader extends BasicImgLoader
{
	@Override
	public SetupImgLoader< ? > getSetupImgLoader( final int setupId );
}
