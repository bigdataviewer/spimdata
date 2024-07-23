/*
 * #%L
 * SPIM Data: registered, multi-angle, multi-channel etc. image sequences.
 * %%
 * Copyright (C) 2013 - 2024 BigDataViewer developers.
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

import static mpicbg.spim.data.XmlKeys.MISSINGVIEWS_TAG;
import static mpicbg.spim.data.XmlKeys.MISSINGVIEW_SETUP_ATTRIBUTE_NAME;
import static mpicbg.spim.data.XmlKeys.MISSINGVIEW_TAG;
import static mpicbg.spim.data.XmlKeys.MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.generic.base.XmlIoSingleton;

import org.jdom2.Element;

public class XmlIoMissingViews extends XmlIoSingleton< MissingViews >
{
	public XmlIoMissingViews()
	{
		super( MISSINGVIEWS_TAG, MissingViews.class );
		handledTags.add( MISSINGVIEW_TAG );
	}

	public Element toXml( final MissingViews missingViews )
	{
		final Element elem = super.toXml();

		final ArrayList< ViewId > list = new ArrayList< ViewId >( missingViews.getMissingViews() );
		Collections.sort( list );
		for ( final ViewId v : list )
		{
			final Element child = new Element( MISSINGVIEW_TAG );
			child.setAttribute( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME, Integer.toString( v.getTimePointId() ) );
			child.setAttribute( MISSINGVIEW_SETUP_ATTRIBUTE_NAME, Integer.toString( v.getViewSetupId() ) );
			elem.addContent( child );
		}

		return elem;
	}

	@Override
	public MissingViews fromXml( final Element elem ) throws SpimDataException
	{
		final MissingViews missingViews = super.fromXml( elem );

		final HashSet< ViewId > views = new HashSet< ViewId >();
		for ( final Element c : elem.getChildren( MISSINGVIEW_TAG ) )
		{
			final int t = Integer.parseInt( c.getAttributeValue( MISSINGVIEW_TIMEPOINT_ATTRIBUTE_NAME ) );
			final int s = Integer.parseInt( c.getAttributeValue( MISSINGVIEW_SETUP_ATTRIBUTE_NAME ) );
			views.add( new ViewId( t, s ) );
		}
		missingViews.setMissingViews( views );

		return missingViews;
	}
}
