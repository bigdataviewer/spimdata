/*-
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
package mpicbg.spim.data.sequence;

import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessible;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.Converter;
import net.imglib2.converter.Converters;
import net.imglib2.converter.RealTypeConverters;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;

/**
 * Helpers to provide images converted to float in {@code SetupImgLoader} and
 * {@code MultiResolutionSetupImgLoader}.
 *
 * @author Tobias Pietzsch
 */
class ConvertFloatUtils
{
	// TODO: Remove when RealTypeConvertes.copyFromTo has multithreading support
	public static void copyFromToMultithreaded(
			final RandomAccessible< ? extends RealType< ? > > source,
			final RandomAccessibleInterval< ? extends RealType< ? > > destination )
	{
		final IntervalView< ? extends RealType< ? > > sourceInterval = Views.interval( source, destination );
		final RealType< ? > s = net.imglib2.util.Util.getTypeFromInterval( sourceInterval );
		final RealType< ? > d = net.imglib2.util.Util.getTypeFromInterval( destination );
		final Converter< RealType< ? >, RealType< ? > > copy = RealTypeConverters.getConverter( s, d );
		LoopBuilder.setImages( sourceInterval, destination ).multiThreaded().forEachPixel( copy::convert );
	}

	/**
	 * normalize img to 0...1 in place
	 */
	public static void normalize( final IterableInterval< FloatType > img )
	{
		final float[] minmax = getMinMax( img );
		final float min = minmax[ 0 ];
		final float max = minmax[ 1 ];
		final float scale = ( float ) ( 1.0 / ( max - min ) );
		for ( final FloatType t : img )
			t.set( ( t.get() - min ) * scale );
	}

	/**
	 * return img normalized to 0...1 as converted view
	 */
	public static RandomAccessibleInterval< FloatType > convertNormalize( final RandomAccessibleInterval< FloatType > img )
	{
		final float[] minmax = getMinMax( Views.iterable( img ) );
		final float min = minmax[ 0 ];
		final float max = minmax[ 1 ];
		final float scale = ( float ) ( 1.0 / ( max - min ) );
		return Converters.convert( img, ( i, o ) -> o.set( ( i.get() - min ) * scale ), new FloatType() );
	}

	private static float[] getMinMax( final IterableInterval< FloatType > img )
	{
		float currentMax = img.firstElement().get();
		float currentMin = currentMax;
		for ( final FloatType t : img )
		{
			final float f = t.get();
			if ( f > currentMax )
				currentMax = f;
			else if ( f < currentMin )
				currentMin = f;
		}

		return new float[] { currentMin, currentMax };
	}
}
