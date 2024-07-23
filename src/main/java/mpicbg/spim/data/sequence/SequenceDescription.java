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
package mpicbg.spim.data.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;

public class SequenceDescription extends AbstractSequenceDescription< ViewSetup, ViewDescription, ImgLoader >
{
	public SequenceDescription( final TimePoints timepoints, final Map< Integer, ViewSetup > setups )
	{
		this( timepoints, setups, null, null );
	}

	public SequenceDescription( final TimePoints timepoints, final Map< Integer, ViewSetup > setups, final ImgLoader imgLoader )
	{
		this( timepoints, setups, imgLoader, null );
	}

	public SequenceDescription( final TimePoints timepoints, final Map< Integer, ViewSetup > setups, final ImgLoader imgLoader, final MissingViews missingViews )
	{
		super( timepoints, setups, imgLoader, missingViews );
	}

	public SequenceDescription( final TimePoints timepoints, final Collection< ViewSetup > setups )
	{
		this( timepoints, Entity.idMap( setups ) );
	}

	public SequenceDescription( final TimePoints timepoints, final Collection< ViewSetup > setups, final ImgLoader imgLoader )
	{
		this( timepoints, Entity.idMap( setups ), imgLoader );
	}

	public SequenceDescription( final TimePoints timepoints, final Collection< ViewSetup > setups, final ImgLoader imgLoader, final MissingViews missingViews )
	{
		super( timepoints, Entity.idMap( setups ), imgLoader, missingViews );
	}

	/**
	 * convenience method. shortcut for
	 * <code>getViewDescriptions().get( new ViewId( timepointId, setupId ) )</code>
	 *
	 * @param timepointId
	 * @param setupId
	 * @return
	 */
	public ViewDescription getViewDescription( final int timepointId, final int setupId )
	{
		return getViewDescriptions().get( new ViewId( timepointId, setupId ) );
	}

	/**
	 * convenience method. shortcut for
	 * <code>getViewDescriptions().get( view )</code>
	 *
	 * @param view
	 * @return
	 */
	public ViewDescription getViewDescription( final ViewId view )
	{
		return getViewDescriptions().get( view );
	}

	/**
	 * Get all {@link Tile}s occurring in any {@link #getViewSetups() setup}.
	 *
	 * @return a map from tile id to {@link Tile}
	 */
	public Map< Integer, Tile > getAllTiles()
	{
		final HashMap< Integer, Tile > tiles = new HashMap< Integer, Tile >();
		for ( final ViewSetup setup : getViewSetups().values() )
		{
			final Tile t = setup.getTile();
			tiles.put( t.getId(), t );
		}
		return tiles;
	}

	/**
	 * Get all {@link Tile}s occurring in any {@link #getViewSetups() setup},
	 * ordered by tile id.
	 *
	 * @return a list of {@link Tile}s, ordered by tile id.
	 */
	public List< Tile > getAllTilesOrdered()
	{
		return Entity.sortById( new ArrayList< Tile >( getAllTiles().values() ) );
	}

	/**
	 * Get all {@link Channel}s occurring in any {@link #getViewSetups() setup}.
	 *
	 * @return a map from channel id to {@link Channel}
	 */
	public Map< Integer, Channel > getAllChannels()
	{
		final HashMap< Integer, Channel > channels = new HashMap< Integer, Channel >();
		for ( final ViewSetup setup : getViewSetups().values() )
		{
			final Channel c = setup.getChannel();
			channels.put( c.getId(), c );
		}
		return channels;
	}

	/**
	 * Get all {@link Channel}s occurring in any {@link #getViewSetups() setup},
	 * ordered by channel id.
	 *
	 * @return a list of {@link Channel}s, ordered by channel id.
	 */
	public List< Channel > getAllChannelsOrdered()
	{
		return Entity.sortById( new ArrayList< Channel >( getAllChannels().values() ) );
	}

	/**
	 * Get all {@link Illumination}s occurring in any {@link #getViewSetups()
	 * setups}.
	 *
	 * @return a map from illumination id to {@link Illumination}
	 */
	public Map< Integer, Illumination > getAllIlluminations()
	{
		final HashMap< Integer, Illumination > illuminations = new HashMap< Integer, Illumination >();
		for ( final ViewSetup setup : getViewSetups().values() )
		{
			final Illumination c = setup.getIllumination();
			illuminations.put( c.getId(), c );
		}
		return illuminations;
	}

	/**
	 * Get all {@link Illumination}s occurring in any {@link #getViewSetups()
	 * setup}, ordered by illumination id.
	 *
	 * @return a list of {@link Illumination}s, ordered by illumination id.
	 */
	public List< Illumination > getAllIlluminationsOrdered()
	{
		return Entity.sortById( new ArrayList< Illumination >( getAllIlluminations().values() ) );
	}

	/**
	 * Get all {@link Angle}s occurring in any {@link #getViewSetups()
	 * setups}.
	 *
	 * @return a map from angle id to {@link Angle}
	 */
	public Map< Integer, Angle > getAllAngles()
	{
		final HashMap< Integer, Angle > angles = new HashMap< Integer, Angle >();
		for ( final ViewSetup setup : getViewSetups().values() )
		{
			final Angle c = setup.getAngle();
			angles.put( c.getId(), c );
		}
		return angles;
	}

	/**
	 * Get all {@link Angle}s occurring in any {@link #getViewSetups()
	 * setup}, ordered by angle id.
	 *
	 * @return a list of {@link Angle}s, ordered by angle id.
	 */
	public List< Angle > getAllAnglesOrdered()
	{
		return Entity.sortById( new ArrayList< Angle >( getAllAngles().values() ) );
	}

	@Override
	protected ViewDescription createViewDescription( final int timepointId, final int setupId )
	{
		return new ViewDescription( timepointId, setupId, true, this );
	}

	protected SequenceDescription()
	{}
}
