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
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
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
