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
package mpicbg.spim.data.registration;

import java.util.ArrayList;
import java.util.List;

import mpicbg.spim.data.sequence.ViewId;
import net.imglib2.realtransform.AffineTransform3D;

public class ViewRegistration extends ViewId
{
	/**
	 * The affine registration model of this view mapping local into world
	 * coordinates.
	 */
	protected final AffineTransform3D model;

	protected final ArrayList< ViewTransform > transformList;

	/**
	 * Creates a new {@link ViewRegistration} object using the identity transformation
	 *
	 * @param timepointId
	 * @param setupId
	 */
	public ViewRegistration( final int timepointId, final int setupId )
	{
		super( timepointId, setupId );
		model = new AffineTransform3D();
		transformList = new ArrayList< ViewTransform >();
	}

	/**
	 * Creates a new {@link ViewRegistration} object using one
	 * {@link AffineTransform3D} transform. The <code>transform</code> specifies
	 * a general affine transformation from view-local coordinates (pixels) to
	 * global coordinates.
	 *
	 * @param timepointId
	 * @param setupId
	 * @param transform
	 */
	public ViewRegistration( final int timepointId, final int setupId, final AffineTransform3D transform )
	{
		super( timepointId, setupId );
		model = transform.copy();
		transformList = new ArrayList< ViewTransform >();
		transformList.add( new ViewTransformAffine( null, transform ) );
	}

	/**
	 * Creates a new {@link ViewRegistration} object using a list of
	 * {@link ViewTransform} transforms. The affine transformation from
	 * view-local coordinates (pixels) to global coordinates is obtained as the
	 * concatenation of all <code>transforms</code>.
	 *
	 * @param timepointId
	 * @param setupId
	 * @param transforms
	 */
	public ViewRegistration( final int timepointId, final int setupId, final ArrayList< ViewTransform > transforms )
	{
		super( timepointId, setupId );
		model = new AffineTransform3D();
		transformList = new ArrayList< ViewTransform >( transforms );
		updateModel();
	}

	/**
	 * Get the affine registration model of this view mapping local into world
	 * coordinates.
	 *
	 * @return registration model
	 */
	public AffineTransform3D getModel()
	{
		return model;
	}

	/**
	 * Update the {@link #getModel() model} by concatenating all transforms from
	 * the {@link #getTransformList() transform list}.
	 */
	public void updateModel()
	{
		model.identity();
		for ( final ViewTransform t : transformList )
			model.concatenate( t.asAffine3D() );
	}

	/**
	 * Get the list of {@link ViewTransform transforms}. The affine
	 * transformation from view-local coordinates (pixels) to global coordinates
	 * is obtained as the concatenation of the transforms in the list.
	 * <p>
	 * The returned list is modifiable, as well as the transforms it contains.
	 * Modifications to the list or individual transforms should be followed by
	 * {@link #updateModel()} to update the concatenated {@link #getModel()
	 * model}.
	 * <p>
	 * The returned list may be empty, which implies that the overall
	 * {@link #getModel() model} is the identity transform.
	 *
	 * @return list of transforms, may be empty.
	 */
	public List< ViewTransform > getTransformList()
	{
		return transformList;
	}

	/**
	 * Concatenate a transform to the {@link #getTransformList() transform list}
	 * and {@link #updateModel() update} the concatenated model.
	 *
	 * @param transform
	 */
	public void concatenateTransform( final ViewTransform transform )
	{
		transformList.add( transform );
		model.concatenate( transform.asAffine3D() );
	}

	/**
	 * Pre-concatenate a transform to the {@link #getTransformList() transform
	 * list} and {@link #updateModel() update} the concatenated model.
	 *
	 * @param transform
	 */
	public void preconcatenateTransform( final ViewTransform transform )
	{
		transformList.add( 0, transform );
		model.preConcatenate( transform.asAffine3D() );
	}
	
	/**
	 * Reset the {@link ViewRegistration} to the identity transform
	 */
	public void identity()
	{
		model.identity();
		transformList.clear();
	}
}
