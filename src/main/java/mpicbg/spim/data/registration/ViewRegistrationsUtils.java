package mpicbg.spim.data.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mpicbg.spim.data.sequence.ViewId;
import mpicbg.spim.data.sequence.ViewIdUtils;

public class ViewRegistrationsUtils extends ViewIdUtils
{
	public static void changeViewsetupIds( final ViewRegistrations viewRegistrations, final Map< Integer, Integer > oldIdToNewIdMap )
	{
		final ArrayList< ViewRegistration > list = new ArrayList< ViewRegistration >( viewRegistrations.getViewRegistrations().values() );
		ViewIdUtils.changeViewsetupIds( list, oldIdToNewIdMap );
		final HashMap< ViewId, ViewRegistration > regs = new HashMap< ViewId, ViewRegistration >();
		for ( final ViewRegistration vr : list )
			regs.put( vr, vr );
		viewRegistrations.setViewRegistrations( regs );
	}

	public static void changeViewsetupIds( final ViewRegistrations viewRegistrations, final int... oldIdToNewIdMap )
	{
		assert ( oldIdToNewIdMap.length % 2 == 0 );
		final Map< Integer, Integer > map = new HashMap< Integer, Integer >();
		for ( int i = 0; i < oldIdToNewIdMap.length; i += 2 )
			map.put( oldIdToNewIdMap[ i ], oldIdToNewIdMap[ i + 1 ] );
		changeViewsetupIds( viewRegistrations, map );
	}
}
