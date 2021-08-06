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
