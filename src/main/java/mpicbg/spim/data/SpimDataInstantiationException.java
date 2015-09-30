package mpicbg.spim.data;

/**
 * This exception is thrown when SpimData entities cannot be constructed (while
 * loading).
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class SpimDataInstantiationException extends SpimDataException
{
	private static final long serialVersionUID = 8461622263661575411L;

	public SpimDataInstantiationException()
	{
		super();
	}

	public SpimDataInstantiationException( final String s )
	{
		super( s );
	}

	public SpimDataInstantiationException( final String s, final Throwable cause )
	{
		super( s, cause );
	}

	public SpimDataInstantiationException( final Throwable cause )
	{
		super( cause );
	}
}
