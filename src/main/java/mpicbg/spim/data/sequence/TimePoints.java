package mpicbg.spim.data.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpicbg.spim.data.generic.base.Entity;

public class TimePoints
{
	private Map< Integer, TimePoint > timepoints;

	private List< TimePoint > timepointsOrdered;

	public TimePoints( final Map< Integer, TimePoint > timepoints )
	{
		setTimePoints( timepoints );
	}

	public TimePoints( final Collection< TimePoint > timepoints )
	{
		final HashMap< Integer, TimePoint > map = new HashMap< Integer, TimePoint >();
		for ( final TimePoint tp : timepoints )
			map.put( tp.getId(), tp );
		setTimePoints( map );
	}

	public Map< Integer, TimePoint > getTimePoints()
	{
		return timepoints;
	}

	public List< TimePoint > getTimePointsOrdered()
	{
		return timepointsOrdered;
	}

	public int size()
	{
		return timepointsOrdered.size();
	}

	protected void setTimePoints( final Collection< TimePoint > timepoints )
	{
		final HashMap< Integer, TimePoint > map = new HashMap< Integer, TimePoint >();
		for ( final TimePoint tp : timepoints )
			map.put( tp.getId(), tp );
		this.timepoints = Collections.unmodifiableMap( map );
		final ArrayList< TimePoint > tps = new ArrayList< TimePoint >( this.timepoints.values() );
		this.timepointsOrdered = Collections.unmodifiableList( Entity.sortById( tps ) );
	}

	protected void setTimePoints( final Map< Integer, TimePoint > timepoints )
	{
		this.timepoints = Collections.unmodifiableMap( new HashMap< Integer, TimePoint >( timepoints ) );
		final ArrayList< TimePoint > tps = new ArrayList< TimePoint >( this.timepoints.values() );
		this.timepointsOrdered = Collections.unmodifiableList( Entity.sortById( tps ) );
	}

	protected TimePoints()
	{}
}
