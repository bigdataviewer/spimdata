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
package mpicbg.spim.data.sequence;

import java.util.Arrays;

/**
 * A default implementation of {@link VoxelDimensions} with
 * spacing of one for all dimensions and units of "pixel"s.
 *
 * @author John Bogovic &lt;bogovicj@janelia.hhmi.org&gt;
 */
public class DefaultVoxelDimensions implements VoxelDimensions
{
	private final int numDimensions;

	public DefaultVoxelDimensions( int numDimensions )
	{
		this.numDimensions = numDimensions;
	}

	@Override
	public int numDimensions()
	{
		return numDimensions;
	}

	@Override
	public String unit()
	{
		return "pixel";
	}

	/**
	 * Sets all entries to one.
	 */
	@Override
	public void dimensions( final double[] dims )
	{
		for ( int d = 0; d < dims.length; ++d )
			dims[ d ] = 1;
	}

	/**
	 * Always returns one.
	 *
	 * @return 1
	 */
	@Override
	public double dimension(int d)
	{
		return 1;
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer( this.getClass().getSimpleName() );
		sb.append( "{unit='" ).append( unit() ).append( '\'' );
		sb.append( ", dimensions=" ).append( Arrays.toString( dimensionsAsDoubleArray() ) );
		sb.append( '}' );
		return sb.toString();
	}
}
