package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_TAG;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class XmlIoTimePointsAbstract< T extends TimePoint >
{
	/**
	 * TODO
	 */
	public String getTagName()
	{
		return TIMEPOINTS_TAG;
	}

	public abstract TimePoints< T > fromXml( final Element timepoints );

	public abstract Element toXml( final Document doc, final TimePoints< T > timepoints );
}
