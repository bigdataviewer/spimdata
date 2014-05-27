package mpicbg.spim.data.generic.sequence;

import java.io.File;

import org.jdom2.Element;

public interface XmlIoBasicImgLoader< T extends BasicImgLoader< ? > >
{
	public Element toXml( final T imgLoader, final File basePath );

	public T fromXml( final Element elem, final File basePath, AbstractSequenceDescription< ?, ?, ? > sequenceDescription );
}
