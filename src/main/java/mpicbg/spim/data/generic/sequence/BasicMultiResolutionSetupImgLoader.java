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
package mpicbg.spim.data.generic.sequence;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.realtransform.AffineTransform3D;

/**
 * A {@link BasicSetupImgLoader} providing multiple resolutions of each image. By
 * convention, resolution level <em>0</em> is the full resolution.
 *
 * @param <T>
 *            the pixel type of images provided.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public interface BasicMultiResolutionSetupImgLoader< T > extends BasicSetupImgLoader< T >
{
	/**
	 * Get the image at the specified timepoint and resolution level.
	 *
	 * <p>
	 * The returned image has pixel type {@code T} (an instance of {@code T} may
	 * be obtained by {@link #getImageType()}).
	 *
	 * @param timepointId
	 *            timepoint for which to retrieve the image.
	 * @param level
	 *            resolution level for which to retrieve the image.
	 * @param hints
	 *            optional hints regarding how to load the image.
	 * @return image of type T.
	 */
	RandomAccessibleInterval< T > getImage( int timepointId, final int level, ImgLoaderHint... hints );

	/**
	 * Get the sub-sampling factors, indexed by resolution level and dimension.
	 * For example, a sub-sampling factor of 2 means the respective resolution
	 * level is scaled by 0.5 in the respective dimension.
	 *
	 * @return sub-sampling factors, indexed by resolution level and dimension.
	 */
	double[][] getMipmapResolutions();

	/**
	 * Get the transformation from coordinates of the sub-sampled image of a a
	 * resolution level to coordinates of the full resolution image. The array
	 * of transforms is indexed by resolution level.
	 *
	 * @return array with one transformation for each mipmap level.
	 */
	AffineTransform3D[] getMipmapTransforms();

	/**
	 * Get number of resolution levels.
	 *
	 * @return number of resolution levels.
	 */
	int numMipmapLevels();
}
