package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.sequence.BasicMultiResolutionImgLoader;

public interface MultiResolutionImgLoader extends BasicMultiResolutionImgLoader, ImgLoader
{
	@Override
	public MultiResolutionSetupImgLoader< ? > getSetupImgLoader( final int setupId );
}
