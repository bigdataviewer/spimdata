package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.XmlKeys.CHANNEL_TAG;
import mpicbg.spim.data.generic.base.ViewSetupAttributeIo;
import mpicbg.spim.data.generic.base.XmlIoNamedEntity;

@ViewSetupAttributeIo( name = "channel", type = Channel.class )
public class XmlIoChannel extends XmlIoNamedEntity< Channel >
{
	public XmlIoChannel()
	{
		super( CHANNEL_TAG, Channel.class );
	}
}