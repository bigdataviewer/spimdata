/*-
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2022 BigDataViewer developers.
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

import mpicbg.spim.data.generic.base.NamedEntity;

/**
 * Defines a tile that is part of the ViewSetup. An {@link Tile} is a
 * {@link NamedEntity} that may have a location in space
 *
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 */
public class Tile extends NamedEntity implements Comparable< Tile >
{
	/**
	 * The approximate location from the microscope meta-data (if available,
	 * otherwise null)
	 */
	private double[] location;

	public Tile( final int id, final String name, final double[] location )
	{
		super( id, name );
		this.location = location;
	}

	public Tile( final int id, final String name )
	{
		this( id, name, null );
	}

	public Tile( final int id )
	{
		this( id, Integer.toString( id ) );
	}

	/**
	 * Get the unique id of this location.
	 */
	@Override
	public int getId()
	{
		return super.getId();
	}

	/**
	 * Get the name of this tile.
	 *
	 * The name is used for example to replace it in filenames when
	 * opening individual 3d-stacks (e.g. SPIM_TL20_Tile1_Angle45.tif)
	 */
	@Override
	public String getName()
	{
		return super.getName();
	}

	/**
	 * Set the name of this tile.
	 */
	@Override
	public void setName( final String name )
	{
		super.setName( name );
	}

	/**
	 * Get the the approximate location (as defined by the microscope meta-data).
	 *
	 * @return the location or null if location is undefined.
	 */
	public double[] getLocation()
	{
		return location;
	}

	/**
	 * Set the approximate location (as defined by the microscope meta-data).
	 * @param location
	 */
	public void setLocation( final double[] location )
	{
		this.location = location;
	}

	/**
	 * Is the locations defined?
	 *
	 * @return true, if the location is defined.
	 */
	public boolean hasLocation()
	{
		return location != null;
	}
	/**
	 * Compares the {@link #getId() ids}.
	 */
	@Override
	public int compareTo( final Tile o )
	{
		return getId() - o.getId();
	}

	protected Tile()
	{}
}
