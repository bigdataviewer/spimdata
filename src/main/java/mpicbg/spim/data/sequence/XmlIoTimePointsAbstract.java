package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.TIMEPOINTS_TAG;

import org.jdom2.Element;

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

	public abstract Element toXml( final TimePoints< T > timepoints );
}
