package mpicbg.spim.data.newstuff;


public class ViewDescription< T extends TimePoint, V extends ViewSetup >
{
	/**
	 * The timepoint index.
	 */
	protected final int timepoint;

	/**
	 * The setup index (within the timepoint).
	 */
	protected final int setup;

	/**
	 * TODO
	 */
	protected final boolean present;

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

	public boolean isPresent()
	{
		return present;
	}

	public V getViewSetup()
	{
		return null;
	}

	public T getTimepoint()
	{
		return null;
	}

	public ViewDescription()
	{
		timepoint = 0;
		setup = 0;
		present = true;
	}
}
