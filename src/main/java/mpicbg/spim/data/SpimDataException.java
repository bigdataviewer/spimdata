package mpicbg.spim.data;

public class SpimDataException extends Exception
{
	private static final long serialVersionUID = 8461622263661575411L;

	public SpimDataException()
	{
		super();
	}

	public SpimDataException( final String s )
	{
		super( s );
	}

	public SpimDataException( final String s, final Throwable cause )
	{
		super( s, cause );
	}

	public SpimDataException( final Throwable cause )
	{
		super( cause );
	}
}
