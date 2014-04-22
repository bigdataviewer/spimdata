package mpicbg.spim.data.sequence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Holds a list of {@link TimePoint} and additional descriptions like the
 * integer pattern it was created from so that it can be saved the same way
 * as it was loaded
 *
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 *
 */
public class TimePoints< T extends TimePoint >
{
	final protected List< T > timepoints;
	protected HashMap< String, String > descriptionTags;

	public TimePoints( final List< T > timepoints )
	{
		this.timepoints = timepoints;
		this.descriptionTags = new HashMap< String, String >();
	}

	public List< T > getTimePointList() { return timepoints; }
	public HashMap< String, String > getHashMap() { return descriptionTags; } // TODO: rename to getDescriptionTags()

	public TimePoints< T > getUnmodifiableTimePoints()
	{
		final TimePoints< T > tps = new TimePoints< T >( Collections.unmodifiableList( timepoints ) );
		tps.getHashMap().putAll( descriptionTags );
		return tps;
	}
}
