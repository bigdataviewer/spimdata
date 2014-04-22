package mpicbg.spim.data.registration;

import static mpicbg.spim.data.registration.XmlKeys.VIEWREGISTRATIONS_TAG;
import static mpicbg.spim.data.registration.XmlKeys.VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.registration.XmlKeys.VIEWREGISTRATION_TAG;
import static mpicbg.spim.data.registration.XmlKeys.VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME;
import static mpicbg.spim.data.registration.XmlKeys.VIEWTRANSFORM_TAG;

import java.util.ArrayList;
import java.util.HashMap;

import mpicbg.spim.data.sequence.ViewId;

import org.jdom2.Element;

public class XmlIoViewRegistrations
{
	/**
	 * TODO
	 */
	public String getTagName()
	{
		return VIEWREGISTRATIONS_TAG;
	}

	protected XmlIoViewTransform xmlViewTransform;

	public XmlIoViewRegistrations( final XmlIoViewTransform xmlViewTransform )
	{
		this.xmlViewTransform = xmlViewTransform;
	}

	public XmlIoViewRegistrations()
	{
		this( new XmlIoViewTransform() );
	}

	/**
	 * Load {@link ViewRegistrations} from the given DOM element.
	 *
	 * @param element
	 *            a {@value XmlKeys#VIEWREGISTRATIONS_TAG} DOM element.
	 */
	public ViewRegistrations fromXml( final Element viewRegistrations )
	{
		final HashMap< ViewId, ViewRegistration > regs = new HashMap< ViewId, ViewRegistration >();
		for ( final Element reg : viewRegistrations.getChildren( VIEWREGISTRATION_TAG ) )
		{
			final ViewRegistration vr = viewRegistrationFromXml( reg );
			regs.put( vr, vr );
		}
		return new ViewRegistrations( regs );
	}

	public Element toXml( final ViewRegistrations viewRegistrations )
	{
		final Element elem = new Element( VIEWREGISTRATIONS_TAG );
		for ( final ViewRegistration vr : viewRegistrations.getOrderedViewRegistrations() )
			elem.addContent( viewRegistrationToXml( vr ) );
		return elem;
	}

	protected ViewRegistration viewRegistrationFromXml( final Element viewRegistration )
	{
		final int timepointId = Integer.parseInt( viewRegistration.getAttributeValue( VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME ) );
		final int setupId = Integer.parseInt( viewRegistration.getAttributeValue( VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME ) );
		final ArrayList< ViewTransform > transforms = new ArrayList< ViewTransform >();
		for ( final Element t : viewRegistration.getChildren( VIEWTRANSFORM_TAG ) )
			transforms.add( xmlViewTransform.fromXml( t ) );
		return new ViewRegistration( timepointId, setupId, transforms );
	}

	protected Element viewRegistrationToXml( final ViewRegistration viewRegistration )
	{
		final Element elem = new Element( VIEWREGISTRATION_TAG );
		elem.setAttribute( VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME, Integer.toString( viewRegistration.getTimePointId() ) );
		elem.setAttribute( VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME, Integer.toString( viewRegistration.getViewSetupId() ) );
		final ArrayList< ViewTransform > transforms = new ArrayList< ViewTransform >( viewRegistration.getTransformList() );
		if ( transforms.isEmpty() )
			transforms.add( new ViewTransformAffine( null, viewRegistration.getModel() ) );
		for ( final ViewTransform t : transforms )
			elem.addContent( xmlViewTransform.toXml( t ) );
		return elem;
	}
}
