package mpicbg.spim.data.generic.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpicbg.spim.data.generic.base.EntityUtils;

public class AbstractSequenceDescriptionUtils
{
	public static < V extends BasicViewSetup > void changeIds(
			final AbstractSequenceDescription< V, ?, ? > seq,
			final Map< Integer, Integer > oldIdToNewIdMap )
	{
		final List< V > setups = new ArrayList< V >( seq.getViewSetupsOrdered() );
		EntityUtils.changeIds( setups, oldIdToNewIdMap );
		final HashMap< Integer, V > setupsMap = new HashMap< Integer, V >();
		for ( final V setup : setups )
			setupsMap.put( setup.getId(), setup );
		seq.setViewSetups( setupsMap );
	}

	public static < V extends BasicViewSetup > void changeIds(
			final AbstractSequenceDescription< V, ?, ? > seq,
			final int... oldIdToNewIdMap )
	{
		assert ( oldIdToNewIdMap.length % 2 == 0 );
		final Map< Integer, Integer > map = new HashMap< Integer, Integer >();
		for ( int i = 0; i < oldIdToNewIdMap.length; i += 2 )
			map.put( oldIdToNewIdMap[ i ], oldIdToNewIdMap[ i + 1 ] );
		changeIds( seq, map );
	}
}
