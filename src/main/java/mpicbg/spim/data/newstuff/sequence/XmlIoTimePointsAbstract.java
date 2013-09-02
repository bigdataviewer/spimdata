package mpicbg.spim.data.newstuff.sequence;

import static mpicbg.spim.data.newstuff.sequence.XmlKeys.TIMEPOINTS_TAG;

import java.util.ArrayList;
import java.util.List;

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

	public abstract ArrayList< T > fromXml( final Element timepoints );

	public abstract Element toXml( final Document doc, final List< T > timepoints );
}
