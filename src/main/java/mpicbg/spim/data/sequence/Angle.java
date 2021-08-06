/*
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2021 BigDataViewer developers.
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
