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
package mpicbg.spim.data.generic.sequence;

import static mpicbg.spim.data.XmlKeys.IMGLOADER_FORMAT_ATTRIBUTE_NAME;
import static mpicbg.spim.data.XmlKeys.IMGLOADER_TAG;
import static mpicbg.spim.data.XmlKeys.SEQUENCEDESCRIPTION_TAG;

import java.io.File;
import java.util.HashMap;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.generic.base.XmlIoEntityMap;
import mpicbg.spim.data.generic.base.XmlIoSingleton;
import mpicbg.spim.data.sequence.MissingViews;
import mpicbg.spim.data.sequence.TimePoints;
import mpicbg.spim.data.sequence.XmlIoMissingViews;
import mpicbg.spim.data.sequence.XmlIoTimePoints;

import org.jdom2.Element;

public class XmlIoAbstractSequenceDescription< V extends BasicViewSetup, T extends AbstractSequenceDescription< V, ?, ? > > extends XmlIoSingleton< T >
{
	private final XmlIoTimePoints xmlIoTimePoints;
	private final XmlIoEntityMap< V > xmlIoViewSetups;
	private final XmlIoMissingViews xmlIoMissingViews;

	public XmlIoAbstractSequenceDescription( final Class< T > klass,
			final XmlIoTimePoints xmlIoTimePoints,
			final XmlIoEntityMap< V > xmlIoViewSetups,
			final XmlIoMissingViews xmlIoMissingViews )
	{
		super( SEQUENCEDESCRIPTION_TAG, klass );
		this.xmlIoTimePoints = xmlIoTimePoints;
		this.xmlIoViewSetups = xmlIoViewSetups;
		this.xmlIoMissingViews = xmlIoMissingViews;

		handledTags.add( xmlIoTimePoints.getTag() );
		handledTags.add( xmlIoViewSetups.getTag() );
		handledTags.add( xmlIoMissingViews.getTag() );
		handledTags.add( IMGLOADER_TAG );
	}

	public Element toXml( final T sequenceDescription, final File basePath ) throws SpimDataException
	{
		final Element elem = super.toXml();

		final BasicImgLoader imgLoader = sequenceDescription.getImgLoader();
		if ( imgLoader != null )
		{
			final XmlIoBasicImgLoader< ? > imgLoaderIo = ImgLoaders.createXmlIoForImgLoaderClass( imgLoader.getClass() );
			elem.addContent( createImgLoaderElement( imgLoaderIo, imgLoader, basePath ) );
		}

		elem.addContent( xmlIoViewSetups.toXml( sequenceDescription.getViewSetups() ) );

		elem.addContent( xmlIoTimePoints.toXml( sequenceDescription.getTimePoints() ) );

		if ( sequenceDescription.getMissingViews() != null )
			elem.addContent( xmlIoMissingViews.toXml( sequenceDescription.getMissingViews() ) );

		return elem;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public T fromXml( final Element elem, final File basePath ) throws SpimDataException
	{
		final T sequenceDescription = super.fromXml( elem );

		final HashMap< Integer, V > setups = xmlIoViewSetups.fromXml( elem.getChild( xmlIoViewSetups.getTag() ) );
		sequenceDescription.setViewSetups( setups );

		final TimePoints timepoints = xmlIoTimePoints.fromXml( elem.getChild( xmlIoTimePoints.getTag() ) );
		sequenceDescription.setTimePoints( timepoints );

		final Element missingViewElem = elem.getChild( xmlIoMissingViews.getTag() );
		final MissingViews missingViews = ( missingViewElem == null ) ? null : xmlIoMissingViews.fromXml( missingViewElem );
		sequenceDescription.setMissingViews( missingViews );

		final Element imgLoaderElem = elem.getChild( IMGLOADER_TAG );
		if ( imgLoaderElem != null )
		{
			final String format = imgLoaderElem.getAttributeValue( IMGLOADER_FORMAT_ATTRIBUTE_NAME );
			final XmlIoBasicImgLoader< ? > imgLoaderIo = ImgLoaders.createXmlIoForFormat( format );
			final BasicImgLoader imgLoader = imgLoaderIo.fromXml( imgLoaderElem, basePath, sequenceDescription );
			setImgLoader( ( AbstractSequenceDescription ) sequenceDescription, imgLoader );
		}
		else
			sequenceDescription.setImgLoader( null );

		return sequenceDescription;
	}

	/**
	 * Casting madness.
	 */
	@SuppressWarnings( "unchecked" )
	private static < L extends BasicImgLoader > Element createImgLoaderElement( final XmlIoBasicImgLoader< L > imgLoaderIo, final BasicImgLoader imgLoader, final File basePath )
	{
		return imgLoaderIo.toXml( ( L ) imgLoader, basePath );
	}

	/**
	 * Casting madness.
	 */
	@SuppressWarnings( "unchecked" )
	private static < L extends BasicImgLoader, T extends AbstractSequenceDescription< ?, ?, L > > void setImgLoader( final T sequenceDescription, final BasicImgLoader imgLoader )
	{
		sequenceDescription.setImgLoader( ( L ) imgLoader );
	}
}
