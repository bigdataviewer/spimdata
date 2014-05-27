package mpicbg.spim.data;

import mpicbg.spim.data.generic.XmlIoAbstractSpimData;
import mpicbg.spim.data.registration.XmlIoViewRegistrations;
import mpicbg.spim.data.sequence.SequenceDescription;
import mpicbg.spim.data.sequence.XmlIoSequenceDescription;

public class XmlIoSpimData extends XmlIoAbstractSpimData< SequenceDescription, SpimData >
{
	public XmlIoSpimData()
	{
		super( SpimData.class, new XmlIoSequenceDescription(), new XmlIoViewRegistrations() );
	}
}
