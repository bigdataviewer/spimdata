package mpicbg.spim.data.sequence;

import java.text.ParseException;
import java.util.Collections;
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
		setTimePoints( Collections.unmodifiableMap( map ) );

		if ( pattern == null || "".equals( pattern ) )
			return;
	    for ( final int t : IntegerPattern.parseIntegerString( pattern ) )
	    	map.put( t, new TimePoint( t ) );
	}

	protected TimePointsPattern()
	{}
}
