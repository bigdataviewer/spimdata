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
package mpicbg.spim.data.generic.base;

import static mpicbg.spim.data.XmlKeys.NAME_TAG;
import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.XmlKeys;

import org.jdom2.Element;

/**
 * {@link XmlIoEntity} for {@link NamedEntity} that also handles &lt;{@value XmlKeys#NAME_TAG}&gt;
 *
 * @param <T>
 *            the {@link NamedEntity} type
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class XmlIoNamedEntity< T extends NamedEntity > extends XmlIoEntity< T >
{
	public XmlIoNamedEntity( final String tag, final Class< T > klass )
	{
		super( tag, klass );
		handledTags.add( NAME_TAG );
	}

	@Override
	public Element toXml( final T entity )
	{
		final Element elem = super.toXml( entity );
		elem.addContent( XmlHelpers.textElement( NAME_TAG, entity.getName() ) );
		return elem;
	}

	@Override
	public T fromXml( final Element elem ) throws SpimDataException
	{
		final T entity = super.fromXml( elem );
		entity.setName( elem.getChildText( NAME_TAG ) );
		return entity;
	}
}
