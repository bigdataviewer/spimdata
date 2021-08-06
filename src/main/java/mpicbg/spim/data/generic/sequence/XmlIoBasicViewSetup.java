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

import static mpicbg.spim.data.XmlKeys.NAME_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWSETUP_ATTRIBUTES_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWSETUP_SIZE_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWSETUP_TAG;
import static mpicbg.spim.data.XmlKeys.VIEWSETUP_VOXELSIZE_TAG;
import static mpicbg.spim.data.XmlKeys.VOXELDIMENSIONS_SIZE_TAG;
import static mpicbg.spim.data.XmlKeys.VOXELDIMENSIONS_UNIT_TAG;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.XmlKeys;
import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.base.XmlIoEntity;
import mpicbg.spim.data.sequence.Channel;
import mpicbg.spim.data.sequence.FinalVoxelDimensions;
import mpicbg.spim.data.sequence.VoxelDimensions;
import mpicbg.spim.data.sequence.XmlIoChannel;

import org.jdom2.Element;

public class XmlIoBasicViewSetup< T extends BasicViewSetup > extends XmlIoEntity< T >
{
	public XmlIoBasicViewSetup( final Class< T > klass )
	{
		super( VIEWSETUP_TAG, klass );
		handledTags.add( NAME_TAG );
		handledTags.add( VIEWSETUP_SIZE_TAG );
		handledTags.add( VIEWSETUP_VOXELSIZE_TAG );
		handledTags.add( VIEWSETUP_ATTRIBUTES_TAG );
	}

	/**
	 * Maps attribute tag name to map from id to entity.
	 */
	protected HashMap< String, HashMap< Integer, ? extends Entity > > attributeMap;

	/**
	 * Set the attribute map. This is a map from attribute tag name to a map
	 * from id to entity.
	 *
	 * For example, the key {@value XmlKeys#CHANNEL_TAG} (
	 * {@link XmlIoChannel#getTag()}) maps to a map from channel id to
	 * {@link Channel}.
	 */
	public void setAttributeMap( final HashMap< String, HashMap< Integer, ? extends Entity > > attributeMap )
	{
		this.attributeMap = attributeMap;
	}

	@Override
	public Element toXml( final T setup )
	{
		final Element elem = super.toXml( setup );
		if ( setup.hasName() )
			elem.addContent( XmlHelpers.textElement( NAME_TAG, setup.getName() ) );
		if ( setup.hasSize() )
			elem.addContent( XmlHelpers.dimensionsElement( VIEWSETUP_SIZE_TAG, setup.getSize() ) );
		if ( setup.hasVoxelSize() )
			elem.addContent( voxelDimensionsElement( VIEWSETUP_VOXELSIZE_TAG, setup.getVoxelSize() ) );

		final Map< String, Entity > attributes = setup.getAttributes();
		if ( !attributes.isEmpty() )
		{
			final Element as = new Element( VIEWSETUP_ATTRIBUTES_TAG );
			for ( final Entry< String, Entity > entry : attributes.entrySet() )
				as.addContent( XmlHelpers.intElement( entry.getKey(), entry.getValue().getId() ) );
			elem.addContent( as );
		}

		return elem;
	}

	@Override
	public T fromXml( final Element elem ) throws SpimDataException
	{
		final T setup = super.fromXml( elem );
		setup.setName( XmlHelpers.getText( elem, NAME_TAG, null ) );
		setup.setSize( XmlHelpers.getDimensions( elem, VIEWSETUP_SIZE_TAG, null ) );
		setup.setVoxelSize( getVoxelDimensions( elem, VIEWSETUP_VOXELSIZE_TAG, null ) );

		final Element as = elem.getChild( VIEWSETUP_ATTRIBUTES_TAG );
		final HashMap< String, Entity > attributes = new HashMap< String, Entity >();
		if ( as != null )
		{
			for ( final Element a : as.getChildren() )
			{
				final String name = a.getName();
				final int id = Integer.parseInt( a.getTextTrim() );
				final Entity attribute = attributeMap.get( name ).get( id );
				attributes.put( name, attribute );
			}
		}
		setup.setAttributes( attributes );

		return setup;
	}

	protected static Element voxelDimensionsElement( final String name, final VoxelDimensions voxelSize )
	{
		final Element elem = new Element( name );
		final double[] array = new double[ voxelSize.numDimensions() ];
		voxelSize.dimensions( array );
		elem.addContent( XmlHelpers.textElement( VOXELDIMENSIONS_UNIT_TAG, voxelSize.unit() ) );
		elem.addContent( XmlHelpers.doubleArrayElement( VOXELDIMENSIONS_SIZE_TAG, array ) );
		return elem;
	}

	protected static VoxelDimensions getVoxelDimensions( final Element parent, final String name )
	{
		final Element elem = parent.getChild( name );
		final String unit = XmlHelpers.getText( elem, VOXELDIMENSIONS_UNIT_TAG );
		final double[] size = XmlHelpers.getDoubleArray( elem, VOXELDIMENSIONS_SIZE_TAG );
		return new FinalVoxelDimensions( unit, size );
	}

	protected static VoxelDimensions getVoxelDimensions( final Element parent, final String name, final VoxelDimensions defaultValue )
	{
		return parent.getChild( name ) == null ? defaultValue : getVoxelDimensions( parent, name );
	}
}
