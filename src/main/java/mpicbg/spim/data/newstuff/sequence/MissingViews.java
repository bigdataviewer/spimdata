package mpicbg.spim.data.newstuff.sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
	 * TODO
	 * assumed ordered
	 *
	 * @param viewDescriptions
	 * @return ordered
	 */
	public < T extends TimePoint, V extends ViewSetup > ArrayList< ViewDescription< T, V > > markMissingViews(
			final ArrayList< ViewDescription< T, V > > viewDescriptions )
	{
		if ( viewDescriptions.isEmpty() || missingViews.isEmpty() )
			return viewDescriptions;
		final Iterator< ViewDescription< T, V >> ri = viewDescriptions.iterator();
		final Iterator< ViewId > mi = missingViews.iterator();
		ViewDescription< T, V > r = ri.next();
		ViewId m = mi.next();
		while ( true )
		{
			final int m2r = m.compareTo( r );
			if ( m2r > 0 )
			{
				if ( !ri.hasNext() )
					break;
				r = ri.next();
			}
			else if ( m2r < 0 )
			{
				if ( !mi.hasNext() )
					break;
				m = mi.next();
			}
			else // if ( m2r == 0 )
			{
				r.present = false;
				if ( ( !ri.hasNext() ) || ( !mi.hasNext() ) )
					break;
				r = ri.next();
				m = mi.next();
			}
		}
		return viewDescriptions;
	}
}
