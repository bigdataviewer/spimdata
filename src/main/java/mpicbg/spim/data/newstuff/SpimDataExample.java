package mpicbg.spim.data.newstuff;

//import static mpicbg.spim.data.newstuff.SpimDataXmlKeys.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import mpicbg.spim.data.SequenceDescription;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class SpimDataExample
{
	public static class MySpimData extends SpimData
	{
		public MySpimData( final String xmlFilename ) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
		{
			super( xmlFilename );
		}

		@Override
		protected File loadBasePath( final Element root ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
		{
			System.out.println( "MySpimData.loadBasePath" );
			return super.loadBasePath( root );
		}

		@Override
		protected SequenceDescription loadSequenceDescription( final Element root )
		{
			System.out.println( basePath );
			return super.loadSequenceDescription( root );
		}
	}
	public static void main( final String[] args ) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final String xmlFilename = "/Users/pietzsch/Desktop/example.xml";
		final SpimData sd = new MySpimData( xmlFilename );
	}
}
