package mpicbg.spim.data.sequence;

import static mpicbg.spim.data.XmlKeys.TILE_TAG;
import mpicbg.spim.data.generic.base.ViewSetupAttributeIo;
import mpicbg.spim.data.generic.base.XmlIoNamedEntity;

@ViewSetupAttributeIo( name = "tile", type = Tile.class )
public class XmlIoTile extends XmlIoNamedEntity< Tile >
{
	public XmlIoTile()
	{
		super( TILE_TAG, Tile.class );
	}
}