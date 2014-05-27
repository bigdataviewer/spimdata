package mpicbg.spim.data.registration;

import net.imglib2.realtransform.AffineGet;
import net.imglib2.realtransform.AffineTransform3D;

public interface ViewTransform
{
	/**
	 * Whether this transform has has a {@link #getName()}.
	 *
	 * @return true, if this transform has a name.
	 */
	public boolean hasName();

	/**
	 * Get the name of this transform.
	 * This is serialized to XML and can be used to identify the transformation, e.g., "z scaling".
	 *
	 * @return the name of this transform or null if it is not set.
	 */
	public String getName();

	/**
	 * A representation of this {@link ViewTransform} as a 3D {@link AffineGet}.
	 * This is used to concatenate {@link ViewTransform}s into a single {@link AffineTransform3D}.
	 *
	 * @return 3D affine representation of this transform.
	 */
	public AffineGet asAffine3D();
}
