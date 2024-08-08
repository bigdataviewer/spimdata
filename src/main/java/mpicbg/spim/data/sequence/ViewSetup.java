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

import java.util.Map;
import java.util.Optional;

import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.base.ViewSetupAttributes;
import mpicbg.spim.data.generic.sequence.BasicViewSetup;
import net.imglib2.Dimensions;

/**
 * A collection of parameters describing the setup for a particular stack coming
 * from a SPIM microscope (angle, illumination direction, etc). A
 * {@link ViewSetup} is a {@link BasicViewSetup} that must have a channel,
 * angle, and illumination direction.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 */
public class ViewSetup extends BasicViewSetup implements Comparable< ViewSetup >
{
	private Tile tile;

	private Channel channel;

	private Angle angle;

	private Illumination illumination;

	protected static String tileAttributeKey = ViewSetupAttributes.getNameForClass( Tile.class );

	protected static String channelAttributeKey = ViewSetupAttributes.getNameForClass( Channel.class );

	protected static String angleAttributeKey = ViewSetupAttributes.getNameForClass( Angle.class );

	protected static String illuminationAttributeKey = ViewSetupAttributes.getNameForClass( Illumination.class );

	/**
	 * TODO
	 *
	 * @param id
	 * @param name
	 * @param size
	 * @param voxelSize
	 * @param channel
	 * @param angle
	 * @param illumination
	 */
	public ViewSetup(
			final int id,
			final String name,
			final Dimensions size,
			final VoxelDimensions voxelSize,
			final Tile tile,
			final Channel channel,
			final Angle angle,
			final Illumination illumination )
	{
		super( id, name, size, voxelSize );
		setChannel( channel );
		setAngle( angle );
		setIllumination( illumination );
		setTile( tile );
	}

	/**
	 * TODO
	 *
	 * @param id
	 * @param name
	 * @param size
	 * @param voxelSize
	 * @param channel
	 * @param angle
	 * @param illumination
	 */
	public ViewSetup(
			final int id,
			final String name,
			final Dimensions size,
			final VoxelDimensions voxelSize,
			final Channel channel,
			final Angle angle,
			final Illumination illumination )
	{
		this( id, name, size, voxelSize, new Tile( 0 ), channel, angle, illumination );
	}

	public ViewSetup(
			final int id,
			final String name,
			final Dimensions size,
			final VoxelDimensions voxelSize,
			final Map< String, Entity > attributes )
	{
		super( id, name, size, voxelSize );
		setAttributes( attributes );
	}

	/**
	 * Get the unique id of this {@link ViewSetup}.
	 *
	 * @return unique id.
	 */
	@Override
	public int getId()
	{
		return super.getId();
	}

	/**
	 * Whether this setup has has a {@link #getName()}.
	 *
	 * @return true, if this setup has a name.
	 */
	@Override
	public boolean hasName()
	{
		return super.hasName();
	}

	/**
	 * Get the name of this setup.
	 *
	 * @return the name of this setup or null if it is not set.
	 */
	@Override
	public String getName()
	{
		return super.getName();
	}

	/**
	 * Whether this setup has has a {@link #getSize()}.
	 *
	 * @return true, if this setup has a size.
	 */
	@Override
	public boolean hasSize()
	{
		return super.hasSize();
	}

	/**
	 * Get the {@link Dimensions} of images from this setup.
	 *
	 * @return the {@link Dimensions} of images from this setup or null if it
	 *         is not set.
	 */
	@Override
	public Dimensions getSize()
	{
		return super.getSize();
	}

	/**
	 * Set the {@link Dimensions} of images from this setup.
	 */
	@Override
	public void setSize( final Dimensions size )
	{
		super.setSize( size );
	}

	/**
	 * Whether this setup has has a {@link #getVoxelSize()}.
	 *
	 * @return true, if this setup has a voxel size.
	 */
	@Override
	public boolean hasVoxelSize()
	{
		return super.hasVoxelSize();
	}

	/**
	 * Get the {@link VoxelDimensions} of images from this setup.
	 *
	 * @return The {@link VoxelDimensions} of images from this setup or null if it is
	 * not set.
	 */
	@Override
	public VoxelDimensions getVoxelSize()
	{
		return super.getVoxelSize();
	}

	/**
	 * Set the {@link VoxelDimensions} of images from this setup.
	 */
	@Override
	public void setVoxelSize( final VoxelDimensions voxelSize )
	{
		super.setVoxelSize( voxelSize );
	}

	/**
	 * Get the {@link Tile}.
	 *
	 * @return the tile
	 */
	public Tile getTile()
	{
		return tile;
	}

	/**
	 * Set the {@link Tile}.
	 *
	 * @param tile
	 *            the tile
	 */
	protected void setTile( final Tile tile )
	{
		this.tile = tile;
		getAttributes().put( tileAttributeKey, tile );
	}

	/**
	 * Get the {@link Channel}.
	 *
	 * @return the channel
	 */
	public Channel getChannel()
	{
		return channel;
	}

	/**
	 * Set the {@link Channel}.
	 *
	 * @param channel
	 *            the channel
	 */
	protected void setChannel( final Channel channel )
	{
		this.channel = channel;
		getAttributes().put( channelAttributeKey, channel );
	}

	/**
	 * Get stage rotation {@link Angle}.
	 *
	 * @return the angle.
	 */
	public Angle getAngle()
	{
		return angle;
	}

	/**
	 * Set the rotation {@link Angle}
	 *
	 * @param angle
	 *            the angle.
	 */
	protected void setAngle( final Angle angle )
	{
		this.angle = angle;
		getAttributes().put( angleAttributeKey, angle );
	}

	/**
	 * Get the {@link Illumination} direction.
	 *
	 * @return the illumination direction
	 */
	public Illumination getIllumination()
	{
		return illumination;
	}

	/**
	 * Set the {@link Illumination} direction.
	 *
	 * @param illumination the illumination direction
	 */
	protected void setIllumination( final Illumination illumination )
	{
		this.illumination = illumination;
		getAttributes().put( illuminationAttributeKey, illumination );
	}

	/**
	 * Compares the {@link #getId() ids}.
	 */
	@Override
	public int compareTo( final ViewSetup o )
	{
		return getId() - o.getId();
	}

	/**
	 * Set the attributes map. And set the individual angle, channel, and
	 * illumination fields.
	 */
	@Override
	protected void setAttributes( final Map< String, Entity > attributes )
	{
		super.setAttributes( attributes );

		tile = ( Tile ) attributes.get( tileAttributeKey );
		channel = ( Channel ) attributes.get( channelAttributeKey );
		angle = ( Angle ) attributes.get( angleAttributeKey );
		illumination = ( Illumination ) attributes.get( illuminationAttributeKey );

		// if any attribute is not present, set it to a default
		if ( getTile() == null )
			setTile( new Tile( 0 ) );

		if ( getChannel() == null )
			setChannel( new Channel( 0 ) );

		if ( getAngle() == null )
			setAngle( new Angle( 0 ) );

		if ( getIllumination() == null )
			setIllumination( new Illumination( 0 ) );
	}

	@Override
	public < T extends Entity > void setAttribute( final T attribute )
	{
		if ( attribute instanceof Tile )
			setTile( tile );
		else if ( attribute instanceof Channel )
			setChannel( channel );
		else if ( attribute instanceof Angle )
			setAngle( angle );
		else if ( attribute instanceof Illumination )
			setIllumination( illumination );
		else
			super.setAttribute( attribute );
	}

	ViewSetup()
	{}
}
