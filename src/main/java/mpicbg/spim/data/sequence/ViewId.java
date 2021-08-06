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

/**
 * Identifies a particular view as a combination of a {@link TimePoint} id and a
 * {@link ViewSetup} id.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class ViewId implements Comparable< ViewId >
{
	/**
	 * The timepoint id.
	 */
	protected int timepoint;

	/**
	 * The setup id.
	 */
	protected int setup;

	public ViewId( final int timepointId, final int setupId )
	{
		timepoint = timepointId;
		setup = setupId;
	}

	/**
	 * Get the timepoint id.
	 *
	 * @return timepoint id
	 */
	public int getTimePointId()
	{
		return timepoint;
	}

	void setTimePointId( final int id )
	{
		timepoint = id;
	}

	/**
	 * Get the setup id.
	 *
	 * @return setup id
	 */
	public int getViewSetupId()
	{
		return setup;
	}

	void setViewSetupId( final int id )
	{
		setup = id;
	}

	/**
	 * Two {@link ViewId} are equal if they have the same
	 * {@link #getTimePointId() timepoint} and {@link #getViewSetupId() setup}
	 * ids.
	 */
	@Override
	public boolean equals( final Object o )
	{
		if ( o == null )
		{
			return false;
		}
		else if ( o instanceof ViewId )
		{
			final ViewId i = ( ViewId ) o;

			if ( i.getTimePointId() == getTimePointId() && i.getViewSetupId() == getViewSetupId() )
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Order by {@link #getTimePointId() timepoint} id, then
	 * {@link #getViewSetupId() setup} id.
	 */
	@Override
	public int compareTo( final ViewId o )
	{
		if ( timepoint == o.timepoint )
			return setup - o.setup;
		else
			return timepoint - o.timepoint;
	}

	@Override
	public int hashCode()
	{
		// some non-colliding hash assuming we have not more that 100000 viewsetups
		return getViewSetupId() + getTimePointId() * 100000;
	}
}
