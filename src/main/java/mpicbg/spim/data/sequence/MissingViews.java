package mpicbg.spim.data.sequence;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MissingViews
{
	/**
	 * Set of missing {@link ViewId}s.
	 */
	private Set< ViewId > missingViews;

	public MissingViews( final Collection< ? extends ViewId > missingViews )
	{
		this.missingViews = Collections.unmodifiableSet( new HashSet< ViewId >( missingViews ) );
	}

	public Set< ViewId > getMissingViews()
	{
		return missingViews;
	}

	/**
	 * @param missingViews
	 *            ordered list of missing views
	 */
	protected void setMissingViews( final Set< ViewId > missingViews )
	{
		this.missingViews = Collections.unmodifiableSet( missingViews );
	}

	protected MissingViews()
	{}
}
