package mpicbg.spim.data;

import java.io.File;

import mpicbg.spim.data.generic.AbstractSpimData;
import mpicbg.spim.data.registration.ViewRegistrations;
import mpicbg.spim.data.sequence.SequenceDescription;

public class SpimData extends AbstractSpimData< SequenceDescription >
{
	public SpimData( final File basePath, final SequenceDescription sequenceDescription, final ViewRegistrations viewRegistrations )
	{
		super( basePath, sequenceDescription, viewRegistrations );
	}

	protected SpimData()
	{}
}
