package mpicbg.spim.data.newstuff;

import mpicbg.spim.data.SequenceDescription;

// TODO: getter setter, constructor
public class TimePoint
{
	/**
	 * This unique id is the index of this {@link TimePoint} in {@link SequenceDescription#setups}.
	 */
	protected final int id;

	protected String name;

	public TimePoint()
	{
		id = 0;
		name = "";
	}
}
