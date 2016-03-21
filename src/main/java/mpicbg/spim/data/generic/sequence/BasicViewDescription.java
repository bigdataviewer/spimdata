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
package mpicbg.spim.data.generic.sequence;

import java.util.Map;

import mpicbg.spim.data.sequence.MissingViews;
import mpicbg.spim.data.sequence.TimePoint;
import mpicbg.spim.data.sequence.ViewId;

/**
 * TODO
 *
 * @param <V>
 *            ViewSetup type
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class BasicViewDescription< V extends BasicViewSetup > extends ViewId
{
	/**
	 * TODO
	 *
	 * @param timepointId
	 * @param setupId
	 * @param present
	 * @param sequenceDescription
	 */
	public BasicViewDescription(
			final int timepointId,
			final int setupId,
			final boolean present,
			final AbstractSequenceDescription< ? extends V, ?, ? > sequenceDescription )
	{
		super( timepointId, setupId );
		this.present = present;
		this.sequenceDescription = sequenceDescription;
	}

	/**
	 * TODO
	 */
	protected boolean present;

	protected final AbstractSequenceDescription< ? extends V, ?, ? > sequenceDescription;

	public TimePoint getTimePoint()
	{
		return sequenceDescription.getTimePoints().getTimePoints().get( timepoint );
	}

	public V getViewSetup()
	{
		return sequenceDescription.getViewSetups().get( setup );
	}

	/**
	 * TODO
	 *
	 * @return
	 */
	public boolean isPresent()
	{
		return present;
	}

	/**
	 * @param viewDescriptions
	 */
	public static < V extends BasicViewSetup, D extends BasicViewDescription< V > > void markMissingViews( final Map< ViewId, D  > viewDescriptions, final MissingViews missingViews )
	{
		for ( final ViewId viewId : missingViews.getMissingViews() )
		{
			final BasicViewDescription< V > viewDesc = viewDescriptions.get( viewId );
			if ( viewDesc != null )
				viewDesc.setPresent( false );
		}
	}

	protected void setPresent( final boolean present )
	{
		this.present = present;
	}
}
