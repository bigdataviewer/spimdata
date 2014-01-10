package mpicbg.spim.data.sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MissingViews
{
	/**
	 * Ordered list of missing {@link ViewId}s.
	 */
	protected final List< ViewId > missingViews;

	public MissingViews( final List< ? extends ViewId > missingViews )
	{
		this.missingViews = new ArrayList< ViewId >( missingViews );
		Collections.sort( missingViews );
	}

	/**
	 * @param viewDescriptions
	 * @return ordered
	 */
	public < T extends TimePoint, V extends ViewSetup > HashMap< ViewId, ViewDescription< T, V > > markMissingViews(
			final HashMap< ViewId, ViewDescription< T, V > > viewDescriptions )
	{
		for ( final ViewId viewId : missingViews )
		{
			final ViewDescription< T, V > viewDesc = viewDescriptions.get( viewId );
			
			if ( viewDesc != null )
				viewDesc.present = false;
		}
		
		return viewDescriptions;
	}
}
