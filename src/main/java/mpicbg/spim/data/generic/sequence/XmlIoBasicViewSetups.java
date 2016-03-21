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
package mpicbg.spim.data.generic.sequence;

import static mpicbg.spim.data.XmlKeys.ATTRIBUTES_NAME_ATTRIBUTE_NAME;
import static mpicbg.spim.data.XmlKeys.ATTRIBUTES_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWSETUPS_TAG;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlKeys;
import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.base.ViewSetupAttributes;
import mpicbg.spim.data.generic.base.XmlIoEntity;
import mpicbg.spim.data.generic.base.XmlIoEntityMap;

import org.jdom2.Element;

/**
 * Writes and reads collections of a specific {@link BasicViewSetup} subclass as
 * an XML section. Each {@link BasicViewSetup} contains a set of
 * {@link BasicViewSetup#getAttributes() attributes}. All occurring attributes
 * types are collected. For each attribute type, an &lt;
 * {@value XmlKeys#ATTRIBUTES_TAG}&gt; section is added as a child, containing
 * all values of the attribute type that occur.
 *
 * @param <T>
 *            the {@link BasicViewSetup} type
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class XmlIoBasicViewSetups< T extends BasicViewSetup > extends XmlIoEntityMap< T >
{
	private final XmlIoBasicViewSetup< T > xmlIoViewSetup;

	/**
	 * Maps attribute name to reader/writer for collections of that attribute
	 * type.
	 */
	private final Map< String, XmlIoEntityMap< ? > > attributeNameToXmlIoMap;

	public XmlIoBasicViewSetups( final Class< T > klass )
	{
		this( klass, new XmlIoBasicViewSetup< T >( klass ) );
	}

	private XmlIoBasicViewSetups( final Class< T > klass, final XmlIoBasicViewSetup< T > xmlIoViewSetup )
	{
		super( VIEWSETUPS_TAG, klass, xmlIoViewSetup );
		this.xmlIoViewSetup = xmlIoViewSetup;
		this.attributeNameToXmlIoMap = new HashMap< String, XmlIoEntityMap< ? > >();

		handledTags.add( ATTRIBUTES_TAG );
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Override
	public Element toXml( final Collection< T > setups ) throws SpimDataException
	{
		final Element elem = super.toXml( setups );

		final HashMap< String, HashMap< Integer, Entity > > attributeMap = new HashMap< String, HashMap< Integer, Entity > >();
		for ( final T setup : setups )
			for ( final Entry< String, Entity > entry : setup.getAttributes().entrySet() )
			{
				final String name = entry.getKey();
				final Entity attribute = entry.getValue();
				HashMap< Integer, Entity > map = attributeMap.get( name );
				if ( map == null )
				{
					map = new HashMap< Integer, Entity >();
					attributeMap.put( name, map );
				}
				map.put( attribute.getId(), attribute );
			}

		for ( final Entry< String, HashMap< Integer, Entity > > entry : attributeMap.entrySet() )
		{
			final String name = entry.getKey();
			final HashMap< Integer, Entity > map = entry.getValue();

			final XmlIoEntityMap< ? > xmlIoAttributes = getXmlIoForAttributeName( name );
			final Element c = xmlIoAttributes.toXml( ( Map ) map );
			c.setAttribute( ATTRIBUTES_NAME_ATTRIBUTE_NAME, name );
			elem.addContent( c );
		}

		return elem;
	}

	@Override
	public HashMap< Integer, T > fromXml( final Element elem ) throws SpimDataException
	{
		// maps attribute name (e.g. "channel") to map from entity id to entity (e.g. id -> Channel)
		final HashMap< String, HashMap< Integer, ? extends Entity > > attributeMap = new HashMap< String, HashMap< Integer, ? extends Entity > >();

		for ( final Element c : elem.getChildren( ATTRIBUTES_TAG ) )
		{
			final String name = c.getAttributeValue( ATTRIBUTES_NAME_ATTRIBUTE_NAME );
			final XmlIoEntityMap< ? > xmlIoAttributes = getXmlIoForAttributeName( name );
			attributeMap.put( name, xmlIoAttributes.fromXml( c ) );
		}

		xmlIoViewSetup.setAttributeMap( attributeMap );
		return super.fromXml( elem );
	}

	/**
	 * Get reader/writer for a collection of attributes of a specific type.
	 * These are constructed by {@link ViewSetupAttributes} and cached in
	 * {@link #attributeNameToXmlIoMap}.
	 *
	 * @param name
	 *            attribute name
	 * @return reader/writer for a collection of attributes of the specific type
	 *         associated to name
	 * @throws SpimDataException
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	private XmlIoEntityMap< ? > getXmlIoForAttributeName( final String name ) throws SpimDataException
	{
		XmlIoEntityMap< ? > xmlIoAttributes = attributeNameToXmlIoMap.get( name );
		if ( xmlIoAttributes == null )
		{
			final XmlIoEntity< ? > xmlIoAttribute = ViewSetupAttributes.createXmlIoForName( name );
			xmlIoAttributes = new XmlIoEntityMap( ATTRIBUTES_TAG, xmlIoAttribute.getEntityClass(), xmlIoAttribute );
			attributeNameToXmlIoMap.put( name, xmlIoAttributes );
		}
		return xmlIoAttributes;
	}
}
