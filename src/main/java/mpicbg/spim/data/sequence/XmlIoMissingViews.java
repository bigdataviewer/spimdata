package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.XmlKeys.MISSINGVIEWS_TAG;
import static mpicbg.spim.data.XmlKeys.MISSINGVIEW_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.XmlKeys.MISSINGVIEW_TAG;
import static mpicbg.spim.data.XmlKeys.MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.generic.base.XmlIoSingleton;

import org.jdom2.Element;

public class XmlIoMissingViews extends XmlIoSingleton< MissingViews >
{
	public XmlIoMissingViews()
	{
		super( MISSINGVIEWS_TAG, MissingViews.class );
		handledTags.add( MISSINGVIEW_TAG );
	}

	public Element toXml( final MissingViews missingViews )
	{
		final Element elem = super.toXml();

		final ArrayList< ViewId > list = new ArrayList< ViewId >( missingViews.getMissingViews() );
		Collections.sort( list );
		for ( final ViewId v : list )
		{
			final Element child = new Element( MISSINGVIEW_TAG );
			child.setAttribute( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME, Integer.toString( v.getTimePointId() ) );
			child.setAttribute( MISSINGVIEW_SETUP_ATTRIBUTE_NAME, Integer.toString( v.getViewSetupId() ) );
			elem.addContent( child );
		}

		return elem;
	}

	@Override
	public MissingViews fromXml( final Element elem ) throws SpimDataException
	{
		final MissingViews missingViews = super.fromXml( elem );

		final HashSet< ViewId > views = new HashSet< ViewId >();
		for ( final Element c : elem.getChildren( MISSINGVIEW_TAG ) )
		{
			final int t = Integer.parseInt( c.getAttributeValue( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME ) );
			final int s = Integer.parseInt( c.getAttributeValue( MISSINGVIEW_SETUP_ATTRIBUTE_NAME ) );
			views.add( new ViewId( t, s ) );
		}
		missingViews.setMissingViews( views );

		return missingViews;
	}
}
