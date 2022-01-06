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

import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATIONS_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATION_TAG;

import java.util.HashMap;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlKeys;
import mpicbg.spim.data.generic.base.XmlIoSingleton;
import mpicbg.spim.data.sequence.ViewId;

import org.jdom2.Element;

public class XmlIoViewRegistrations extends XmlIoSingleton< ViewRegistrations >
{
	protected XmlIoViewTransform xmlViewTransform;

	private final XmlIoViewRegistration xmlIoViewRegistration;

	public XmlIoViewRegistrations()
	{
		super( VIEWREGISTRATIONS_TAG, ViewRegistrations.class );
		this.xmlIoViewRegistration = new XmlIoViewRegistration();

		handledTags.add( VIEWREGISTRATION_TAG );
	}

	/**
	 * Load {@link ViewRegistrations} from the given DOM element.
	 *
	 * @param elem
	 *            a {@value XmlKeys#VIEWREGISTRATIONS_TAG} DOM element.
	 * @throws SpimDataException
	 */
	@Override
	public ViewRegistrations fromXml( final Element elem ) throws SpimDataException
	{
		final ViewRegistrations viewRegistrations = super.fromXml( elem );

		final HashMap< ViewId, ViewRegistration > regs = new HashMap< ViewId, ViewRegistration >();
		for ( final Element c : elem.getChildren( xmlIoViewRegistration.getTag() ) )
		{
			final ViewRegistration vr = xmlIoViewRegistration.fromXml( c );
			regs.put( vr, vr );
		}
		viewRegistrations.setViewRegistrations( regs );

		return viewRegistrations;
	}

	public Element toXml( final ViewRegistrations viewRegistrations )
	{
		final Element elem = super.toXml();

		for ( final ViewRegistration vr : viewRegistrations.getViewRegistrationsOrdered() )
			elem.addContent( xmlIoViewRegistration.toXml( vr ) );

		return elem;
	}
}
