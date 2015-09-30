package mpicbg.spim.data.registration;

import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATIONS_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATION_TAG;

import java.util.HashMap;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlKeys;
import mpicbg.spim.data.generic.base.XmlIoSingleton;
import mpicbg.spim.data.sequence.ViewId;

import org.jdom2.Element;

public class XmlIoViewRegistrations extends XmlIoSingleton< ViewRegistrations >
{
	protected XmlIoViewTransform xmlViewTransform;

	private final XmlIoViewRegistration xmlIoViewRegistration;

	public XmlIoViewRegistrations()
	{
		super( VIEWREGISTRATIONS_TAG, ViewRegistrations.class );
		this.xmlIoViewRegistration = new XmlIoViewRegistration();

		handledTags.add( VIEWREGISTRATION_TAG );
	}

	/**
	 * Load {@link ViewRegistrations} from the given DOM element.
	 *
	 * @param elem
	 *            a {@value XmlKeys#VIEWREGISTRATIONS_TAG} DOM element.
	 * @throws SpimDataException
	 */
	@Override
	public ViewRegistrations fromXml( final Element elem ) throws SpimDataException
	{
		final ViewRegistrations viewRegistrations = super.fromXml( elem );

		final HashMap< ViewId, ViewRegistration > regs = new HashMap< ViewId, ViewRegistration >();
		for ( final Element c : elem.getChildren( xmlIoViewRegistration.getTag() ) )
		{
			final ViewRegistration vr = xmlIoViewRegistration.fromXml( c );
			regs.put( vr, vr );
		}
		viewRegistrations.setViewRegistrations( regs );

		return viewRegistrations;
	}

	public Element toXml( final ViewRegistrations viewRegistrations )
	{
		final Element elem = super.toXml();

		for ( final ViewRegistration vr : viewRegistrations.getViewRegistrationsOrdered() )
			elem.addContent( xmlIoViewRegistration.toXml( vr ) );

		return elem;
	}
}
