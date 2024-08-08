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
package mpicbg.spim.data.zarr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.scijava.annotations.Index;
import org.scijava.annotations.IndexItem;

import mpicbg.spim.data.SpimDataInstantiationException;
import mpicbg.spim.data.generic.base.Entity;

class JsonViewSetupAttributeIos
{
	private static final Map< Class< ? extends Entity >, String > attributeClass_to_JsonIoClassName = new ConcurrentHashMap<>();

	private static final Map< String, String > attributeName_to_JsonIoClassName = new ConcurrentHashMap<>();

	private static boolean buildWasCalled = false;

	private static synchronized void build()
	{
		if ( !buildWasCalled )
		{
			try
			{
				final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				final Index< JsonViewSetupAttributeIo > annotationIndex = Index.load( JsonViewSetupAttributeIo.class, classLoader );
				for ( final IndexItem< JsonViewSetupAttributeIo > item : annotationIndex )
				{
					attributeName_to_JsonIoClassName.put( item.annotation().name(), item.className() );
					attributeClass_to_JsonIoClassName.put( item.annotation().type(), item.className() );
				}
			}
			catch ( final Exception e )
			{
				throw new RuntimeException( "problem accessing annotation index", e );
			}
			buildWasCalled = true;
		}
	}

	@SuppressWarnings( "unchecked" )
	public static < T extends Entity >
	JsonIoViewSetupAttribute< T > createJsonIoForAttributeName( final String name ) throws SpimDataInstantiationException
	{
		if ( !buildWasCalled )
			build();

		final String className = attributeName_to_JsonIoClassName.get( name );
		if ( className == null )
			throw new SpimDataInstantiationException( "could not find " + JsonIoViewSetupAttribute.class.getSimpleName() + " implementation for format " + name );

		try
		{
			return ( JsonIoViewSetupAttribute< T > ) Class.forName( className ).newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "could not create " + JsonIoViewSetupAttribute.class.getSimpleName() + " instance " + className, e );
		}
	}

	@SuppressWarnings( "unchecked" )
	public static < T extends Entity >
	JsonIoViewSetupAttribute< T > createJsonIoForAttributeClass( final Class< T > klass ) throws SpimDataInstantiationException
	{
		if ( !buildWasCalled )
			build();

		final String className = attributeClass_to_JsonIoClassName.get( klass );
		if ( className == null )
			throw new SpimDataInstantiationException( "could not find " + JsonIoViewSetupAttribute.class.getSimpleName() + " implementation for " + klass.getSimpleName() );

		try
		{
			return ( JsonIoViewSetupAttribute< T > ) Class.forName( className ).newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "could not create " + JsonIoViewSetupAttribute.class.getSimpleName() + " instance " + className, e );
		}
	}
}
