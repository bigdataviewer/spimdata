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
package mpicbg.spim.data.generic;

import java.io.File;

import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;
import mpicbg.spim.data.registration.ViewRegistrations;

public class AbstractSpimData< S extends AbstractSequenceDescription< ?, ?, ? > >
{
	/**
	 * Relative paths in the XML should be interpreted with respect to this.
	 */
	private File basePath;

	private S sequenceDescription;

	private ViewRegistrations viewRegistrations;

	public AbstractSpimData( final File basePath, final S sequenceDescription, final ViewRegistrations viewRegistrations )
	{
		this.basePath = basePath;
		this.sequenceDescription = sequenceDescription;
		this.viewRegistrations = viewRegistrations;
	}

	/**
	 * Get the base path of the sequence. Relative paths in the XML sequence
	 * description are interpreted with respect to this.
	 *
	 * @return the base path of the sequence
	 */
	public File getBasePath()
	{
		return basePath;
	}

	public S getSequenceDescription()
	{
		return sequenceDescription;
	}

	public ViewRegistrations getViewRegistrations()
	{
		return viewRegistrations;
	}

	public void setBasePath( final File basePath )
	{
		this.basePath = basePath;
	}

	protected void setSequenceDescription( final S sequenceDescription )
	{
		this.sequenceDescription = sequenceDescription;
	}

	protected void setViewRegistrations( final ViewRegistrations viewRegistrations )
	{
		this.viewRegistrations = viewRegistrations;
	}

	protected AbstractSpimData()
	{}
}
