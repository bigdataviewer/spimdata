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
package mpicbg.spim.data.generic.base;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.SpimDataInstantiationException;

import org.jdom2.Element;

/**
 * Writes and reads objects of a specific class as XML elements. This is for
 * sections in the XML that only occur once, i.e., &lt;ViewSetups&gt; or
 * &lt;ViewRegistrations&gt;.
 *
 * Derived classes should add the tag names of children that they handle to the
 * set {@link #handledTags}. When reading an (the) object from an XML element,
 * unhandled children of the element are stored. When writing an object to an XML
 * element these children will be added. This provides a way to read / modify /
 * write spimdata XML files without knowing the details of possible extensions.
 *
 * @param <T>
 *            the entity type
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class XmlIoSingleton< T >
{
	private final String tag;

	private final Constructor< T > ctor;

	protected final HashSet< String > handledTags;

	private final ArrayList< Element > unhandledContent;

	/**
	 * Construct a {@link XmlIoSingleton} that writes and reads objects of the
	 * specified {@link Class} as XML elements with the specified tag name. This
	 * takes care of unhandled tags.
	 *
	 * @param tag
	 *            the tag name of the XML elements that are written by this
	 *            {@link XmlIoEntity}.
	 * @param klass
	 *            the class of the objects that are read by this
	 *            {@link XmlIoEntity}.
	 */
	public XmlIoSingleton( final String tag, final Class< T > klass )
	{
		this.tag = tag;
		try
		{
			this.ctor = klass.getDeclaredConstructor();
		}
		catch ( final Exception e )
		{
			throw new IllegalArgumentException( klass.getSimpleName() + " must have a no-argument constructor.", e );
		}
		ctor.setAccessible( true );
		handledTags = new HashSet< String >();
		unhandledContent = new ArrayList< Element >();
	}

	/**
	 * Get the tag name of the XML element that is written by this
	 * {@link XmlIoSingleton}.
	 *
	 * @return the tag name of the XML elements that is written.
	 */
	public String getTag()
	{
		return tag;
	}

	/**
	 * Create an XML element from the given object.
	 */
	protected Element toXml()
	{
		final Element elem = new Element( tag );
		for ( final Element c : unhandledContent )
			elem.addContent( c.clone() );
		return elem;
	}

	/**
	 * Create an object from the given element. Unhandled children of the
	 * element are stored and added to any XML element that is
	 * {@link #toXml() created}.
	 *
	 * Calling this method clears all unhandled content from previous
	 * invocations.
	 */
	protected T fromXml( final Element elem ) throws SpimDataException
	{
		T entity;
		try
		{
			entity = ctor.newInstance();
		}
		catch ( final Exception e )
		{
			throw new SpimDataInstantiationException( "Cannot instantiate " + ctor.getDeclaringClass().getName(), e );
		}

		unhandledContent.clear();
		for ( final Element c : elem.getChildren() )
			if ( !handledTags.contains( c.getName() ) )
				unhandledContent.add( c.clone() );

		return entity;
	}

}
