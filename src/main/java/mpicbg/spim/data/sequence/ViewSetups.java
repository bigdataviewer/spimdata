package mpicbg.spim.data.sequence;

import java.util.Collection;
import java.util.HashMap;

/**
 * Helper methods for dealing with {@link ViewSetup} collections.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 */
public class ViewSetups
{
	public static HashMap< Integer, Tile > getAllTiles( final Collection< ? extends ViewSetup > setups )
	{
		final HashMap< Integer, Tile > tiles = new HashMap< Integer, Tile >();

		for ( final ViewSetup setup : setups )
		{
			final Tile t = setup.getTile();
			tiles.put( t.getId(), t );
		}

		return tiles;
	}

	public static HashMap< Integer, Channel > getAllChannels( final Collection< ? extends ViewSetup > setups )
	{
		final HashMap< Integer, Channel > channels = new HashMap< Integer, Channel >();

		for ( final ViewSetup setup : setups )
		{
			final Channel c = setup.getChannel();
			channels.put( c.getId(), c );
		}

		return channels;
	}

	public static HashMap< Integer, Angle > getAllAngles( final Collection< ? extends ViewSetup > setups )
	{
		final HashMap< Integer, Angle > angles = new HashMap< Integer, Angle >();

		for ( final ViewSetup setup : setups )
		{
			final Angle a = setup.getAngle();
			angles.put( a.getId(), a );
		}

		return angles;
	}

	public static HashMap< Integer, Illumination > getAllIlluminations( final Collection< ? extends ViewSetup > setups )
	{
		final HashMap< Integer, Illumination > illuminations = new HashMap< Integer, Illumination >();

		for ( final ViewSetup setup : setups )
		{
			final Illumination i = setup.getIllumination();
			illuminations.put( i.getId(), i );
		}

		return illuminations;
	}
}
