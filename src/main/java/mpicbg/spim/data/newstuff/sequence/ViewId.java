package mpicbg.spim.data.newstuff.sequence;


/**
 * Identifies a particular view as a combination of a {@link TimePoint} id and a
 * {@link ViewSetup} id.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class ViewId implements Comparable< ViewId >
{
	/**
	 * The timepoint id (index).
	 */
	protected final int timepoint;

	/**
	 * The setup id (index within the timepoint).
	 */
	protected final int setup;

	public ViewId( final int timepointId, final int setupId )
	{
		timepoint = timepointId;
		setup = setupId;
	}

	/**
	 * Get the timepoint id (index).
	 *
	 * @return timepoint id
	 */
	public int getTimepointId()
	{
		return timepoint;
	}

	/**
	 * Get the setup id (index within the timepoint).
	 *
	 * @return setup id
	 */
	public int getViewSetupId()
	{
		return setup;
	}

	@Override
	public int compareTo( final ViewId o )
	{
		if ( timepoint == o.timepoint )
			return setup - o.setup;
		else
			return timepoint - o.timepoint;
	}
}