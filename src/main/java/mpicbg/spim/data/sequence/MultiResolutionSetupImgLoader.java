/*
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
package mpicbg.spim.data.sequence;

import java.util.Arrays;

import net.imglib2.Dimensions;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.RealTypeConverters;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.util.Util;

import mpicbg.spim.data.generic.sequence.BasicMultiResolutionSetupImgLoader;
import mpicbg.spim.data.generic.sequence.ImgLoaderHint;
import mpicbg.spim.data.generic.sequence.ImgLoaderHints;

/**
 * A {@link SetupImgLoader} providing multiple resolutions of each image. By
 * convention, resolution level <em>0</em> is the full resolution.
 *
 * @param <T>
 *            the pixel type of images provided.
 *
 * @author Tobias Pietzsch
 */
public interface MultiResolutionSetupImgLoader< T > extends BasicMultiResolutionSetupImgLoader< T >, SetupImgLoader< T >
{
	@Override
	public default RandomAccessibleInterval< FloatType > getFloatImage( final int timepointId, final boolean normalize, final ImgLoaderHint... hints )
	{
		return getFloatImage( timepointId, 0, normalize, hints );
	}

	/**
	 * Get image at the specified timepoint and resolution level, converted to
	 * {@link FloatType}. If requested, the image is normalized to the range
	 * [0,1].
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image.
	 * @param level
	 *            resolution level for which to retrieve the image.
	 * @param normalize
	 *            whether the image should be normalized to [0,1].
	 * @param hints
	 *            optional hints regarding how to load the image.
	 * @return {@link FloatType} image
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public default RandomAccessibleInterval< FloatType > getFloatImage( final int timepointId, final int level, final boolean normalize, final ImgLoaderHint... hints )
	{
		final T type = getImageType();
		if ( !( type instanceof RealType ) )
			throw new IllegalArgumentException( "Don't know how to converter image of type " + type.getClass().getSimpleName() + " to FloatType" );

		final RandomAccessibleInterval< T > img = getImage( timepointId, level, hints );

		if ( Arrays.asList( hints ).contains( ImgLoaderHints.LOAD_COMPLETELY ) )
		{
			ImgFactory< FloatType > factory;

			try
			{
				// can throw an UnsupportedOperationException (e.g. by VolatileCachedCellImg)
				factory = Util.getSuitableImgFactory( img, new FloatType() );
			}
			catch ( UnsupportedOperationException e )
			{
				// then do the next best
				factory = new CellImgFactory<>( new FloatType() );
			}

			final Img< FloatType > floatImg = factory.create( img );

			// TODO: replace with multithreaded RealTypeConverters.copyFromTo( ushortImg, floatImg );
			ConvertFloatUtils.copyFromToMultithreaded( ( RandomAccessibleInterval ) img, floatImg );

			if ( normalize )
				// normalize the image to 0...1
				ConvertFloatUtils.normalize( floatImg );

			return floatImg;
		}
		else
		{
			final RandomAccessibleInterval floatImg = RealTypeConverters.convert( ( RandomAccessibleInterval ) img, new FloatType() );
			return normalize ? ConvertFloatUtils.convertNormalize( floatImg ) : floatImg;
		}
	}

	@Override
	public default Dimensions getImageSize( final int timepointId )
	{
		return getImageSize( timepointId, 0 );
	}

	/**
	 * Get the size of an image. If possible, load only the meta-data for the
	 * specified image. Note, that this should <em>not</em> get the meta-data
	 * from the {@link SequenceDescription} but pull it from the image file.
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image size.
	 * @param level
	 *            resolution level for which to retrieve the image size.
	 * @return the image size, or null if it could not be determined.
	 */
	public Dimensions getImageSize( final int timepointId, final int level );
}
