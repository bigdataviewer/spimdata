package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.base.Entity;

public class TimePoint extends Entity
{
	public TimePoint( final int id )
	{
		super( id );
	}

	public String getName()
	{
		return Integer.toString( getId() );
	}

	protected TimePoint()
	{}
}
