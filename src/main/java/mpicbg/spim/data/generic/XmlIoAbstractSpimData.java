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
package mpicbg.spim.data.generic;

import static mpicbg.spim.data.XmlKeys.BASEPATH_TAG;
import static mpicbg.spim.data.XmlKeys.SPIMDATA_TAG;
import static mpicbg.spim.data.XmlKeys.SPIMDATA_VERSION_ATTRIBUTE_CURRENT;
import static mpicbg.spim.data.XmlKeys.SPIMDATA_VERSION_ATTRIBUTE_NAME;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.SpimDataIOException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.XmlKeys;
import mpicbg.spim.data.generic.base.XmlIoSingleton;
import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;
import mpicbg.spim.data.generic.sequence.XmlIoAbstractSequenceDescription;
import mpicbg.spim.data.registration.XmlIoViewRegistrations;

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
		try
		{
			final File file = new File( xmlFilename );
			final BufferedReader reader = new BufferedReader( new FileReader( file ) );
			return load( reader, file.toURI() );
		}
		catch ( final IOException e )
		{
			throw new SpimDataIOException( e );
		}
	}

	/**
	 * Load SpimData from the given {@code Reader} (that can read from a local
	 * file, or S3 for example). The {@code xmlURI} is the URI that the {@code
	 * xmlReader} reads from, and it is used to construct the base path URI.
	 */
	public T load( final Reader xmlReader, final URI xmlURI ) throws SpimDataException
	{
		final SAXBuilder sax = new SAXBuilder();
		Document doc;
		try
		{
			doc = sax.build( xmlReader );
		}
		catch ( final Exception e )
		{
			throw new SpimDataIOException( e );
		}
		final Element root = doc.getRootElement();

		if ( !Objects.equals( root.getName(), SPIMDATA_TAG ) )
			throw new RuntimeException( "expected <" + SPIMDATA_TAG + "> root element. wrong file?" );

		return fromXml( root, xmlURI );
	}

	public void save( final T spimData, final String xmlFilename ) throws SpimDataException
	{
		try ( FileWriter writer = new FileWriter( xmlFilename ) )
		{
			save( spimData, writer, new File( xmlFilename ).toURI() );
		}
		catch ( final IOException e )
		{
			throw new SpimDataIOException( e );
		}
	}

	/**
	 * Write {@code spimData} to the given {@code Writer} (that can write to a
	 * local file, or S3 for example). The {@code xmlURI} is the URI that the
	 * {@code xmlWriter} writes to, and it is used to construct the base path
	 * URI.
	 */
	public void save( final T spimData, final Writer xmlWriter, final URI xmlURI ) throws SpimDataException
	{
		final URI basePathURI = getParent( xmlURI );
		final Document doc = new Document( toXml( spimData, basePathURI ) );
		final XMLOutputter xout = new XMLOutputter( Format.getPrettyFormat() );
		try
		{
			xout.output( doc, xmlWriter );
		}
		catch ( final IOException e )
		{
			throw new SpimDataIOException( e );
		}
	}

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

	public T fromXml( final Element root, final File xmlFile ) throws SpimDataException
	{
		return fromXml(  root, xmlFile.toURI() );
	}

	public T fromXml( final Element root, final URI xmlURI ) throws SpimDataException
	{
		final T spimData = super.fromXml( root );

//		String version = getVersion( root );

		final URI basePathURI = loadBasePathURI( root, xmlURI );
		spimData.setBasePathURI( basePathURI );

		Element elem = root.getChild( xmlIoSequenceDescription.getTag() );
		if ( elem == null )
			throw new SpimDataIOException( "no <" + xmlIoSequenceDescription.getTag() + "> element found." );
		spimData.setSequenceDescription( xmlIoSequenceDescription.fromXml( elem, basePathURI ) );

		elem = root.getChild( xmlIoViewRegistrations.getTag() );
		if ( elem == null )
			throw new SpimDataIOException( "no <" + xmlIoViewRegistrations.getTag() + "> element found." );
		spimData.setViewRegistrations( xmlIoViewRegistrations.fromXml( elem ) );

		return spimData;
	}

	protected URI loadBasePathURI( final Element root, final URI xmlURI ) throws SpimDataIOException
	{
		final URI parent = getParent( xmlURI );
		return XmlHelpers.loadPathURI( root, BASEPATH_TAG, ".", parent );
	}

	/**
	 * Gets a URI's parent, e.g. the containing directory of a file.
	 * <p>
	 * This function behaves differently than the invocation
	 * {@code uri.resolve("..")} when the URI ends in a trailing slash:
	 * </p>
	 * <pre>{@code
	 * jshell> new URI("file:/foo/bar/").resolve("..")
	 * $1 ==> file:/foo/
	 *
	 * jshell> new URI("file:/foo/bar").resolve("..")
	 * $2 ==> file:/
	 * }</pre>
	 * <p>
	 * Whereas this function returns "file:/foo/" in both cases.
	 */
	private static URI getParent( final URI uri ) throws SpimDataIOException
	{
		try
		{
			final String uriPath = uri.getPath();
			final int parentSlash = uriPath.lastIndexOf( "/", uriPath.length() - 2 );
			if ( parentSlash < 0 )
			{
				throw new SpimDataIOException( "URI is already at the root" );
			}
			// NB: The "+ 1" below is *very important*, so that the resultant URI
			// ends in a trailing slash. The behaviour of URI differs depending on
			// whether this trailing slash is present; specifically:
			//
			// * new URI("file:/foo/bar/").resolve(".") -> "file:/foo/bar/"
			// * new URI("file:/foo/bar").resolve(".") -> "file:/foo/"
			//
			// That is: /foo/bar/ is considered to be in the directory /foo/bar,
			// whereas /foo/bar is considered to be in the directory /foo.
			final String parentPath = uriPath.substring( 0, parentSlash + 1 );
			return new URI( uri.getScheme(), uri.getUserInfo(), uri.getHost(),
				uri.getPort(), parentPath, uri.getQuery(), uri.getFragment() );
		}
		catch ( URISyntaxException e )
		{
			throw new SpimDataIOException( e );
		}
	}

	public Element toXml( final T spimData, final File xmlFileDirectory ) throws SpimDataException
	{
		return toXml( spimData, xmlFileDirectory.toURI() );
	}

	public Element toXml( final T spimData, final URI xmlParentURI ) throws SpimDataException
	{
		final Element root = super.toXml();
		root.setAttribute( SPIMDATA_VERSION_ATTRIBUTE_NAME, SPIMDATA_VERSION_ATTRIBUTE_CURRENT );
		root.addContent( XmlHelpers.pathElementURI( "BasePath", spimData.getBasePathURI(), xmlParentURI ) );
		root.addContent( xmlIoSequenceDescription.toXml( spimData.getSequenceDescription(), spimData.getBasePathURI() ) );
		root.addContent( xmlIoViewRegistrations.toXml( spimData.getViewRegistrations() ) );
		return root;
	}

}
