package mpicbg.spim.data.registration;

import static mpicbg.spim.data.registration.XmlKeys.VIEWREGISTRATIONS_TAG;
import static mpicbg.spim.data.registration.XmlKeys.VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.registration.XmlKeys.VIEWREGISTRATION_TAG;
import static mpicbg.spim.data.registration.XmlKeys.VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mpicbg.spim.data.sequence.ViewId;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


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
	public ViewRegistrations fromXml( final Element viewRegistrations ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final HashMap< ViewId, ViewRegistration > regs = new HashMap< ViewId, ViewRegistration >();
		final NodeList nodes = viewRegistrations.getElementsByTagName( VIEWREGISTRATION_TAG );
		for ( int i = 0; i < nodes.getLength(); ++i )
		{
			final ViewRegistration vr = viewRegistrationFromXml( ( Element ) nodes.item( i ) ); 
			regs.put( vr, vr );
		}
		return new ViewRegistrations( regs );
	}

	public Element toXml( final Document doc, final ViewRegistrations viewRegistrations ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final Element elem = doc.createElement( VIEWREGISTRATIONS_TAG );
		
		// sort the ViewRegistration objects so that they can be easily edited manually
		final ArrayList< ViewRegistration > vrList = new ArrayList< ViewRegistration >();
		vrList.addAll( viewRegistrations.getRegistrations().values() );
		Collections.sort( vrList );

		for ( final ViewRegistration vr : vrList )
			elem.appendChild( viewRegistrationToXml( doc, vr ) );
		
		return elem;
	}

	protected ViewRegistration viewRegistrationFromXml( final Element viewRegistration ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final int timepointId = Integer.parseInt( viewRegistration.getAttribute( VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME ) );
		final int setupId = Integer.parseInt( viewRegistration.getAttribute( VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME ) );
		final NodeList nodes = viewRegistration.getElementsByTagName( XmlKeys.VIEWTRANSFORM_TAG );
		final ArrayList< ViewTransform > transforms = new ArrayList< ViewTransform >();
		for ( int i = 0; i < nodes.getLength(); ++i )
			transforms.add( xmlViewTransform.fromXml( ( Element ) nodes.item( i ) ) );
		return new ViewRegistration( timepointId, setupId, transforms );
	}

	protected Element viewRegistrationToXml( final Document doc, final ViewRegistration viewRegistration ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final Element elem = doc.createElement( VIEWREGISTRATION_TAG );
		elem.setAttribute( VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME, Integer.toString( viewRegistration.getTimePointId() ) );
		elem.setAttribute( VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME, Integer.toString( viewRegistration.getViewSetupId() ) );
		final ArrayList< ViewTransform > transforms = new ArrayList< ViewTransform >( viewRegistration.getTransformList() );
		if ( transforms.isEmpty() )
			transforms.add( new ViewTransformAffine( null, viewRegistration.getModel() ) );
		for ( final ViewTransform t : transforms )
			elem.appendChild( xmlViewTransform.toXml( doc, t ) );
		return elem;
	}
}
