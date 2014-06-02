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
