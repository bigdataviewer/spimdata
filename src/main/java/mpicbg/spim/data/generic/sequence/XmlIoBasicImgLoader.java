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

import java.io.File;
import java.net.URI;

import org.jdom2.Element;

public interface XmlIoBasicImgLoader< T extends BasicImgLoader >
{
	Element toXml( final T imgLoader, final File basePath );

	/**
	 * Subclasses that construct ImgLoaders that are able to read from sources
	 * other than local files should override this method. The default
	 * implementation falls back to {@link #toXml(BasicImgLoader, File)}, but
	 * this will fail if the {@code basePathURI} does not refer to a local file.
	 */
	default Element toXml( final T imgLoader, final URI basePathURI )
	{
		final File basePath;
		try
		{
			basePath = basePathURI == null ? null : new File( basePathURI );
		}
		catch ( final IllegalArgumentException e )
		{
			throw new RuntimeException( "Could not convert base path \"" + basePathURI + "\" into File, and " + this.getClass().getName() + " does not handle base path URIs.", e );
		}
		return toXml( imgLoader, basePath );
	}

	T fromXml( final Element elem, final File basePath, AbstractSequenceDescription< ?, ?, ? > sequenceDescription );

	/**
	 * Subclasses that construct ImgLoaders that are able to read from sources
	 * other than local files should override this method. The default
	 * implementation falls back to {@link #fromXml(Element, File,
	 * AbstractSequenceDescription)}, but this will fail if the {@code
	 * basePathURI} does not refer to a local file.
	 */
	default T fromXml( final Element elem, final URI basePathURI, AbstractSequenceDescription< ?, ?, ? > sequenceDescription )
	{
		final File basePath;
		try
		{
			basePath = basePathURI == null ? null : new File( basePathURI );
		}
		catch ( final IllegalArgumentException e )
		{
			throw new RuntimeException( "Could not convert base path \"" + basePathURI + "\" into File, and " + this.getClass().getName() + " does not handle base path URIs.", e );
		}
		return fromXml( elem, basePath, sequenceDescription );
	}
}
