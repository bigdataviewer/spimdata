package mpicbg.spim.data.generic.sequence;

public interface BasicMultiResolutionImgLoader extends BasicImgLoader
{
	@Override
	public BasicMultiResolutionSetupImgLoader< ? > getSetupImgLoader( final int setupId );
}
