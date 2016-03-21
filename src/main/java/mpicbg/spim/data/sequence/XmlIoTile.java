package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.XmlKeys.TILE_LOCATION_TAG;
import static mpicbg.spim.data.XmlKeys.TILE_TAG;

import org.jdom2.Element;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.generic.base.ViewSetupAttributeIo;
import mpicbg.spim.data.generic.base.XmlIoNamedEntity;

@ViewSetupAttributeIo( name = "tile", type = Tile.class )
public class XmlIoTile extends XmlIoNamedEntity< Tile >
{
	public XmlIoTile()
	{
		super( TILE_TAG, Tile.class );
	}

	@Override
	public Element toXml( final Tile tile )
	{
		final Element elem = super.toXml( tile );

		if ( tile.hasLocation() )
			elem.addContent( XmlHelpers.doubleArrayElement( TILE_LOCATION_TAG, tile.getLocation() ) );

		return elem;
	}

	@Override
	public Tile fromXml( final Element elem ) throws SpimDataException
	{
		final Tile tile = super.fromXml( elem );
		final double[] location = XmlHelpers.getDoubleArray( elem, TILE_LOCATION_TAG, null );
		tile.setLocation( location );
		return tile;
	}
}