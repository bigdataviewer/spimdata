package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.base.NamedEntity;

/**
 * Defines a channel which is part of the ViewSetup.
 *
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class Channel extends NamedEntity implements Comparable< Channel >
{
	/**
	 * Construct a channel with the specified id and name.
	 *
	 * @param id
	 * @param name
	 */
	public Channel( final int id, final String name )
	{
		super( id, name );
	}

	/**
	 * Construct a Channel with the specified id. The {@link #getName() name} is
	 * set to the String representation of the id.
	 *
	 * @param id
	 */
	public Channel( final int id )
	{
		this( id, Integer.toString( id ) );
	}

	/**
	 * Get the unique id of this channel.
	 */
	@Override
	public int getId()
	{
		return super.getId();
	}

	/**
	 * Get the name of this channel.
	 *
	 * The name is used for example to replace it in filenames when opening
	 * individual 3d-stacks (e.g. SPIM_TL20_Angle45_Channel5.tif or
	 * SPIM_TL5_GFP.czi)
	 */
	@Override
	public String getName()
	{
		return super.getName();
	}

	/**
	 * Set the name of this channel.
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
	public int compareTo( final Channel o )
	{
		return getId() - o.getId();
	}

	protected Channel()
	{}
}
