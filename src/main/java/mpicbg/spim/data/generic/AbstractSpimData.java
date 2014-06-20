package mpicbg.spim.data.generic;

import java.io.File;

import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;
import mpicbg.spim.data.registration.ViewRegistrations;

public class AbstractSpimData< S extends AbstractSequenceDescription< ?, ?, ? > >
{
	/**
	 * Relative paths in the XML should be interpreted with respect to this.
	 */
	private File basePath;

	private S sequenceDescription;

	private ViewRegistrations viewRegistrations;

	public AbstractSpimData( final File basePath, final S sequenceDescription, final ViewRegistrations viewRegistrations )
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

	public S getSequenceDescription()
	{
		return sequenceDescription;
	}

	public ViewRegistrations getViewRegistrations()
	{
		return viewRegistrations;
	}

	public void setBasePath( final File basePath )
	{
		this.basePath = basePath;
	}

	protected void setSequenceDescription( final S sequenceDescription )
	{
		this.sequenceDescription = sequenceDescription;
	}

	protected void setViewRegistrations( final ViewRegistrations viewRegistrations )
	{
		this.viewRegistrations = viewRegistrations;
	}

	protected AbstractSpimData()
	{}
}
