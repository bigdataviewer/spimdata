package mpicbg.spim.data.generic.base;

/**
 * An {@link Entity} that has a name.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class NamedEntity extends Entity
{
	private String name;

	public NamedEntity( final int id, final String name )
	{
		super( id );
		this.name = name;
	}

	protected NamedEntity()
	{
	}

	public String getName()
	{
		return name;
	}

	protected void setName( final String name )
	{
		this.name = name;
	}
}
