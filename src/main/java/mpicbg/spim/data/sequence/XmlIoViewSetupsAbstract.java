package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUPS_TAG;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

public abstract class XmlIoViewSetupsAbstract< V extends ViewSetup >
{
	/**
	 * TODO
	 */
	public String getTagName()
	{
		return VIEWSETUPS_TAG;
	}

	public abstract ArrayList< V > fromXml( final Element viewSetups );

	public abstract Element toXml( final List< V > viewSetups );

}
