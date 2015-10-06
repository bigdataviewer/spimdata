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
package mpicbg.spim.data;

//import static mpicbg.spim.data.newstuff.SpimDataXmlKeys.*;

import java.io.File;

import mpicbg.spim.data.sequence.TimePoint;
import mpicbg.spim.data.sequence.TimePointsPattern;
import mpicbg.spim.data.sequence.ViewSetup;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class SpimDataExample
{
	public static void main( final String[] args ) throws Exception
	{
		// catch the Exception so that we can run the test without having a implementation of ImgLoader
//		XmlIoSequenceDescription.catchClassNotFoundException = true;

		// load SpimData from xml file
		final String xmlFilename = ClassLoader.getSystemResource( "example_timepointpattern.xml" ).getPath();
		final XmlIoSpimData io = new XmlIoSpimData();
		final SpimData spimData = io.load( xmlFilename );

		System.out.println( "Num Timepoints: " + spimData.getSequenceDescription().getTimePoints().size() );
		
		if ( spimData.getSequenceDescription().getTimePoints() instanceof TimePointsPattern )
			System.out.println( ( (TimePointsPattern)spimData.getSequenceDescription().getTimePoints() ).getPattern() );
		
		for ( final TimePoint t : spimData.getSequenceDescription().getTimePoints().getTimePointsOrdered() )
		{
			System.out.println( "\nTimepoint: " + t.getId() );
			
			for ( final ViewSetup setup : spimData.getSequenceDescription().getViewSetupsOrdered() )
			{
				System.out.println( "Setup: " + setup.getId() );
				if ( setup.hasName() )
					System.out.println( setup.getName() );
				
				System.out.println( spimData.getViewRegistrations().getViewRegistration( t.getId(), setup.getId() ).getModel() );
			}
		}
		
		// save SpimData to xml file
		io.save( spimData, "example_new.xml" );

		// write SpimData into a xml Document
		final Document doc = new Document( io.toXml( spimData, new File(".") ) );

		// output Document to System.out
		new XMLOutputter( Format.getPrettyFormat() ).output( doc, System.out );
	}
}
