package mpicbg.spim.data.sequence;

import java.util.Collection;
import java.util.Map;

public class ViewIdUtils
{
	protected static void changeViewsetupIds( final Collection< ? extends ViewId > viewIds, final Map< Integer, Integer > oldIdToNewIdMap )
	{
		for ( final ViewId viewId : viewIds )
		{
			final Integer newId = oldIdToNewIdMap.get( viewId.getViewSetupId() );
			if ( newId != null )
				viewId.setViewSetupId( newId );
		}
	}
}
