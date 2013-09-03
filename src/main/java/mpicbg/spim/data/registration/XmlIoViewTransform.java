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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	public ViewTransform fromXml( final Element viewTransform ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final String type = viewTransform.getAttribute( VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME );
		if ( VIEWTRANSFORM_TYPE_VALUE_AFFINE.equals( type ) )
		{
			return fromXmlAffine( viewTransform );
		}
		else if ( VIEWTRANSFORM_TYPE_VALUE_GENERIC.equals( type ) )
		{
			return fromXmlGeneric( viewTransform );
		}
		else
			throw new RuntimeException( "unknown <" + VIEWTRANSFORM_TAG + "> type: " + type );
	}

	public Element toXml( final Document doc, final ViewTransform viewTransform )
	{
		if ( ViewTransformAffine.class.isInstance( viewTransform ) )
			return toXmlAffine( doc, ( ViewTransformAffine ) viewTransform );
		else if ( ViewTransformGeneric.class.isInstance( viewTransform ) )
			return toXmlGeneric( doc, ( ViewTransformGeneric ) viewTransform );
		else
			throw new RuntimeException( "unknown ViewTransform type: " + viewTransform.getClass().getName() );
	}

	protected ViewTransformAffine fromXmlAffine( final Element viewTransform )
	{
		final String name = XmlHelpers.getText( viewTransform, VIEWTRANSFORM_NAME_TAG, "" );
		final AffineTransform3D affine = XmlHelpers.loadAffineTransform3D( ( Element ) viewTransform.getElementsByTagName( VIEWTRANSFORM_AFFINE_TAG ).item( 0 ) );
		return new ViewTransformAffine( name, affine );
	}

	protected Element toXmlAffine( final Document doc, final ViewTransformAffine viewTransform )
	{
		final Element elem = doc.createElement( VIEWTRANSFORM_TAG );
		elem.setAttribute( VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME, VIEWTRANSFORM_TYPE_VALUE_AFFINE );
		final String name = viewTransform.getName();
		if ( name != null && !name.isEmpty() )
			elem.appendChild( XmlHelpers.textElement( doc, VIEWTRANSFORM_NAME_TAG, name ) );
		elem.appendChild( XmlHelpers.affineTransform3DElement( doc, VIEWTRANSFORM_AFFINE_TAG, viewTransform.asAffine3D() ) );
		return elem;
	}

	protected ViewTransformGeneric fromXmlGeneric( final Element viewTransform ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final ViewTransformGeneric t = ( ViewTransformGeneric ) Class.forName( viewTransform.getAttribute( VIEWTRANSFORM_CLASS_ATTRIBUTE_NAME  ) ).newInstance();
		t.init( viewTransform );
		return t;
	}

	protected Element toXmlGeneric( final Document doc, final ViewTransformGeneric viewTransform )
	{
		return viewTransform.toXml( doc );
	}

}
