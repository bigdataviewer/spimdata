package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.MISSINGVIEWS_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.MISSINGVIEW_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.sequence.XmlKeys.MISSINGVIEW_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME;

import java.util.ArrayList;

import org.jdom2.Element;

/**
 * TODO
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class XmlIoMissingViews
{
	/**
	 * TODO
	 */
	public String getTagName()
	{
		return MISSINGVIEWS_TAG;
	}

	/**
	 * Load {@link MissingViews} from the given DOM element.
	 *
	 * @param element
	 *            a {@value XmlKeys#MISSINGVIEWS_TAG} DOM element.
	 */
	public MissingViews fromXml( final Element missingViews )
	{
		final ArrayList< ViewId > missing = new ArrayList< ViewId >();
		for ( final Element elem : missingViews.getChildren( MISSINGVIEW_TAG ) )
			missing.add( viewIdFromXml( elem ) );
		final MissingViews mvs = new MissingViews( missing );
		return mvs;
	}

	public Element toXml( final MissingViews missingViews )
	{
		final Element mvs = new Element( MISSINGVIEWS_TAG );
		for ( final ViewId viewId : missingViews.missingViews )
			mvs.addContent( viewIdToXml( viewId ) );
		return mvs;
	}

	protected Element viewIdToXml( final ViewId viewId )
	{
		final Element mv = new Element( MISSINGVIEW_TAG );
		mv.setAttribute( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME, Integer.toString( viewId.getTimePointId() ) );
		mv.setAttribute( MISSINGVIEW_SETUP_ATTRIBUTE_NAME, Integer.toString( viewId.getViewSetupId() ) );
		return mv;
	}

	protected ViewId viewIdFromXml( final Element missingView )
	{
		final int timepointId = Integer.parseInt( missingView.getAttributeValue( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME ) );
		final int setupId = Integer.parseInt( missingView.getAttributeValue( MISSINGVIEW_SETUP_ATTRIBUTE_NAME ) );
		return new ViewId( timepointId, setupId );
	}
}
