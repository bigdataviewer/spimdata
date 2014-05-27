package mpicbg.spim.data.registration;

import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATION_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME;

import java.util.ArrayList;

import mpicbg.spim.data.SpimDataInstantiationException;

import org.jdom2.Element;

public class XmlIoViewRegistration
{
	private final XmlIoViewTransform xmlIoViewTransform;

	public XmlIoViewRegistration()
	{
		this.xmlIoViewTransform = new XmlIoViewTransform();
	}

	/**
	 * Get the tag name of the XML element that is written by this class.
	 *
	 * @return the tag name of the XML elements that is written.
	 */
	public String getTag()
	{
		return VIEWREGISTRATION_TAG;
	}

	public Element toXml( final ViewRegistration viewRegistration )
	{
		final Element elem = new Element( VIEWREGISTRATION_TAG );
		elem.setAttribute( VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME, Integer.toString( viewRegistration.getTimePointId() ) );
		elem.setAttribute( VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME, Integer.toString( viewRegistration.getViewSetupId() ) );
		final ArrayList< ViewTransform > transforms = new ArrayList< ViewTransform >( viewRegistration.getTransformList() );
		if ( transforms.isEmpty() )
			transforms.add( new ViewTransformAffine( null, viewRegistration.getModel() ) );
		for ( final ViewTransform t : transforms )
			elem.addContent( xmlIoViewTransform.toXml( t ) );
		return elem;
	}

	public ViewRegistration fromXml( final Element elem ) throws SpimDataInstantiationException
	{
		final int timepointId = Integer.parseInt( elem.getAttributeValue( VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME ) );
		final int setupId = Integer.parseInt( elem.getAttributeValue( VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME ) );
		final ArrayList< ViewTransform > transforms = new ArrayList< ViewTransform >();
		for ( final Element c : elem.getChildren( xmlIoViewTransform.getTag() ) )
			transforms.add( xmlIoViewTransform.fromXml( c ) );
		return new ViewRegistration( timepointId, setupId, transforms );
	}
}
