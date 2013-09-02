package mpicbg.spim.data.newstuff.sequence;

import static mpicbg.spim.data.newstuff.sequence.XmlKeys.MISSINGVIEWS_TAG;
import static mpicbg.spim.data.newstuff.sequence.XmlKeys.MISSINGVIEW_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.newstuff.sequence.XmlKeys.MISSINGVIEW_TAG;
import static mpicbg.spim.data.newstuff.sequence.XmlKeys.MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME;

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
		{
			final Element elem = ( Element ) nodes.item( i );
			final int timepointId = Integer.parseInt( elem.getAttribute( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME ) );
			final int setupId = Integer.parseInt( elem.getAttribute( MISSINGVIEW_SETUP_ATTRIBUTE_NAME ) );
			missing.add( new ViewId( timepointId, setupId ) );
		}
		final MissingViews mv = new MissingViews( missing );
		return mv;
	}

	public Element toXml( final Document doc, final MissingViews missingViews )
	{
		final Element mvs = doc.createElement( MISSINGVIEWS_TAG );
		for ( final ViewId viewId : missingViews.missingViews )
		{
			final Element mv = doc.createElement( MISSINGVIEW_TAG );
			mv.setAttribute( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME, String.format( "%d", viewId.getTimepointId() ) );
			mv.setAttribute( MISSINGVIEW_SETUP_ATTRIBUTE_NAME, String.format( "%d", viewId.getViewSetupId() ) );
			mvs.appendChild( mv );
		}
		return mvs;
	}
}
