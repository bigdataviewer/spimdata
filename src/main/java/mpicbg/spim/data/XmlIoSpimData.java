package mpicbg.spim.data;

import static mpicbg.spim.data.SpimDataXmlKeys.BASEPATH_TAG;
import static mpicbg.spim.data.SpimDataXmlKeys.SPIMDATA_TAG;
import static mpicbg.spim.data.SpimDataXmlKeys.SPIMDATA_VERSION_ATTRIBUTE_DEFAULT;
import static mpicbg.spim.data.SpimDataXmlKeys.SPIMDATA_VERSION_ATTRIBUTE_NAME;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import mpicbg.spim.data.registration.ViewRegistrations;
import mpicbg.spim.data.registration.XmlIoViewRegistrations;
import mpicbg.spim.data.sequence.SequenceDescription;
import mpicbg.spim.data.sequence.TimePoint;
import mpicbg.spim.data.sequence.ViewSetup;
import mpicbg.spim.data.sequence.XmlIoSequenceDescription;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlIoSpimData< T extends TimePoint, V extends ViewSetup >
{
	protected final XmlIoSequenceDescription< T, V > xmlSequenceDescription;

	protected final XmlIoViewRegistrations xmlViewRegistrations;

	public XmlIoSpimData(
			final XmlIoSequenceDescription< T, V > xmlSequenceDescription,
			final XmlIoViewRegistrations xmlViewRegistrations )
	{
		this.xmlSequenceDescription = xmlSequenceDescription;
		this.xmlViewRegistrations = xmlViewRegistrations;
	}

	public static XmlIoSpimData< TimePoint, ViewSetup > createDefault()
	{
		return new XmlIoSpimData< TimePoint, ViewSetup >( XmlIoSequenceDescription.createDefault(), new XmlIoViewRegistrations() );
	}

	public SpimData< T, V > load( final String xmlFilename ) throws ParserConfigurationException, SAXException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final File xmlFile = new File( xmlFilename );

		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Document dom = db.parse( xmlFilename );
		final Element root = dom.getDocumentElement();

		if ( root.getNodeName() != SPIMDATA_TAG )
			throw new RuntimeException( "expected <" + SPIMDATA_TAG + "> root element. wrong file?" );

		final File basePath = loadBasePath( root, xmlFile );

		return fromXml( root, basePath );
	}

	public void save( final SpimData< T, V > spimData, final String xmlFilename ) throws ParserConfigurationException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, TransformerFactoryConfigurationError, TransformerException
	{
		final Document doc = XmlHelpers.newXmlDocument();
		final File xmlFileDirectory = new File( xmlFilename ).getParentFile();
		doc.appendChild( toXml( doc, spimData, xmlFileDirectory ) );
		XmlHelpers.writeXmlDocument( doc, xmlFilename );
	}

	protected File loadBasePath( final Element root, final File xmlFile ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		File xmlFileParentDirectory = xmlFile.getParentFile();
		if ( xmlFileParentDirectory == null )
			xmlFileParentDirectory = new File( "." );
		return XmlHelpers.loadPath( root, BASEPATH_TAG, ".", xmlFileParentDirectory );
	}

	protected String getVersion( final Element root )
	{
		final String versionAttr = root.getAttribute( SPIMDATA_VERSION_ATTRIBUTE_NAME );
		final String version;
		if ( versionAttr.isEmpty() )
		{
			System.out.println( "<" + SPIMDATA_TAG + "> does not specify \"" + SPIMDATA_VERSION_ATTRIBUTE_NAME + "\" attribute." );
			System.out.println( "assuming \"" + SPIMDATA_VERSION_ATTRIBUTE_DEFAULT + "\"" );
			version = SPIMDATA_VERSION_ATTRIBUTE_DEFAULT;
		}
		else
			version = versionAttr;
		return version;
	}

	public SpimData< T, V > fromXml( final Element root, final File basePath ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
//		String version = getVersion( root );

		NodeList nodes = root.getElementsByTagName( xmlSequenceDescription.getTagName() );
		if ( nodes.getLength() == 0 )
			throw new IllegalArgumentException( "no <" + xmlSequenceDescription.getTagName() + "> element found." );
		final SequenceDescription< T, V > seq = xmlSequenceDescription.fromXml( ( Element ) nodes.item( 0 ), basePath );

		nodes = root.getElementsByTagName( xmlViewRegistrations.getTagName() );
		if ( nodes.getLength() == 0 )
			throw new IllegalArgumentException( "no <" + xmlViewRegistrations.getTagName() + "> element found." );
		final ViewRegistrations reg = xmlViewRegistrations.fromXml( ( Element ) nodes.item( 0 ) );

		return new SpimData< T, V >( basePath, seq, reg );
	}

	public Element toXml( final Document doc, final SpimData< T, V > spimData, final File xmlFileDirectory ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		final Element root = doc.createElement( SPIMDATA_TAG );
		root.setAttribute( SPIMDATA_VERSION_ATTRIBUTE_NAME, SPIMDATA_VERSION_ATTRIBUTE_DEFAULT );
		root.appendChild( XmlHelpers.pathElement( doc, "BasePath", spimData.getBasePath(), xmlFileDirectory ) );
		root.appendChild( xmlSequenceDescription.toXml( doc, spimData.getSequenceDescription(), spimData.getBasePath() ) );
		root.appendChild( xmlViewRegistrations.toXml( doc, spimData.getViewRegistrations() ) );
		return root;
	}
}
