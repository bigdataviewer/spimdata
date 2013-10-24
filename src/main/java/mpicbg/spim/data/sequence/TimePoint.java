package mpicbg.spim.data.sequence;

// TODO: getter setter, constructor
public class TimePoint
{
	/**
	 * This unique id is the index of this {@link TimePoint} in
	 * {@link SequenceDescription#timepoints}.
	 */
	protected final int id;

	/**
	 * The name of the timepoint. This could be used to construct filenames, for
	 * example.
	 */
	protected String name;

	public TimePoint( final int id, final String name )
	{
		this.id = id;
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName( final String name )
	{
		this.name = name;
	}
	
	@Override
	public int hashCode() 
	{
		return getId();
	}
}
