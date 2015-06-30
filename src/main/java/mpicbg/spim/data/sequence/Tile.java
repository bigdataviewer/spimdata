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