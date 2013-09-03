package mpicbg.spim.data;

import java.io.File;

import mpicbg.spim.data.registration.ViewRegistrations;
import mpicbg.spim.data.sequence.SequenceDescription;
import mpicbg.spim.data.sequence.TimePoint;
import mpicbg.spim.data.sequence.ViewSetup;

public class SpimData< T extends TimePoint, V extends ViewSetup >
{
	/**
	 * Relative paths in the XML should be interpreted with respect to this.
	 */
	protected final File basePath;

	protected final SequenceDescription< T, V > sequenceDescription;

	protected final ViewRegistrations viewRegistrations;

	public SpimData( final File basePath, final SequenceDescription< T, V > sequenceDescription, final ViewRegistrations viewRegistrations )
	{
		this.basePath = basePath;
		this.sequenceDescription = sequenceDescription;
		this.viewRegistrations = viewRegistrations;
	}

	/**
	 * Get the base path of the sequence. Relative paths in the XML sequence
	 * description are interpreted with respect to this.
	 *
	 * @return the base path of the sequence
	 */
	public File getBasePath()
	{
		return basePath;
	}

	public SequenceDescription< T, V > getSequenceDescription()
	{
		return sequenceDescription;
	}

	public ViewRegistrations getViewRegistrations()
	{
		return viewRegistrations;
	}
}
