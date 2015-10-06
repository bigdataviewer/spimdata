/*
 * #%L
 * SPIM Data: representation of registered, multi-angle, multi-channel (etc.) image sequences
 * %%
 * Copyright (C) 2013 - 2015 BigDataViewer authors
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
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
