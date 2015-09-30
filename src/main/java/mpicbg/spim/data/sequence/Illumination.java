package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.base.NamedEntity;

/**
 * Defines an illumination direction which is part of the ViewSetup.
 *
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class Illumination extends NamedEntity implements Comparable< Illumination >
{
	/**
	 * Construct an illumination direction with the specified id and name.
	 *
	 * @param id
	 * @param name
	 */
	public Illumination( final int id, final String name )
	{
		super( id, name );
	}

	/**
	 * Construct an illumination direction with the specified id. The
	 * {@link #getName() name} is set to the String representation of the id.
	 *
	 * @param id
	 */
	public Illumination( final int id )
	{
		this( id, Integer.toString( id ) );
	}

	/**
	 * Get the unique id of this illumination direction.
	 */
	@Override
	public int getId()
	{
		return super.getId();
	}

	/**
	 * Get the name of this illumination direction.
	 *
	 * The name is used for example to replace it in filenames when opening
	 * individual 3d-stacks (e.g. SPIM_TL20_Angle45_Illumination5.tif or
	 * SPIM_TL5_GFP.czi)
	 */
	@Override
	public String getName()
	{
		return super.getName();
	}

	/**
	 * Set the name of this illumination direction.
	 */
	@Override
	public void setName( final String name )
	{
		super.setName( name );
	}

	/**
	 * Compares the {@link #getId() ids}.
	 */
	@Override
	public int compareTo( final Illumination o )
	{
		return getId() - o.getId();
	}

	protected Illumination()
	{}
}
