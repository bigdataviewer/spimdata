package mpicbg.spim.data.generic.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EntityUtils
{
	public static void changeIds( final Collection< ? extends Entity > entities, final Map< Integer, Integer > oldIdToNewIdMap )
	{
		for ( final Entity entity : entities )
		{
			final Integer newId = oldIdToNewIdMap.get( entity.getId() );
			if ( newId != null )
				entity.setId( newId );
		}
	}

	public static void changeIds( final Collection< ? extends Entity > entities, final int... oldIdToNewIdMap )
	{
		assert ( oldIdToNewIdMap.length % 2 == 0 );
		final Map< Integer, Integer > map = new HashMap< Integer, Integer >();
		for ( int i = 0; i < oldIdToNewIdMap.length; i += 2 )
			map.put( oldIdToNewIdMap[ i ], oldIdToNewIdMap[ i + 1 ] );
		changeIds( entities, map );
	}
}
