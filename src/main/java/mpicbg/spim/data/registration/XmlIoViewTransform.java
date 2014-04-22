package mpicbg.spim.data.registration;

import static mpicbg.spim.data.registration.XmlKeys.VIEWTRANSFORM_AFFINE_TAG;
import static mpicbg.spim.data.registration.XmlKeys.VIEWTRANSFORM_CLASS_ATTRIBUTE_NAME;
import static mpicbg.spim.data.registration.XmlKeys.VIEWTRANSFORM_NAME_TAG;
import static mpicbg.spim.data.registration.XmlKeys.VIEWTRANSFORM_TAG;
import static mpicbg.spim.data.registration.XmlKeys.VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME;
import static mpicbg.spim.data.registration.XmlKeys.VIEWTRANSFORM_TYPE_VALUE_AFFINE;
import static mpicbg.spim.data.registration.XmlKeys.VIEWTRANSFORM_TYPE_VALUE_GENERIC;
import mpicbg.spim.data.XmlHelpers;
import net.imglib2.realtransform.AffineTransform3D;

import org.jdom2.Element;

public class XmlIoViewTransform
{
	/**
	 * TODO
	 */
	public String getTagName()
	{
		return VIEWTRANSFORM_TAG;
	}

	/**
	 * Load a {@link ViewTransform} from the given DOM element.
	 *
	 * @param element
	 *            a {@value XmlKeys#VIEWTRANSFORM_TAG} DOM element.
	 */
	public ViewTransform fromXml( final Element viewTransform )
	{
		final String type = viewTransform.getAttributeValue( VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME );
		if ( VIEWTRANSFORM_TYPE_VALUE_AFFINE.equals( type ) )
		{
			return fromXmlAffine( viewTransform );
		}
		else if ( VIEWTRANSFORM_TYPE_VALUE_GENERIC.equals( type ) )
		{
			try
			{
				return fromXmlGeneric( viewTransform );
			}
			catch ( final Exception e )
			{
				throw new RuntimeException( e );
			}
		}
		else
			throw new RuntimeException( "unknown <" + VIEWTRANSFORM_TAG + "> type: " + type );
	}

	public Element toXml( final ViewTransform viewTransform )
	{
		if ( ViewTransformAffine.class.isInstance( viewTransform ) )
			return toXmlAffine( ( ViewTransformAffine ) viewTransform );
		else if ( ViewTransformGeneric.class.isInstance( viewTransform ) )
			return toXmlGeneric( ( ViewTransformGeneric ) viewTransform );
		else
			throw new RuntimeException( "unknown ViewTransform type: " + viewTransform.getClass().getName() );
	}

	protected ViewTransformAffine fromXmlAffine( final Element viewTransform )
	{
		// TODO: Is it okay, to use null as default here?
		// would be: final String name = viewTransform.getChildText( VIEWTRANSFORM_NAME_TAG );
		final String name = XmlHelpers.getText( viewTransform, VIEWTRANSFORM_NAME_TAG, "" );
		final AffineTransform3D affine = XmlHelpers.loadAffineTransform3D( viewTransform.getChild( VIEWTRANSFORM_AFFINE_TAG ) );
		return new ViewTransformAffine( name, affine );
	}

	protected Element toXmlAffine( final ViewTransformAffine viewTransform )
	{
		final Element elem = new Element( VIEWTRANSFORM_TAG );
		elem.setAttribute( VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME, VIEWTRANSFORM_TYPE_VALUE_AFFINE );
		final String name = viewTransform.getName();
		if ( name != null && !name.isEmpty() )
			elem.addContent( new Element( VIEWTRANSFORM_NAME_TAG ).setText( name ) );
		elem.addContent( XmlHelpers.affineTransform3DElement( VIEWTRANSFORM_AFFINE_TAG, viewTransform.asAffine3D() ) );
		return elem;
	}

	protected ViewTransformGeneric fromXmlGeneric( final Element viewTransform ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final ViewTransformGeneric t = ( ViewTransformGeneric ) Class.forName( viewTransform.getAttributeValue( VIEWTRANSFORM_CLASS_ATTRIBUTE_NAME  ) ).newInstance();
		t.init( viewTransform );
		return t;
	}

	protected Element toXmlGeneric( final ViewTransformGeneric viewTransform )
	{
		return viewTransform.toXml();
	}

}
