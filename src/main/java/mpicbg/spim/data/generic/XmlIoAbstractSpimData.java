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
package mpicbg.spim.data.generic;

import static mpicbg.spim.data.XmlKeys.BASEPATH_TAG;
import static mpicbg.spim.data.XmlKeys.SPIMDATA_TAG;
import static mpicbg.spim.data.XmlKeys.SPIMDATA_VERSION_ATTRIBUTE_CURRENT;
import static mpicbg.spim.data.XmlKeys.SPIMDATA_VERSION_ATTRIBUTE_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.SpimDataIOException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.XmlKeys;
import mpicbg.spim.data.generic.base.XmlIoSingleton;
import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;
import mpicbg.spim.data.generic.sequence.XmlIoAbstractSequenceDescription;
import mpicbg.spim.data.registration.XmlIoViewRegistrations;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlIoAbstractSpimData< S extends AbstractSequenceDescription< ?, ?, ? >, T extends AbstractSpimData< S > > extends XmlIoSingleton< T >
{
	private final XmlIoAbstractSequenceDescription< ?, S > xmlIoSequenceDescription;

	private final XmlIoViewRegistrations xmlIoViewRegistrations;

	public XmlIoAbstractSpimData( final Class< T > klass,
			final XmlIoAbstractSequenceDescription< ?, S > xmlIoSequenceDescription,
			final XmlIoViewRegistrations xmlIoViewRegistrations )
	{
		super( XmlKeys.SPIMDATA_TAG, klass );
		this.xmlIoSequenceDescription = xmlIoSequenceDescription;
		this.xmlIoViewRegistrations = xmlIoViewRegistrations;

		handledTags.add( xmlIoSequenceDescription.getTag() );
		handledTags.add( xmlIoViewRegistrations.getTag() );
		handledTags.add( BASEPATH_TAG );
	}

	public T load( final String xmlFilename ) throws SpimDataException
	{
		final SAXBuilder sax = new SAXBuilder();
		Document doc;
		try
		{
			final File file = new File( xmlFilename );
			if ( file.exists() )
				doc = sax.build( file );
			else
				doc = sax.build( xmlFilename );
		}
		catch ( final Exception e )
		{
			throw new SpimDataIOException( e );
		}
		final Element root = doc.getRootElement();

		if ( root.getName() != SPIMDATA_TAG )
			throw new RuntimeException( "expected <" + SPIMDATA_TAG + "> root element. wrong file?" );

		return fromXml( root, new File( xmlFilename ) );
	}

	public void save( final T spimData, final String xmlFilename ) throws SpimDataException
	{
		final File xmlFileDirectory = new File( xmlFilename ).getParentFile();
		final Document doc = new Document( toXml( spimData, xmlFileDirectory ) );
		final XMLOutputter xout = new XMLOutputter( Format.getPrettyFormat() );
		try ( FileOutputStream outputStream = new FileOutputStream( xmlFilename ) )
		{
			xout.output( doc, outputStream );
		}
		catch ( final IOException e )
		{
			throw new SpimDataIOException( e );
		}
	}

	/**
	 * TODO
	 *
	 * @param root
	 * @return
	 */
	public String getVersion( final Element root )
	{
		final String versionAttr = root.getAttributeValue( SPIMDATA_VERSION_ATTRIBUTE_NAME );
		final String version;
		if ( versionAttr.isEmpty() )
		{
			System.out.println( "<" + SPIMDATA_TAG + "> does not specify \"" + SPIMDATA_VERSION_ATTRIBUTE_NAME + "\" attribute." );
			System.out.println( "assuming \"" + SPIMDATA_VERSION_ATTRIBUTE_CURRENT + "\"" );
			version = SPIMDATA_VERSION_ATTRIBUTE_CURRENT;
		}
		else
			version = versionAttr;
		return version;
	}

	/**
	 * TODO
	 *
	 * @param root
	 * @param xmlFile
	 * @return
	 */
	public T fromXml( final Element root, final File xmlFile ) throws SpimDataException
	{
		final T spimData = super.fromXml( root );

//		String version = getVersion( root );

		final File basePath = loadBasePath( root, xmlFile );
		spimData.setBasePath( basePath );

		Element elem = root.getChild( xmlIoSequenceDescription.getTag() );
		if ( elem == null )
			throw new SpimDataIOException( "no <" + xmlIoSequenceDescription.getTag() + "> element found." );
		spimData.setSequenceDescription( xmlIoSequenceDescription.fromXml( elem, basePath ) );

		elem = root.getChild( xmlIoViewRegistrations.getTag() );
		if ( elem == null )
			throw new SpimDataIOException( "no <" + xmlIoViewRegistrations.getTag() + "> element found." );
		spimData.setViewRegistrations( xmlIoViewRegistrations.fromXml( elem ) );

		return spimData;
	}

	protected File loadBasePath( final Element root, final File xmlFile )
	{
		File xmlFileParentDirectory = xmlFile.getParentFile();
		if ( xmlFileParentDirectory == null )
			xmlFileParentDirectory = new File( "." );
		return XmlHelpers.loadPath( root, BASEPATH_TAG, ".", xmlFileParentDirectory );
	}

	/**
	 * TODO
	 *
	 * @param spimData
	 * @param xmlFileDirectory
	 * @return
	 * @throws SpimDataException
	 */
	public Element toXml( final T spimData, final File xmlFileDirectory ) throws SpimDataException
	{
		final Element root = super.toXml();
		root.setAttribute( SPIMDATA_VERSION_ATTRIBUTE_NAME, SPIMDATA_VERSION_ATTRIBUTE_CURRENT );
		root.addContent( XmlHelpers.pathElement( "BasePath", spimData.getBasePath(), xmlFileDirectory ) );
		root.addContent( xmlIoSequenceDescription.toXml( spimData.getSequenceDescription(), spimData.getBasePath() ) );
		root.addContent( xmlIoViewRegistrations.toXml( spimData.getViewRegistrations() ) );
		return root;
	}
}
