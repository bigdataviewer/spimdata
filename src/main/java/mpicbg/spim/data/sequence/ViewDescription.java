package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;
import mpicbg.spim.data.generic.sequence.BasicViewDescription;

public class ViewDescription extends BasicViewDescription< ViewSetup >
{
	public ViewDescription(
			final int timepointId,
			final int setupId,
			final boolean present,
			final AbstractSequenceDescription< ? extends ViewSetup, ?, ? > sequenceDescription )
	{
		super( timepointId, setupId, present, sequenceDescription );
	}
}
