/*
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2024 BigDataViewer developers.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.sequence.MissingViews;
import mpicbg.spim.data.sequence.TimePoint;
import mpicbg.spim.data.sequence.TimePoints;
import mpicbg.spim.data.sequence.ViewId;

/**
 * A SPIM sequence consisting of a list of timepoints and a list of view setups.
 * Every (timepoint, setup) pair is a view (i.e., an image stack acquired at
 * that time with that setup).
 *
 * @param <V>
 *            ViewSetup type
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public abstract class AbstractSequenceDescription< V extends BasicViewSetup, D extends BasicViewDescription< V >, L extends BasicImgLoader >
{
	/**
	 * Contains all time-points.
	 */
	private TimePoints timepoints;

	/**
	 * Maps setup id to setup.
	 */
	private Map< Integer, ? extends V > setups;

	/**
	 * Provides images for views (timepoint, setup pairs). May be null.
	 */
	private L imgLoader;

	/**
	 * Missing views (timepoint, setup pairs). May be null;
	 */
	private MissingViews missingViews;

	/**
	 * links ViewId to the ViewDescriptions
	 */
	private Map< ViewId, D > viewDescriptions;

	private boolean viewDescriptionsDirty;

	/**
	 * List of {@link BasicViewSetup} ordered by id
	 */
	private List< V > viewSetupsOrdered;

	private boolean viewSetupsOrderedDirty;

	public AbstractSequenceDescription( final TimePoints timepoints, final Map< Integer, ? extends V > setups, final L imgLoader, final MissingViews missingViews )
	{
		this.timepoints = timepoints;
		this.setups = setups;
		this.imgLoader = imgLoader;
		this.missingViews = missingViews;
		viewDescriptionsDirty = true;
		viewSetupsOrderedDirty = true;
	}

	public TimePoints getTimePoints()
	{
		return timepoints;
	}

	public Map< Integer, ? extends V > getViewSetups()
	{
		return setups;
	}

	public List< V > getViewSetupsOrdered()
	{
		if ( viewSetupsOrderedDirty )
		{
			final ArrayList< V > list = new ArrayList< V >();
			for ( final V setup : setups.values() )
				list.add( setup );
			viewSetupsOrdered = Entity.sortById( list );
			viewSetupsOrderedDirty = false;
		}
		return viewSetupsOrdered;
	}

	public L getImgLoader()
	{
		return imgLoader;
	}

	public MissingViews getMissingViews()
	{
		return missingViews;
	}

	public Map< ViewId, D > getViewDescriptions()
	{
		createViewDescriptions();
		return viewDescriptions;
	}

	protected void setTimePoints( final TimePoints timepoints )
	{
		if ( timepoints != this.timepoints )
			viewDescriptionsDirty = true;
		this.timepoints = timepoints;
	}

	protected void setViewSetups( final Map< Integer, ? extends V > setups )
	{
		if ( setups != this.setups )
		{
			viewDescriptionsDirty = true;
			viewSetupsOrderedDirty = true;
		}
		this.setups = setups;
	}

	public void setImgLoader( final L imgLoader )
	{
		this.imgLoader = imgLoader;
	}

	protected void setMissingViews( final MissingViews missingViews )
	{
		if ( missingViews != this.missingViews )
			viewDescriptionsDirty = true;
		this.missingViews = missingViews;
	}

	protected void createViewDescriptions()
	{
		if ( viewDescriptionsDirty )
		{
			viewDescriptionsDirty = false;
			viewDescriptions = new HashMap< ViewId, D >();
			for ( final TimePoint t : timepoints.getTimePoints().values() )
			{
				for ( final V s : setups.values() )
				{
					final D d = createViewDescription( t.getId(), s.getId() );
					viewDescriptions.put( d, d );
				}
			}
			if ( missingViews != null )
				BasicViewDescription.markMissingViews( viewDescriptions, missingViews );
		}
	}

	protected abstract D createViewDescription( final int timepointId, final int setupId );

	protected AbstractSequenceDescription()
	{}
}
