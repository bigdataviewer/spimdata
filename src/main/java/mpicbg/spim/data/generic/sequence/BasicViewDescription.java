package mpicbg.spim.data.generic.sequence;

import java.util.Map;

import mpicbg.spim.data.sequence.MissingViews;
import mpicbg.spim.data.sequence.TimePoint;
import mpicbg.spim.data.sequence.ViewId;

/**
 * TODO
 *
 * @param <V>
 *            ViewSetup type
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class BasicViewDescription< V extends BasicViewSetup > extends ViewId
{
	/**
	 * TODO
	 *
	 * @param timepointId
	 * @param setupId
	 * @param present
	 * @param sequenceDescription
	 */
	public BasicViewDescription(
			final int timepointId,
			final int setupId,
			final boolean present,
			final AbstractSequenceDescription< ? extends V, ?, ? > sequenceDescription )
	{
		super( timepointId, setupId );
		this.present = present;
		this.sequenceDescription = sequenceDescription;
	}

	/**
	 * TODO
	 */
	protected boolean present;

	protected final AbstractSequenceDescription< ? extends V, ?, ? > sequenceDescription;

	public TimePoint getTimePoint()
	{
		return sequenceDescription.getTimePoints().getTimePoints().get( timepoint );
	}

	public V getViewSetup()
	{
		return sequenceDescription.getViewSetups().get( setup );
	}

	/**
	 * TODO
	 *
	 * @return
	 */
	public boolean isPresent()
	{
		return present;
	}

	/**
	 * @param viewDescriptions
	 * @return ordered
	 */
	public static < V extends BasicViewSetup, D extends BasicViewDescription< V > > void markMissingViews( final Map< ViewId, D  > viewDescriptions, final MissingViews missingViews )
	{
		for ( final ViewId viewId : missingViews.getMissingViews() )
		{
			final BasicViewDescription< V > viewDesc = viewDescriptions.get( viewId );
			if ( viewDesc != null )
				viewDesc.setPresent( false );
		}
	}

	protected void setPresent( final boolean present )
	{
		this.present = present;
	}
}
