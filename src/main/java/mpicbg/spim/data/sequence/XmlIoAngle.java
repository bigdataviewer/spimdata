package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.XmlKeys.ANGLE_ROTATIONAXIS_TAG;
import static mpicbg.spim.data.XmlKeys.ANGLE_ROTATIONDEGREES_TAG;
import static mpicbg.spim.data.XmlKeys.ANGLE_TAG;
import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.generic.base.ViewSetupAttributeIo;
import mpicbg.spim.data.generic.base.XmlIoNamedEntity;

import org.jdom2.Element;

@ViewSetupAttributeIo( name = "angle", type = Angle.class )
public class XmlIoAngle extends XmlIoNamedEntity< Angle >
{
	public XmlIoAngle()
	{
		super( ANGLE_TAG, Angle.class );
		handledTags.add( ANGLE_ROTATIONAXIS_TAG );
		handledTags.add( ANGLE_ROTATIONDEGREES_TAG );
	}

	@Override
	public Element toXml( final Angle angle )
	{
		final Element elem = super.toXml( angle );
		if ( angle.hasRotation() )
		{
			elem.addContent( XmlHelpers.doubleArrayElement( ANGLE_ROTATIONAXIS_TAG, angle.getRotationAxis() ) );
			elem.addContent( XmlHelpers.doubleElement( ANGLE_ROTATIONDEGREES_TAG, angle.getRotationAngleDegrees() ) );
		}
		return elem;
	}

	@Override
	public Angle fromXml( final Element elem ) throws SpimDataException
	{
		final Angle angle = super.fromXml( elem );
		final double[] axis = XmlHelpers.getDoubleArray( elem, ANGLE_ROTATIONAXIS_TAG, null );
		final double degrees = XmlHelpers.getDouble( elem, ANGLE_ROTATIONDEGREES_TAG, Double.NaN );
		angle.setRotation( axis, degrees );
		return angle;
	}
}
