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
package mpicbg.spim.data.sequence;

import java.util.Collection;
import java.util.HashMap;

/**
 * Helper methods for dealing with {@link ViewSetup} collections.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class ViewSetups
{
	public static HashMap< Integer, Channel > getAllChannels( final Collection< ? extends ViewSetup > setups )
	{
		final HashMap< Integer, Channel > channels = new HashMap< Integer, Channel >();

		for ( final ViewSetup setup : setups )
		{
			final Channel c = setup.getChannel();
			channels.put( c.getId(), c );
		}

		return channels;
	}

	public static HashMap< Integer, Angle > getAllAngles( final Collection< ? extends ViewSetup > setups )
	{
		final HashMap< Integer, Angle > angles = new HashMap< Integer, Angle >();

		for ( final ViewSetup setup : setups )
		{
			final Angle a = setup.getAngle();
			angles.put( a.getId(), a );
		}

		return angles;
	}

	public static HashMap< Integer, Illumination > getAllIlluminations( final Collection< ? extends ViewSetup > setups )
	{
		final HashMap< Integer, Illumination > illuminations = new HashMap< Integer, Illumination >();

		for ( final ViewSetup setup : setups )
		{
			final Illumination i = setup.getIllumination();
			illuminations.put( i.getId(), i );
		}

		return illuminations;
	}
}
