package mpicbg.spim.data.registration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpicbg.spim.data.sequence.ViewId;

/**
 * Only contains a {@link Map} from {@link ViewId} to {@link ViewRegistration}
 * currently.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 * @author Stephan Preibisch &lt;stephan.preibisch@gmx.de&gt;
 */
public class ViewRegistrations
{
	private Map< ViewId, ViewRegistration > registrations;

	public ViewRegistrations( final Map< ViewId, ViewRegistration > registrations )
	{
		this.registrations = registrations;
	}

	public ViewRegistrations( final Collection< ViewRegistration > registrations )
	{
		this.registrations = new HashMap< ViewId, ViewRegistration >();
		for ( final ViewRegistration reg : registrations )
			this.registrations.put( reg, reg );
	}

	public Map< ViewId, ViewRegistration > getViewRegistrations()
	{
		return registrations;
	}

	public List< ViewRegistration > getViewRegistrationsOrdered()
	{
		final ArrayList< ViewRegistration > list = new ArrayList< ViewRegistration >( registrations.values() );
		Collections.sort( list );
		return list;
	}

	public ViewRegistration getViewRegistration( final int timepointId, final int setupId )
	{
		return getViewRegistration( new ViewId( timepointId, setupId ) );
	}

	public ViewRegistration getViewRegistration( final ViewId viewId )
	{
		return registrations.get( viewId );
	}

	protected void setViewRegistrations( final Map< ViewId, ViewRegistration > registrations )
	{
		this.registrations = registrations;
	}

	protected ViewRegistrations()
	{}
}
