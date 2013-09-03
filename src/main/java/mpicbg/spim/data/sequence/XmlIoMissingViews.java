package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.MISSINGVIEWS_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.MISSINGVIEW_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.sequence.XmlKeys.MISSINGVIEW_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
		final NodeList nodes = missingViews.getElementsByTagName( MISSINGVIEW_TAG );
		for ( int i = 0; i < nodes.getLength(); ++i )
			missing.add( viewIdFromXml( ( Element ) nodes.item( i ) ) );
		final MissingViews mvs = new MissingViews( missing );
		return mvs;
	}

	public Element toXml( final Document doc, final MissingViews missingViews )
	{
		final Element mvs = doc.createElement( MISSINGVIEWS_TAG );
		for ( final ViewId viewId : missingViews.missingViews )
			mvs.appendChild( viewIdToXml( doc, viewId ) );
		return mvs;
	}

	protected Element viewIdToXml( final Document doc, final ViewId viewId )
	{
		final Element mv = doc.createElement( MISSINGVIEW_TAG );
		mv.setAttribute( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME, Integer.toString( viewId.getTimePointId() ) );
		mv.setAttribute( MISSINGVIEW_SETUP_ATTRIBUTE_NAME, Integer.toString( viewId.getViewSetupId() ) );
		return mv;
	}

	protected ViewId viewIdFromXml( final Element missingView )
	{
		final int timepointId = Integer.parseInt( missingView.getAttribute( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME ) );
		final int setupId = Integer.parseInt( missingView.getAttribute( MISSINGVIEW_SETUP_ATTRIBUTE_NAME ) );
		return new ViewId( timepointId, setupId );
	}
}
