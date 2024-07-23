/*
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2024 BigDataViewer developers.
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
package mpicbg.spim.data.legacy;

import java.util.HashMap;

import mpicbg.spim.data.generic.sequence.BasicImgLoader;
import mpicbg.spim.data.generic.sequence.BasicSetupImgLoader;
import mpicbg.spim.data.generic.sequence.ImgLoaderHint;
import mpicbg.spim.data.sequence.ImgLoader;
import mpicbg.spim.data.sequence.SetupImgLoader;
import mpicbg.spim.data.sequence.ViewId;
import mpicbg.spim.data.sequence.VoxelDimensions;
import net.imglib2.Dimensions;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.FloatType;

//@Deprecated
public class LegacyBasicImgLoaderWrapper< T, I extends LegacyBasicImgLoader< T > > implements BasicImgLoader
{
	protected final I legacyImgLoader;

	private final HashMap< Integer, SetupImgLoaderWrapper > setupImgLoaders;

	public LegacyBasicImgLoaderWrapper( final I legacyImgLoader )
	{
		this.legacyImgLoader = legacyImgLoader;
		setupImgLoaders = new HashMap< Integer, SetupImgLoaderWrapper >();
	}

	@Override
	public synchronized SetupImgLoaderWrapper getSetupImgLoader( final int setupId )
	{
		SetupImgLoaderWrapper sil = setupImgLoaders.get( setupId );
		if ( sil == null )
		{
			sil = new SetupImgLoaderWrapper( setupId );
			setupImgLoaders.put( setupId, sil );
		}
		return sil;
	}

	public class SetupImgLoaderWrapper implements BasicSetupImgLoader< T >
	{
		private final int setupId;

		protected SetupImgLoaderWrapper( final int setupId )
		{
			this.setupId = setupId;
		}

		@Override
		public RandomAccessibleInterval< T > getImage( final int timepointId, final ImgLoaderHint... hints )
		{
			return legacyImgLoader.getImage( new ViewId( timepointId, setupId ) );
		}

		@Override
		public T getImageType()
		{
			return legacyImgLoader.getImageType();
		}
	}
}
