package mpicbg.spim.data.registration;

import static mpicbg.spim.data.XmlKeys.VIEWTRANSFORM_AFFINE_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWTRANSFORM_CLASS_ATTRIBUTE_NAME;
import static mpicbg.spim.data.XmlKeys.VIEWTRANSFORM_NAME_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWTRANSFORM_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME;
import static mpicbg.spim.data.XmlKeys.VIEWTRANSFORM_TYPE_VALUE_AFFINE;
import static mpicbg.spim.data.XmlKeys.VIEWTRANSFORM_TYPE_VALUE_GENERIC;
import mpicbg.spim.data.SpimDataInstantiationException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.XmlKeys;
import net.imglib2.realtransform.AffineTransform3D;

import org.jdom2.Element;

public class XmlIoViewTransform
{
	/**
	 * Get the tag name of the XML element that is written by this class.
	 *
	 * @return the tag name of the XML elements that is written.
	 */
	public String getTag()
	{
		return VIEWTRANSFORM_TAG;
	}

	/**
	 * Load a {@link ViewTransform} from the given DOM element.
	 *
	 * @param viewTransform
	 *            a {@value XmlKeys#VIEWTRANSFORM_TAG} DOM element.
	 * @throws SpimDataInstantiationException
	 */
	public ViewTransform fromXml( final Element viewTransform ) throws SpimDataInstantiationException
	{
		final String type = viewTransform.getAttributeValue( VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME );
		if ( VIEWTRANSFORM_TYPE_VALUE_AFFINE.equals( type ) )
			return fromXmlAffine( viewTransform );
		else if ( VIEWTRANSFORM_TYPE_VALUE_GENERIC.equals( type ) )
			return fromXmlGeneric( viewTransform );
		else
			throw new IllegalArgumentException( "unknown <" + VIEWTRANSFORM_TAG + "> type: " + type );
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
		final String name = XmlHelpers.getText( viewTransform, VIEWTRANSFORM_NAME_TAG, null );
		final AffineTransform3D affine = XmlHelpers.getAffineTransform3D( viewTransform, VIEWTRANSFORM_AFFINE_TAG );
		return new ViewTransformAffine( name, affine );
	}

	protected Element toXmlAffine( final ViewTransformAffine viewTransform )
	{
		final Element elem = new Element( VIEWTRANSFORM_TAG );
		elem.setAttribute( VIEWTRANSFORM_TYPE_ATTRIBUTE_NAME, VIEWTRANSFORM_TYPE_VALUE_AFFINE );
		if ( viewTransform.hasName() )
			elem.addContent( XmlHelpers.textElement( VIEWTRANSFORM_NAME_TAG, viewTransform.getName() ) );
		elem.addContent( XmlHelpers.affineTransform3DElement( VIEWTRANSFORM_AFFINE_TAG, viewTransform.asAffine3D() ) );
		return elem;
	}

	protected ViewTransformGeneric fromXmlGeneric( final Element viewTransform ) throws SpimDataInstantiationException
	{
		final String attributeClassName = viewTransform.getAttributeValue( VIEWTRANSFORM_CLASS_ATTRIBUTE_NAME );
		try
		{
			final ViewTransformGeneric t = ( ViewTransformGeneric ) Class.forName( attributeClassName ).newInstance();
			t.init( viewTransform );
			return t;
		}
		catch( final Exception e )
		{
			throw new SpimDataInstantiationException( "could not instantiate " + ViewTransformGeneric.class.getSimpleName() + " instance " + attributeClassName, e );
		}
	}

	protected Element toXmlGeneric( final ViewTransformGeneric viewTransform )
	{
		return viewTransform.toXml();
	}
}
