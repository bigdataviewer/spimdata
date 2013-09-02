package mpicbg.spim.data.newstuff;

//import static mpicbg.spim.data.newstuff.SpimDataXmlKeys.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mpicbg.spim.data.newstuff.sequence.SequenceDescription;
import mpicbg.spim.data.newstuff.sequence.TimePoint;
import mpicbg.spim.data.newstuff.sequence.ViewSetup;
import mpicbg.spim.data.newstuff.sequence.XmlIoSequenceDescription;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SpimDataExample
{
	public static class MySpimData extends SpimData
	{

		public MySpimData( final String xmlFilename ) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
		{
			super( xmlFilename );
			loadSequenceDescription();
		}

		protected SequenceDescription< TimePoint, ViewSetup > loadSequenceDescription() throws InstantiationException, IllegalAccessException, ClassNotFoundException
		{
			System.out.println( basePath );

			final XmlIoSequenceDescription< TimePoint, ViewSetup > reader = XmlIoSequenceDescription.createDefault();
			final NodeList nodes = root.getElementsByTagName( reader.getTagName() );
			if ( nodes.getLength() > 0 )
			{
				final SequenceDescription< TimePoint, ViewSetup > desc = reader.fromXml( ( Element ) nodes.item(0), basePath );
				System.out.println( desc.numTimePoints() * desc.numViewSetups() );
			}

			return null;
		}
	}
	public static void main( final String[] args ) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final String xmlFilename = ClassLoader.getSystemResource( "example.xml" ).getPath();
		new MySpimData( xmlFilename );
	}
}
