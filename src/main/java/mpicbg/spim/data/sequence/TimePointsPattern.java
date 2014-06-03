package mpicbg.spim.data.sequence;

import java.text.ParseException;
import java.util.HashMap;



public class TimePointsPattern extends TimePoints
{
	private String pattern;

	public TimePointsPattern( final String pattern ) throws ParseException
	{
		setPattern( pattern );
	}

	public String getPattern()
	{
		return pattern;
	}

	protected void setPattern( final String pattern ) throws ParseException
	{
		this.pattern = pattern;
		final HashMap< Integer, TimePoint > map = new HashMap< Integer, TimePoint >();

		if ( pattern == null || "".equals( pattern ) )
		{
			// set an empty list
			setTimePoints( map );
			return;
		}

	    for ( final int t : IntegerPattern.parseIntegerString( pattern ) )
	    	map.put( t, new TimePoint( t ) );

	    // set a full list (this copies the list)
		setTimePoints( map );
	}

	protected TimePointsPattern()
	{}
}
