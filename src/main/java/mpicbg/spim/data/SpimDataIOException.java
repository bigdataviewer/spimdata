package mpicbg.spim.data;

/**
 * This exception is thrown when an IO error occurs while reading or writing an
 * XML file.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class SpimDataIOException extends SpimDataException
{
	private static final long serialVersionUID = -7306608713023295089L;

	public SpimDataIOException()
	{
		super();
	}

	public SpimDataIOException( final String s )
	{
		super( s );
	}

	public SpimDataIOException( final String s, final Throwable cause )
	{
		super( s, cause );
	}

	public SpimDataIOException( final Throwable cause )
	{
		super( cause );
	}
}
