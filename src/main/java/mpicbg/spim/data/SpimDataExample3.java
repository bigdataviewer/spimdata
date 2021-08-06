/*-
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
package mpicbg.spim.data;

import mpicbg.spim.data.sequence.MissingViews;
import mpicbg.spim.data.sequence.ViewDescription;
import mpicbg.spim.data.sequence.ViewId;

public class SpimDataExample3 {

	public static void main( final String[] args ) throws Exception
	{
		// load SpimData from xml file
		final String xmlFilename = "/Users/preibischs/Documents/Microscopy/SPIM/HisYFP-SPIM/dataset_missing_no_imgloader.xml";

		//final XmlIoSpimData< TimePoint, ViewSetupBeads > io = XmlIoSpimData.createDefault();
		//final XmlIoSpimData< TimePoint, ViewSetupBeads > io = XmlIo.createDefaultIo();

		//final SpimData< TimePoint, ViewSetupBeads > spimData = io.load( xmlFilename );

		final XmlIoSpimData io = new XmlIoSpimData();

		final SpimData spimData = io.load( xmlFilename );

		MissingViews m = spimData.getSequenceDescription().getMissingViews();

		for ( final ViewId view : m.getMissingViews() )
			System.out.println( "Missing: " + view.getTimePointId() + " " + view.getViewSetupId() );

		for ( final ViewDescription vd : spimData.getSequenceDescription().getViewDescriptions().values() )
			System.out.println( "Present: " + vd.isPresent() + " -- " + vd.getTimePointId() + " " + vd.getViewSetupId() );

		/*
		// save SpimData to xml file
		io.save( spimData, "/Users/preibischs/Documents/Microscopy/SPIM/HisYFP-SPIM/example_fromdialog2.xml" );
		
		// write SpimData into a xml Document
		final Document doc = new Document( io.toXml( spimData, new File(".") ) );

		// output Document to System.out
		new XMLOutputter( Format.getPrettyFormat() ).output( doc, System.out );
		*/
	}

}
