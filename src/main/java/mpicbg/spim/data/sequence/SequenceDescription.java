package mpicbg.spim.data.sequence;

import java.util.Collection;
import java.util.Map;

import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;

public class SequenceDescription extends AbstractSequenceDescription< ViewSetup, ViewDescription, ImgLoader< ? > >
{
	public SequenceDescription( final TimePoints timepoints, final Map< Integer, ViewSetup > setups )
	{
		this( timepoints, setups, null, null );
	}

	public SequenceDescription( final TimePoints timepoints, final Map< Integer, ViewSetup > setups, final ImgLoader< ? > imgLoader )
	{
		this( timepoints, setups, imgLoader, null );
	}

	public SequenceDescription( final TimePoints timepoints, final Map< Integer, ViewSetup > setups, final ImgLoader< ? > imgLoader, final MissingViews missingViews )
	{
		super( timepoints, setups, imgLoader, missingViews );
	}

	public SequenceDescription( final TimePoints timepoints, final Collection< ViewSetup > setups )
	{
		this( timepoints, Entity.idMap( setups ) );
	}

	public SequenceDescription( final TimePoints timepoints, final Collection< ViewSetup > setups, final ImgLoader< ? > imgLoader )
	{
		this( timepoints, Entity.idMap( setups ), imgLoader );
	}

	public SequenceDescription( final TimePoints timepoints, final Collection< ViewSetup > setups, final ImgLoader< ? > imgLoader, final MissingViews missingViews )
	{
		super( timepoints, Entity.idMap( setups ), imgLoader, missingViews );
	}

	@Override
	protected ViewDescription createViewDescription( final int timepointId, final int setupId )
	{
		return new ViewDescription( timepointId, setupId, true, this );
	}

	protected SequenceDescription()
	{}
}
