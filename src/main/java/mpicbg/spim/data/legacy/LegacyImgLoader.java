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
package mpicbg.spim.data.legacy;

import mpicbg.spim.data.sequence.SequenceDescription;
import mpicbg.spim.data.sequence.ViewId;
import mpicbg.spim.data.sequence.VoxelDimensions;
import net.imglib2.Dimensions;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.FloatType;

//@Deprecated
public interface LegacyImgLoader< T > extends LegacyBasicImgLoader< T >
{
	/**
	 * Get {@link FloatType} image. If requested, the image is normalized to the
	 * range [0,1].
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the image.
	 * @param normalize
	 *            whether the image should be normalized to [0,1].
	 * @return {@link FloatType} image
	 */
	public RandomAccessibleInterval< FloatType > getFloatImage( ViewId view, boolean normalize );

	/**
	 * Get the size of an image. If possible, load only the meta-data for the
	 * specified view. Note, that this should <em>not</em> get the meta-data from
	 * the {@link SequenceDescription} but pull it from the image file.
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the image size.
	 * @return the image size, or null if it could not be determined.
	 */
	public Dimensions getImageSize( ViewId view );

	/**
	 * Get the voxel size of an image. If possible, load only the meta-data for
	 * the specified view. Note, that this should <em>not</em> get the meta-data
	 * from the {@link SequenceDescription} but pull it from the image file.
	 *
	 * @param view
	 *            timepoint and setup for which to retrieve the voxel size.
	 * @return the voxel size, or null if it could not be determined.
	 */
	public VoxelDimensions getVoxelSize( ViewId view );
}
