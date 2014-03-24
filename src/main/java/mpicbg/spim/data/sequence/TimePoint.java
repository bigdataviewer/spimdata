package mpicbg.spim.data.sequence;

// TODO: getter setter, constructor
public class TimePoint implements Comparable< TimePoint >
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
	public int hashCode() { return getId(); }
	
	@Override
	public boolean equals( final Object o )
	{
		if ( o == null )
		{
			return false;
		}
		else if ( o instanceof TimePoint )
		{
			if ( ((TimePoint)o).getId() == getId() )
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public int compareTo( final TimePoint o ) { return id - o.getId(); }
}
