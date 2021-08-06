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
package mpicbg.spim.data.generic.base;

import java.util.HashMap;

import org.scijava.annotations.Index;
import org.scijava.annotations.IndexItem;

public class ViewSetupAttributes
{
	private static final HashMap< Class< ? extends Entity >, String > attributeClass_to_name = new HashMap< Class< ? extends Entity >, String >();

	private static final HashMap< String, String > name_to_XmlIoClassName = new HashMap< String, String >();

	private static boolean buildWasCalled = false;

	private static void build()
	{
		buildWasCalled = true;
		try
		{
			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			final Index< ViewSetupAttributeIo > annotationIndex = Index.load( ViewSetupAttributeIo.class, classLoader );
			for ( final IndexItem< ViewSetupAttributeIo > item : annotationIndex )
			{
				name_to_XmlIoClassName.put( item.annotation().name(), item.className() );
				attributeClass_to_name.put( item.annotation().type(), item.annotation().name() );
			}
		}
		catch( final Exception e )
		{
			throw new RuntimeException( "problem accessing annotation index", e );
		}
	}

	public static String getNameForClass( final Class< ? extends Entity > klass )
	{
		if ( !buildWasCalled )
			build();

		Class< ? > k = klass;
		while ( k != null && Entity.class.isAssignableFrom( k ) )
		{
			final String name = attributeClass_to_name.get( k );
			if ( name != null )
				return name;
			k = k.getSuperclass();
		}
//		if ( klass.isAssignableFrom( Illumination.class ) )
//			return "illumination";
//		else if ( klass.isAssignableFrom( Channel.class ) )
//			return "channel";
//		else if ( klass.isAssignableFrom( Angle.class ) )
//			return "angle";

		return null;
	}

	public static XmlIoEntity< ? > createXmlIoForName( final String name )
	{
		if ( !buildWasCalled )
			build();

		final String className = name_to_XmlIoClassName.get( name );
		if ( className != null )
			try
			{
				return ( XmlIoEntity< ? > ) Class.forName( className ).newInstance();
			}
			catch ( final Exception e )
			{
				e.printStackTrace();
			}

		return new XmlIoEntity< Entity >( name, Entity.class );
	}

	/**
	 * Register an {@link XmlIoEntity} attribute IO class manually. This can be
	 * used in case scijava-common is not present or cannot be used for other
	 * reasons.
	 *
	 * For example I had the case where I wanted to use a jar build by Eclipse
	 * for a partially compiling project in Fiji. In this case (because it's
	 * build by eclipse, the annotation index was not present.
	 *
	 * @param xmlIoClass
	 *            the class to register.
	 */
	public static void registerManually( final Class< ? extends XmlIoEntity< ? > > xmlIoClass )
	{
		final ViewSetupAttributeIo annotation = xmlIoClass.getAnnotation( ViewSetupAttributeIo.class );
		if ( annotation != null )
		{
			final String attributeName = annotation.name();
			final Class< ? extends Entity > attributeClass = annotation.type();
			name_to_XmlIoClassName.put( attributeName, xmlIoClass.getName() );
			attributeClass_to_name.put( attributeClass, attributeName );
		}
	}
}
