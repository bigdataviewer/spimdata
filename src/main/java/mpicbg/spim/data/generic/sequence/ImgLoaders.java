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
package mpicbg.spim.data.generic.sequence;

import java.util.HashMap;

import mpicbg.spim.data.SpimDataInstantiationException;

import org.scijava.annotations.Index;
import org.scijava.annotations.IndexItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImgLoaders
{
	private static final Logger LOG = LoggerFactory.getLogger(ImgLoaders.class);

	private static final HashMap< Class< ? extends BasicImgLoader >, String > imgLoaderClass_to_XmlIoClassName = new HashMap< Class< ? extends BasicImgLoader >, String >();

	private static final HashMap< String, String > format_to_XmlIoClassName = new HashMap< String, String >();

	private static boolean buildWasCalled = false;

	private static synchronized void build()
	{
		LOG.info("build: entry, buildWasCalled={}", buildWasCalled);
		if (! buildWasCalled) {
			try {
				final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				final Index<ImgLoaderIo> annotationIndex = Index.load(ImgLoaderIo.class, classLoader);
				for (final IndexItem<ImgLoaderIo> item : annotationIndex) {
					LOG.info("build: mapping format={}, className={}, type={}",
							 item.annotation().format(), item.className(), item.annotation().type());
					format_to_XmlIoClassName.put(item.annotation().format(), item.className());
					imgLoaderClass_to_XmlIoClassName.put(item.annotation().type(), item.className());
				}
			} catch (final Exception e) {
				throw new RuntimeException("problem accessing annotation index", e);
			}
			buildWasCalled = true;
		}
		LOG.info("build: exit");
	}

	public static XmlIoBasicImgLoader< ? > createXmlIoForFormat( final String format ) throws SpimDataInstantiationException
	{
		LOG.info("createXmlIoForFormat: entry, format={}, buildWasCalled={}", format, buildWasCalled);
		if ( !buildWasCalled )
			build();

		final String className = format_to_XmlIoClassName.get( format );
		if ( className == null )
			throw new SpimDataInstantiationException( "could not find " + XmlIoBasicImgLoader.class.getSimpleName() + " implementation for format " + format );

		try
		{
			LOG.info("createXmlIoForFormat: exit");
			return ( XmlIoBasicImgLoader< ? > ) Class.forName( className ).newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "could not create " + XmlIoBasicImgLoader.class.getSimpleName() + " instance " + className, e );
		}
	}

	@SuppressWarnings( "unchecked" )
	public static < T extends BasicImgLoader >
	XmlIoBasicImgLoader< T > createXmlIoForImgLoaderClass( final Class< T > klass ) throws SpimDataInstantiationException
	{
		LOG.info("createXmlIoForImgLoaderClass: entry, klass={}, buildWasCalled={}", klass.getName(), buildWasCalled);
		if ( !buildWasCalled )
			build();

		final String className = imgLoaderClass_to_XmlIoClassName.get( klass );
		if ( className == null )
			throw new SpimDataInstantiationException( "could not find " + XmlIoBasicImgLoader.class.getSimpleName() + " implementation for " + klass.getSimpleName() );

		try
		{
			LOG.info("createXmlIoForImgLoaderClass: exit");
			return ( XmlIoBasicImgLoader< T > ) Class.forName( className ).newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "could not create " + XmlIoBasicImgLoader.class.getSimpleName() + " instance " + className, e );
		}
	}

	/**
	 * Register an {@link XmlIoBasicImgLoader} class manually. This can be used
	 * in case scijava-common is not present or cannot be used for other
	 * reasons.
	 *
	 * For example I had the case where I wanted to use a jar build by Eclipse
	 * for a partially compiling project in Fiji. In this case (because it's
	 * build by eclipse, the annotation index was not present.
	 *
	 * @param xmlIoClass
	 *            the class to register.
	 */
	public static void registerManually( final Class< ? extends XmlIoBasicImgLoader< ? > > xmlIoClass )
	{
		LOG.info("registerManually: entry, xmlIoClass={}", xmlIoClass.getName());
		final ImgLoaderIo annotation = xmlIoClass.getAnnotation( ImgLoaderIo.class );
		if ( annotation != null )
		{
			final String format = annotation.format();
			final Class< ? extends BasicImgLoader > imgLoaderClass = annotation.type();
			LOG.info("registerManually: mapping format={}, className={}, type={}",
					 annotation.format(), xmlIoClass.getName(), annotation.type());
			imgLoaderClass_to_XmlIoClassName.put( imgLoaderClass, xmlIoClass.getName() );
			format_to_XmlIoClassName.put( format, xmlIoClass.getName() );
		}
	}
}
