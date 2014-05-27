package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.sequence.XmlIoAbstractSequenceDescription;

public class XmlIoSequenceDescription extends XmlIoAbstractSequenceDescription< ViewSetup, SequenceDescription >
{
	public XmlIoSequenceDescription()
	{
		super( SequenceDescription.class, new XmlIoTimePoints(), new XmlIoViewSetups(), new XmlIoMissingViews() );
	}
}
