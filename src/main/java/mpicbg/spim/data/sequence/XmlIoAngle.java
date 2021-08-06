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

import static mpicbg.spim.data.XmlKeys.ANGLE_ROTATIONAXIS_TAG;
import static mpicbg.spim.data.XmlKeys.ANGLE_ROTATIONDEGREES_TAG;
import static mpicbg.spim.data.XmlKeys.ANGLE_TAG;
import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.generic.base.ViewSetupAttributeIo;
import mpicbg.spim.data.generic.base.XmlIoNamedEntity;

import org.jdom2.Element;

@ViewSetupAttributeIo( name = "angle", type = Angle.class )
public class XmlIoAngle extends XmlIoNamedEntity< Angle >
{
	public XmlIoAngle()
	{
		super( ANGLE_TAG, Angle.class );
		handledTags.add( ANGLE_ROTATIONAXIS_TAG );
		handledTags.add( ANGLE_ROTATIONDEGREES_TAG );
	}

	@Override
	public Element toXml( final Angle angle )
	{
		final Element elem = super.toXml( angle );
		if ( angle.hasRotation() )
		{
			elem.addContent( XmlHelpers.doubleArrayElement( ANGLE_ROTATIONAXIS_TAG, angle.getRotationAxis() ) );
			elem.addContent( XmlHelpers.doubleElement( ANGLE_ROTATIONDEGREES_TAG, angle.getRotationAngleDegrees() ) );
		}
		return elem;
	}

	@Override
	public Angle fromXml( final Element elem ) throws SpimDataException
	{
		final Angle angle = super.fromXml( elem );
		final double[] axis = XmlHelpers.getDoubleArray( elem, ANGLE_ROTATIONAXIS_TAG, null );
		final double degrees = XmlHelpers.getDouble( elem, ANGLE_ROTATIONDEGREES_TAG, Double.NaN );
		angle.setRotation( axis, degrees );
		return angle;
	}
}
