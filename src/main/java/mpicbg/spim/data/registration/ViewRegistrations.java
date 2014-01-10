package mpicbg.spim.data.registration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mpicbg.spim.data.sequence.ViewId;

/**
 * Only contains a <code>ArrayList&lt;ViewRegistration&gt;</code> currently.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com> and Stephan Preibisch <stephan.preibisch@gmx.de>
 */
public class ViewRegistrations
{
	protected final HashMap< ViewId, ViewRegistration > registrations;

	public ViewRegistrations( final HashMap< ViewId, ViewRegistration > registrations )
	{
		this.registrations = registrations;
	}
	
	public ViewRegistration getViewRegistration( final int timepointId, final int setupId )
	{
		return getViewRegistration( new ViewId( timepointId, setupId ) );
	}
	
	public ViewRegistration getViewRegistration( final ViewId viewId )
	{
		return registrations.get( viewId );
	}

	public HashMap< ViewId, ViewRegistration > getRegistrations()
	{
		return registrations;
	}
	
	public ArrayList< ViewRegistration > getOrderedViewRegistrations()
	{
		final ArrayList< ViewRegistration > list = new ArrayList< ViewRegistration >();
		list.addAll( getRegistrations().values() );
		Collections.sort( list );
		return list;
	}
}
