package mpicbg.spim.data.registration;

import net.imglib2.realtransform.AffineGet;
import net.imglib2.realtransform.AffineTransform3D;

public class ViewTransformAffine implements ViewTransform
{
	protected final String name;

	protected final AffineTransform3D affine;

	/**
	 * @param name may be null
	 * @param affine
	 */
	public ViewTransformAffine( final String name, final AffineTransform3D affine )
	{
		this.name = name;
		this.affine = affine;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public AffineGet asAffine3D()
	{
		return affine;
	}

	@Override
	public boolean hasName()
	{
		return name != null;
	}
}
