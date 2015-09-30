package mpicbg.spim.data.sequence;

import mpicbg.spim.data.generic.base.NamedEntity;

/**
 * Defines an angle which is part of the ViewSetup. An {@link Angle} is a
 * {@link NamedEntity} that may have a rotation axis and angle.
 *
 * @author Stephan Preibisch (stephan.preibisch@gmx.de)
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class Angle extends NamedEntity implements Comparable< Angle >
{
	/**
	 * The approximate rotation axis from the microscope meta-data (if available,
	 * otherwise null)
	 */
	private double[] rotationAxis;

	/**
	 * The approximate rotation angle from the microscope meta-data (if
	 * available, otherwise NaN)
	 */
	private double rotationAngle;

	public Angle( final int id, final String name, final double rotationAngleDegrees, final double[] rotationAxis )
	{
		super( id, name );
		this.rotationAngle = rotationAngleDegrees;
		this.rotationAxis = rotationAxis;
	}

	public Angle( final int id, final String name )
	{
		this( id, name, Double.NaN, null );
	}

	public Angle( final int id )
	{
		this( id, Integer.toString( id ) );
	}

	/**
	 * Get the unique id of this angle.
	 */
	@Override
	public int getId()
	{
		return super.getId();
	}

	/**
	 * Get the name of this angle.
	 *
	 * The name is used for example to replace it in filenames when
	 * opening individual 3d-stacks (e.g. SPIM_TL20_Angle45.tif)
	 */
	@Override
	public String getName()
	{
		return super.getName();
	}

	/**
	 * Set the name of this angle.
	 */
	@Override
	public void setName( final String name )
	{
		super.setName( name );
	}

	/**
	 * Is the approximate rotation defined?
	 *
	 * @return true, if the approximate rotation is defined.
	 */
	public boolean hasRotation()
	{
		return rotationAxis != null;
	}

	/**
	 * Get the the approximate rotation axis (as defined by the microscope meta-data).
	 *
	 * @return the rotation axis or null if rotation is undefined.
	 */
	public double[] getRotationAxis()
	{
		return rotationAxis;
	}

	/**
	 * Get the approximate rotation angle (as defined by the microscope meta-data).
	 *
	 * @return the rotation angle in degrees or {@link Double#NaN} if rotation is undefined.
	 */
	public double getRotationAngleDegrees()
	{
		return rotationAngle;
	}

	/**
	 * Set the approximate rotation angle and axis (as defined by the microscope meta-data).
	 * @param axis
	 * @param degrees
	 */
	public void setRotation( final double[] axis, final double degrees )
	{
		final boolean isRotationDefined = degrees != Double.NaN && axis != null;
		rotationAngle = isRotationDefined ? degrees : Double.NaN;
		rotationAxis = isRotationDefined ? axis : null;
	}

	/**
	 * Compares the {@link #getId() ids}.
	 */
	@Override
	public int compareTo( final Angle o )
	{
		return getId() - o.getId();
	}

	protected Angle()
	{}
}
