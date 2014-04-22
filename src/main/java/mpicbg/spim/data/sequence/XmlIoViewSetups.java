package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUPS_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_ANGLE_ID_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_ANGLE_NAME_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_ANGLE_ROTATIONAXIS_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_ANGLE_ROTATION_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_CHANNEL_ID_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_CHANNEL_NAME_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_DEPTH_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_HEIGHT_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_ID_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_ILLUMINATION_ID_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_ILLUMINATION_NAME_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_PIXELDEPTH_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_PIXELHEIGHT_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_PIXELWIDTH_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_UNIT_TAG;
import static mpicbg.spim.data.sequence.XmlKeys.VIEWSETUP_WIDTH_TAG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mpicbg.spim.data.XmlHelpers;

import org.jdom2.Element;

public class XmlIoViewSetups extends XmlIoViewSetupsAbstract< ViewSetup >
{
	@Override
	public ArrayList< ViewSetup > fromXml( final Element viewSetups )
	{
		final ArrayList< ViewSetup > setups = new ArrayList< ViewSetup >();
		for ( final Element elem : viewSetups.getChildren( VIEWSETUP_TAG ) )
		{
			final int id = XmlHelpers.getInt( elem, VIEWSETUP_ID_TAG );

			final int angleId = XmlHelpers.getInt( elem, VIEWSETUP_ANGLE_ID_TAG );
			final String angleName = XmlHelpers.getText( elem, VIEWSETUP_ANGLE_NAME_TAG, Integer.toString( angleId ) );
			final double rotation = XmlHelpers.getDouble( elem, VIEWSETUP_ANGLE_ROTATION_TAG, Double.NaN );
			final double[] rotationAxis = XmlHelpers.getDoubleArray( elem, VIEWSETUP_ANGLE_ROTATIONAXIS_TAG, null );

			final int illuminationId = XmlHelpers.getInt( elem, VIEWSETUP_ILLUMINATION_ID_TAG );
			final String illuminationName = XmlHelpers.getText( elem, VIEWSETUP_ILLUMINATION_NAME_TAG, Integer.toString( illuminationId ) );

			final int channelId = XmlHelpers.getInt( elem, VIEWSETUP_CHANNEL_ID_TAG );
			final String channelName = XmlHelpers.getText( elem, VIEWSETUP_CHANNEL_NAME_TAG, Integer.toString( channelId ) );

			final int width = XmlHelpers.getInt( elem, VIEWSETUP_WIDTH_TAG, -1 );
			final int height = XmlHelpers.getInt( elem, VIEWSETUP_HEIGHT_TAG, -1 );
			final int depth = XmlHelpers.getInt( elem, VIEWSETUP_DEPTH_TAG, -1 );
			final String unit = XmlHelpers.getText( elem, VIEWSETUP_UNIT_TAG, "" );
			final double pixelWidth = XmlHelpers.getDouble( elem, VIEWSETUP_PIXELWIDTH_TAG, -1 );
			final double pixelHeight = XmlHelpers.getDouble( elem, VIEWSETUP_PIXELHEIGHT_TAG, -1 );
			final double pixelDepth = XmlHelpers.getDouble( elem, VIEWSETUP_PIXELDEPTH_TAG, -1 );

			setups.add( new ViewSetup( id,
					new Angle( angleId, angleName, rotation, rotationAxis ),
					new Illumination( illuminationId, illuminationName ),
					new Channel( channelId, channelName),
					width, height, depth, unit, pixelWidth, pixelHeight, pixelDepth ) );
		}
		Collections.sort( setups ); // sorts by id
		return setups;
	}

	@Override
	public Element toXml( final List< ViewSetup > viewSetups )
	{
		final Element setups = new Element( VIEWSETUPS_TAG );
		for ( final ViewSetup s : viewSetups )
		{
			final Element setup = new Element( VIEWSETUP_TAG );
			setup.addContent( XmlHelpers.intElement( VIEWSETUP_ID_TAG, s.getId() ) );

			setup.addContent( XmlHelpers.intElement( VIEWSETUP_ANGLE_ID_TAG, s.getAngle().getId() ) );
			setup.addContent( XmlHelpers.textElement( VIEWSETUP_ANGLE_NAME_TAG, s.getAngle().getName() ) );
			if ( !Double.isNaN( s.getAngle().getRotationAngle() ) )
				setup.addContent( XmlHelpers.doubleElement( VIEWSETUP_ANGLE_ROTATION_TAG, s.getAngle().getRotationAngle() ) );
			if ( s.getAngle().getRotationAxis() != null )
				setup.addContent( XmlHelpers.doubleArrayElement( VIEWSETUP_ANGLE_ROTATIONAXIS_TAG, s.getAngle().getRotationAxis() ) );

			setup.addContent( XmlHelpers.intElement( VIEWSETUP_ILLUMINATION_ID_TAG, s.getIllumination().getId() ) );
			setup.addContent( XmlHelpers.textElement( VIEWSETUP_ILLUMINATION_NAME_TAG, s.getIllumination().getName() ) );

			setup.addContent( XmlHelpers.intElement( VIEWSETUP_CHANNEL_ID_TAG, s.getChannel().getId() ) );
			setup.addContent( XmlHelpers.textElement( VIEWSETUP_CHANNEL_NAME_TAG, s.getChannel().getName() ) );

			if ( s.getWidth() != -1 )
				setup.addContent( XmlHelpers.intElement( VIEWSETUP_WIDTH_TAG, s.getWidth() ) );
			if ( s.getHeight() != -1 )
				setup.addContent( XmlHelpers.intElement( VIEWSETUP_HEIGHT_TAG, s.getHeight() ) );
			if ( s.getDepth() != -1 )
				setup.addContent( XmlHelpers.intElement( VIEWSETUP_DEPTH_TAG, s.getDepth() ) );
			if ( s.getPixelSizeUnit() != null && !s.getPixelSizeUnit().isEmpty() )
				setup.addContent( XmlHelpers.textElement( VIEWSETUP_UNIT_TAG, s.getPixelSizeUnit() ) );
			if ( s.getPixelWidth() != -1 )
				setup.addContent( XmlHelpers.doubleElement( VIEWSETUP_PIXELWIDTH_TAG, s.getPixelWidth() ) );
			if ( s.getPixelHeight() != -1 )
				setup.addContent( XmlHelpers.doubleElement( VIEWSETUP_PIXELHEIGHT_TAG, s.getPixelHeight() ) );
			if ( s.getPixelDepth() != -1 )
				setup.addContent( XmlHelpers.doubleElement( VIEWSETUP_PIXELDEPTH_TAG, s.getPixelDepth() ) );

			setups.addContent( setup );
		}
		return setups;
	}
}
