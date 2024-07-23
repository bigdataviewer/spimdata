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
package mpicbg.spim.data.generic.base;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Something that has a unique id. This class is meant to be derived. Unique id
 * means unique among all objects of the same concrete derived type.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class Entity
{
	/**
	 * The unique id of this entity. Used for example as a key in
	 * {@link Map}s and for cross-referencing in XML files.
	 */
	private int id;

	protected Entity( final int id )
	{
		this.id = id;
	}

	protected Entity()
	{}

	/**
	 * Get the unique id of this entity. This unique id is used for example
	 * as a key in {@link Map}s and for cross-referencing in XML files.
	 *
	 * @return unique id.
	 */
	public int getId()
	{
		return id;
	}

	void setId( final int id )
	{
		this.id = id;
	}


	@Override
	public int hashCode()
	{
		return getId();
	}

	/**
	 * Two {@link Entity}s are equal if they have the same {@link #getId()}.
	 */
	@Override
	public boolean equals( final Object o )
	{
		if ( o == null )
		{
			return false;
		}
		else if ( getClass().isInstance( o ) )
		{
			if ( ( ( Entity ) o ).getId() == getId() )
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
	 * Helper method to sort a list of entities by ascending id.
	 *
	 * @param list
	 *            a list of entities that will be sorted in-place.
	 * @return the sorted list
	 */
	public static < T extends Entity, L extends List< T > > L sortById( final L list )
	{
		Collections.sort( list, new Comparator< Entity >()
		{

			@Override
			public int compare( final Entity o1, final Entity o2 )
			{
				return o1.getId() - o2.getId();
			}
		} );
		return list;
	}

	/**
	 * Helper method to create a id-to-entity map from a collection of entities.
	 *
	 * @param values
	 *            a collection of entities
	 * @return a id-to-entity map
	 */
	public static < T extends Entity > HashMap< Integer, T > idMap( final Collection< T > values )
	{
		final HashMap< Integer, T > map = new HashMap< Integer, T >();
		for ( final T v : values )
			map.put( v.getId(), v );
		return map;
	}
}
