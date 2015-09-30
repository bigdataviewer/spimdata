package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.XmlKeys.ILLUMINATION_TAG;
import mpicbg.spim.data.generic.base.ViewSetupAttributeIo;
import mpicbg.spim.data.generic.base.XmlIoNamedEntity;

@ViewSetupAttributeIo( name = "illumination", type = Illumination.class )
public class XmlIoIllumination extends XmlIoNamedEntity< Illumination >
{
	public XmlIoIllumination()
	{
		super( ILLUMINATION_TAG, Illumination.class );
	}
}
