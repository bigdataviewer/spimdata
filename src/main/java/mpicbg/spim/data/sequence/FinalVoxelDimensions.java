package mpicbg.spim.data.sequence;


/**
 * An implementation of {@link VoxelDimensions}.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public final class FinalVoxelDimensions implements VoxelDimensions
{
	private final String unit;

	private final double[] dimensions;

	public FinalVoxelDimensions( final String unit, final double... dimensions )
	{
		this.unit = unit;
		this.dimensions = dimensions.clone();
	}

	@Override
	public int numDimensions()
	{
		return dimensions.length;
	}

	@Override
	public String unit()
	{
		return unit;
	}

	@Override
	public void dimensions( final double[] dims )
	{
		for ( int d = 0; d < dims.length; ++d )
			dims[ d ] = this.dimensions[ d ];
	}

	@Override
	public double dimension( final int d )
	{
		return dimensions[ d ];
	}
}
