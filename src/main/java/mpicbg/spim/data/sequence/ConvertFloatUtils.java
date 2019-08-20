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
