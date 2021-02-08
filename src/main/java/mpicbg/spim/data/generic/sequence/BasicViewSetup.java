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

import java.util.HashMap;
import java.util.Map;

import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.base.ViewSetupAttributes;
import mpicbg.spim.data.sequence.VoxelDimensions;
import net.imglib2.Dimensions;
import net.imglib2.util.Intervals;

/**
 * An view setup is an {@link Entity} that may have a name, image size, and voxel size.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class BasicViewSetup extends Entity
{
	/**
	 * The name of this setup or null if it is not set.
	 */
	private String name;

	/**
	 * The {@link Dimensions} of images from this setup or null if it is not
	 * set.
	 */
	private Dimensions size;

	/**
	 * The {@link VoxelDimensions} of images from this setup or null if it is
	 * not set.
	 */
	private VoxelDimensions voxelSize;

	/**
	 * Map attribute name to attribute entity.
	 *
	 * TODO: private or protected?
	 * TODO: final?
	 * TODO: create in constructor?
	 * TODO: getAttributes() unmodifiable?
	 */
	private Map< String, Entity > attributes;

	public BasicViewSetup( final int id, final String name, final Dimensions size, final VoxelDimensions voxelSize )
	{
		super( id );
		this.name = name;
		this.size = size;
		this.voxelSize = voxelSize;
		this.attributes = new HashMap< String, Entity >();
	}

	/**
	 * Whether this setup has has a {@link #getName()}.
	 *
	 * @return true, if this setup has a name.
	 */
	public boolean hasName()
	{
		return name != null;
	}

	/**
	 * Get the name of this setup.
	 *
	 * @return the name of this setup or null if it is not set.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Whether this setup has has a {@link #getSize()}.
	 *
	 * @return true, if this setup has a size.
	 */
	public boolean hasSize()
	{
		return size != null;
	}

	/**
	 * Get the {@link Dimensions} of images from this setup.
	 *
	 * @return the {@link Dimensions} of images from this setup or null if it
	 *         is not set.
	 */
	public Dimensions getSize()
	{
		return size;
	}

	/**
	 * Whether this setup has has a {@link #getVoxelSize()}.
	 *
	 * @return true, if this setup has a voxel size.
	 */
	public boolean hasVoxelSize()
	{
		return voxelSize != null;
	}

	/**
	 * Get the {@link VoxelDimensions} of images from this setup.
	 *
	 * @return The {@link VoxelDimensions} of images from this setup or null if it is
	 * not set.
	 */
	public VoxelDimensions getVoxelSize()
	{
		return voxelSize;
	}

	public Map< String, Entity > getAttributes()
	{
		return attributes;
	}

	@SuppressWarnings( "unchecked" )
	public < T extends Entity > T getAttribute( final Class< T > attributeClass )
	{
		return ( T ) attributes.get( ViewSetupAttributes.getNameForClass( attributeClass ) );
	}

	public < T extends Entity > void setAttribute( final T attribute )
	{
		attributes.put( ViewSetupAttributes.getNameForClass( attribute.getClass() ), attribute );
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer( "BasicViewSetup{" );
		sb.append( "name='" ).append( name ).append( '\'' );
		sb.append( ", size=" ).append( Intervals.toString( size ) );
		sb.append( ", voxelSize=" ).append( voxelSize );
		sb.append( ", attributes=" ).append( attributes );
		sb.append( '}' );
		return sb.toString();
	}

	protected void setName( final String name )
	{
		this.name = name;
	}

	protected void setSize( final Dimensions size )
	{
		assert size.numDimensions() == 3;
		this.size = size;
	}

	protected void setVoxelSize( final VoxelDimensions voxelSize )
	{
		assert voxelSize.numDimensions() == 3;
		this.voxelSize = voxelSize;
	}

	protected void setAttributes( final Map< String, Entity > attributes )
	{
		this.attributes = attributes;
	}

	protected BasicViewSetup()
	{}
}
