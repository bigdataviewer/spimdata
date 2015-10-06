/*
 * #%L
 * SPIM Data: representation of registered, multi-angle, multi-channel (etc.) image sequences
 * %%
 * Copyright (C) 2013 - 2015 BigDataViewer authors
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

import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATION_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME;

import java.util.ArrayList;

import mpicbg.spim.data.SpimDataInstantiationException;

import org.jdom2.Element;

public class XmlIoViewRegistration
{
	private final XmlIoViewTransform xmlIoViewTransform;

	public XmlIoViewRegistration()
	{
		this.xmlIoViewTransform = new XmlIoViewTransform();
	}

	/**
	 * Get the tag name of the XML element that is written by this class.
	 *
	 * @return the tag name of the XML elements that is written.
	 */
	public String getTag()
	{
		return VIEWREGISTRATION_TAG;
	}

	public Element toXml( final ViewRegistration viewRegistration )
	{
		final Element elem = new Element( VIEWREGISTRATION_TAG );
		elem.setAttribute( VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME, Integer.toString( viewRegistration.getTimePointId() ) );
		elem.setAttribute( VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME, Integer.toString( viewRegistration.getViewSetupId() ) );
		final ArrayList< ViewTransform > transforms = new ArrayList< ViewTransform >( viewRegistration.getTransformList() );
		if ( transforms.isEmpty() )
			transforms.add( new ViewTransformAffine( null, viewRegistration.getModel() ) );
		for ( final ViewTransform t : transforms )
			elem.addContent( xmlIoViewTransform.toXml( t ) );
		return elem;
	}

	public ViewRegistration fromXml( final Element elem ) throws SpimDataInstantiationException
	{
		final int timepointId = Integer.parseInt( elem.getAttributeValue( VIEWREGISTRATION_TIMEPOINT_ATTRIBUTE_NAME ) );
		final int setupId = Integer.parseInt( elem.getAttributeValue( VIEWREGISTRATION_SETUP_ATTRIBUTE_NAME ) );
		final ArrayList< ViewTransform > transforms = new ArrayList< ViewTransform >();
		for ( final Element c : elem.getChildren( xmlIoViewTransform.getTag() ) )
			transforms.add( xmlIoViewTransform.fromXml( c ) );
		return new ViewRegistration( timepointId, setupId, transforms );
	}
}
