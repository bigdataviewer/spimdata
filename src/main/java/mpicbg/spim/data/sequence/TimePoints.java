/*
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2021 BigDataViewer developers.
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
