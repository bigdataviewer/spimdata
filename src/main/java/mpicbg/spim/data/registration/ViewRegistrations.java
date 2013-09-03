package mpicbg.spim.data.registration;

import java.util.ArrayList;

/**
 * Only contains a <code>ArrayList&lt;ViewRegistration&gt;</code> currently.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public class ViewRegistrations
{
	protected final ArrayList< ViewRegistration > registrations;

	public ViewRegistrations( final ArrayList< ViewRegistration > registrations )
	{
		this.registrations = registrations;
	}

	public ArrayList< ViewRegistration > getRegistrations()
	{
		return registrations;
	}
}
