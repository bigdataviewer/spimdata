package mpicbg.spim.data.sequence;

import net.imglib2.EuclideanSpace;

public interface VoxelDimensions extends EuclideanSpace
{
	/**
	 * Get the unit in which dimensions are specified.
	 */
	String unit();

	/**
	 * Write the size of a voxel in each dimension into double[].
	 *
	 * @param dimensions
	 */
	public void dimensions( double[] dimensions );

	/**
	 * Get the size of a voxel in a given dimension <em>d</em>.
	 *
	 * @param d
	 */
	public double dimension( int d );
}
