/*-
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2022 BigDataViewer developers.
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
package mpicbg.spim.data.generic.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpicbg.spim.data.generic.base.EntityUtils;

public class AbstractSequenceDescriptionUtils
{
	public static < V extends BasicViewSetup > void changeIds(
			final AbstractSequenceDescription< V, ?, ? > seq,
			final Map< Integer, Integer > oldIdToNewIdMap )
	{
		final List< V > setups = new ArrayList< V >( seq.getViewSetupsOrdered() );
		EntityUtils.changeIds( setups, oldIdToNewIdMap );
		final HashMap< Integer, V > setupsMap = new HashMap< Integer, V >();
		for ( final V setup : setups )
			setupsMap.put( setup.getId(), setup );
		seq.setViewSetups( setupsMap );
	}

	public static < V extends BasicViewSetup > void changeIds(
			final AbstractSequenceDescription< V, ?, ? > seq,
			final int... oldIdToNewIdMap )
	{
		assert ( oldIdToNewIdMap.length % 2 == 0 );
		final Map< Integer, Integer > map = new HashMap< Integer, Integer >();
		for ( int i = 0; i < oldIdToNewIdMap.length; i += 2 )
			map.put( oldIdToNewIdMap[ i ], oldIdToNewIdMap[ i + 1 ] );
		changeIds( seq, map );
	}
}
