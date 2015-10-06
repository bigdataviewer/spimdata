/*
 * #%L
 * SPIM Data: representation of registered, multi-angle, multi-channel (etc.) image sequences
 * %%
 * Copyright (C) 2013 - 2015 BigDataViewer authors
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
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
			// this timeseries has just one timepoint
			map.put( 0, new TimePoint( 0 ) );
		}
		else
		{
			// parse all timepoints
			for ( final int t : IntegerPattern.parseIntegerString( pattern ) )
				map.put( t, new TimePoint( t ) );
		}

		setTimePoints( map );
	}

	protected TimePointsPattern()
	{}
}
